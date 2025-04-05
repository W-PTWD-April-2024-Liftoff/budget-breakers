package org.launchcode.budget_planning_backend.models;

import jakarta.validation.constraints.Email;

import java.util.ArrayList;
import java.util.List;

public class UserGroup extends AbstractEntity{

    private static int nextId = 1;

    private final List<User> users = new ArrayList<>();

    private final List<Event> events = new ArrayList<>();

    private  final List<Chore> chores = new ArrayList<>();

//    private List<Email> userEmails = new ArrayList<>();

    public UserGroup(String name, String description){
        setName(name);
        setDescription(description);
//        this.userEmails = userEmails;
        this.setId(nextId);
        nextId++;
    }

    public UserGroup(){}

    public List<User> getUsers() {
        return users;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Chore> getChores() {
        return chores;
    }

    public void addUsers(User user){
        this.users.add(user);
    }

    public void addEvents(Event event){
        this.events.add(event);
    }

    public void addChores(Chore chore){
        this.chores.add(chore);
    }

//    public void addEmails(Email email) {
//        this.userEmails.add(email);
//    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + getId() +
                "name=" + getName() +
                "description=" + getDescription() +
                "users=" + users +
                " events=" + events +
                " chores=" + chores +
                "}";
    }
}
