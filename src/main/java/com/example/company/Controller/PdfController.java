package com.example.company.Controller;

import com.example.company.Service.ServiceImpl.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/pdf")
public class PdfController {
    @Autowired
    private PdfService pdfService;

    @GetMapping("/createPdf")
    public ResponseEntity<?> createPdf() throws IOException {
        InputStreamResource file = new InputStreamResource(pdfService.createPdf());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "invoice.pdf")
                .contentType(MediaType.parseMediaType("application/pdf"))
                .contentType(MediaType.parseMediaType("application/pdf")).body(file);

    }
}
