import React from "react";
import "../../styles/generator/Loading.css";

function LoadingSpinner() {

    return (

        <div className="loading-overlay">

            <div className="loading-card">

                <div className="ai-icon">

                    🤖

                </div>

                <h2>

                    AI is Generating Code...

                </h2>

                <div className="progress-bar">

                    <div className="progress-fill"></div>

                </div>

                <div className="loading-steps">

                    <p>✔ Understanding Input</p>

                    <p>✔ Selecting Algorithm</p>

                    <p>✔ Optimizing Solution</p>

                    <p>✔ Generating Code</p>

                </div>

                <div className="loading-text">

                    Please wait...

                </div>

            </div>

        </div>

    );

}

export default LoadingSpinner;