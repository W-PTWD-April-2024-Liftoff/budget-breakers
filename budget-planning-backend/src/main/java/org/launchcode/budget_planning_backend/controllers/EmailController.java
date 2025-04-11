package org.launchcode.budget_planning_backend.controllers;

import jakarta.servlet.http.HttpSession;
import org.launchcode.budget_planning_backend.models.Invitation;
import org.launchcode.budget_planning_backend.models.User;
import org.launchcode.budget_planning_backend.service.EmailService;
import org.launchcode.budget_planning_backend.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/invite")
@CrossOrigin
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    UserGroupService userGroupService;

    @GetMapping("/accept")
    public ResponseEntity<String> acceptInvitation(@RequestParam String token, HttpSession session) {
        System.out.println("Received token: " + token);
        User authenticatedUser = (User) session.getAttribute("user");

        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to accept an invitation.");
        }

        Optional<Invitation> invitationOpt = emailService.findByToken(token);

        if (invitationOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid invitation token.");
        }

        Invitation invitation = invitationOpt.get();
        System.out.println("Found invitation: " + invitation.getToken());

        if (invitation.isExpired() || invitation.isUsed()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This invitation is expired or already used.");
        }

        if (!invitation.getEmail().equals(authenticatedUser.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This invitation is not for the logged-in user.");
        }

        int userGroupId = invitation.getGroup().getId();

        System.out.println("Id for group: " + userGroupId);
        userGroupService.addUsersToGroup(userGroupId, authenticatedUser);

        emailService.markAsUsed(token);
        return ResponseEntity.ok().build();
    }

}