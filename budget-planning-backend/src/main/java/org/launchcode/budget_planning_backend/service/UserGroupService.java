package org.launchcode.budget_planning_backend.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import org.launchcode.budget_planning_backend.models.Chore;
import org.launchcode.budget_planning_backend.models.Event;
import org.launchcode.budget_planning_backend.models.User;
import org.launchcode.budget_planning_backend.models.UserGroup;
import org.launchcode.budget_planning_backend.models.dto.UserGroupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserGroupService {

    @Autowired
    AuthenticationService authenticationService;

    private final Logger logger = LoggerFactory.getLogger(UserGroupService.class);

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

    public UserGroup getGroupByName(String groupName) {
        for (UserGroup group : groupsList) {
            if(group.getName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }

    public List<UserGroup> getGroupsByUser (int userID) {
        List<UserGroup> groupsByUser = new ArrayList<>();
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
        User currentUser = authenticationService.getCurrentUser(request);
        if (currentUser != null) {
            group.addUsers(currentUser);
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

    public void addUsersToGroup(int groupID, User user) {
        UserGroup group = getGroupByID(groupID);
        group.addUsers(user);
    }

    public boolean hasAccessToGroups(int groupID, int userID) {
        UserGroup group = getGroupByID(groupID);
        if (group == null) {
            return false;
        }
        for (User user : getGroupByID(groupID).getUsers()) {
            if(user.getId() == userID) {
                return true;
            }
        }
        return false;
    }
}
