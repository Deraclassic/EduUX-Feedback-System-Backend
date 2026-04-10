package com.dera.eduux_feedback_system.service;

import com.dera.eduux_feedback_system.entity.Feedback;
import com.dera.eduux_feedback_system.repository.FeedbackRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportService {

    private final FeedbackRepository feedbackRepository;

    public byte[] exportCsv() throws IOException {
        List<Feedback> feedbackList = feedbackRepository.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(out))) {

            // Header
            writer.writeNext(new String[]{
                    "ID", "Student Name", "Email", "Course",
                    "System Used", "Rating", "Category", "Comment", "Date"
            });

            // Rows
            for (Feedback f : feedbackList) {
                writer.writeNext(new String[]{
                        String.valueOf(f.getId()),
                        f.getUser().getName(),
                        f.getUser().getEmail(),
                        f.getCourseName(),
                        f.getSystemUsed(),
                        String.valueOf(f.getRating()),
                        f.getCategory().getName(),
                        f.getComment() != null ? f.getComment() : "",
                        f.getCreatedAt().toString()
                });
            }

            writer.flush();
            return out.toByteArray();
        }
    }

    public byte[] exportPdf() throws DocumentException {
        List<Feedback> feedbackList = feedbackRepository.findAll();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, out);
        document.open();

        // Title
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Paragraph title = new Paragraph("EduUX — Feedback Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Table
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1f, 2f, 2.5f, 2f, 2f, 1f, 2f, 3f});

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
        Font cellFont  = new Font(Font.FontFamily.HELVETICA, 8);

        String[] headers = {"ID", "Name", "Email", "Course", "System", "Rating", "Category", "Comment"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPadding(5);
            table.addCell(cell);
        }

        for (Feedback f : feedbackList) {
            table.addCell(new PdfPCell(new Phrase(String.valueOf(f.getId()), cellFont)));
            table.addCell(new PdfPCell(new Phrase(f.getUser().getName(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(f.getUser().getEmail(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(f.getCourseName(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(f.getSystemUsed(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(f.getRating()), cellFont)));
            table.addCell(new PdfPCell(new Phrase(f.getCategory().getName(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(f.getComment() != null ? f.getComment() : "", cellFont)));
        }

        document.add(table);
        document.close();
        return out.toByteArray();
    }
}