package org.launchcode.budget_planning_backend.models;

import java.util.ArrayList;
import java.util.List;

public class Group extends AbstractEntity{

    private static int nextId = 1;

    private final List<User> users = new ArrayList<>();

    private final List<Event> events = new ArrayList<>();

    private  final List<Chore> chores = new ArrayList<>();

    public Group(List<User> users, List<Event> events){
        this.setId(nextId);
        nextId++;
    }

    public Group(){}

    public List<User> getUsers() {
        return users;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Chore> getChores() {
        return chores;
    }
}
