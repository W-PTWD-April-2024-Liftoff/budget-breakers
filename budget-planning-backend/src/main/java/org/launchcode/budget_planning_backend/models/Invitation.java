package org.launchcode.budget_planning_backend.models;

import java.time.LocalDateTime;

public class Invitation extends BaseAbstractEntity {

    private String token;
    private String email;
    private LocalDateTime expiryDate;
    private boolean used = false;
    private UserGroup group;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}
