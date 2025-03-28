package org.launchcode.budget_planning_backend.models;

public class DetermineAccountType {

    public static void determineAccountType(User user) {
        if(AgeCalculator.calculateUserAge(user) >= 18) {
            user.setAccountType(AccountType.ADULT);
        } else {
            user.setAccountType(AccountType.MINOR);
        }
    }
}
