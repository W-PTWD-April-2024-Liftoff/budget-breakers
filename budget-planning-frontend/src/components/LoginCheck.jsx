import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function LoginCheck() {
  const [loggedIn, setLoggedIn] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const checkLoginStatus = async () => {
      try {
        const response = await fetch("http://localhost:8080/Home", {
          method: 'GET',
          credentials: 'include',
        });

        if (response.status === 403) {
          setLoggedIn(false);
          navigate("/login");
        } else if (response.ok) {
          setLoggedIn(true);
        } else {
          setLoggedIn(false);
        }
      } catch (error) {
        console.error("Error checking login status:", error);
        setLoggedIn(false);
      }
    };

    checkLoginStatus();
  }, [navigate]);

  if (loggedIn === null) {
    return <div>Loading...</div>;  // Show loading while checking login status
  }

//   return (
//     <div>
//       {loggedIn ? 'Home - User is logged in' : 'Home - User is not logged in'}
//     </div>
//   );
}

export default LoginCheck;