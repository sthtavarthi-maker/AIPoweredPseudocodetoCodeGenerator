import React from "react";

import ProfileHeader from "../components/Profile/ProfileHeader";
import ProfileCard from "../components/Profile/ProfileCard";
import ProfileStats from "../components/Profile/ProfileStats";
import ProfileActions from "../components/Profile/ProfileActions";

import "../styles/profile/Profile.css";
import "../styles/profile/ProfileCard.css";
import "../styles/profile/ProfileStats.css";
import "../styles/profile/Responsive.css";

const Profile = () => {
  return (
    <div className="profile-page">
      <div className="profile-container">

        <ProfileHeader />

        <ProfileCard />

        <ProfileStats />

        <ProfileActions />

      </div>
    </div>
  );
};

export default Profile;