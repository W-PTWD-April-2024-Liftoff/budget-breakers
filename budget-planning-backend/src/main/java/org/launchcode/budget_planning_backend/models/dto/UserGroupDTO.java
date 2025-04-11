package org.launchcode.budget_planning_backend.models.dto;

import jakarta.validation.constraints.Email;
import org.launchcode.budget_planning_backend.models.*;

import java.util.List;

public class UserGroupDTO extends AbstractEntity {

    private User user;

    private Event event;

    private Chore chore;

    private List<String> emails;

//    private List<Email> userEmails;

    public UserGroupDTO() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Chore getChore() {
        return chore;
    }

    public void setChore(Chore chore) {
        this.chore = chore;
    }

    public List<String> getEmails() {
    return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

//    public List<Email> getUserEmails() {
//        return userEmails;
//    }
//
//    public void setUserEmails(List<Email> userEmails) {
//        this.userEmails = userEmails;
//    }
}
