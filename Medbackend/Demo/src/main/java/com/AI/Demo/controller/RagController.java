package com.AI.Demo.controller;

import com.AI.Demo.Service.PdfIngestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rag")
public class RagController {

    private final PdfIngestionService pdfIngestionService;

    public RagController(PdfIngestionService pdfIngestionService) {
        this.pdfIngestionService=pdfIngestionService;
    }

    @GetMapping("/process")
    public String processPdf() throws IOException {

        String path="C:/Users/HP/Desktop/MedHub/Medbackend/Demo/microserviceIBM.pdf";
//        "C:\Users\HP\Desktop\MedHub\Medbackend\Demo\microserviceIBM.pdf"
            pdfIngestionService.ingestPdf(path);

        return "Vector embeding complete";
    }
}
