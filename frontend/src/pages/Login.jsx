import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import { loginUser, registerUser } from "../services/api";

import "../styles/Login.css";

import {
    FaCode,
    FaEnvelope,
    FaLock,
    FaEye,
    FaEyeSlash,
    FaUser
} from "react-icons/fa";

function Login() {

    const navigate = useNavigate();

    // =========================================================
    // SWITCH BETWEEN LOGIN AND REGISTER
    // =========================================================

    const [isRegister, setIsRegister] = useState(false);

    // =========================================================
    // LOGIN FIELDS
    // =========================================================

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    // =========================================================
    // REGISTER FIELDS
    // =========================================================

    const [name, setName] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    // =========================================================
    // PASSWORD VISIBILITY
    // =========================================================

    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);

    // =========================================================
    // OTHER STATES
    // =========================================================

    const [rememberMe, setRememberMe] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");


    // =========================================================
    // LOGIN
    // =========================================================

    const handleLogin = async (e) => {

        e.preventDefault();

        if (!email || !password) {
            return;
        }

        try {

            const data = await loginUser(
                email,
                password
            );

            console.log(
                "LOGIN RESPONSE:",
                data
            );

            // =====================================================
            // SAVE JWT
            // =====================================================

            const token = data.token;

            if (!token) {

                alert(
                    "Login failed: JWT token was not received."
                );

                return;
            }

            localStorage.setItem(
                "token",
                token
            );

            console.log(
                "JWT token saved successfully."
            );

            // =====================================================
            // GO TO DASHBOARD
            // =====================================================

            navigate("/dashboard");

        } catch (error) {

            console.error(
                "Login failed:",
                error
            );

            if (error.response) {

                alert(
                    error.response.data?.message ||
                    "Invalid email or password."
                );

            } else {

                alert(
                    "Unable to connect to the backend."
                );
            }
        }
    };


    // =========================================================
    // REGISTER
    // =========================================================

    const handleRegister = async (e) => {

        e.preventDefault();

        if (
            !name ||
            !email ||
            !password ||
            !confirmPassword
        ) {

            return;
        }

        if (password !== confirmPassword) {

            alert(
                "Passwords do not match."
            );

            return;
        }

        try {

            await registerUser({
                fullName: name,
                email: email,
                password: password,
                favoriteLanguage: "Java"
            });

            setSuccessMessage(
                "Registration successful! Please sign in."
            );

            setName("");
            setEmail("");
            setPassword("");
            setConfirmPassword("");

            setIsRegister(false);

        } catch (error) {

            console.error(
                "Registration failed:",
                error
            );

            alert(
                error.response?.data?.message ||
                "Registration failed."
            );
        }
    };


    // =========================================================
    // SWITCH TO REGISTER
    // =========================================================

    const showRegister = () => {

        setSuccessMessage("");

        setIsRegister(true);

        setName("");
        setEmail("");
        setPassword("");
        setConfirmPassword("");

    };


    // =========================================================
    // SWITCH TO LOGIN
    // =========================================================

    const showLogin = () => {

        setSuccessMessage("");

        setIsRegister(false);

        setName("");
        setEmail("");
        setPassword("");
        setConfirmPassword("");

    };


    // =========================================================
    // UI
    // =========================================================

    return (

        <div className="login-page">

            <div className="login-background">


                {/* =============================================
                    LEFT BRANDING
                ============================================= */}

                <div className="login-brand">

                    <div className="brand-logo">
                        <FaCode />
                    </div>

                    <h1>
                        Pseudocode
                        <span> to Code</span>
                    </h1>

                    <h2>
                        Generator
                    </h2>

                    <p>
                        Transform your pseudocode into
                        production-ready code using AI.
                    </p>

                    <div className="code-preview">

                        <span>&lt;/&gt;</span>

                        <div>

                            <strong>
                                AI Code Generation
                            </strong>

                            <small>
                                Java • Python • C • C++
                            </small>

                        </div>

                    </div>

                </div>


                {/* =============================================
                    LOGIN / REGISTER CARD
                ============================================= */}

                <div className="login-card">


                    {/* =============================================
                        HEADER
                    ============================================= */}

                    <div className="login-header">

                        <h2>

                            {isRegister
                                ? "Create Account"
                                : "Welcome Back"
                            }

                        </h2>

                        <p>

                            {isRegister
                                ? "Create your account to get started"
                                : "Sign in to your account"
                            }

                        </p>

                    </div>


                    {/* =============================================
                        SUCCESS MESSAGE
                    ============================================= */}

                    {successMessage && (

                        <div className="success-message">

                            {successMessage}

                        </div>

                    )}


                    {/* =============================================
                        REGISTER FORM
                    ============================================= */}

                    {isRegister ? (

                        <form onSubmit={handleRegister}>


                            {/* NAME */}

                            <div className="form-group">

                                <label>
                                    Full Name
                                </label>

                                <div className="input-wrapper">

                                    <FaUser className="input-icon" />

                                    <input
                                        type="text"
                                        placeholder="Enter your name"
                                        value={name}
                                        onChange={(e) =>
                                            setName(e.target.value)
                                        }
                                        required
                                    />

                                </div>

                            </div>


                            {/* EMAIL */}

                            <div className="form-group">

                                <label>
                                    Email
                                </label>

                                <div className="input-wrapper">

                                    <FaEnvelope className="input-icon" />

                                    <input
                                        type="email"
                                        placeholder="Enter your email"
                                        value={email}
                                        onChange={(e) =>
                                            setEmail(e.target.value)
                                        }
                                        required
                                    />

                                </div>

                            </div>


                            {/* PASSWORD */}

                            <div className="form-group">

                                <label>
                                    Password
                                </label>

                                <div className="input-wrapper">

                                    <FaLock className="input-icon" />

                                    <input
                                        type={
                                            showPassword
                                                ? "text"
                                                : "password"
                                        }
                                        placeholder="Create a password"
                                        value={password}
                                        onChange={(e) =>
                                            setPassword(e.target.value)
                                        }
                                        required
                                    />

                                    <button
                                        type="button"
                                        className="password-toggle"
                                        onClick={() =>
                                            setShowPassword(
                                                !showPassword
                                            )
                                        }
                                    >

                                        {showPassword
                                            ? <FaEyeSlash />
                                            : <FaEye />
                                        }

                                    </button>

                                </div>

                            </div>


                            {/* CONFIRM PASSWORD */}

                            <div className="form-group">

                                <label>
                                    Confirm Password
                                </label>

                                <div className="input-wrapper">

                                    <FaLock className="input-icon" />

                                    <input
                                        type={
                                            showConfirmPassword
                                                ? "text"
                                                : "password"
                                        }
                                        placeholder="Confirm your password"
                                        value={confirmPassword}
                                        onChange={(e) =>
                                            setConfirmPassword(
                                                e.target.value
                                            )
                                        }
                                        required
                                    />

                                    <button
                                        type="button"
                                        className="password-toggle"
                                        onClick={() =>
                                            setShowConfirmPassword(
                                                !showConfirmPassword
                                            )
                                        }
                                    >

                                        {showConfirmPassword
                                            ? <FaEyeSlash />
                                            : <FaEye />
                                        }

                                    </button>

                                </div>

                            </div>


                            {/* REGISTER BUTTON */}

                            <button
                                type="submit"
                                className="login-btn"
                            >

                                Create Account

                            </button>

                        </form>

                    ) : (


                        /* =============================================
                           LOGIN FORM
                        ============================================= */

                        <form onSubmit={handleLogin}>


                            {/* EMAIL */}

                            <div className="form-group">

                                <label>
                                    Email
                                </label>

                                <div className="input-wrapper">

                                    <FaEnvelope className="input-icon" />

                                    <input
                                        type="email"
                                        placeholder="Enter your email"
                                        value={email}
                                        onChange={(e) =>
                                            setEmail(e.target.value)
                                        }
                                        required
                                    />

                                </div>

                            </div>


                            {/* PASSWORD */}

                            <div className="form-group">

                                <label>
                                    Password
                                </label>

                                <div className="input-wrapper">

                                    <FaLock className="input-icon" />

                                    <input
                                        type={
                                            showPassword
                                                ? "text"
                                                : "password"
                                        }
                                        placeholder="Enter your password"
                                        value={password}
                                        onChange={(e) =>
                                            setPassword(e.target.value)
                                        }
                                        required
                                    />

                                    <button
                                        type="button"
                                        className="password-toggle"
                                        onClick={() =>
                                            setShowPassword(
                                                !showPassword
                                            )
                                        }
                                    >

                                        {showPassword
                                            ? <FaEyeSlash />
                                            : <FaEye />
                                        }

                                    </button>

                                </div>

                            </div>


                            {/* REMEMBER / FORGOT */}

                            <div className="login-options">

                                <label className="remember-me">

                                    <input
                                        type="checkbox"
                                        checked={rememberMe}
                                        onChange={(e) =>
                                            setRememberMe(
                                                e.target.checked
                                            )
                                        }
                                    />

                                    <span>
                                        Remember me
                                    </span>

                                </label>
                            </div>


                            {/* LOGIN BUTTON */}

                            <button
                                type="submit"
                                className="login-btn"
                            >

                                Sign In

                            </button>

                        </form>

                    )}


                    {/* =============================================
                        LOGIN / REGISTER SWITCH
                    ============================================= */}

                    <p className="register-text">

                        {isRegister
                            ? "Already have an account?"
                            : "Don't have an account?"
                        }


                        <button
                            type="button"
                            className="register-link"
                            onClick={
                                isRegister
                                    ? showLogin
                                    : showRegister
                            }
                        >

                            {isRegister
                                ? "Sign In"
                                : "Register"
                            }

                        </button>

                    </p>

                </div>

            </div>

        </div>

    );
}

export default Login;