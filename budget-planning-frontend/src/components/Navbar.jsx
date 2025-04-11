import React from "react";
import { NavLink } from "react-router";
import "./Navbar.css";
import budgetLogo from "../styles/images/logo-transparent-png.png";
import useCurrentUser from '../hooks/useCurrentUser';
import { isAdult } from "../utils/userUtils.jsx";

function Navbar() {

  const { user } = useCurrentUser();  
  return (
    <>
      <div className="nav-bar">
        <nav>
          <img src={budgetLogo} className="logo" alt="Budget Breaker logo" />
          <NavLink to="/" className="title">
            Red, Green, VACAY!
          </NavLink>
          <div className="menu">
            <span></span>
            <span></span>
            <span></span>
          </div>
          <ul>
            <li>
              <NavLink to="/Home">Home </NavLink>
            </li>
            <li>
              <NavLink to="/Events">Events</NavLink>
            </li>
            <li>
            {isAdult(user) ? (
                <NavLink to="/Chores">Chores</NavLink> 
              ) : null}
            </li>
            <li>
              <NavLink to="/Groups">Groups</NavLink>
            </li>
            <li>
              <NavLink to="/Logout"> Logout</NavLink>
            </li>
          </ul>
        </nav>
      </div>
    </>
  );
}

export default Navbar;
