import React, { useEffect, useState } from "react";
import axios from "axios";
import Button from "./Button";
import { useParams, useNavigate } from "react-router-dom";
import TextInputField from "./TextInputField";
import TextAreaInputField from "./TextAreaInputField";
import NumericInputField from "./NumericInputField";
import DateInputField from "./DateInputField";
import { ProgressBar } from "react-bootstrap";
import ModalWindow from "./ModalWindow";

export default function UpdateEventDetails() {
  const [formData, setFormData] = useState({
    eventName: "",
    eventBudget: "",
    eventLocation: "",
    eventDescription: "",
    eventDate: "",
    eventEarnings: "",
    userGroupName: "",
  });

  const { userGroupId, eventId } = useParams();
  const [message, setMessage] = useState("");
  const [newErrors, setErrors] = useState({});
  const [modalType, setModalType] = useState("success");
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate();

  const failedMessage =
    "Oops! Something went wrong while updating the event.Please try again.";
  const successMessage = "Event successfully updated!";

  useEffect(() => {
    const getEvents = () => {
      axios
        .get(`http://localhost:8080/events/${userGroupId}/${eventId}`, { withCredentials: true })
        .then((response) => {
          setFormData(response.data);
          setErrors({});
        })
        .catch((error) => {
          setErrors("Failed to load event details");
          console.error("Error fetching event details:", error);
        });
    };
    getEvents();
  }, [userGroupId, eventId]);

  const validateForm = () => {
    let isValid = true;
    let newErrors = {};
    if (
      !formData.eventName.trim() ||
      formData.eventName.length < 3 ||
      formData.eventName.length > 50
    ) {
      newErrors.eventName = "Name is required";
      isValid = false;
    }
    if (
      !formData.eventBudget ||
      isNaN(formData.eventBudget) ||
      formData.eventBudget < 0
    ) {
      newErrors.eventBudget = "Budget is required and must be a postive number";
      isValid = false;
    }
    setErrors(newErrors);
    return isValid;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    fetch(`http://localhost:8080/events/edit/${userGroupId}/${eventId}`, {
      method: "PUT",
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
        setShowModal(true);
      })
      .catch((error) => {
        setMessage(failedMessage);
        setModalType("danger");
        setShowModal(true);
      });
  };

  const handleModalClose = () => {
    setShowModal(false);
    if (modalType === "success") {
      navigate(`/events/${userGroupId}/list`); 
    }
  };
  return (
    <div className="pageBody">
      <h3> Edit Event</h3>
      <div className="progressBar">
        <ProgressBar
          animated
          now={formData.eventEarnings}
          max={formData.eventBudget}
        />
      </div>
      <form>
        <TextInputField
          label="Event name"
          name="eventName"
          value={formData.eventName}
          setFormData={setFormData}
        />
        {newErrors.eventName && (
          <p className="error">{newErrors.eventName}</p>
        )}
        <p>Group Name: {formData.userGroupName}</p>
        <TextAreaInputField
          label="Description"
          name="eventDescription"
          value={formData.eventDescription}
          setFormData={setFormData}
        />
        <NumericInputField
          label="Budget"
          name="eventBudget"
          value={formData.eventBudget}
          setFormData={setFormData}
        />
        {newErrors.eventBudget && (
          <p className="error">{newErrors.eventBudget}</p>
        )}
        <TextInputField
          label="Location"
          name="eventLocation"
          value={formData.eventLocation}
          setFormData={setFormData}
        />
        <DateInputField
          label="Event Date"
          name="eventDate"
          value={formData.eventDate}
          setFormData={setFormData}
        />
        <Button
          label="Back to Event List"
          onClick={() => navigate(`/events/${userGroupId}/list`)}
        ></Button>
        <Button label="Update Event" onClick={handleSubmit}></Button>
        <ModalWindow
          showState={showModal}
          message={message}
          type={modalType}
          onClose={() => handleModalClose()}
          onConfirm={handleModalClose}
        />
      </form>
    </div>
  );
}
