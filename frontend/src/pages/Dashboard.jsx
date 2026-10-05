import React from "react";
import { Outlet } from "react-router-dom";
import Sidebar from "../components/Layout/Sidebar";
import "../styles/Dashboard.css";

function Dashboard() {
    return (
        <div className="dashboard-layout">

            <Sidebar />

            <main className="dashboard-main">

                <Outlet />

            </main>

        </div>
    );
}

export default Dashboard;