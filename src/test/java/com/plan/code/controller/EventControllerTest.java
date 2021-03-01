package com.plan.code.controller;

import com.plan.code.model.Event;
import com.plan.code.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    public void testGet_Success() throws Exception{
        int id = 1;

        Event event = new Event();
        event.setId(id);
        event.setName("Test Event");
        event.setCreatedAt(OffsetDateTime.now());
        event.setPublished(true);

        when(eventService.getEventById(id)).thenReturn(event);

        this.mockMvc.perform(
                get("/api/v1/events/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(event.getId())))
                .andExpect(jsonPath("$.name", is(event.getName())))
                .andExpect(jsonPath("$.createdAt", is(event.getCreatedAt().toString())))
                .andExpect(jsonPath("$.published", is(event.isPublished())));
    }

    @Test
    public void testGet_IdNotFound() throws Exception{
        when(eventService.getEventById(1)).thenThrow(new EntityNotFoundException("1"));
        int id=1;
        MvcResult result = this.mockMvc.perform(
                get("/api/v1/events/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();


        String content = result.getResponse().getErrorMessage();
        assertEquals("Event with id "+id+" not found.", content);
    }

    @Test
    public void testCreate_Success() throws Exception{
        int id = 1;
        Event event = new Event();
        event.setId(id);
        event.setName("Test Event");
        event.setCreatedAt(OffsetDateTime.now());
        event.setPublished(true);

        when(eventService.createEvent(any(Event.class))).thenReturn(event);

        this.mockMvc.perform(
                post("/api/v1/events/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Event\",\"published\": true }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(event.getId())))
                .andExpect(jsonPath("$.name", is(event.getName())))
                .andExpect(jsonPath("$.createdAt", is(event.getCreatedAt().toString())))
                .andExpect(jsonPath("$.published", is(event.isPublished())));
    }

    @Test
    public void testCreate_MissingFields() throws Exception{
        this.mockMvc.perform(
                post("/api/v1/events/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
