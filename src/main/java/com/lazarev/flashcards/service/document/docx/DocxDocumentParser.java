package com.lazarev.flashcards.service.document.docx;

import com.lazarev.flashcards.dto.document.ParsedDocumentResult;
import com.lazarev.flashcards.dto.element.FlashCardDto;
import com.lazarev.flashcards.service.document.DocumentParser;
import lombok.SneakyThrows;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class DocxDocumentParser implements DocumentParser {
    private static final Set<String> FLASH_CARD_FIELDS_NAMES = Set.of("frontSide", "backSide", "examples");
    private static final int DEFAULT_COLUMNS_NUM = 3;
    private static final Field[] FLASH_CARD_FIELDS;

    static {
        FLASH_CARD_FIELDS = Arrays.stream(FlashCardDto.class.getDeclaredFields())
                .filter(field -> FLASH_CARD_FIELDS_NAMES.contains(field.getName()))
                .peek(field -> field.setAccessible(true))
                .toArray(Field[]::new);
    }

    @Override
    @SneakyThrows
    public ParsedDocumentResult parse(InputStream inputStream) {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage
                .load(inputStream);

        MainDocumentPart mainDocument = wordPackage.getMainDocumentPart();
        List<FlashCardDto> flashCards = getFlashCards(mainDocument);

        return new ParsedDocumentResult(flashCards);
    }

    @SneakyThrows
    private List<FlashCardDto> getFlashCards(MainDocumentPart mainDocument) {
        for (Object element : mainDocument.getContent()) {
            if (element instanceof JAXBElement<?> jaxbElement) {
                if (jaxbElement.getValue() instanceof Tbl table) {
                    List<Object> rows = table.getContent();

                    return parseRows(rows);
                }
            }
        }
        return List.of();
    }

    private List<FlashCardDto> parseRows(List<Object> rows) throws Exception {
        List<FlashCardDto> flashCards = new ArrayList<>();
        for (Object row : rows) {
            if (row instanceof Tr tableRow) {
                List<Object> cells = tableRow.getContent();
                if (cells.size() != DEFAULT_COLUMNS_NUM) {
                    continue;
                }
                FlashCardDto flashCard = parseCells(cells);
                flashCards.add(flashCard);
            }
        }
        return flashCards;
    }

    private FlashCardDto parseCells(List<Object> cells) throws Exception {
        FlashCardDto flashCard = new FlashCardDto();
        for (int i = 0; i < DEFAULT_COLUMNS_NUM; i++) {
            Object cell = cells.get(i);
            if (cell instanceof JAXBElement<?> jaxbCellElement) {
                if (jaxbCellElement.getValue() instanceof Tc tableCell) {
                    List<Object> cellContents = tableCell.getContent();
                    for (int j = 0; j < cellContents.size(); j++) {
                        Object cellContent = cellContents.get(j);
                        Field fieldToSet = FLASH_CARD_FIELDS[i];
                        if (cellContent instanceof P paragraph) {
                            String cellValue = getTextFromParagraph(paragraph);
                            if (j == 0) {
                                fieldToSet.set(flashCard, cellValue);
                            } else {
                                Object currentValue = fieldToSet.get(flashCard);
                                fieldToSet.set(flashCard, currentValue + "\n" + cellValue);
                            }
                        }
                    }
                }
            }
        }
        return flashCard;
    }

    private String getTextFromParagraph(P paragraph) {
        StringBuilder sb = new StringBuilder();

        for (Object run : paragraph.getContent()) {
            if (run instanceof R r) {
                for (Object runContent : r.getContent()) {
                    if (runContent instanceof JAXBElement<?> textElement) {
                        if (textElement.getValue() instanceof Text text) {
                            sb.append(text.getValue());
                        }
                    }
                }
            }
        }

        return sb.toString();
    }
}
