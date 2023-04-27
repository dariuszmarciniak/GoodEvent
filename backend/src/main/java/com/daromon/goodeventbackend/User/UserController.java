
package com.daromon.goodeventbackend.User;


import com.daromon.goodeventbackend.Event.Event;
import com.daromon.goodeventbackend.Event.EventRepository;
import com.daromon.goodeventbackend.Security.AuthRequest;
import com.daromon.goodeventbackend.User.User;
import com.daromon.goodeventbackend.User.UserRepository;
import com.daromon.goodeventbackend.User.UserService;
import com.daromon.goodeventbackend.UserFavouriteEvent.UserFavorites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final EventRepository eventRepository;
    @Value("${secret.key}")
    private String KEY;

    public UserController(UserService userService, UserRepository userRepository, EventRepository eventRepository) {
        this.userService = userService;
        this.userRepository = userRepository;

        this.eventRepository = eventRepository;
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        return userService.login(authRequest);
    }

    @PostMapping("/user/register")
    public Boolean register(@RequestBody User user) {
        return userService.register(user.getUsername(), user.getPassword());
    }

    @Transactional
    @PostMapping("/user/updatePassword")
    public Boolean updatePassword(@RequestBody User user) {
        userService.updatePassword(user.getUsername(), user.getPassword());
        return true;
    }

    @GetMapping("/{userId}/favorite-events")
    public List<UserFavorites> getFavoriteEvents(@PathVariable Long userId) {
        return userService.getFavoriteEvents(userId);
    }

    @PostMapping("/{userId}/favoriteEvents/{eventId}")
    public boolean addFavoriteEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        userService.addEventToFavorites(userId, eventId);
        return true;
    }

    @DeleteMapping("/{userId}/delete/favoriteEvents/{eventId}")
    public boolean removeFavoriteEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        userService.removeEventFromFavorites(userId, eventId);
        return true;
    }


}

