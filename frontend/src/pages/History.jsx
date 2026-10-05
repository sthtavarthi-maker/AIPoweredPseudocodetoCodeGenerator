import React, { useEffect, useState } from "react";

import HistoryHeader from "../components/History/HistoryHeader";
import HistorySearch from "../components/History/HistorySearch";
import HistoryList from "../components/History/HistoryList";

import { getHistory } from "../services/api";

import "../styles/history/History.css";
import "../styles/history/HistoryCard.css";
import "../styles/history/HistoryModal.css";


const History = () => {

    const [searchTerm, setSearchTerm] = useState("");
    const [historyData, setHistoryData] = useState([]);
    const [loading, setLoading] = useState(true);


    // =========================================================
    // LOAD HISTORY
    // =========================================================

    useEffect(() => {

        const loadHistory = async () => {

            try {

                const data = await getHistory();

                setHistoryData(data);

            } catch (error) {

                console.error(
                    "Failed to load history:",
                    error
                );

            } finally {

                setLoading(false);

            }
        };


        loadHistory();

    }, []);


    // =========================================================
    // SEARCH
    // =========================================================

    const filteredHistory =
        historyData.filter((item) => {

            const search =
                searchTerm.toLowerCase();

            return (
                item.language
                    ?.toLowerCase()
                    .includes(search) ||

                item.inputType
                    ?.toLowerCase()
                    .includes(search) ||

                item.approach
                    ?.toLowerCase()
                    .includes(search) ||

                item.generatedCode
                    ?.toLowerCase()
                    .includes(search)
            );
        });


    // =========================================================
    // UI
    // =========================================================

    return (

        <div className="history-page">

            <div className="history-container">

                <HistoryHeader />

                <HistorySearch
                    searchTerm={searchTerm}
                    setSearchTerm={setSearchTerm}
                />


                {loading ? (

                    <div>
                        Loading history...
                    </div>

                ) : (

                    <HistoryList
                        historyData={filteredHistory}
                        setHistoryData={setHistoryData}
                    />

                )}

            </div>

        </div>
    );
};


export default History;
