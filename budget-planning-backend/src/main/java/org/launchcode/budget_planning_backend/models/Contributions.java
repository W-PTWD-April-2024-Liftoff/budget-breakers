package org.launchcode.budget_planning_backend.models;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class Contributions extends BaseAbstractEntity{

    @NotNull
    private User user;

    @NotNull
    private Double amountOfContribution;

    @NotNull
    private Date date;

    @NotNull
    private Event event;

    @NotNull
    private String status;

    public Contributions() {}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public Double getAmountOfContribution() {return amountOfContribution;}

    public void setAmountOfContribution(Double amountOfContribution) {
        this.amountOfContribution = amountOfContribution;
    }

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public Event getEvent() {return event;}

    public void setEvent(Event event) {this.event = event;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}
}
