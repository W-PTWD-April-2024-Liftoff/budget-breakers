package org.launchcode.budget_planning_backend.service;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.budget_planning_backend.controllers.AuthenticationController;
import org.launchcode.budget_planning_backend.models.*;
import org.launchcode.budget_planning_backend.models.dto.UserGroupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import static org.launchcode.budget_planning_backend.models.DummyObjectsToBeDeleted.getUserByID;

@Service
public class UserGroupService {

    private final Logger logger = LoggerFactory.getLogger(UserGroupService.class);

    @Autowired
    AuthenticationController authenticationController;

    public final List<UserGroup> groupsList = new ArrayList<>();

    private final List<UserGroup> groupsByUser = new ArrayList<>();

    public void saveGroups(UserGroup group) {
        groupsList.add(group);
    }

    public List<UserGroup> getAllGroups() {return groupsList;}

    public UserGroup getGroupByID(int id) {
        for (UserGroup group : groupsList) {
            if(group.getId() == id) {
                return group;
            }
        }
        return null;
    }

    public List<UserGroup> getGroupsByUser (int userID) {
        for (UserGroup group : groupsList) {
            for (User user : group.getUsers()) {
                if(user.getId() == userID && !groupsByUser.contains(group)) {
                    groupsByUser.add(group);
                }
            }
        }
        return groupsByUser;
    }

    public UserGroup createNewGroup (UserGroupDTO groupDTO, HttpServletRequest request) {
        UserGroup group = new UserGroup(groupDTO.getName(), groupDTO.getDescription());
//        User user = authenticationController.getUserFromSession(request.getSession());
        User user = DummyObjectsToBeDeleted.getUserByID(1);
        if (user != null && user.getId() == 1) {
            group.addUsers(user);
        }
        logger.info("New Group: ".concat(group.toString()));
        return group;
    }

    public List<Chore> getChoresFromGroup(int groupID) {
        for (UserGroup group : groupsList) {
            if(group.getId() == groupID) {
                return group.getChores();
            }
        }
        return null;
    }

    public List<Event> getEventsFromGroup(int groupID) {
        for (UserGroup group : groupsList) {
            if(group.getId() == groupID) {
                return group.getEvents();
            }
        }
        return null;
    }

    public List<User> getUsersFromGroup(int groupID) {
        for (UserGroup group : groupsList) {
            if(group.getId() == groupID) {
                return group.getUsers();
            }
        }
        return null;
    }
}
