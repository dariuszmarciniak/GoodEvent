package com.daromon.goodeventbackend.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
Event findEventById(Long id);
}

