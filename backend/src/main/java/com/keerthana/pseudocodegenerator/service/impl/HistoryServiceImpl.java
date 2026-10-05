package com.keerthana.pseudocodegenerator.service.impl;

import com.keerthana.pseudocodegenerator.dto.HistoryResponse;
import com.keerthana.pseudocodegenerator.entity.History;
import com.keerthana.pseudocodegenerator.entity.User;
import com.keerthana.pseudocodegenerator.repository.HistoryRepository;
import com.keerthana.pseudocodegenerator.repository.UserRepository;
import com.keerthana.pseudocodegenerator.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;


    // =========================================================
    // GET USER HISTORY
    // =========================================================

    @Override
    public List<HistoryResponse> getHistory(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return historyRepository.findByUser(user)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // =========================================================
    // SAVE HISTORY
    // =========================================================

    @Override
    public HistoryResponse saveHistory(
            String language,
            String inputType,
            String approach,
            String prompt,
            String generatedCode,
            Long userId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        History history = History.builder()
                .language(language)
                .inputType(inputType)
                .approach(approach)
                .prompt(prompt)
                .generatedCode(generatedCode)
                .generatedAt(LocalDateTime.now())
                .user(user)
                .build();

        History savedHistory =
                historyRepository.save(history);

        return convertToResponse(savedHistory);
    }


    // =========================================================
    // DELETE HISTORY
    // =========================================================

    @Override
    public void deleteHistory(
            Long historyId,
            Long userId
    ) {

        History history =
                historyRepository.findById(historyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "History item not found"
                                )
                        );

        // -----------------------------------------------------
        // SECURITY CHECK
        // -----------------------------------------------------

        if (history.getUser() == null ||
                !history.getUser().getId().equals(userId)) {

            throw new RuntimeException(
                    "You are not authorized to delete this history item"
            );
        }

        historyRepository.delete(history);
    }


    // =========================================================
    // ENTITY → DTO
    // =========================================================

    private HistoryResponse convertToResponse(
            History history
    ) {

        return new HistoryResponse(
                history.getId(),
                history.getLanguage(),
                history.getInputType(),
                history.getApproach(),
                history.getGeneratedCode()
        );
    }
}