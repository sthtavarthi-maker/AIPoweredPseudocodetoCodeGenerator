import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Home.css";

function Home() {

    const navigate = useNavigate();

    return (
        <div className="home-page">

            <div className="hero-section">

                <div className="hero-content">

                    <div className="hero-badge">
                        ✨ AI POWERED
                    </div>

                    <h1>
                        👋 Welcome to
                        <span> AI Code Generator</span>
                    </h1>

                    <p>
                        Transform your problem descriptions and pseudocode
                        into clean, efficient, and optimized code with AI.
                    </p>

                    <button
                        className="generate-main-btn"
                        onClick={() => navigate("/dashboard/generator")}
                    >
                        <span className="btn-icon">💻</span>
                        <span>Generate Code</span>
                        <span className="btn-arrow">→</span>
                    </button>

                </div>

                <div className="hero-visual">

                    <div className="glow-circle"></div>

                    <div className="code-orbit orbit-one">
                        &lt;/&gt;
                    </div>

                    <div className="code-orbit orbit-two">
                        {"{ }"}
                    </div>

                    <div className="code-orbit orbit-three">
                        AI
                    </div>

                    <div className="ai-core">

                        <div className="robot-face">
                            🤖
                        </div>

                        <div className="core-ring"></div>

                    </div>

                    <div className="floating-code code-one">
                        {"<code />"}
                    </div>

                    <div className="floating-code code-two">
                        {"AI.generate()"}
                    </div>

                    <div className="floating-code code-three">
                        {"010101"}
                    </div>

                </div>

            </div>

            <div className="home-bottom">

                <div className="feature-item">
                    <span>⚡</span>
                    <div>
                        <strong>Fast Generation</strong>
                        <small>Generate code quickly</small>
                    </div>
                </div>

                <div className="feature-item">
                    <span>🧠</span>
                    <div>
                        <strong>AI Powered</strong>
                        <small>Intelligent code assistance</small>
                    </div>
                </div>

                <div className="feature-item">
                    <span>🚀</span>
                    <div>
                        <strong>Optimized Code</strong>
                        <small>Clean and efficient solutions</small>
                    </div>
                </div>

            </div>

        </div>
    );
}

export default Home;