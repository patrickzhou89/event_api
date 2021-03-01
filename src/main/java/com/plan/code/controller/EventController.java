package com.plan.code.controller;

import com.plan.code.dto.CreateEventDTO;
import com.plan.code.dto.EventDTO;
import com.plan.code.model.Event;
import com.plan.code.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController()
@RequestMapping("api/v1/events")
public class EventController {

    private EventService eventService;
    private ModelMapper modelMapper;

    @Autowired
    public EventController(EventService eventService, ModelMapper modelMapper){
        this.eventService = eventService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves id from eventservice, throws response status exception with 404 if not found
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public EventDTO getEvent(@PathVariable("id") int id){
        try {
            return convertToDTO(eventService.getEventById(id));
        }catch (EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event with id "+ id +" not found.", ex);
        }
    }

    /**
     * Creates a new event from createEventDTO
     * @param createEventDTO
     * @return
     */
    @PostMapping
    public EventDTO createEvent(@Valid @RequestBody CreateEventDTO createEventDTO){
        Event newEvent = new Event(createEventDTO.getName(), createEventDTO.getPublished());
        return convertToDTO(eventService.createEvent(newEvent));
    }

    /**
     * Converts entity to DTO
     * @param event
     * @return
     */
    private EventDTO convertToDTO(Event event){
        return modelMapper.map(event, EventDTO.class);
    }
}
