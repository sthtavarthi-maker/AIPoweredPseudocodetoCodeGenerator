import React, { useState } from "react";
import AccordionCard from "./AccordionCard";
import "../../styles/generator/Analysis.css";

function AnalysisCards({
    explanation,
    dryRun,
    timeComplexity,
    spaceComplexity,
    edgeCases
}) {

    const [openSection, setOpenSection] = useState(null);

    const handleToggle = (section) => {
        setOpenSection(
            openSection === section ? null : section
        );
    };

    return (
        <div className="analysis-section">

            <div className="analysis-header-section">

                <div>
                    <h2 className="analysis-heading">
                        🧠 AI Analysis
                    </h2>

                    <p className="analysis-subtitle">
                        Understand the generated solution step by step
                    </p>
                </div>

            </div>

            <div className="analysis-accordion">

                <AccordionCard
                    icon="📖"
                    title="Explanation"
                    content={
                        explanation || "Explanation not available."
                    }
                    isOpen={openSection === "explanation"}
                    onClick={() => handleToggle("explanation")}
                    color="#2563eb"
                />

                <AccordionCard
                    icon="🧪"
                    title="Dry Run"
                    content={
                        dryRun || "Dry run not available."
                    }
                    isOpen={openSection === "dryRun"}
                    onClick={() => handleToggle("dryRun")}
                    color="#7c3aed"
                />

                <AccordionCard
                    icon="⚡"
                    title="Time Complexity"
                    content={
                        timeComplexity ||
                        "Time complexity not available."
                    }
                    isOpen={openSection === "timeComplexity"}
                    onClick={() => handleToggle("timeComplexity")}
                    color="#ea580c"
                />

                <AccordionCard
                    icon="💾"
                    title="Space Complexity"
                    content={
                        spaceComplexity ||
                        "Space complexity not available."
                    }
                    isOpen={openSection === "spaceComplexity"}
                    onClick={() => handleToggle("spaceComplexity")}
                    color="#0891b2"
                />

             
                <AccordionCard
                    icon="⚠️"
                    title="Edge Cases"
                    content={
                        edgeCases ||
                        "Edge cases not available."
                    }
                    isOpen={openSection === "edgeCases"}
                    onClick={() => handleToggle("edgeCases")}
                    color="#ca8a04"
                />

            </div>

        </div>
    );
}

export default AnalysisCards;