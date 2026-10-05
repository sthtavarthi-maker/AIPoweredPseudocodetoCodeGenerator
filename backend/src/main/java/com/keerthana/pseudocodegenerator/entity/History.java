package com.keerthana.pseudocodegenerator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String inputType;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String prompt;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String generatedCode;

    @Column(nullable = false)
    private String approach;

    @Column(nullable = false)
    private LocalDateTime generatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}