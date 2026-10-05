import React, { useEffect, useState } from "react";
import axios from "axios";

import { getHistory } from "../../services/api";

import "../../styles/profile/ProfileStats.css";

const ProfileStats = () => {

  const [stats, setStats] = useState({
    projectsGenerated: 0,
    savedHistory: 0,
    favoriteLanguage: "N/A",
    mostUsedAlgorithm: "N/A"
  });

  const [loading, setLoading] = useState(true);

  useEffect(() => {

    const fetchStats = async () => {

      try {

        const token = localStorage.getItem("token");

        // Fetch profile
        const profileResponse = await axios.get(
          "http://localhost:8080/api/profile",
          {
            headers: {
              Authorization: `Bearer ${token}`
            }
          }
        );

        // Fetch existing history
        const historyData = await getHistory();

        const profile = profileResponse.data;
        const history = Array.isArray(historyData)
          ? historyData
          : [];

        // Count approaches
        const approachCount = {};

        history.forEach((item) => {

          const approach = item.approach;

          if (approach) {

            approachCount[approach] =
              (approachCount[approach] || 0) + 1;

          }

        });

        // Find most used approach
        let mostUsedAlgorithm = "N/A";

        const approaches = Object.keys(approachCount);

        if (approaches.length > 0) {

          mostUsedAlgorithm = approaches.reduce(
            (current, next) =>
              approachCount[current] >= approachCount[next]
                ? current
                : next
          );

        }

        setStats({
          projectsGenerated: history.length,
          savedHistory: history.length,
          favoriteLanguage:
            profile.favoriteLanguage || "N/A",
          mostUsedAlgorithm
        });

      } catch (error) {

        console.error(
          "Failed to load profile statistics:",
          error
        );

      } finally {

        setLoading(false);

      }

    };

    fetchStats();

  }, []);

  if (loading) {

    return (
      <div className="profile-stats">

        <div className="stat-card">
          <h2>...</h2>
          <p>Projects Generated</p>
        </div>

        <div className="stat-card">
          <h2>...</h2>
          <p>Saved History</p>
        </div>

        <div className="stat-card">
          <h2>...</h2>
          <p>Favorite Language</p>
        </div>

        <div className="stat-card">
          <h2>...</h2>
          <p>Most Used Algorithm</p>
        </div>

      </div>
    );

  }

  return (
    <div className="profile-stats">

      <div className="stat-card">
        <h2>{stats.projectsGenerated}</h2>
        <p>Projects Generated</p>
      </div>

      <div className="stat-card">
        <h2>{stats.savedHistory}</h2>
        <p>Saved History</p>
      </div>

      <div className="stat-card">
        <h2>{stats.favoriteLanguage}</h2>
        <p>Favorite Language</p>
      </div>

      <div className="stat-card">
        <h2>{stats.mostUsedAlgorithm}</h2>
        <p>Most Used Algorithm</p>
      </div>

    </div>
  );
};

export default ProfileStats;