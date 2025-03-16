package org.launchcode.budget_planning_backend.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ChoreDto extends AbstractEntity {

    //private User user;

    //private Event event;

    //private Group group;

    @Positive(message = "Amount must be a positive number.")
    @NotNull(message = "Amount is required.")
    private Double amountOfEarnings;

    public ChoreDto() {}

    //public User getUser() {return user;}

    //public void setUser(User user) {this.user = user;}

    //public Event getEvent() {return event;}

    //public void setEvent(Event event) {this.event = event;}

    //public Group getGroup() {return group;}

    //public void setGroup(Group group) {this.group = group;}

    public Double getAmountOfEarnings() {return amountOfEarnings;}

    public void setAmountOfEarnings(Double amountOfEarnings) {
        this.amountOfEarnings = amountOfEarnings;
    }
}
