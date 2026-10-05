import React from "react";
import "../../styles/history/Search.css";

const HistorySearch = ({ searchTerm, setSearchTerm }) => {
  return (
    <div className="history-search">
      <input
        type="text"
        placeholder="🔍 Search by title, language, input type or date..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
    </div>
  );
};

export default HistorySearch;