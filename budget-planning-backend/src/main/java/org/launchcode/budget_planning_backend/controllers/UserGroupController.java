package org.launchcode.budget_planning_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.budget_planning_backend.models.User;
import org.launchcode.budget_planning_backend.models.UserGroup;
import org.launchcode.budget_planning_backend.models.dto.UserGroupDTO;
import org.launchcode.budget_planning_backend.service.EmailService;
import org.launchcode.budget_planning_backend.service.AuthenticationService;
import org.launchcode.budget_planning_backend.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value="/groups")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserGroupController {

    private final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    @Autowired
    UserGroupService groupService;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value="/create")
    public ResponseEntity<String> createNewGroup(@RequestBody UserGroupDTO userGroupDTO, HttpServletRequest request) {

        UserGroup group = groupService.createNewGroup(userGroupDTO, request);
        groupService.saveGroups(group);
        String groupName = userGroupDTO.getName();
        String groupDescription = userGroupDTO.getDescription();
        List<String> emails = userGroupDTO.getEmails();

        System.out.println("Emails received: " + emails);

        String subject = "You are invited to join " + groupName + " on Red, Green, VACAY!";

        for (String email : emails) {
            emailService.sendEmailInvites(email, subject, groupName, groupDescription, group);
        }
        return ResponseEntity.ok("Group created successfully!");
    }

    @GetMapping(value = "/{userID}/list")
    public ResponseEntity<List<UserGroup>> displayGroupsBySpecifiedUser(@PathVariable Integer userID, Integer groupID, HttpServletRequest request) {
        User currentUser = authenticationService.getCurrentUser(request);  // Use authenticated user
        if (groupID != null && !groupService.hasAccessToGroups(groupID, currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        }

        List<UserGroup> groupsByUser = groupService.getGroupsByUser(userID);
        if(groupsByUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList());
        }
        return ResponseEntity.ok(groupsByUser);
    }

    @GetMapping(value = "/{userID}/{groupID}")
    public ResponseEntity<UserGroup> displayGroupBySpecifiedID(@PathVariable Integer groupID, HttpServletRequest request) {
        User currentUser = authenticationService.getCurrentUser(request);
        if (!groupService.hasAccessToGroups(groupID, currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);  // If no access, return forbidden
        }

        UserGroup group = groupService.getGroupByID(groupID);
        if(group == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(group);
    }

    @PostMapping(value = "/{userID}/{groupID}/add-member")
    public void addMembersToGroup( @PathVariable Integer groupID, User user) {
        groupService.addUsersToGroup(groupID, user);
    }
}
