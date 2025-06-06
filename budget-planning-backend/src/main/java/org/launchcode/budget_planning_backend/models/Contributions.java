package org.launchcode.budget_planning_backend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * This class represents a contribution made to an event which can be a part of any group (A group of family members/friends/both.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Contributions extends BaseAbstractEntity{

    @ManyToOne
    @JoinColumn(name="user_id")
    @NotNull
    private User user;

    @NotNull
    private Double amountOfContribution;

    @NotNull
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @NotNull
   // @JsonBackReference
    private Event event;

    @NotNull
    private Status status;

    public Contributions() {
    }

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
                " user=" + user.getId()+user.getFirstName() +
                ", amountOfContribution=" + amountOfContribution +
                ", date=" + date +
                ", event=" + event +
                ", status=" + status +
                '}';
    }
}
