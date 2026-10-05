import React from "react";
import "../../styles/history/HistoryCard.css";

const HistoryCard = ({ item, onView, onCopy, onDownload, onDelete }) => {

    return (

        <div className="history-card">

            <div className="history-card-top">

                <div className="history-left">

                    <span className="language">
                        {item.language}
                    </span>

                    <h2>
                        {item.title}
                    </h2>

                </div>

                <div className="history-right">

                    <span className="date">
                        {item.date}
                    </span>

                </div>

            </div>

            <div className="history-tags">

                <span className="tag">
                    {item.inputType}
                </span>

                <span className="tag optimal">
                    {item.approach}
                </span>

            </div>

            <div className="history-code">

                <pre>{item.generatedCode}</pre>

            </div>

            <div className="history-actions">

               <button onClick={onView}>
                👁 View
               </button>

                <button onClick={() => onCopy(item.generatedCode)}>
                 📋 Copy
                </button>

                <button onClick={() => onDownload(item)}>
                 ⬇ Download
                </button>

                <button
               className="delete-btn"
                 onClick={() => onDelete(item.id)}
                 >
                   🗑 Delete
                 </button>

            </div>

        </div>

    );

};

export default HistoryCard;