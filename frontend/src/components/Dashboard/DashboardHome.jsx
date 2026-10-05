import "../../styles/Dashboard.css";
import StatsCard from "./StatsCard";
import QuickActions from "./QuickActions";
import RecentActivity from "./RecentActivity";

function DashboardHome() {
  return (
    <div className="dashboard">

      <div className="dashboard-header">

        <h1>Welcome Back 👋</h1>

        <p>Ready to generate optimized code today?</p>

      </div>

      <div className="stats-grid">

        <StatsCard
          title="Total Codes"
          value="128"
          icon="📄"
        />

        <StatsCard
          title="Languages"
          value="4"
          icon="💻"
        />

        <StatsCard
          title="History"
          value="52"
          icon="📜"
        />

        <StatsCard
          title="AI Usage"
          value="98%"
          icon="⚡"
        />

      </div>

      <QuickActions />

      <RecentActivity />

    </div>
  );
}

export default DashboardHome;