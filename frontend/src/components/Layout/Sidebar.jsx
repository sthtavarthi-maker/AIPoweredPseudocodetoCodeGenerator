import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import "../../styles/Sidebar.css";

function Sidebar() {

    const navigate = useNavigate();

    const handleLogout = () => {
        navigate("/");
    };

    return (

        <aside className="sidebar">

            <div className="sidebar-logo">

                🤖

                <h2>AI Code Generator</h2>

            </div>

            <nav className="sidebar-menu">

                <NavLink
                    to="/dashboard"
                    end
                    className={({ isActive }) =>
                        isActive ? "active-link" : ""
                    }
                >
                    🏠 Home
                </NavLink>

                <NavLink
                    to="/dashboard/generator"
                    className={({ isActive }) =>
                        isActive ? "active-link" : ""
                    }
                >
                    💻 AI Generator
                </NavLink>

                <NavLink
                    to="/dashboard/history"
                    className={({ isActive }) =>
                        isActive ? "active-link" : ""
                    }
                >
                    📜 History
                </NavLink>

                <NavLink
                    to="/dashboard/profile"
                    className={({ isActive }) =>
                        isActive ? "active-link" : ""
                    }
                >
                    👤 Profile
                </NavLink>

            </nav>

            <button
                className="logout-btn"
                onClick={handleLogout}
            >
                🚪 Logout
            </button>

        </aside>

    );

}

export default Sidebar;