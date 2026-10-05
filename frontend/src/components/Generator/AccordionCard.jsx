import React from "react";
import "../../styles/generator/Accordion.css";

function AccordionCard({
    icon,
    title,
    content,
    isOpen,
    onClick,
    color = "#2563eb"
}) {

    return (

        <div className={`accordion-card ${isOpen ? "open" : ""}`}>

            <button
                type="button"
                className="accordion-header"
                onClick={onClick}
                aria-expanded={isOpen}
            >

                <div className="accordion-left">

                    <div
                        className="accordion-icon"
                        style={{ background: color }}
                    >
                        {icon}
                    </div>

                    <span className="accordion-title">
                        {title}
                    </span>

                </div>

                <span
                    className={`accordion-arrow ${
                        isOpen ? "rotate" : ""
                    }`}
                >
                    ▼
                </span>

            </button>

            {isOpen && (

                <div className="accordion-content">

                    {content}

                </div>

            )}

        </div>
    );
}

export default AccordionCard;