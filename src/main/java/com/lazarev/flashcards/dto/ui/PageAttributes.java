package com.lazarev.flashcards.dto.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class PageAttributes {
    private String mainScript;
    private String title;
    private String header;
    private String formId;
    private String authType;
    private String submitText;
    private String buttonText;
    private String elementType;
    private String nameLabelText;
    private String descriptionLabelText;
    private String fileLabelText;
    private Set<String> defaultElementTypes;
    private String frontSideText;
    private String backSideText;
    private String examplesText;
    private String error;
}
