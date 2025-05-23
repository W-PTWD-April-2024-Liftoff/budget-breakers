import React from "react";
import { NavLink } from "react-router";
import "./Navbar.css";
import budgetLogo from "../styles/images/logo-transparent-png.png";


function Navbar() {

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
              <NavLink to="/Groups">Groups</NavLink>
            </li>
            <li>
              <NavLink to="/Events">Events</NavLink>
            </li>
            <li>
              <NavLink to="/Chores">Chores</NavLink>
            </li>
            <li>
                <NavLink to="/Login"> Login</NavLink>
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
