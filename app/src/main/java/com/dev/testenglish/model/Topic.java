package com.dev.testenglish.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Topic {
    @Id
    public long id;
    public String name;

}
