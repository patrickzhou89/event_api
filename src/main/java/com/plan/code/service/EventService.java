package com.plan.code.service;

import com.plan.code.model.Event;

import javax.persistence.EntityNotFoundException;

public interface EventService {

    Event getEventById(int eventId) throws EntityNotFoundException;
    Event createEvent(Event event);
}
