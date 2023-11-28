package com.lazarev.flashcards.service.document.docx;

import com.lazarev.flashcards.dto.document.DocumentDto;
import com.lazarev.flashcards.dto.element.FlashCardDto;
import com.lazarev.flashcards.service.document.DocumentCreator;
import lombok.SneakyThrows;
import org.docx4j.jaxb.Context;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.lazarev.flashcards.enums.FileExtension.*;

@Service
public class DocxDocumentCreator implements DocumentCreator {
    private static final String[] DOCUMENT_HEADERS = {"Front-side", "Back-side", "Examples"};
    private static final int COLUMNS_NUM = 3;
    private static final int COLUMN_HEADER_FONT_SIZE = 12;
    private static final int DOCUMENT_HEADER_FONT_SIZE = 16;

    private static final String EMPTY_DOCX_NAME = "files/empty.docx";
    private static final String EMPTY_ZIP_NAME = "files/empty.zip";

    private static final DocumentDto EMPTY_DOCX;
    private static final DocumentDto EMPTY_ZIP;

    static {
        try {
            ClassPathResource emptyDocx = new ClassPathResource(EMPTY_DOCX_NAME);
            EMPTY_DOCX = new DocumentDto(EMPTY_DOCX_NAME, DOCX, emptyDocx.getContentAsByteArray());

            ClassPathResource emptyZip = new ClassPathResource(EMPTY_ZIP_NAME);
            EMPTY_ZIP = new DocumentDto(EMPTY_ZIP_NAME, ZIP, emptyZip.getContentAsByteArray());
        } catch (Exception ex) {
            throw new RuntimeException("Cannot start application: " + ex.getMessage(), ex);
        }
    }

    @Override
    @SneakyThrows
    public DocumentDto create(String documentName, List<FlashCardDto> data) {
        if (data.size() == 0) {
            return EMPTY_DOCX;
        }

        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        ObjectFactory factory = Context.getWmlObjectFactory();

        P headerParagraph = createHeaderParagraph(factory, documentName, 16);
        mainDocumentPart.getContent().add(headerParagraph);

        Tbl tbl = createTable(wordPackage, data.size());
        List<Object> rows = tbl.getContent();
        fillHeaderRow(factory, (Tr) rows.get(0));

        for (int i = 1; i < rows.size(); i++) {
            FlashCardDto flashCard = data.get(i - 1);
            fillRow(factory, (Tr) rows.get(i), flashCard);
        }

        mainDocumentPart.getContent().add(tbl);

        String filename = documentName + "." + DOCX.getName();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wordPackage.save(baos);

        return new DocumentDto(filename, DOCX, baos.toByteArray());
    }

    @Override
    public DocumentDto createZip(String documentName, List<String> elementsNames, List<List<FlashCardDto>> data) {
        if (data.size() == 0) {
            return EMPTY_ZIP;
        }

        List<DocumentDto> documents = new ArrayList<>();
        int size = elementsNames.size();
        for (int i = 0; i < size; i++) {
            String elementName = elementsNames.get(i);
            List<FlashCardDto> flashCards = data.get(i);

            DocumentDto document = create(elementName, flashCards);
            documents.add(document);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(baos)) {
            for (DocumentDto document : documents) {
                ZipEntry zipEntry = new ZipEntry(document.filename());
                zip.putNextEntry(zipEntry);

                byte[] bytes = document.content();
                zip.write(bytes, 0, bytes.length);
            }
        }
        catch (Exception ex) {
            return EMPTY_ZIP;
        }

        String filename = documentName + "." + ZIP.getName();
        return new DocumentDto(filename, ZIP, baos.toByteArray());
    }

    private Tbl createTable(WordprocessingMLPackage wordPackage, int rows){
        int rowsNumber = rows + 1;
        int colsNumber = COLUMNS_NUM;
        int writableWidth = wordPackage.getDocumentModel()
                .getSections().get(0).getPageDimensions().getWritableWidthTwips();

        return TblFactory.createTable(rowsNumber, colsNumber, writableWidth / colsNumber);
    }

    private void fillHeaderRow(ObjectFactory factory, Tr headerRow){
        List<Object> headerCells = headerRow.getContent();
        for(int i=0; i<headerCells.size(); i++){
            ((Tc) headerCells.get(i)).getContent()
                    .add(createHeaderParagraph(factory, DOCUMENT_HEADERS[i], COLUMN_HEADER_FONT_SIZE));
        }
        removeEmptyParagraph(headerRow);
    }

    private void fillRow(ObjectFactory factory, Tr row, FlashCardDto flashCard){
        List<Object> cells = row.getContent();

        Tc frontSideCell = (Tc) cells.get(0);
        frontSideCell.getContent().add(createParagraph(factory, flashCard.getFrontSide()));

        Tc backSideCell = (Tc) cells.get(1);
        backSideCell.getContent().add(createParagraph(factory, flashCard.getBackSide()));

        Tc examplesCell = (Tc) cells.get(2);
        examplesCell.getContent().add(createParagraph(factory, flashCard.getExamples()));

        removeEmptyParagraph(row);
    }

    private P createHeaderParagraph(ObjectFactory factory, String text, int fontSize) {
        Text t = factory.createText();
        t.setValue(text);

        R r = factory.createR();
        r.getContent().add(t);

        P p = factory.createP();
        p.getContent().add(r);

        RPr rpr = factory.createRPr();
        PPr ppr = factory.createPPr();

        setBold(rpr);
        setFontSize(factory, rpr, fontSize);
        justifyCenter(factory, ppr);

        r.setRPr(rpr);
        p.setPPr(ppr);

        return p;
    }

    private P createParagraph(ObjectFactory factory, String text) {
        String[] textParts = text.split("\n");

        P p = factory.createP();
        for (int i = 0; i < textParts.length; i++) {
            Text t = factory.createText();
            t.setValue(textParts[i]);

            R r = factory.createR();
            r.getContent().add(t);

            if (i != textParts.length - 1) {
                Br br = factory.createBr();
                r.getContent().add(br);
            }

            p.getContent().add(r);
        }

        return p;
    }

    private void removeEmptyParagraph(Tr row) {
        for (Object tc : row.getContent()) {
            Tc cell = (Tc) tc;
            cell.getContent().remove(0);
        }
    }

    private void setBold(RPr rpr){
        BooleanDefaultTrue boldTrue = new BooleanDefaultTrue();
        boldTrue.setVal(Boolean.TRUE);
        rpr.setB(boldTrue);
    }

    private void setFontSize(ObjectFactory factory, RPr rpr, int fontSize){
        BigInteger fontSizeValue = new BigInteger(Integer.toString(fontSize * 2));
        HpsMeasure hpsMeasure = factory.createHpsMeasure();
        hpsMeasure.setVal(fontSizeValue);
        rpr.setSz(hpsMeasure);
    }

    private void justifyCenter(ObjectFactory factory, PPr ppr){
        Jc justification = factory.createJc();
        justification.setVal(JcEnumeration.CENTER);
        ppr.setJc(justification);
    }
}
