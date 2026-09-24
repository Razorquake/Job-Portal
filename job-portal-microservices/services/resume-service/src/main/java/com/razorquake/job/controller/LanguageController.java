package com.razorquake.job.controller;

import com.razorquake.job.dto.LanguageRequest;
import com.razorquake.job.dto.LanguageResponse;
import com.razorquake.job.dto.ApiResponse;
import com.razorquake.job.service.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes/{resumeId}/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping
    public ResponseEntity<LanguageResponse> createLanguage(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid LanguageRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        languageService.createLanguage(
                                resumeId,
                                candidateId,
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<LanguageResponse>> getLanguages(
            @PathVariable Long resumeId
    ) {
        return ResponseEntity.ok(
                languageService.getLanguagesByResumeId(resumeId)
        );
    }

    @PutMapping("/{languageId}")
    public ResponseEntity<LanguageResponse> updateLanguage(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long languageId,
            @RequestBody @Valid LanguageRequest request
    ) {
        return ResponseEntity.ok(
                languageService.updateLanguage(
                        resumeId,
                        candidateId,
                        languageId,
                        request
                )
        );
    }

    @DeleteMapping("/{languageId}")
    public ResponseEntity<ApiResponse> deleteLanguage(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long languageId
    ) {
        languageService.deleteLanguage(resumeId, candidateId, languageId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        ApiResponse.builder()
                                .message("Language deleted successfully")
                                .success(true)
                                .build()
                );
    }

}
