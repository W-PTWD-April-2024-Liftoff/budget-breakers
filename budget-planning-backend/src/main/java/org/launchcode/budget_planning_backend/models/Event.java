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
    private UserGroup userGroup;

    public Event(double budget, String location, Date date, Status status, double earnings, UserGroup userGroup) {
        this.budget = budget;
        this.location = location;
        this.date = date;
        this.status = status;
        this.earnings = earnings;
        this.userGroup = userGroup;
        this.setId(nextId);
        nextId++;
    }

    public Event(){}

    public void updateEventStatus() {
        if(this.getStatus().equals(Status.OPEN)) {
            if(this.earnings == this.budget) {
                setStatus(Status.COMPLETE);
            }
        }
    }

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

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
}
