package com.daromon.goodeventbackend.Security;

import com.daromon.goodeventbackend.Event.Event;
import com.daromon.goodeventbackend.UserFavouriteEvent.UserFavorites;

import java.util.List;

public class AuthResponse {

    private String username;
    private String accessToken;
    private Long idUser;
    private List<UserFavorites> favoriteEvents;
    public AuthResponse(String username, String accessToken, Long idUser, List<UserFavorites> favoriteEvents) {
        this.username = username;
        this.accessToken = accessToken;
        this.idUser = idUser;
        this.favoriteEvents = favoriteEvents;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public List<UserFavorites> getFavoriteEvents() {
        return favoriteEvents;
    }

    public void setFavoriteEvents(List<UserFavorites> favoriteEvents) {
        this.favoriteEvents = favoriteEvents;
    }
}