package com.lazarev.flashcards.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("flash-card")
public class FlashCardProperties {
    private Integer primaryCorrectAnswers;
    private Integer daysFactor;
    private Integer daysAdditional;
}
