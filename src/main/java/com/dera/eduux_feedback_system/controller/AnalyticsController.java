package com.dera.eduux_feedback_system.controller;

import com.dera.eduux_feedback_system.dto.response.AnalyticsResponse;
import com.dera.eduux_feedback_system.dto.response.ApiResponse;
import com.dera.eduux_feedback_system.dto.response.RecommendationResponse;
import com.dera.eduux_feedback_system.service.AnalyticsService;
import com.dera.eduux_feedback_system.service.ExportService;
import com.dera.eduux_feedback_system.service.RecommendationService;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final RecommendationService recommendationService;
    private final ExportService exportService;

    @GetMapping("/analytics")
    public ResponseEntity<ApiResponse<AnalyticsResponse>> getAnalytics() {
        return ResponseEntity.ok(ApiResponse.success(analyticsService.getAnalytics(), "Analytics retrieved"));
    }

    @GetMapping("/recommendations")
    public ResponseEntity<ApiResponse<List<RecommendationResponse>>> getRecommendations() {
        return ResponseEntity.ok(ApiResponse.success(recommendationService.getRecommendations(), "Recommendations generated"));
    }

    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportCsv() throws IOException {
        byte[] data = exportService.exportCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=feedback_report.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(data);
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPdf() throws DocumentException {
        byte[] data = exportService.exportPdf();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=feedback_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }
}
