import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function Logout() {
  const navigateLogout = useNavigate();

  useEffect(() => {
    const logout = async () => {
           try {
             const response = await fetch("http://localhost:8080/user/logout", {
               method: "GET",
               credentials: "include",
             });

        if (response.ok) {
          localStorage.removeItem("authToken");
          sessionStorage.removeItem("authToken")
          navigateLogout("/login"); // Redirect to login
        } else {
          console.error("Logout failed");
        }
      } catch (error) {
        console.error("Logout error", error);
      }
    };

    logout();
  }, [navigateLogout]);

  return (
    <div>
      <h2>Logging out...</h2>
    </div>
  );
    }