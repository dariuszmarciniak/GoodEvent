package com.daromon.goodeventbackend.UserFavouriteEvent;

import com.daromon.goodeventbackend.Event.Event;
import com.daromon.goodeventbackend.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserFavoritesRepository extends JpaRepository<UserFavorites, Long> {
    List<UserFavorites> findByUser(User user);
    void deleteAByUserIdAndEventId(Long userId, Long eventId);
    void deleteByEventId(Long eventId);
}