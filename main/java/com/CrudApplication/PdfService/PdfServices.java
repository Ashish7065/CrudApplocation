package com.CrudApplication.PdfService;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


@Service
public class PdfServices {

    // indicate the start of the PDF creation process.
    private Logger logger = LoggerFactory.getLogger(PdfServices.class);

    public ByteArrayInputStream CreatePdf() {

        logger.info("Create PDF Starter : ");

        // (Data write in byte array)  (It just hold the data in memory form of byteArray
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, out); // A PdfWriter is instantiated to write the document content to the ByteArrayOutputStream.
            document.open();

            // This for title
            PdfPTable combinedTable = new PdfPTable(1);
            combinedTable.setWidthPercentage(100);

            // Combine title and content into one cell
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            Phrase combinedPhrase = new Phrase();
            combinedPhrase.add(new Chunk("RECEIPT ", titleFont));
            combinedPhrase.add(new Chunk("This serves as proof for any payment made.", paraFont));

            PdfPCell combinedCell = new PdfPCell(combinedPhrase);
            combinedCell.setBackgroundColor(Color.LIGHT_GRAY);
            combinedCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            combinedCell.setPadding(9);
            combinedCell.setBorder(Rectangle.BOX);
            combinedTable.setSpacingAfter(10);
            combinedTable.addCell(combinedCell);

            // Add combined table to document
            document.add(combinedTable);


            // This is for create Date
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12, 22);
            Paragraph Datapara = new Paragraph("Date : 14/08/2024", dateFont);
            Datapara.setSpacingBefore(10);
            document.add(Datapara);


            // This is for create Description
            Font descFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph Descpara = new Paragraph(
                    "The below payment is made pertaining to the vehicle bearing registration number : 120345678",
                    descFont);
            Descpara.setSpacingBefore(5);
            document.add(Descpara);



            // This is for table PAYMENT DETAILS
            PdfPTable PaymenttitleTable = new PdfPTable(1);
            Font paymentfont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            // size

            PdfPCell paymenttitleCell = new PdfPCell(new Phrase("Payment Details", paymentfont));
            paymenttitleCell.setBackgroundColor(Color.LIGHT_GRAY);
            paymenttitleCell.setPadding(4);
            paymenttitleCell.setBorder(Rectangle.BOX);
            PaymenttitleTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            PaymenttitleTable.setWidthPercentage(100);
            PaymenttitleTable.setSpacingBefore(50);

            PaymenttitleTable.addCell(paymenttitleCell);
            document.add(PaymenttitleTable);


            // User payment details tables (A table with two columns is created to hold the payment details.)
            PdfPTable paymentDetailsTable = new PdfPTable(2);
            paymentDetailsTable.setWidths(new float[] { 1, 2 });

            PdfPCell cell1 = new PdfPCell(new Phrase(" Amount"));
            cell1.setPadding(6); // Add padding for spacing
            paymentDetailsTable.addCell(cell1);
            PdfPCell cell2 = new PdfPCell(new Phrase("$1000"));
            cell2.setPadding(6); // Add padding for spacing
            paymentDetailsTable.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase(" Purpose"));
            cell3.setPadding(6); // Add padding for spacing
            cell3.setMinimumHeight(70);
            paymentDetailsTable.addCell(cell3);
            PdfPCell cell4 = new PdfPCell(new Phrase("Tuition Fees"));
            cell4.setPadding(6); // Add padding for spacing
            paymentDetailsTable.addCell(cell4);

            PdfPCell cell5 = new PdfPCell(new Phrase(" Mode of payment"));
            cell5.setPadding(6); // Add padding for spacing
            paymentDetailsTable.addCell(cell5);
            PdfPCell cell6 = new PdfPCell(new Phrase("Paypal"));
            cell6.setPadding(6); // Add padding for spacing
            paymentDetailsTable.addCell(cell6);

            PdfPCell cell7 = new PdfPCell(new Phrase(" Payment reference No"));
            cell7.setPadding(6); // Add padding for spacing
            paymentDetailsTable.addCell(cell7);
            PdfPCell cell8 = new PdfPCell(new Phrase("99999999999"));
            cell8.setPadding(6); // Add padding for spacing
            paymentDetailsTable.addCell(cell8);

            paymentDetailsTable.setSpacingAfter(60);
            paymentDetailsTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            paymentDetailsTable.setWidthPercentage(100);
            document.add(paymentDetailsTable);



            // Paid By Section
            PdfPTable PaidtitleTable = new PdfPTable(1);
            Font paidfont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16); // Here we have define the font

            PdfPCell PaidtitleCell = new PdfPCell(new Phrase("Paid By ", paidfont));
            PaidtitleCell.setBackgroundColor(Color.LIGHT_GRAY); // Set background color
            PaidtitleCell.setPadding(4);
            PaidtitleCell.setBorder(Rectangle.BOX); // Optional: remove border for a cleaner look
            PaidtitleTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            PaidtitleTable.setWidthPercentage(100);
            PaidtitleTable.addCell(PaidtitleCell);
            document.add(PaidtitleTable);

            // User Paid details tables
            PdfPTable paidByTable = new PdfPTable(2);
            paidByTable.setWidths(new float[] { 1, 2 });
            PdfPCell celltable1 = new PdfPCell(new Phrase(" Name"));
            celltable1.setPadding(6); // Add padding for spacing
            paidByTable.addCell(celltable1);
            PdfPCell celltable2 = new PdfPCell(new Phrase("Ashish"));
            celltable2.setPadding(6); // Add padding for spacing
            paidByTable.addCell(celltable2);

            PdfPCell celltable3 = new PdfPCell(new Phrase(" NRI no."));
            celltable3.setPadding(6); // Add padding for spacing
            paidByTable.addCell(celltable3);
            PdfPCell celltable4 = new PdfPCell(new Phrase("8988788"));
            celltable4.setPadding(6); // Add padding for spacing
            paidByTable.addCell(celltable4);
            paidByTable.addCell("  Signature");

            //An image is added to the signature cell, with fallback text if the image cannot be loaded.
            try {
                Image img = Image.getInstance(
                        new ClassPathResource("img/uk.png")
                                .getPath());
                img.scaleToFit(60, 60); // Scale image to fit
                PdfPCell imageCell = new PdfPCell(img);
                imageCell.setBorder(Rectangle.BOX);
                imageCell.setMinimumHeight(80);
                imageCell.setPaddingLeft(2);
                imageCell.setPaddingTop(2);
                paidByTable.addCell(imageCell);
            } catch (Exception e) {
                e.printStackTrace();
                paidByTable.addCell("Signature not available");
            }

            paidByTable.setSpacingAfter(60);
            paidByTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            paidByTable.setWidthPercentage(100);

            document.add(paidByTable);

            // Received By Section
            PdfPTable ReceivedtitleTable = new PdfPTable(1);
            Font Recievedfont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            // size///
            PdfPCell RecievedtitleCell = new PdfPCell(new Phrase("RECEIVED BY", Recievedfont));
            RecievedtitleCell.setBackgroundColor(Color.LIGHT_GRAY);
            RecievedtitleCell.setPadding(4);
            RecievedtitleCell.setBorder(Rectangle.BOX);
            ReceivedtitleTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            ReceivedtitleTable.setWidthPercentage(100);
            ReceivedtitleTable.addCell(RecievedtitleCell);
            document.add(ReceivedtitleTable);

            // User Received details tables
            PdfPTable receivedByTable = new PdfPTable(2);
            receivedByTable.setWidths(new float[] { 1, 2 });
            PdfPCell cellreceivedtable = new PdfPCell(new Phrase(" Name"));
            cellreceivedtable.setPadding(5); // Add padding for spacing
            receivedByTable.addCell(cellreceivedtable);
            PdfPCell cellreceivedtable2 = new PdfPCell(new Phrase("Jessica"));
            celltable2.setPadding(5); // Add padding for spacing
            receivedByTable.addCell(cellreceivedtable2);

            PdfPCell cellreceivedtable3 = new PdfPCell(new Phrase(" NRI no."));
            cellreceivedtable3.setPadding(5); // Add padding for spacing
            receivedByTable.addCell(cellreceivedtable3);
            PdfPCell cellreceivedtable4 = new PdfPCell(new Phrase("988998998"));
            cellreceivedtable4.setPadding(5); // Add padding for spacing
            receivedByTable.addCell(cellreceivedtable4);
            receivedByTable.addCell("  Signature");

            try {
                Image img = Image.getInstance(
                        new ClassPathResource("img/suk2.jpg")
                                .getPath());
                img.scaleToFit(70, 70);
                PdfPCell imageCell = new PdfPCell(img);
                imageCell.setBorder(Rectangle.BOX);
                imageCell.setMinimumHeight(80);
                imageCell.setPaddingLeft(2);
                imageCell.setPaddingTop(2);
                receivedByTable.addCell(imageCell);
            } catch (Exception e) {
                e.printStackTrace();
                receivedByTable.addCell("Signature not available");
            }

            receivedByTable.setSpacingAfter(60);
            receivedByTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            receivedByTable.setWidthPercentage(100);

            document.add(receivedByTable);



        } catch (Exception e) {
            e.printStackTrace();
        }

        document.close(); //document is closed to complete the writing process.

        return new ByteArrayInputStream(out.toByteArray()); // use for read the data..(manipulating the data using this)

    }
}
