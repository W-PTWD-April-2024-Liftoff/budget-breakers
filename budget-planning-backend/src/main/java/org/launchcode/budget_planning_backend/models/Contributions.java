package org.launchcode.budget_planning_backend.models;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

public class Contributions extends BaseAbstractEntity{

    @NotNull
    private User user;

    @NotNull
    private Double amountOfContribution;

    @NotNull
    private LocalDate date;

    @NotNull
    private Event event;

    @NotNull
    private Status status;

    public Contributions() {}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public Double getAmountOfContribution() {return amountOfContribution;}

    public void setAmountOfContribution(Double amountOfContribution) {
        this.amountOfContribution = amountOfContribution;
    }

    public LocalDate getDate() {return date;}

    public void setDate(LocalDate date) {this.date = date;}

    public Event getEvent() {return event;}

    public void setEvent(Event event) {this.event = event;}

    public Status getStatus() {return status;}

    public void setStatus(Status status) {this.status = status;}

    @Override
    public String toString() {
        return "Contributions{" +
                "id=" + getId() +
                " user=" + user +
                ", amountOfContribution=" + amountOfContribution +
                ", date=" + date +
                ", event=" + event +
                ", status=" + status +
                '}';
    }
}
