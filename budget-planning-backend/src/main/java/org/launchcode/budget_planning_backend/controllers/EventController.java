package org.launchcode.budget_planning_backend.controllers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.budget_planning_backend.data.ContributionsRepository;
import org.launchcode.budget_planning_backend.data.EventRepository;
import org.launchcode.budget_planning_backend.models.*;
import org.launchcode.budget_planning_backend.service.AuthenticationService;
import org.launchcode.budget_planning_backend.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value ="/events")
public class EventController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ContributionsRepository contributionsRepository;

    private final Logger logger = LoggerFactory.getLogger(EventController.class);

    @GetMapping("/{groupID}/list")
    public ResponseEntity<List<EventDTO>> getEvents(@PathVariable Integer groupID, HttpServletRequest request){
        logger.info("Inside GetEvents");
//        User user = authenticationService.getCurrentUser(request);
        User user = authenticationController.getUserFromSession(request.getSession());
        List<Event> listOfEvents = eventService.getEvents(groupID, user);
        List<EventDTO> listOfEventsDto = new ArrayList<>();
        if (listOfEvents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList());
        }else{
            eventService.setEventDtoList(listOfEvents, listOfEventsDto);
        }
        return ResponseEntity.ok(listOfEventsDto);
    }

    @PostMapping("/create")
    public void createEvent(@Valid @RequestBody EventDTO eventDto){
        eventService.createEvent(eventDto);
        logger.info("Event created successfully");
    }

    @GetMapping("/{userGroupId}/{eventId}")
    public EventDTO viewEventDetails(@PathVariable Integer userGroupId, @PathVariable Integer eventId, HttpServletRequest request) {
        logger.info("Inside viewEventDetails ");
//        User user = authenticationService.getCurrentUser(request);
        User user = authenticationController.getUserFromSession(request.getSession());
        List<Event> events = eventService.getEvents(userGroupId, user);
        EventDTO eventDto = new EventDTO();
        for(Event event: events){
            if(event.getId() == eventId){
                eventService.setEventDtoForEvent(event, eventDto);
            }
        }
        logger.info("Event fetched successfully".concat(eventDto.toString()));
        return eventDto;
    }
    @PutMapping("/edit/{userGroupId}/{eventId}")
    public String editEventDetails(@Valid @RequestBody EventDTO eventDto, @PathVariable Integer userGroupId, @PathVariable Integer eventId, HttpServletRequest request) {
        logger.info("Inside editEventDetails ");
//        User user = authenticationService.getCurrentUser(request);
        User user = authenticationController.getUserFromSession(request.getSession());
        List<Event> events = eventService.getEvents(userGroupId, user);
        for(Event event: events){
            if(event.getId() == eventId) {
                eventService.updateEvent(event, eventDto);
                return "Event updated Successfully";
            }
        }
        return "Event Update failed";
    }

    @PostMapping("/contribute/{userGroupId}/{eventId}")
    public String addContribution(@Valid @RequestBody ContributionDTO contributionDTO, @PathVariable Integer userGroupId, @PathVariable Integer eventId, HttpServletRequest request){
        logger.info("Inside Contribute");
        Contributions contributions = null;
        // Need to implement get group with groupID
//        User user = authenticationService.getCurrentUser(request);
        User user = authenticationController.getUserFromSession(request.getSession());
        Event event = eventService.getEventForGroup(user, userGroupId, eventId);

        //Set Contribution details to event
        if(event !=null) {
            contributions = eventService.addContributionToEvent(user, event, contributionDTO.getAmountOfContribution());
            logger.info("Contributed Successfully".concat(contributions.toString()));
        }
        return "Contribution added successfully";
    }

    @GetMapping("/contributions/{userGroupId}/{eventId}")
    public List<ContributionDTO> getContributions(@PathVariable Integer userGroupId, @PathVariable Integer eventId, HttpServletRequest request) {
//        User user = authenticationService.getCurrentUser(request);
        User user = authenticationController.getUserFromSession(request.getSession());
        return eventService.getContributionsForEvent(user, userGroupId, eventId);
    }

    @PostMapping("/approveContribution/{userGroupId}/{eventId}/{contributionId}")
    public void approveContribution(@PathVariable Integer userGroupId, @PathVariable Integer eventId, @PathVariable Integer contributionId, HttpServletRequest request){
       logger.info("Inside approveContribution");
//        User user = authenticationService.getCurrentUser(request);
        User user = authenticationController.getUserFromSession(request.getSession());
        Event event = eventService.getEventForGroup(user, userGroupId, eventId);
        Contributions contribution = eventService.getContribution(event, contributionId);
        contribution.setStatus(Status.COMPLETE);
        event.setEarnings(event.getEarnings() + contribution.getAmountOfContribution());
        eventService.isBudgetReachedForEvent(event);
        eventRepository.save(event);
        contributionsRepository.save(contribution);
        logger.info("Contribution approved successfully");
    }

    @DeleteMapping("/delete/{userGroupId}/{eventId}")
    public void deleteEvent(@PathVariable Integer userGroupId, @PathVariable Integer eventId, HttpServletRequest request){
        logger.info("Inside delete Event");
//        User user = authenticationService.getCurrentUser(request);
        User user = authenticationController.getUserFromSession(request.getSession());
        eventService.deleteEvent(user, userGroupId, eventId);
        logger.info("Event deleted successfully! EventId: "+ eventId +"from the group Group ID: "+ userGroupId+" by the user Name: "+user.getFirstName());
    }

}
