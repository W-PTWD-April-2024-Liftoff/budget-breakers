import { useState } from "react";
import "./App.css";
import { Routes, Route, Link } from "react-router-dom";
import RegisterForm from "./components/RegisterForm";
import LoginForm from "./components/LoginForm";
import Events from "./components/Pages/Events";
import Chores from "./components/Pages/Chores";
import Navbar from "./components/Navbar";
import ChoreCreationForm from "./components/ChoreCreationForm";
import ChoresList from "./components/ChoresList";
import SingleChorePage from "./components/SingleChorePage";
import Home from "./components/Pages/Home";
import EditChorePage from "./components/EditChorePage";
import CreateGroupForm from "./components/CreateGroupForm";
import GroupsList from "./components/GroupsList";

function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/register" element={<RegisterForm />} />
        <Route path="/login" element={<LoginForm />} />
        <Route path="/Home" element={<Home />} />
        <Route path="/Events" element={<Events />} />
        <Route path="/Chores" element={<Chores />} />
        <Route path="/chores/create" element={<ChoreCreationForm />} />
        <Route path="/chores/1/list" element={<ChoresList />} />
        <Route path="/chores/:choreId" element={<SingleChorePage />} />
        <Route path="/chores/:userGroupId/list" element={<ChoresList />} />
        <Route path="/chores/:userGroupId/:choreId" element={<SingleChorePage />} />
        <Route path="/chores/:choreId/edit" element={<EditChorePage />} />
        <Route path="/groups/create" element={<CreateGroupForm />} />
        <Route path="/groups/:userID/list" element={<GroupsList />} />
      </Routes>
    </>
  );
}

export default App;
