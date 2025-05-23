import { useEffect, useState } from "react";
import TextInputField from "./TextInputField";
import TextAreaInputField from "./TextAreaInputField";
import Button from "./Button";
import ModalWindow from "./ModalWindow";
import { useNavigate, useParams } from "react-router-dom";
import useCurrentUser from "../hooks/useCurrentUser";
import { useFetchSingleGroup } from "../hooks/useFetchGroups";
import axios from "axios";
import { RiDeleteBin5Line } from "react-icons/ri";
import { GrClose } from "react-icons/gr";
import "../styles/groupCreationFormStyle.css";

const EditGroupForm = () => {
  const { user, error } = useCurrentUser();
  const { groupID } = useParams();
  const [formData, setFormData] = useState({ name: "", description: "" });
  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState("");
  const [modalType, setModalType] = useState("success");
  const [showModal, setShowModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const userID = user?.id;
  console.log(groupID);
  const {
    group,
    error: groupError,
    loading,
  } = useFetchSingleGroup(userID, groupID);
  const navigate = useNavigate();

  useEffect(() => {
    if (group) {
      setFormData({ name: group.name, description: group.description });
    }
  }, [group]);

  const failedMessage =
    "Oops! Something went wrong while updating your group. Please try again.";
  const successMessage = "Hooray! Your group has been successfully updated.";

  const validateUserInputs = () => {
    let errors = {};
    let isValid = true;

    if (
      !formData.name.trim() ||
      formData.name.length < 3 ||
      formData.name.length > 50
    ) {
      errors.name = "Name must be between 3 and 50 characters.";
      isValid = false;
    }
    setErrors(errors);
    return isValid;
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (!validateUserInputs()) return;
    fetch(`http://localhost:8080/groups/${userID}/${groupID}/edit`, {
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

  const handleClose = () => {
    setShowModal(false);
    navigate(`/groups/${userID}/${groupID}`, { state: { userID, groupID } });
  };

  const handleModalClose = () => {
    setShowDeleteModal(false);
    navigate(`/groups/${userID}/${groupID}`, { state: { userID, groupID } });
  };

  const handleDelete = () => {
    setShowDeleteModal(false);
    axios
      .delete(`http://localhost:8080/groups/${userID}/${groupID}/delete`, {
        withCredentials: true,
      })
      .then(() => {
        navigate(`/groups/${userID}/list`);
      })
      .catch((error) => {
        console.error("Error deleting group:", error);
      });
  };

  return (
    <form className="group-form-container">
      <div className="close-btn-container">
        <Button
          label={<GrClose size={20} />}
          onClick={() =>
            navigate(`/groups/${userID}/${groupID}`, {
              state: { userID, groupID },
            })
          }
          className="close-btn"
        />
      </div>
      <h1 className="group-form-heading">Update Group Details</h1>
      <div>
        <TextInputField
          label="Group Name"
          name="name"
          value={formData.name}
          setFormData={setFormData}
        />
        {errors.name && <p className="error">{errors.name}</p>}
        <TextAreaInputField
          label="Group Description"
          name="description"
          value={formData.description}
          setFormData={setFormData}
        />
      </div>
      <div className="tooltip-container">
        <Button label="Update Group" onClick={handleSubmit} />
        <Button
          className={"delete-btn"}
          label={<RiDeleteBin5Line size={24} />}
          onClick={() => setShowDeleteModal(true)}
        />
      </div>
      <ModalWindow
        showState={showModal}
        message={message}
        type={modalType}
        onClose={() => handleClose()}
      />
      <ModalWindow
        showState={showDeleteModal}
        message="You are about to delete the group. Click OK to confirm or close the window to return"
        onClose={handleModalClose}
        onConfirm={handleDelete}
      />
    </form>
  );
};

export default EditGroupForm;
