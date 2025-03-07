package org.launchcode.budget_planning_backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Chore extends AbstractEntity{

    private User user;

    private Event event;

    private Contributions contribution;

    @NotNull
    @NotBlank(message = "A group selection is required.")
    private Group group;

    private String status;

    @NotNull
    @NotBlank(message = "Amount of earnings for this chore is required.")
    private Double amountOfEarnings;

    public Chore() {}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public Event getEvent() {return event;}

    public void setEvent(Event event) {this.event = event;}

    public Contributions getContribution() {return contribution;}

    public void setContribution(Contributions contribution) {
        this.contribution = contribution;
    }

    public Group getGroup() {return group;}

    public void setGroup(Group group) {this.group = group;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public Double getAmountOfEarnings() {return amountOfEarnings;}

    public void setAmountOfEarnings(Double amountOfEarnings) {
        this.amountOfEarnings = amountOfEarnings;
    }
}
