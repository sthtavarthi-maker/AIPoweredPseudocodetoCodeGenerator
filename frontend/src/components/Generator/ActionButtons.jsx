import React from "react";
import "../../styles/generator/Buttons.css";

function ActionButtons({ code, language }) {

    const extensions = {
        Java: "java",
        Python: "py",
        "C++": "cpp",
        JavaScript: "js",
        C: "c",
        "C#": "cs",
        Go: "go",
        Rust: "rs"
    };

    const handleCopy = async () => {

        if (!code) {
            alert("No code available to copy.");
            return;
        }

        try {

            await navigator.clipboard.writeText(code);

            alert("Code copied successfully!");

        } catch (error) {

            console.error("Copy failed:", error);
            alert("Failed to copy code.");

        }
    };

    const handleDownload = () => {

        if (!code) {
            alert("No code available to download.");
            return;
        }

        const extension = extensions[language] || "txt";

        const blob = new Blob(
            [code],
            { type: "text/plain" }
        );

        const url = URL.createObjectURL(blob);

        const link = document.createElement("a");

        link.href = url;
        link.download = `GeneratedCode.${extension}`;

        document.body.appendChild(link);

        link.click();

        document.body.removeChild(link);

        URL.revokeObjectURL(url);
    };

    const handleSaveHistory = () => {

        if (!code) {
            alert("No generated code available.");
            return;
        }

        alert("This generated code has already been saved to History.");
    };

    return (
        <div className="action-buttons">

            <button
                className="copy-btn"
                onClick={handleCopy}
            >
                📋 Copy Code
            </button>

            <button
                className="download-btn"
                onClick={handleDownload}
            >
                ⬇ Download .{extensions[language] || "txt"}
            </button>

            <button
                className="history-btn"
                onClick={handleSaveHistory}
            >
                ❤️ Save History
            </button>

        </div>
    );
}

export default ActionButtons;