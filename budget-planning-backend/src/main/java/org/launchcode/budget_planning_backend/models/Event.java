package org.launchcode.budget_planning_backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class Event extends AbstractEntity{

    private static int nextId = 1;

    @NotNull
    @NotBlank(message = "Budget amount is required")
    private double budget;

    @NotNull
    @NotBlank(message = "Location is required")
    @Size(min = 4, max = 50 , message = "Location must be  between 4 and 50 characters")
    private String location;

    private Date date;

    private Status status;

    private double earnings;

    @NotNull(message = "Group is required")
    private Group group;

    public Event(double budget, String location, Date date, Status status, double earnings, Group group) {
        this.budget = budget;
        this.location = location;
        this.date = date;
        this.status = status;
        this.earnings = earnings;
        this.group = group;
        this.setId(nextId);
        nextId++;
    }

    public Event(){}

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
