import { useState, useEffect } from "react";
import '../index.css'
import { useNavigate } from "react-router-dom";
import Button from "./Button";
import PasswordInputField from "./PasswordInputField";
import TextInputField from "./TextInputField";

export default function LoginForm () {

    const initialValues = { username: "", password: "" };
    const [formData, setFormData] = useState(initialValues);
    const [formErrors, setFormErrors] = useState({});
    const [isSubmit, setIsSubmit] = useState(false);

           let navigate = useNavigate();
           const routeChange = () =>{
                 event.preventDefault();
                 let path = `/register`;
                 navigate(path);
           };

           const handleSubmit = async (event) => {
              event.preventDefault();
              setFormErrors(validate(formData));
              setIsSubmit(true);

              if (Object.keys(formErrors).length > 0) return;

              const response = await fetch("http://localhost:8080/user/login", {
                          method: "POST",
                          headers: { "Content-Type": "application/json" },
                          credentials: "include",
                          body: JSON.stringify(formData)
        });
                if(response.ok) {
                    const data = await response.json();
                          navigate("/Home");

                        } else {
                          const errorData = await response.json();
                          alert(errorData.message || "Login failed");
                        }
                      };

      useEffect(() => {
          if (Object.keys(formErrors).length === 0 && isSubmit) {
            console.log(formData);
          }
        }, [formErrors]);

        const validate = (values) => {
          const errors = {};
          if (!values.username) {
            errors.username = "Username is required!";
          } else if (values.username.length < 4) {
              errors.username = "Username must be more than 4 characters";
          } else if (values.username.length > 15) {
              errors.username = "Username cannot exceed more than 15 characters";
          }
          if (!values.password) {
            errors.password = "Password is required";
          } else if (values.password.length < 4) {
            errors.password = "Password must be more than 4 characters";
          } else if (values.password.length > 15) {
            errors.password = "Password cannot exceed more than 15 characters";
          }
          return errors;
        };

      return (
        <form onSubmit={handleSubmit}>
            <h1>Login</h1>
            <div>
                <TextInputField label="Username" name="username" value={formData.username} setFormData={setFormData} />
                <p>{formErrors.username}</p>

                <PasswordInputField label="Password" name="password" value={formData.password} setFormData={setFormData} />
                <p>{formErrors.password}</p>

                <Button label="Login" onClick={handleSubmit} />
                <Button label="Register" onClick={routeChange} />
            </div>
        </form>
      )
  }
