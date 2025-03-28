package org.launchcode.budget_planning_backend.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class AgeCalculator {

    public static int calculateUserAge(User user) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthdate = LocalDate.of(user.getDateOfBirth().getYear(), user.getDateOfBirth().getMonth(), user.getDateOfBirth().getDayOfMonth());
        Period period = Period.between(birthdate,currentDate);
        int age = period.getYears();
        return age;
    }

}
