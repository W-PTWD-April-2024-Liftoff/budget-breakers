import React, { useEffect, useState } from 'react'
import "../styles/choreListStyle.css";
import axios from 'axios';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { useNavigate } from 'react-router-dom';
import Button from "./Button";

export default function ViewEvents() {
    const [events, setEvents ] = useState([]);
    const [newErrors, setErrors] = useState(null);
    const [userGroupId, setUserGroupId] = useState(1);
    let navigate = useNavigate();

    useEffect(() => {
        const getEvents = () => {
          axios
            .get(`http://localhost:8080/events/${userGroupId}/list`, { withCredentials: true })
            .then((response) => {
              setEvents(response.data);
              setErrors(null);
            })
            .catch((err) => {
              setErrors('Failed to load events');
              console.error(err);
            });
        };

        getEvents();
      }, [userGroupId]);

      if (newErrors) return <div>{newErrors}</div>;

      function handleClick(event) {
        console.log(`Event clicked: ${event.name}`);
        navigate(`/events/${userGroupId}/${event.id}`);
      }
  return (
    <div className="tiles-container">
        <div className="tile-list">
          {
            events.map((event, index) => (
            <div
              key={event.id}
              className="tile"
              onClick={() => handleClick(event)}>
              <h3>{event.name}</h3>    
              <p>Fund Available: {event.earnings}$</p> 
              <p>Budget: {event.budget}$</p>
             <p><progress value={event.earnings} max={event.budget}/></p>                 
            </div>
          ))}
        </div>
        <div>
          <Button label="Create New Event" onClick={() => navigate("/event/create")}>
            Create New Event
          </Button>
          </div>
        </div>
  )
}
