import React, { useEffect, useState } from "react";
import axios from "axios";

import "../../styles/profile/ProfileCard.css";

const ProfileCard = () => {

  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {

    const fetchProfile = async () => {

      try {

        const token = localStorage.getItem("token");

        const response = await axios.get(
          "http://localhost:8080/api/profile",
          {
            headers: {
              Authorization: `Bearer ${token}`
            }
          }
        );

        setProfile(response.data);

      } catch (err) {

        console.error("Profile fetch error:", err);

        setError("Unable to load profile.");

      } finally {

        setLoading(false);

      }
    };

    fetchProfile();

  }, []);

  if (loading) {
    return (
      <div className="profile-card">
        <p>Loading profile...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="profile-card">
        <p>{error}</p>
      </div>
    );
  }

  return (
    <div className="profile-card">

      <div className="profile-avatar">
        👤
      </div>

      <h2>{profile.fullName}</h2>

      <p className="profile-role">
        {profile.role}
      </p>

      <div className="profile-info">

        <div className="info-row">
          <span>Email</span>
          <strong>{profile.email}</strong>
        </div>

        <div className="info-row">
          <span>Favorite Language</span>
          <strong>{profile.favoriteLanguage}</strong>
        </div>

        <div className="info-row">
          <span>Account Created</span>
          <strong>
            {new Date(profile.createdAt).toLocaleDateString()}
          </strong>
        </div>

      </div>

    </div>
  );
};

export default ProfileCard;