import React, { useEffect, useState } from "react";
import "../styles/AuthPage.css";
import { getProfile } from "../api/profile";

const apiUrl = import.meta.env.REACT_APP_API_URL;

interface User {
    userName: string;
    email: string;
    role: string;
}

const ProfilePage: React.FC = () => {
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const storedUserName = localStorage.getItem("userName");
        if (!storedUserName) {
            setError("User not logged in.");
            setLoading(false);
            return;
        }

        getProfile()
            .then((data) => {
                console.log(data);
                setUser(data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, []);

    if (loading) return <div style={{ textAlign: "center", marginTop: "2rem" }}>Loading...</div>;
    if (error) return <div style={{ textAlign: "center", marginTop: "2rem", color: "red" }}>{error}</div>;

    return (
        <div style={{ display: "flex", justifyContent: "center", marginTop: "4rem" }}>
            <div className="auth-card" style={{ padding: "2rem" }}>
                <h2 style={{ textAlign: "center", marginBottom: "1.5rem" }}>Profile</h2>
                <div>
                    <strong>Username:</strong> {user?.userName}
                </div>
                <div>
                    <strong>Email:</strong> {user?.email}
                </div>
                <div>
                    <strong>Role:</strong> {user?.role}
                </div>
            </div>
        </div>
    );
};

export default ProfilePage;