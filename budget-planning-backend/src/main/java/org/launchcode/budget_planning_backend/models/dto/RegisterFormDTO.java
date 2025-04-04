package org.launchcode.budget_planning_backend.models.dto;
import org.launchcode.budget_planning_backend.models.User;
import java.time.LocalDate;

public class RegisterFormDTO extends User {

    public RegisterFormDTO(String firstName, String lastName, LocalDate dateOfBirth, String email, String username, String password, String verifyPassword) {
        super(firstName, lastName, dateOfBirth, email, username, password, verifyPassword);
    }
}
