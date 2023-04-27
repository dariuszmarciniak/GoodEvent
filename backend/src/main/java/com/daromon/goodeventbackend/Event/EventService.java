
package com.daromon.goodeventbackend.Event;

import com.daromon.goodeventbackend.User.UserService;
import com.daromon.goodeventbackend.UserFavouriteEvent.UserFavorites;
import com.daromon.goodeventbackend.UserFavouriteEvent.UserFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFavoritesRepository userFavoritesRepository;

    public Boolean saveEvent(Event event) {
        eventRepository.save(event);
        return true;
    }

    public List<Event> getAllEvents(Long userId) {
        List<Event> events = eventRepository.findAll();
        List<UserFavorites> userFavorites = userService.getFavoriteEvents(userId);
        for (Event event : events) {
            for (UserFavorites userFavorite : userFavorites) {
                if (event.equals(userFavorite.getEvent())) {
                    event.setFavouritesStatus("1");
                }
            }
        }
        return events;
    }

    public Event getEvent(Long id) {
        return eventRepository.findEventById(id);
    }

    @Transactional
    public void delete(Long eventId) {

        userFavoritesRepository.deleteByEventId(eventId);
        eventRepository.deleteById(eventId);
    }
}

