package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TodoRequestDTO {

    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    private String description;

    private Boolean completed;

    public TodoRequestDTO() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
}
