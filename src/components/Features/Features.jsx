import "./Features.css";
import {
  FaRobot,
  FaCode,
  FaChartLine,
  FaHistory,
} from "react-icons/fa";

function Features() {
  return (
    <section className="features" id="features">

      <h2>Why Choose Our AI?</h2>

      <p className="feature-subtitle">
        Everything you need to transform pseudocode into efficient,
        production-ready programs.
      </p>

      <div className="feature-grid">

        <div className="card">
          <FaRobot className="icon" />
          <h3>AI Code Generation</h3>
          <p>
            Convert pseudocode into clean and optimized source code.
          </p>
        </div>

        <div className="card">
          <FaCode className="icon" />
          <h3>Multi Language</h3>
          <p>
            Supports Java, Python, C and C++.
          </p>
        </div>

        <div className="card">
          <FaChartLine className="icon" />
          <h3>Complexity Analyzer</h3>
          <p>
            Analyze Time Complexity and Space Complexity instantly.
          </p>
        </div>

        <div className="card">
          <FaHistory className="icon" />
          <h3>History</h3>
          <p>
            View previously generated code anytime.
          </p>
        </div>

      </div>

    </section>
  );
}

export default Features;