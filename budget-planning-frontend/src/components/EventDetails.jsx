import React, { useEffect, useState  } from "react";
import axios from "axios";
import "../styles/eventDetailsStyle.css";
import Button from "./Button";
import { useParams, useNavigate , useLocation} from "react-router-dom";
import NumericInputField from "./NumericInputField";
import { ProgressBar } from "react-bootstrap";
import ModalWindow from "./ModalWindow";
import Confetti from 'react-confetti';

export default function EventDetails() {
  const { userGroupId, eventId } = useParams();
  const [newErrors, setErrors] = useState({});
  const navigate = useNavigate();
  const [event, setEvent] = useState(null);
  const [contributions, setContributions] = useState([]);
  const [formData, setFormData] = useState({ amountOfContribution: "" });
  const [message, setMessage] = useState("");
  const [modalType, setModalType] = useState("success");
  const [showModal, setShowModal] = useState(false);
  const [showModalDelete, setShowModalDelete] = useState(false);
  const isAdultUser = sessionStorage.getItem("isAdult");
  const [showConfetti, setShowConfetti] = useState(false);
  const [isBudget, setIsBudget] = useState(false);

  const failedMessage =
    "Oops! Something went wrong while contributing to the event. Give it another try!";
  const successMessage =
    "Hooray! Your contribution to the event has been successfully made.";
  const congratulationsMessage =
    "Congratulations!! You have achieved the budget needed for the event!!! Enjoy the event!";
  const approvesuccessMessage = 
    "Approved the contribution successfully!";

  useEffect(() => {
    const getEvent = () => {
      axios
        .get(`http://localhost:8080/events/${userGroupId}/${eventId}`, { withCredentials: true })
        .then((response) => {
          setEvent(response.data);
          setIsBudget(response.data.budgetAchieved);
          if(isBudget) handleCelebrate();
          setErrors({});
        })
        .catch((error) => {
          setErrors("Failed to load event details");
          console.error("Error fetching event details:", error);
        })
        .finally(() => setLoading(false));
    };
    getEvent();
  }, [userGroupId, eventId]);

  useEffect(() => {
    const getContributionHistory = () => {
      axios
        .get(
          `http://localhost:8080/events/contributions/${userGroupId}/${eventId}`, { withCredentials: true }
        )
        .then((response) => {
          setContributions(response.data);
          setErrors({});
        })
        .catch((err) => {
          setErrors("Failed to load contibutions");
          console.error(err);
        });
    };

    getContributionHistory();
  }, [userGroupId, eventId]);

  if (event === null) {
    return <p>Loading event details...</p>;
  }

  const validateForm = () => {
    let isValid = true;
    let newErrors = {};

    if (
      !formData.amountOfContribution ||
      isNaN(formData.amountOfContribution) ||
      formData.amountOfContribution < 0
    ) {
      newErrors.amountOfContribution =
        "Contribution is required and must be a postive number";
      isValid = false;
    }
    setErrors(newErrors);
    return isValid;
  };

  function addContribution(event) {
    if (!validateForm()) {
      event.preventDefault();
      return;
    }
    const url = `http://localhost:8080/events/contribute/${userGroupId}/${eventId}`;
    fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(formData),
    })
      .then((response) => {
        if (response.ok) {
          setMessage(successMessage);
          setModalType("success");
          setErrors({});
        } else {
          setMessage(failedMessage);
          setModalType("danger");
        }
        if(isBudget) handleCelebrate();
        setShowModal(true);
      })
      .catch((error) => {
        setMessage(failedMessage);
        setModalType("danger");
        setShowModal(true);
      })
      .finally(() => isBudgetReached());
  }

  function approveContribution(contribution) {
    const url = `http://localhost:8080/events/approveContribution/${userGroupId}/${eventId}/${contribution.id}`;
    fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(contribution),
    })
      .then((response) => {
        if (response.ok) {
          setMessage(approvesuccessMessage);
          setModalType("success");
          setErrors({});
        } else {
          setMessage(failedMessage);
          setModalType("danger");
        }
        if(isBudget) handleCelebrate();
        setShowModal(true);
      })
      .catch((error) => {
        setMessage(failedMessage);
        setModalType("danger");
        setShowModal(true);
      })
      .finally(() => isBudgetReached());
    
  }
  
  const handleCelebrate = () => {
    setShowConfetti(true);
    setTimeout(() => setShowConfetti(false), 10000); 
  };  

  function isBudgetReached(){
    if(event.budgetAchieved){
      handleCelebrate();
    }
  }
  const handleDeleteModalClose = () => {
    setShowModalDelete(false);
  };

  const handleModalClose = () => {
    setShowModal(false);
    window.location.reload();
  };

  function deleteEvent() {
    setShowModalDelete(false);
    axios.delete(`http://localhost:8080/events/delete/${userGroupId}/${eventId}`, { withCredentials: true })
      .then(() => {
        navigate(`/events/${userGroupId}/list`);
      })
      .catch((error) => {
        console.error("Error deleting event:", error);
      });
   
  }
  return (
    <>
      <div className="tiles-container">
        <div className="title">
          <h3> View Event</h3>
        </div>
        <div className="contribute-container">
          <div className="progressBar">
            <ProgressBar
              animated
              now={event.eventEarnings}
              max={event.eventBudget}
            />
          </div>
          <div style={{ display: event.budgetAchieved ? "none" : "block" }}>
          <form>
            <NumericInputField
              label="Amount to Contribute"
              name="amountOfContribution"
              value={formData.amountOfContribution}
              setFormData={setFormData}
            />
            {newErrors.amountOfContribution && (
              <p className="error">{newErrors.amountOfContribution}</p>
            )}
            <Button label="Contribute" onClick={()=>(addContribution())}></Button>
          </form>
          </div>
          <div style={{ display: event.budgetAchieved ? "block" : "none" }}> Congratulations!! You have achieved the budget needed for the event!!! Enjoy the event!</div>
          {showConfetti && <Confetti />}
        </div>
        <div className="event-form-container">
          <p>Event Name: {event.eventName}</p>
          <p>Event Description: {event.eventDescription}</p>
          <p>Fund Available: {event.eventEarnings}</p>
          <p>Budget: {event.eventBudget}</p>
          <p>Location: {event.eventLocation}</p>
          <p>Event Date: {event.eventDate}</p>
          <div className="customButton">
          <Button
            label="Back to Event List"
            onClick={() => navigate(`/events/${userGroupId}/list`)}
          ></Button>
          </div>
          <div className="customButton" style={{ display: isAdultUser ? "block" : "none" }}>
            <Button
              label="Update"
              onClick={() => navigate(`/events/edit/${userGroupId}/${eventId}`)}
            ></Button>
          </div>
          <div className="customButton" style={{ display:isAdultUser ? "block" : "none" }}>
            <Button
              label="Delete"
              onClick={() => setShowModalDelete(true)}
            ></Button>
          </div>
          <ModalWindow
            showState={showModal}
            message={message}
            type={modalType}
            onClose={() => handleModalClose()}
            onConfirm={handleModalClose}
          />
        </div>
      <div className="contribution-history-container">
        <table>
          <thead>
            <tr>
              <th>Date</th>
              <th>User</th>
              <th>Amount</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {contributions.map((contribution) => (
              <tr key={contribution.id}>
                <td>{contribution.date}</td>
                <td>{contribution.name}</td>
                <td>{contribution.amountOfContribution}</td>
                <td>{contribution.status}</td>
                <td>{contribution.status == "COMPLETE" ? "APPROVED" : 
                  (isAdultUser ? (<Button label="Approve" onClick={()=>approveContribution(contribution)}/>):("PENDING"))
                }
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {showModalDelete && (
        <ModalWindow
          showState={showModalDelete}
          type="warning"
          message="You are about to delete the event. Click OK to confirm or close the window to return."
          onClose={handleDeleteModalClose}
          onConfirm={deleteEvent}
        />
      )}
      </div>
    </>
  );
}
