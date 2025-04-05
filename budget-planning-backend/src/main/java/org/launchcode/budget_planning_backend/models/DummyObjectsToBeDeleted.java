package org.launchcode.budget_planning_backend.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DummyObjectsToBeDeleted {

    private static List<UserGroup> dummyGroups = new ArrayList<>();
    private static final int OPENED_USER_GROUP_ID = 2;
    private static List<User> dummyUsers = new ArrayList<>();

//    private static void setUpDummyUserGroups() {
//        UserGroup group1 = new UserGroup("Smiths", "our family");
//        UserGroup group2 = new UserGroup("Adams family", "our family");
//        group1.setId(1);
//        group2.setId(2);
//        dummyGroups.add(group1);
//        dummyGroups.add(group2);
//    }

    public static UserGroup getGroupByName(String groupName) {
//        setUpDummyUserGroups();
        for (UserGroup group : dummyGroups) {
            if (group.getName().equalsIgnoreCase(groupName)) {
                return group;
            }
        }
        return null;
    }

    public static int getOpenedUserGroupId() {
        return OPENED_USER_GROUP_ID;
    }

    public static AccountType createDummyUser() {
        LocalDate date = LocalDate.of(2003, 5, 10);
        User user1 = new User("Cat", "Adams", date, "cat@cat.com", "catadams", "password", "password");
        AccountTypeUtil.determineAccountType(user1);
        return user1.getAccountType();
    }

    public static void setUpDummyUsers() {
        LocalDate date1 = LocalDate.of(2003, 5, 10);
        LocalDate date2 = LocalDate.of(2005, 3, 31);
        User user1 = new User("Cat", "Adams", date1, "cat@cat.com", "catadams", "password", "password");
        User user2 = new User("January", "Scaller", date2, "word@worker.com", "TheDoors", "password", "password");
        dummyUsers.add(user1);
        dummyUsers.add(user2);
    }

    public static User getUserByID(int userID) {
        setUpDummyUsers();
        for (User user : dummyUsers) {
            if(user.getId() == userID) {
                return user;
            }
        }
        return null;
    }

}
