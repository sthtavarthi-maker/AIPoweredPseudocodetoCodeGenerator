package com.keerthana.pseudocodegenerator.controller;

import com.keerthana.pseudocodegenerator.dto.HistoryResponse;
import com.keerthana.pseudocodegenerator.entity.User;
import com.keerthana.pseudocodegenerator.repository.UserRepository;
import com.keerthana.pseudocodegenerator.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final UserRepository userRepository;

    // =========================================================
    // GET HISTORY
    // =========================================================

    @GetMapping
    public ResponseEntity<List<HistoryResponse>> getHistory(
            Authentication authentication
    ) {

        User user = getAuthenticatedUser(authentication);

        List<HistoryResponse> history =
                historyService.getHistory(user.getId());

        return ResponseEntity.ok(history);
    }

    // =========================================================
    // DELETE HISTORY
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(
            @PathVariable Long id,
            Authentication authentication
    ) {

        User user = getAuthenticatedUser(authentication);

        historyService.deleteHistory(
                id,
                user.getId()
        );

        return ResponseEntity.noContent().build();
    }

    // =========================================================
    // GET CURRENT USER
    // =========================================================

    private User getAuthenticatedUser(
            Authentication authentication
    ) {

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException(
                    "User is not authenticated"
            );
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found"
                        )
                );
    }
}