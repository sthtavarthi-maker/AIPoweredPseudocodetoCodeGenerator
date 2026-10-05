import React from "react";
import { useNavigate } from "react-router-dom";

const ProfileActions = () => {

  const navigate = useNavigate();

  const handleLogout = () => {

    // Remove JWT token
    localStorage.removeItem("token");

    // Redirect to login page
    navigate("/login");
  };

  return (
    <div className="profile-actions">

      <button
        className="logout-btn"
        onClick={handleLogout}
      >
        🚪 Logout
      </button>

    </div>
  );
};

export default ProfileActions;