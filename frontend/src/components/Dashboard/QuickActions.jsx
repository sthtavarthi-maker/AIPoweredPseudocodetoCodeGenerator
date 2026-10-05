import { useNavigate } from "react-router-dom";

function QuickActions() {

  const navigate = useNavigate();

  return (

    <div className="quick-actions">

      <h2>Quick Actions</h2>

      <div className="action-buttons">

        <button onClick={() => navigate("/generator")}>
          Generate Code
        </button>

        <button onClick={() => navigate("/history")}>
          History
        </button>

        <button onClick={() => navigate("/profile")}>
          Profile
        </button>

      </div>

    </div>

  );
}

export default QuickActions;