import React, { useState } from "react";

import HistoryCard from "./HistoryCard";
import HistoryModal from "./HistoryModal";

import { deleteHistory } from "../../services/api";


const HistoryList = ({
    historyData,
    setHistoryData
}) => {

    const [selectedItem, setSelectedItem] =
        useState(null);


    // =========================================================
    // COPY
    // =========================================================

    const copyCode = async (code) => {

        try {

            await navigator.clipboard.writeText(code);

            alert("Code copied successfully!");

        } catch (error) {

            console.error(
                "Failed to copy code:",
                error
            );

            alert("Failed to copy code.");

        }
    };


    // =========================================================
    // DOWNLOAD
    // =========================================================

    const downloadCode = (item) => {

        let extension = "txt";


        switch (
            item.language?.toLowerCase()
        ) {

            case "java":
                extension = "java";
                break;

            case "python":
                extension = "py";
                break;

            case "c":
                extension = "c";
                break;

            case "c++":
                extension = "cpp";
                break;

            case "javascript":
                extension = "js";
                break;

            case "typescript":
                extension = "ts";
                break;

            case "c#":
                extension = "cs";
                break;

            case "go":
                extension = "go";
                break;

            case "rust":
                extension = "rs";
                break;

            default:
                extension = "txt";
                    }


        const blob = new Blob(
            [item.generatedCode || ""],
            {
                type: "text/plain"
            }
        );


        const url =
            window.URL.createObjectURL(blob);


        const link =
            document.createElement("a");


        link.href = url;


        link.download =
            `generated-code-${item.id}.${extension}`;


        document.body.appendChild(link);

        link.click();

        document.body.removeChild(link);


        window.URL.revokeObjectURL(url);
    };


    // =========================================================
    // DELETE
    // =========================================================

    const handleDelete = async (id) => {

        const confirmDelete =
            window.confirm(
                "Are you sure you want to delete this history item?"
            );


        if (!confirmDelete) {
            return;
        }


        try {

            await deleteHistory(id);


            setHistoryData((previous) =>
                previous.filter(
                    (item) => item.id !== id
                )
            );


            if (
                selectedItem &&
                selectedItem.id === id
            ) {

                setSelectedItem(null);

            }


            alert(
                "History deleted successfully!"
            );

        } catch (error) {

            console.error(
                "Failed to delete history:",
                error
            );

            alert(
                "Failed to delete history."
            );
        }
    };


    // =========================================================
    // UI
    // =========================================================

    return (

        <>

            {historyData.length === 0 ? (

                <div>
                    No history found.
                </div>

            ) : (

                historyData.map((item) => (

                    <HistoryCard
                        key={item.id}
                        item={item}

                        onView={() =>
                            setSelectedItem(item)
                        }

                        onCopy={copyCode}

                        onDownload={downloadCode}

                        onDelete={handleDelete}
                    />

                ))

            )}


            <HistoryModal
                item={selectedItem}
                onClose={() =>
                    setSelectedItem(null)
                }
            />

        </>
    );
};


export default HistoryList;

