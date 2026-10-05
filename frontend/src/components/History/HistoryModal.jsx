import React from "react";
import "../../styles/history/HistoryModal.css";

const HistoryModal = ({
    item,
    onClose
}) => {

    if (!item) {
        return null;
    }


    return (

        <div className="modal-overlay">

            <div className="history-modal">

                <div className="modal-header">

                    <h2>
                        Generated Code
                    </h2>

                    <button onClick={onClose}>
                        ✖
                    </button>

                </div>


                <div className="modal-details">

                    <span>
                        {item.language}
                    </span>

                    <span>
                        {item.inputType}
                    </span>

                    <span>
                        {item.approach}
                    </span>

                </div>


                <pre className="modal-code">
{item.generatedCode}
                </pre>

            </div>

        </div>
    );
};

export default HistoryModal;
