package org.launchcode.budget_planning_backend.models;


public class Chore extends AbstractEntity {

    private static int nextId = 0;

    private User user;

    private Event event;

    private Contributions contribution;

    private UserGroup userGroup;

    private Status status;

    private Double amountOfEarnings;

    public Chore(String name, String description, Double amountOfEarnings){
        setId(nextId++);
        setName(name);
        setDescription(description);
        this.amountOfEarnings = amountOfEarnings;
    }

    public Double getAmountOfEarnings() {
        return amountOfEarnings;
    }

    public void setAmountOfEarnings(Double amountOfEarnings) {
        this.amountOfEarnings = amountOfEarnings;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserGroup getGroup() {
        return userGroup;
    }

    public void setGroup(UserGroup group) {
        this.userGroup = group;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public Contributions getContribution() {
        return contribution;
    }

    public void setContribution(Contributions contribution) {
        this.contribution = contribution;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Chore{" + "id=" + getId() + ", " +
                "name=" + getName() +
                ", description=" + getDescription() +
                ", amountOfEarnings=" + getAmountOfEarnings() +
                ", status=" + getStatus() +
                ", user=" + user +
                ", event=" + event +
                ", contribution=" + contribution +
                ", group=" + userGroup.getName() + '}';
    }
}
