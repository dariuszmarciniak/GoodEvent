
package com.daromon.goodeventbackend.Event;

import com.daromon.goodeventbackend.Event.Event;

import com.daromon.goodeventbackend.Event.EventService;

import com.daromon.goodeventbackend.User.User;
import com.daromon.goodeventbackend.User.UserService;
import com.daromon.goodeventbackend.UserFavouriteEvent.UserFavorites;
import org.aspectj.apache.bcel.classfile.InnerClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/event/getEvents")
    public List<Event> getAllEvents(@RequestBody Long userId) {
        return eventService.getAllEvents(userId);
    }

    @PostMapping("/event/save")
    public Boolean save(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }

    @GetMapping("/event/{id}")
    public Event getEventById(@PathVariable("id") Long id) {
        return eventService.getEvent(id);
    }

    @DeleteMapping("/event/delete/{id}")
    public Boolean deleteEvent(@PathVariable("id") Long id) {
        eventService.delete(id);
        return true;
    }

}

