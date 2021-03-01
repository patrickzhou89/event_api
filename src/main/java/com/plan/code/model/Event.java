package com.plan.code.model;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table
public @Data
class Event {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private boolean published;

    @Column
    private OffsetDateTime createdAt;

    public Event(){

    }

    public Event(String name, boolean published){
        this.name = name;
        this.published = published;
        createdAt = OffsetDateTime.now();
    }
}
