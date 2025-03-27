package org.launchcode.budget_planning_backend.models.dto;
import org.launchcode.budget_planning_backend.models.User;
import java.util.Date;

public class RegisterFormDTO extends User {

    public RegisterFormDTO(String firstName, String lastName, Date dateOfBirth, String email, String username, String password, String verifyPassword) {
        super(firstName, lastName, dateOfBirth, email, username, password, verifyPassword);
    }
}
