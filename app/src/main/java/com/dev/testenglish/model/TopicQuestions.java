package com.dev.testenglish.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class TopicQuestions {
   @Id
   public long id;
   public String question;
   public String answer;
   public long idTopic;
   public String myAnswer;//đáp an của tôi
   public String description;//mô tả
}
