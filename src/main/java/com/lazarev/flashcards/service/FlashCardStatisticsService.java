package com.lazarev.flashcards.service;

import com.lazarev.flashcards.config.property.FlashCardProperties;
import com.lazarev.flashcards.entity.FlashCard;
import com.lazarev.flashcards.entity.FlashCardStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FlashCardStatisticsService {
    private final FlashCardProperties flashCardProperties;

    public void updateFlashCardStatistics(FlashCard flashCard, boolean isCorrectAnswer) {
        FlashCardStatistics statistics = flashCard.getFlashCardStatistics();

        if (isCorrectAnswer) {
            handleCorrectAnswer(statistics);
        } else {
            handleIncorrectAnswer(statistics);
        }
    }

    public void resetFlashCardStatistics(FlashCard flashCard){
        FlashCardStatistics statistics = flashCard.getFlashCardStatistics();
        resetStatistics(statistics);
    }

    private void handleCorrectAnswer(FlashCardStatistics statistics){
        int correctAnswers = statistics.getCorrectAnswers() + 1;
        statistics.setCorrectAnswers(correctAnswers);
        if (correctAnswers == flashCardProperties.getPrimaryCorrectAnswers()) {
            handlePrimaryLearntFlashCard(statistics);
        } else if (correctAnswers > flashCardProperties.getPrimaryCorrectAnswers()) {
            handleNextAfterPrimaryCorrectAnswer(statistics);
        }
    }

    private void handlePrimaryLearntFlashCard(FlashCardStatistics statistics){
        LocalDate primaryMemorizationDate = LocalDate.now();
        statistics.setPrimaryMemorizationDate(primaryMemorizationDate);
        statistics.setNextLearnDate(primaryMemorizationDate.plusDays(1));
        statistics.setDaysToNextLearn(1);
    }

    private void handleNextAfterPrimaryCorrectAnswer(FlashCardStatistics statistics){
        int daysToNextLearn = statistics.getDaysToNextLearn();

        //ax + b
        int daysFactor = flashCardProperties.getDaysFactor();
        int daysAdditional = flashCardProperties.getDaysAdditional();
        int daysToAdd = daysFactor * daysToNextLearn + daysAdditional;

        statistics.setNextLearnDate(LocalDate.now().plusDays(daysToAdd));
        statistics.setDaysToNextLearn(daysToAdd);
    }

    private void handleIncorrectAnswer(FlashCardStatistics statistics){
        resetStatistics(statistics);
    }

    private void resetStatistics(FlashCardStatistics statistics){
        statistics.setCorrectAnswers(0);
        statistics.setDaysToNextLearn(null);
        statistics.setPrimaryMemorizationDate(null);
        statistics.setNextLearnDate(null);
    }
}
