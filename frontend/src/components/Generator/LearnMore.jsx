import React, { useState } from "react";
import AccordionCard from "./AccordionCard";
import "../../styles/generator/LearnMore.css";

function LearnMore({ interviewTips }) {

    const [openCard, setOpenCard] = useState("");

    const toggleCard = (id) => {

        setOpenCard(openCard === id ? "" : id);

    };

    const cards = [

        {
            id: "interview",
            icon: "🎯",
            color: "#ef4444",
            title: "Interview Tips",
            content: interviewTips
        }

    ];

    return (

        <div className="learn-section">

            <h2 className="learn-heading">
                📘 Learn More
            </h2>

            <div className="learn-grid">

                {

                    cards.map((card) => (

                        <AccordionCard

                            key={card.id}

                            icon={card.icon}

                            title={card.title}

                            color={card.color}

                            content={card.content}

                            isOpen={openCard === card.id}

                            onClick={() => toggleCard(card.id)}

                        />

                    ))

                }

            </div>

        </div>

    );

}

export default LearnMore;