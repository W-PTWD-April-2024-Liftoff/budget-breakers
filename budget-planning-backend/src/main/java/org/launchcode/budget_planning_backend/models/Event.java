package org.launchcode.budget_planning_backend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Event extends AbstractEntity{

    @NotNull
//    @NotBlank(message = "Budget amount is required")
    private double budget;

    @NotNull
//    @NotBlank(message = "Location is required")
    @Size(min = 4, max = 50 , message = "Location must be  between 4 and 50 characters")
    private String location;

    private LocalDate date;

    private Status status;

    private double earnings;

    private boolean isBudgetReached;

    @OneToMany( mappedBy = "event", cascade = CascadeType.ALL)
    //@JsonManagedReference
    private final List<Contributions> contributions = new ArrayList<>();

    @ManyToOne
    @NotNull(message = "Group is required")
    // @JsonBackReference
    private UserGroup userGroup;

    public Event(String name, double budget, String location, String description, String date, Status status, double earnings, UserGroup group) {
        this.setName(name);
        this.setDescription(description);
        this.budget = budget;
        this.location = location;
        if(date.isBlank()) this.date = null; else this.date =LocalDate.now();
        this.status = status;
        this.earnings = earnings;
        this.userGroup = userGroup;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public List<Contributions> getContributions() {
        return contributions;
    }

    public void addContributions(Contributions contributions) {
        this.contributions.add(contributions);
        contributions.setEvent(this);
    }

    public boolean isBudgetReached() {
        return isBudgetReached;
    }

    public void setBudgetReached(boolean budgetReached) {
        isBudgetReached = budgetReached;
    }

    @Override
    public String toString() {
        return "Event{" +
                "Event ID= "+ this.getId()+
                ", Name= "+ this.getName()+
                ", budget= " + budget +
                ", location= '" + location + '\'' +
                ", Description= "+this.getDescription()+
                ", date= " + date +
                ", status= " + status +
                ", earnings= " + earnings +
                ", isBudgetReached= " + isBudgetReached +
//                ", group= " + userGroup.getId()+userGroup.getName() +
                '}';
    }
}
