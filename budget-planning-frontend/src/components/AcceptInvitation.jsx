import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

const AcceptInvitation = () => {
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const navigate = useNavigate();
    const location = useLocation();

    const queryParams = new URLSearchParams(location.search);
    const token = queryParams.get('token');

    useEffect(() => {
        console.log("Token:", token);
        if (!token) {
            setError('Invalid token.');
            setIsLoading(false);
            return;
        }

        const acceptInvitation = async () => {
            try {
                const response = await fetch(`http://localhost:8080/invite/accept?token=${token}`, {
                    method: 'GET',
                    credentials: 'include',
                });

                if (!response.ok) {
                    const message = await response.text();
                    setError(message || 'Failed to accept invitation');
                    setIsLoading(false);
                } else {
                    console.log('Invitation accepted, redirecting to /groups');
                    setError(null);
                    setIsLoading(false);
                    navigate('/Groups');
                }
            } catch (error) {
                setError('An error occurred while accepting the invitation.');
                setIsLoading(false);
            }
        };

        acceptInvitation();
    }, [token, navigate]);

    if (isLoading) {
        return <p>Loading...</p>;
    }

    return (
        <div>
            <h1>Accept Invitation</h1>
            {error ? <p>{error}</p> : <p>Invitation accepted successfully!</p>}
        </div>
    );
};

export default AcceptInvitation;