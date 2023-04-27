package com.daromon.goodeventbackend.UserFavouriteEvent;

import com.daromon.goodeventbackend.Event.Event;
import com.daromon.goodeventbackend.User.User;

import javax.persistence.*;


@Entity
@Table(name = "user_favorites")

public class UserFavorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    public UserFavorites( User user,Event event) {
        this.event = event;
        this.user = user;
    }

    public UserFavorites() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }




}
