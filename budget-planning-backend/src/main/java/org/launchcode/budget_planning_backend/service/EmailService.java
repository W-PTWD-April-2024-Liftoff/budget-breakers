package org.launchcode.budget_planning_backend.service;

import org.launchcode.budget_planning_backend.models.Invitation;
import org.launchcode.budget_planning_backend.models.UserGroup;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final Map<String, Invitation> tokenStore = new ConcurrentHashMap<>();

    public void saveInvitation(String token, Invitation invitation) {
        tokenStore.put(token, invitation);
        System.out.println("Saved invitation with token: " + token);
    }

    public Optional<Invitation> findByToken(String token) {
        return Optional.ofNullable(tokenStore.get(token));
    }

    public void markAsUsed(String token) {
        Invitation invitation = tokenStore.get(token);
        if (invitation != null) {
            invitation.setUsed(true);
        }
    }

    public void sendEmailInvites(String email, String subject, String groupName, String groupDescription, UserGroup group) {
        System.out.println("Sending invitation to: " + email);

        try {
            String token = UUID.randomUUID().toString();
            String inviteLink = "http://localhost:5173/invite/accept?token=" + token;

            String body = "Hi, you are invited to join the group: " + groupName
                    + "\nDescription: " + groupDescription
                    + "\nPlease click the link below to join using the same email this invitation was sent to."
                    + "\n" + inviteLink;

            Invitation invitation = new Invitation();
            invitation.setEmail(email);
            invitation.setToken(token);
            invitation.setGroup(group);
            invitation.setExpiryDate(LocalDateTime.now().plusDays(7));
            saveInvitation(token, invitation);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);

            System.out.println("Invitation email sent to: " + email);
        } catch (Exception e) {
            System.out.println("Error sending email to " + email + ": " + e.getMessage());
        }
    }
}