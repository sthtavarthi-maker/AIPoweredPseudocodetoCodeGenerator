package com.keerthana.pseudocodegenerator.service;

import com.keerthana.pseudocodegenerator.dto.HistoryResponse;

import java.util.List;

public interface HistoryService {

    // =========================================================
    // GET USER HISTORY
    // =========================================================

    List<HistoryResponse> getHistory(Long userId);


    // =========================================================
    // SAVE HISTORY
    // =========================================================

    HistoryResponse saveHistory(
            String language,
            String inputType,
            String approach,
            String prompt,
            String generatedCode,
            Long userId
    );


    // =========================================================
    // DELETE HISTORY
    // =========================================================

    void deleteHistory(
            Long historyId,
            Long userId
    );
}
