import React, { useState } from "react";
import { useParams,useNavigate } from 'react-router-dom';
import DateInputField from "./DateInputField";
import TextInputField from "./TextInputField";
import NumericInputField from "./NumericInputField";
import TextAreaInputField from "./TextAreaInputField";
import Button from "./Button";
import ModalWindow from "./ModalWindow";

export default function CreateEvent() {
  const today = new Date().toISOString().split("T")[0];
  const [formData, setFormData] = useState({
    eventName: "",
    eventBudget: "",
    eventLocation: "",
    eventDescription: "",
    eventDate: "",
  });
  const [newErrors, setErrors] = useState({});
  const [message, setMessage] = useState("");
  const [modalType, setModalType] = useState("success");
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate();

  const failedMessage =
    "Oops! Something went wrong while creating the event. Give it another try!";
  const successMessage = "Hooray! Your event has been successfully created.";

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

  const createEvent = (event) => {
    event.preventDefault();
    if (!validateForm()) return;

    fetch("http://localhost:8080/events/create", {
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
        setShowModal(true);
      })
      .catch((error) => {
        setMessage(failedMessage);
        setModalType("danger");
        setShowModal(true);
      });
  }

  const handleModalClose = () =>{
    setShowModal(false);
    if (modalType === "success") {
        navigate(`/events/1/list`);//need to be replaced with /chores/${userGroupId}/list
    }
  }

  return (
    <div class="pageBody">
      <form class="createEventForm">
        <h3> Create Event</h3>
        <TextInputField
          label="Event Name"
          name="eventName"
          value={formData.eventName}
          setFormData={setFormData}
        />
        {newErrors.eventName && <p className="error">{newErrors.eventName}</p>}
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
        <TextAreaInputField
          label="Description"
          name="eventDescription"
          value={formData.eventDescription}
          setFormData={setFormData}
        />
        <DateInputField
          label="Event Date"
          name="eventDate"
          value={formData.eventDate}
          setFormData={setFormData}
        />
        <br />
        <Button label="Create Event" onClick={createEvent} />
        <ModalWindow
          showState={showModal}
          message={message}
          type={modalType}
          onClose={() => handleModalClose()} onConfirm={handleModalClose}
        />
      </form>
    </div>
  )
}
