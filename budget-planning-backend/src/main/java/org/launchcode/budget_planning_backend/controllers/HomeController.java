package org.launchcode.budget_planning_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.launchcode.budget_planning_backend.models.User;
import org.launchcode.budget_planning_backend.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Home")
@CrossOrigin
public class HomeController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping
    public ResponseEntity<String> home(HttpServletRequest request) {
        User user = authenticationController.getUserFromSession(request.getSession());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You need to be logged in to view this page.");
        }
        return ResponseEntity.ok("Homepage");
        }
    }
