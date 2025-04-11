package org.launchcode.budget_planning_backend.service;

import org.launchcode.budget_planning_backend.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    UserGroupService userGroupService;

    private final Logger logger = LoggerFactory.getLogger(EventService.class);

    public void createEvent(EventDTO eventDto) {
        UserGroup group = userGroupService.getGroupByName(eventDto.getUserGroupName());
        Event event = new Event(eventDto.getEventName(), eventDto.getEventBudget(), eventDto.getEventLocation(), eventDto.getEventDescription(),
                eventDto.getEventDate(), Status.OPEN, 0,group);
        event.setUserGroup(group);
        event.setBudgetReached(false);
        group.addEvents(event);
        logger.info("Event Created Successfully".concat(event.toString()));
    }

    public List<Event> getEvents(int userGroupId, User user){
        logger.info("User has access to group: " + userGroupService.hasAccessToGroups(userGroupId, user.getId()));
        List<Event> eventsList = new ArrayList<>();
       if (userGroupService.hasAccessToGroups(userGroupId, user.getId())) {
            eventsList =userGroupService.getEventsFromGroup(userGroupId);
            logger.info("Events for group: " + userGroupId + eventsList);
           return eventsList;
        }
        return eventsList;
    }

    public Event getEventForGroup(User user, int userGroupId, int eventId){
        List<Event> events = getEvents(userGroupId, user);
        Event event = null;
        //Get the event
        for(Event eventDetail: events){
            if(eventDetail.getId() == eventId){
                event = eventDetail;
                break;
            }
        }
        return event;
    }

    public void setEventDtoForEvent(Event event, EventDTO eventDto){
        eventDto.setEventId(event.getId());
        eventDto.setEventName(event.getName());
        eventDto.setEventEarnings(event.getEarnings());
        eventDto.setEventBudget((event.getBudget()));
        eventDto.setEventDescription(event.getDescription());
        if(event.getDate() == null)eventDto.setEventDate( ""); else eventDto.setEventDate(  event.getDate().toString());
        eventDto.setEventLocation(event.getLocation());
        eventDto.setUserGroupName(event.getUserGroup().getName());
        eventDto.setBudgetAchieved(event.isBudgetReached());
    }

    public void updateEvent(Event event, EventDTO eventDto){
        event.setName(eventDto.getEventName());
        event.setDescription(eventDto.getEventDescription());
        event.setBudget(eventDto.getEventBudget());
        event.setLocation(eventDto.getEventLocation());
        if(!eventDto.getEventDate().isBlank()) {
            event.setDate(LocalDate.parse(eventDto.getEventDate()));
        }
        event.setEarnings(eventDto.getEventEarnings());
        isBudgetReachedForEvent(event);
    }

    public void setEventStatus(Event event){
        //Set Status
        if (event.getStatus() == Status.OPEN) {
            event.setStatus(Status.IN_PROGRESS);
        }
        // event completion
        if (event.getEarnings() == event.getBudget()) {
            event.setStatus(Status.COMPLETE);
        }
    }
    public void setContributionStatus(User user, Contributions contributions){
        // based on the user type - set the status to "PENDING" - child, "COMPLETED" - ADULT"
        if(user.getAccountType().equals(AccountType.ADULT)) {
            contributions.setStatus(Status.COMPLETE);
        }else{
            contributions.setStatus(Status.PENDING);
        }
    }

    public void addContributionToEvent(User user, Event event, Contributions contributions, double amountOfContribution){
        if(!user.getAccountType().equals(AccountType.MINOR)){
            event.setEarnings(event.getEarnings() + amountOfContribution);
        }
        logger.info(event.toString());
        // add Contribution to an Event
        contributions.setDate(LocalDate.now());
        contributions.setAmountOfContribution(amountOfContribution);
        contributions.setUser(user);
        setContributionStatus(user, contributions);
        contributions.setEventID(event.getId());
        contributions.setEvent(event);
        // Set event status
        isBudgetReachedForEvent(event);
        event.addContributions(contributions);
        setEventStatus(event);
    }

    public List<ContributionDTO> getContributionsForEvent(User user,int userGroupId, int eventId){
        List<ContributionDTO> contributions = new ArrayList<>();
        List<Event> events = getEvents(userGroupId, user);
        List<Contributions> listOfContributions = null;
        // get the contributions for an event
        for(Event eventDetail: events){
            if(eventDetail.getId() == eventId) {
                listOfContributions = eventDetail.getContributions();
                break;
            }
        }
        // If there are no contributions so far, send an empty list
        if(listOfContributions == null || listOfContributions.isEmpty()){
            return  contributions;
        }else {
            //Set DTO with contribution Details to show in contribution table
            ContributionDTO contributionDto = null;
            for (Contributions contribution : listOfContributions) {
                contributionDto = new ContributionDTO();
                contributionDto.setId(contribution.getId());
                contributionDto.setDate(contribution.getDate());
                contributionDto.setStatus(contribution.getStatus());
                contributionDto.setAmountOfContribution(contribution.getAmountOfContribution());
                contributionDto.setUser(contribution.getUser());
                contributionDto.setName(contribution.getUser().getFirstName()+ contribution.getUser().getLastName());
                contributions.add(contributionDto);
            }
        }
        return  contributions;
    }

    public Contributions getContribution(Event event, int contributionId){
            List<Contributions> contributions = event.getContributions();
            for(Contributions contribution: contributions){
                if(contribution.getId() == contributionId){
                    return contribution;
                }
            }
            return null;
    }

    public void deleteEvent(User user, int userGroupId, int eventId){
        Event event = getEventForGroup(user, userGroupId, eventId);
        userGroupService.getEventsFromGroup(userGroupId).remove(event);
    }

    public void isBudgetReachedForEvent(Event event){
        event.setBudgetReached(event.getEarnings() == event.getBudget() || event.getEarnings() > event.getBudget());
    }
}
