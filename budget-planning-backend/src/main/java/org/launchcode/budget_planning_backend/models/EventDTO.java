package org.launchcode.budget_planning_backend.models;

import java.util.Date;

public class EventDTO {

    private int eventId;
    private String eventName;
    private double eventBudget;
    private String eventLocation;
    private String eventDescription;
    private String eventDate;
    private double eventEarnings;
    private String userGroupName;
    private boolean isBudgetAchieved;

    public EventDTO(){}

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public double getEventBudget() {
        return eventBudget;
    }

    public void setEventBudget(double eventBudget) {
        this.eventBudget = eventBudget;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public double getEventEarnings() {
        return eventEarnings;
    }

    public void setEventEarnings(double eventEarnings) {
        this.eventEarnings = eventEarnings;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public boolean isBudgetAchieved() {
        return isBudgetAchieved;
    }

    public void setBudgetAchieved(boolean budgetAchieved) {
        isBudgetAchieved = budgetAchieved;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventBudget=" + eventBudget +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventEarnings=" + eventEarnings +
                ", userGroupName='" + userGroupName + '\'' +
                ", isBudgetAchieved='" + isBudgetAchieved + '\'' +
                '}';
    }
}
