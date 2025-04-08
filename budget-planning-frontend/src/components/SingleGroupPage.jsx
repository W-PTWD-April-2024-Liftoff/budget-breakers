import React, { useState } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import { useFetchSingleGroup } from "../hooks/useFetchGroups";
import Button from "./Button";
import useCurrentUser from "../hooks/useCurrentUser";
import ChoresList from "./ChoresList";
import ViewEvents from "./ViewEvents";
import { CiEdit } from "react-icons/ci";
import { RiDeleteBin5Line } from "react-icons/ri";
import ModalWindow from "./ModalWindow";

const SingleGroupPage = () => {
  const location = useLocation();
  const { groupID } = useParams();
  const { user, error: userError } = useCurrentUser();
  const userID = user?.id;
  const {
    group,
    error: groupError,
    loading,
  } = useFetchSingleGroup(userID, groupID);

  const navigate = useNavigate();

  if (loading) return <p>Loading group details...</p>;
  if (userError) return <p>Error: {error}</p>;
  if (groupError) return <p>Error: {error}</p>;

  // Have add member button that has a pop up window for adding members
  //  //Need handle click for add member button
  //  //Need handle submit for adding members


  return (
    <div>
      <h2>{group.name}</h2>
      <p>{group.description}</p>
      <div className="tooltip-container">
        <Button
          label={<CiEdit size={24} />}
          onClick={() => navigate(`/groups/${userID}/${groupID}/edit`)}
        />
      </div>
      <div>
        {/* This button is a work in progress */}
        <Button label="Add New Member"></Button>
      </div>
      <div className="tiles-container">
        <h3>All Chores</h3>
        <ChoresList />
      </div>
      <div className="tiles-container">
        <h3>All Events</h3>
        {group.events.length === 0 ? (
          <p>No events available for your group.</p>
        ) : (
          <div>
            <ViewEvents />
          </div>
        )}
      </div>
      <div className="tiles-container">
        <h3>Group Members</h3>
        <div>
          {group.users.map((user) => (
            <div key={user.id}>
              <p>
                {user.firstName} {user.lastName}
              </p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default SingleGroupPage;
