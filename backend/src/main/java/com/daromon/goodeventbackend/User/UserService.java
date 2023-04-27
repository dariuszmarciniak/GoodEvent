package com.daromon.goodeventbackend.User;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.daromon.goodeventbackend.Event.Event;
import com.daromon.goodeventbackend.Event.EventRepository;
import com.daromon.goodeventbackend.Role.Role;
import com.daromon.goodeventbackend.Role.RoleRepository;
import com.daromon.goodeventbackend.Security.AuthRequest;
import com.daromon.goodeventbackend.Security.AuthResponse;
import com.daromon.goodeventbackend.UserFavouriteEvent.UserFavorites;
import com.daromon.goodeventbackend.UserFavouriteEvent.UserFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService {
    @Autowired
    private final UserRepository repository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final EventRepository eventRepository;
    @Autowired
    private final UserFavoritesRepository userFavoritesRepository;
    @Autowired
    private final PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private final AuthenticationManager authorizationManager;
    @Value("${secret.key}")
    private String KEY;
    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository, EventRepository eventRepository, UserFavoritesRepository userFavoritesRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, AuthenticationManager authorizationManager) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.eventRepository = eventRepository;
        this.userFavoritesRepository = userFavoritesRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.authorizationManager = authorizationManager;
    }

    @Transactional
    public Boolean register(String username, String password) {
        if (repository.findByUsernameIgnoreCase(username).isPresent()) {
            return false;
        }
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            roleRepository.save(userRole);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        repository.save(user);
        return true;
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authorizationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            User user = (User) authentication.getPrincipal();
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            String token = JWT.create()
                    .withSubject(user.getUsername())
                    .withIssuer("GoodEvent")
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);

            AuthResponse authResponse = new AuthResponse(user.getUsername(), token, user.getId(), getFavoriteEvents(user.getId()));
            return ResponseEntity.ok(authResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public void updatePassword(String username, String password)
    {
        User userFromDb = repository.findByUsername(username);
        userFromDb.setPassword(encoder.encode(password));
        repository.save(userFromDb);
    }


    public void addEventToFavorites(Long userId, Long eventId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + eventId));
        UserFavorites user_favorites = new UserFavorites(user, event);
        userFavoritesRepository.save(user_favorites);

    }

    @Transactional
    public void removeEventFromFavorites(Long userId, Long eventId) {
        userFavoritesRepository.deleteAByUserIdAndEventId(userId,eventId);
    }

    public List<UserFavorites> getFavoriteEvents(Long userId) {
        User user = repository.findById(userId).orElseThrow(EntityNotFoundException::new);
       return userFavoritesRepository.findByUser(user);

    }

}

