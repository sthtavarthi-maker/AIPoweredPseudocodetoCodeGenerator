import { useState } from "react";
import { Link } from "react-router-dom";
import {
  FaEnvelope,
  FaLock,
  FaEye,
  FaEyeSlash,
  FaGoogle,
  FaCode,
} from "react-icons/fa";

import "../../styles/Login.css";

function LoginForm() {
  const [showPassword, setShowPassword] = useState(false);

  return (
    <div className="login-page">

      <div className="login-container">

        {/* Left Side */}

        <div className="login-left">

          <div className="logo-box">
            <FaCode className="logo-icon" />
            <h1>Pseudocode Generator</h1>
          </div>

          <h2>Welcome Back 👋</h2>

          <p>
            Login to continue generating optimized code
            with AI.
          </p>

        </div>

        {/* Right Side */}

        <div className="login-right">

          <h2>Login</h2>

          <div className="input-group">

            <FaEnvelope className="input-icon" />

            <input
              type="email"
              placeholder="Email Address"
            />

          </div>

          <div className="input-group">

            <FaLock className="input-icon" />

            <input
              type={showPassword ? "text" : "password"}
              placeholder="Password"
            />

            <span
              className="eye-icon"
              onClick={() =>
                setShowPassword(!showPassword)
              }
            >
              {showPassword ? <FaEyeSlash /> : <FaEye />}
            </span>

          </div>

          <div className="remember-row">

            <label>

              <input type="checkbox" />

              Remember Me

            </label>

          </div>

          <button className="login-btn">
            Login
          </button>

          <div className="divider">
            OR
          </div>

          <button className="google-btn">

            <FaGoogle />

            Continue with Google

          </button>

          <p className="register-text">

            Don't have an account?

            <Link to="/register">
              Register
            </Link>

          </p>

        </div>

      </div>

    </div>
  );
}

export default LoginForm;