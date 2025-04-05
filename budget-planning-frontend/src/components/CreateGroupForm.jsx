import { useState } from "react";
import TextInputField from "./TextInputField";
import TextAreaInputField from "./TextAreaInputField";
import Button from "./Button";
import ModalWindow from "./ModalWindow";
import { useNavigate } from "react-router-dom";

const CreateGroupForm = () => {
    const [formData, setFormData] = useState({name: "", description: ""});
    const [errors, setErrors] = useState({})
    const [message, setMessage] = useState("");
    const [modalType, setModalType] = useState("success");
    const [showModal, setShowModal] = useState(false);
    const [userID, setUserID] = useState(1)
    let navigate = useNavigate();

    const failedMessage = "Oops! Something went wrong while creating your group. Please try again.";
    const successMessage = "Hooray! Your group has been successfully created.";

    const validateUserInputs = () => {
        let errors = {};
        let isValid = true;

        if (!formData.name.trim() || formData.name.length < 3 || formData.name.length > 50) {
            errors.name = "Name must be between 3 and 50 characters.";
            isValid = false;
        }
        setErrors(errors); 
        return isValid;
    }
    
    const handleSubmit = (event) => {
        event.preventDefault();
        if(!validateUserInputs()) return;
        fetch("http://localhost:8080/groups/create", {
            method: "POST", 
            headers: {"Content-Type": "application/json"},
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

    const handleClose = () => {
        setShowModal(false);
        navigate(`/groups/${userID}/list`);
    }

    return (
        <form>
            <h1>Create a New Group</h1>
            <div>
                <TextInputField label="Group Name" name="name" value={formData.name} setFormData={setFormData} />
                {errors.name && <p className="error">{errors.name}</p>}
                <TextAreaInputField label="Group Description" name="description" value={formData.description} setFormData={setFormData} />
                <Button label="Create Group" onClick={handleSubmit} />
            </div>
            <ModalWindow showState={showModal} message={message} type={modalType} onClose={() => handleClose()} />
        </form>
    );
};

export default CreateGroupForm;