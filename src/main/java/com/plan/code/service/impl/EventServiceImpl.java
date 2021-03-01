package com.plan.code.service.impl;

import com.plan.code.model.Event;
import com.plan.code.repository.EventRepository;
import com.plan.code.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service("eventService")
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    /**
     * Attempts to retrieve the event by eventId, throws EntityNotFoundException if not found
     * @param eventId
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public Event getEventById(int eventId) throws EntityNotFoundException{
        return eventRepository.findById(eventId).orElseThrow(()-> new EntityNotFoundException(eventId+""));
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
}
