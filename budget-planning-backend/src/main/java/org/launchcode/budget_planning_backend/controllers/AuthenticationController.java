package org.launchcode.budget_planning_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
//import org.launchcode.budget_planning_backend.data.UserRepository;
import org.launchcode.budget_planning_backend.models.dto.LoginFormDTO;
import org.launchcode.budget_planning_backend.models.dto.RegisterFormDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.launchcode.budget_planning_backend.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class AuthenticationController {

    List<User> users = new ArrayList<>();
    Map<String, String> response = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

//    @Autowired
//    UserRepository userRepository;

    private static final String userSessionKey = "user";

    User user;

    public User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(userSessionKey);
    }
    //For persistence with database connection
//        Integer userId = (Integer) session.getAttribute(userSessionKey);
//        if (userId == null) {
//            return null;
//        }
//
////        Optional<User> user = userRepository.findById(userId);
////
////        if (user.isEmpty()) {
////            return null;
////        }
////
////        return user.get();
//
//    }
//
    private static void setUserInSession(HttpSession session, User user) {
//        session.setAttribute(userSessionKey, user.getId());
        session.setAttribute(userSessionKey, user);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> processRegistrationForm(@RequestBody @Valid RegisterFormDTO registerFormDTO,
                                                                       Errors errors, HttpServletRequest request) {

        HttpSession session = request.getSession(true); // Creates session if it doesn't exist already
        logger.info("Session ID Register: " + session.getId());

        if (errors.hasErrors()) {
            logger.info("Errors:" + errors);
            response.put("message", "Registration errors occurred");
            return ResponseEntity.badRequest().body(response);
        }

        //For persistence with database connection
//        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());
//
//        if (existingUser != null) {
//            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
//            model.addAttribute("title", "Register");
//            return "register";
//        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();

        if (!password.equals(verifyPassword)) {
            response.put("message", "Password confirmation error occurred");
            return ResponseEntity.badRequest().body(response);
        }

        User newUser = new User(registerFormDTO.getFirstName(),
                registerFormDTO.getLastName(),
                registerFormDTO.getDateOfBirth(),
                registerFormDTO.getEmail(),
                registerFormDTO.getUsername(),
                registerFormDTO.getPassword(),
                registerFormDTO.getVerifyPassword());
        users.add(newUser);
        setUserInSession(request.getSession(), newUser);
        logger.info("User stored in session: " + newUser.getUsername());

        response.put("message", "Registration successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> processLoginForm(@RequestBody @Valid LoginFormDTO loginFormDTO,

                                                                Errors errors, HttpServletRequest request) {

        logger.info("Session ID Login" .concat(request.getSession().getId()));
        // Conform session is retrieved properly
        HttpSession session = request.getSession(false);
        User sessionUser = getUserFromSession(session);

        if (session == null) {
            // If no session exists, create a new one
            session = request.getSession(true); // true creates a new session if no session exists
            logger.info("New session created with ID: " + session.getId());
        } else {
            logger.info("Session user: " + getUserFromSession(request.getSession()).getUsername());
            logger.info("Session ID Login: " + session.getId());
        }

        logger.info("New login attempt for username: " + loginFormDTO.getUsername());

        if (errors.hasErrors()) {
            response.put("message", "Validation errors occurred");
            return ResponseEntity.badRequest().body(response);
        }

            user = getUserFromSession(request.getSession());
        if (loginFormDTO.getUsername().equals(user.getUsername()) && (loginFormDTO.getPassword().equals(user.getPassword()))) {
                setUserInSession(session, user);
                response.put("message", "Login successful");
                return ResponseEntity.ok(response);
            }

                response.put("message", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            //For persistence with database connection
//        User theUser = userRepository.findByUsername(loginFormDTO.getUsername());
//
//        if (theUser == null) {
//            errors.rejectValue("username", "user.invalid", "The given username does not exist");
//            model.addAttribute("title", "Log In");
//            return "login";
//        }

//        String password = loginFormDTO.getPassword();
//
//        if (!theUser.isMatchingPassword(password)) {
//            errors.rejectValue("password", "password.invalid", "Invalid password");
//            model.addAttribute("title", "Log In");
//            return "login";
        }
//
//        setUserInSession(request.getSession(), theUser);

        //return "redirect:";
//    }

//logout functionality
//    @GetMapping("/logout")
//    public String ResponseEntity<Map<String, String>> logout(HttpServletRequest request){
//        HttpSession session = request.getSession(false);
//        if (sessions != null) {
//          session.invalidate();
//        }
//        request.getSession().invalidate();
//        response.put("message", "Logged out successfully");
//        return ResponseEntity.ok(response);
//    }

//}
