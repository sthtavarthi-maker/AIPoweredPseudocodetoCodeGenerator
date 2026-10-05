import React from "react";
import "../../styles/generator/CodeBlock.css";

function CodeBlock({ language, generatedCode }) {

    const handleCopy = async () => {
        if (!generatedCode) return;

        try {
            await navigator.clipboard.writeText(generatedCode);
            alert("Code copied successfully!");
        } catch (error) {
            console.error("Copy failed:", error);
            alert("Failed to copy code.");
        }
    };

    return (
        <div className="code-card">

            <div className="code-header">

                <div className="language-badge">
                    {language === "Java" ? "☕" : "💻"} {language}
                </div>

                <button
                    className="copy-code-btn"
                    onClick={handleCopy}
                    disabled={!generatedCode}
                >
                    📋 Copy
                </button>

            </div>

            <pre className="generated-code">
                {generatedCode ||
                    `// Your generated ${language} code will appear here...`}
            </pre>

        </div>
    );
}

export default CodeBlock;