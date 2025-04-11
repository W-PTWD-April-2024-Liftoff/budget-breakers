import { useState } from "react";
import TextInputField from "./TextInputField";
import TextAreaInputField from "./TextAreaInputField";
import Button from "./Button";
import ModalWindow from "./ModalWindow";
import { useNavigate } from "react-router-dom";
import useCurrentUser from '../hooks/useCurrentUser';

const CreateGroupForm = () => {
    const { user } = useCurrentUser();
    const [formData, setFormData] = useState({name: "", description: "", emails: [] });
    const [errors, setErrors] = useState({})
    const [message, setMessage] = useState("");
    const [modalType, setModalType] = useState("success");
    const [showModal, setShowModal] = useState(false);
    const userID = user?.id;
    let navigate = useNavigate();

    const failedMessage = "Oops! Something went wrong while creating your group. Please try again.";
    const successMessage = "Hooray! Your group has been successfully created.";

    const validateUserInputs = () => {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;
        let errors = {};
        let isValid = true;

        if (!formData.name.trim() || formData.name.length < 3 || formData.name.length > 50) {
            errors.name = "Name must be between 3 and 50 characters.";
            isValid = false;
        }
        if (formData.emails) {
            formData.emails.forEach((email, index) => {
            if (!regex.test(email)) {
            errors['email${index}'] = "Email ${index + 1} is not a valid email!";
            isValid = false;
            }
        });
        }
        setErrors(errors); 
        return isValid;
    }
    
    const handleSubmit = (event) => {
        event.preventDefault();
        if(!validateUserInputs()) return;
        const dataToSubmit = { ...formData, emails: formData.emails };

        fetch("http://localhost:8080/groups/create", {
            method: "POST", 
            headers: {"Content-Type": "application/json"},
            credentials: "include",
            body: JSON.stringify(dataToSubmit),
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

    const handleAddEmail = () => {
        setFormData((prevData) => ({ ...prevData, emails: [...prevData.emails, ""] }));
    };

    const handleEmailChange = (index, value) => {
        const newEmails = [...formData.emails];
        newEmails[index] = value;
        setFormData((prevData) => ({ ...prevData, emails: newEmails }));
    };

    const handleRemoveEmail = (index) => {
        const newEmails = formData.emails.filter((_, i) => i !== index);
        setFormData((prevData) => ({ ...prevData, emails: newEmails }));
    };

    return (
        <form>
            <h1>Create a New Group</h1>
            <div>
                <TextInputField label="Group Name" name="name" value={formData.name} setFormData={setFormData} />
                {errors.name && <p className="error">{errors.name}</p>}
                <div>
                    <label>Enter emails to invite others to group:</label>
                    {formData.emails.map((email, index) => (
                        <div key={index} className="email-input-container">
                            <input
                                type="email"
                                value={email}
                                onChange={(e) => handleEmailChange(index, e.target.value)}
                                placeholder={`Email ${index + 1}`}
                                required
                            />
                            <button
                                type="button"
                                onClick={() => handleRemoveEmail(index)}
                                className="remove-email"
                            >
                                Remove
                            </button>
                            {errors[`email${index}`] && (
                                <p className="error">{errors[`email${index}`]}</p>
                            )}
                        </div>
                    ))}
                    <button type="button" onClick={handleAddEmail}>
                        Add email
                    </button>
                </div>
                <TextAreaInputField label="Group Description" name="description" value={formData.description} setFormData={setFormData} />
                <Button label="Create Group" onClick={handleSubmit} />
            </div>
            <ModalWindow showState={showModal} message={message} type={modalType} onClose={() => handleClose()} />
        </form>
    );
};

export default CreateGroupForm;