import React, { useEffect, useState } from 'react';
// import "../styles/choreListStyle.css";
import { useNavigate } from "react-router-dom";

import Button from "./Button";
import { useFetchGroups } from '../hooks/useFetchGroups.jsx';

const GroupsList = () => {
  const [userID, setUserID] = useState(1);
  const { groups, error, loading } = useFetchGroups(userID);

  let navigate = useNavigate();

  if (error) return <div>{error}</div>;
  if (loading) return <p>Loading groups...</p>;

  function handleClick(group) {
    navigate(`/groups/${userID}/${group.id}`);
  }

  return (
    <div className="tiles-container">
      {groups.length === 0 ? (
        <div>
          <Button label="Create Group" onClick={() => navigate("/groups/create")}>
            Create Group
          </Button>
        </div>
      ) : (
        <div className="tile-list">
          {groups.map((group) => (
            <div
            key={group.id}
            className="tile"
            onClick={() => handleClick(group)}>
            <h3>{group.name}</h3>
            <p>{group.description}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default GroupsList;