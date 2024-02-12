package com.example.company.Service.ServiceImpl;

import com.itextpdf.html2pdf.HtmlConverter;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;

@Service
public class PdfService {
    @Autowired
    private TemplateEngine templateEngine;

    private Logger logger = LoggerFactory.getLogger(PdfService.class);

    public InputStream createPdf() throws IOException {

      /*  logger.info("create Pdf started");
        Context context=new Context();
        context.setVariable("name","demo");
        String process = templateEngine.process("emai-template.html", context);
        String title = "Welcome";
        String content = "Hi Pooja,How are you? ";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        PdfWriter.getInstance(document,out);

        document.open();
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,60);
        Paragraph titleparagraph = new Paragraph(title,titleFont);
        titleparagraph.setAlignment(Element.ALIGN_BASELINE);
        document.add(titleparagraph);

        Font paraFont = FontFactory.getFont(FontFactory.HELVETICA,20);
        Paragraph paragraph = new Paragraph(content);
        paragraph.add(new Chunk(process));
        document.add(paragraph);

        document.close();

        return  new ByteArrayInputStream(out.toByteArray()) ;*/

        Context context=new Context();
        context.setVariable("name","demo");
        String process = templateEngine.process("emai-template.html", context);

        File file = File.createTempFile("demo", ".pdf");
        HtmlConverter.convertToPdf(process,
                new FileOutputStream(file));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
              InputStream in = new FileInputStream(file);
        FileInputStream input = new FileInputStream(file);
        return in;

    }
}
