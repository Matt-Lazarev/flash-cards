import {FlashCardHttpClient} from "./http/flash-card-http-client.js";
import {urlRoute} from "./http/url-routes.js";

const groupId = window.location.pathname.match(/(\d+)/g)[0];
const deckId = window.location.pathname.match(/(\d+)/g)[1];
const flashCardHttpClient = new FlashCardHttpClient()

let flashCards = null;
let flashCardsSize = 0;
let nextFlashCardIndex = 0;
let isFrontSideShown = true;

let learnMode = null;

const parametersForm = document.getElementById('parametersForm');
const parametersContainer = document.getElementById('parametersContainer');
const alphabeticButton = document.getElementById('alphabeticButton');
const randomButton = document.getElementById('randomButton');
const wordsOrderContainer = document.getElementById('wordsOrder');
const wordsOrderText = document.getElementById('wordsOrderText');
const flashcardContainer = document.getElementById('flashcardContainer');
const flashcard = document.getElementById('flashcard');
const frontSide = document.getElementById('front');
const frontSideText = document.getElementById('frontText');
const backSide = document.getElementById('back');
const backSideText = document.getElementById('backText');
const backSideExamples = document.getElementById('examplesBlock');
const stopButton = document.getElementById('stopButton');
const nextButton = document.getElementById('nextButton');
const prevButton = document.getElementById('prevButton');
const statsButtons = document.getElementById('statsButtons');
const cancelButton = document.getElementById('cancelButton');
const confirmButton = document.getElementById('confirmButton');
const flashCardCounterText = document.getElementById('flashcardCounter');

parametersForm.addEventListener('submit', (event) => {
    event.preventDefault();
    parametersContainer.style.display = 'none';
    flashcardContainer.style.display = 'flex';

    const formData = new FormData(event.target);

    const mode = formData.get('mode');
    const sort = formData.get('sort');
    const order = formData.get('order');
    const option = formData.get('option');

    learnMode = mode;

    if(mode === 'ALL_WORDS'){
        statsButtons.style.visibility = 'hidden';
    }

    const deckNameElement = document.getElementById('deckName');
    flashCardHttpClient.getAllFlashCardInOrderAndSort(deckId, mode, sort, order, option)
        .then(res => {
            flashCards = res.flashCards;
            flashCardsSize = res.flashCards.length;
            deckNameElement.textContent = res.deckName;

            if(flashCardsSize === 0){
                frontSideText.textContent = mode === 'ALL_WORDS'
                    ? 'There are no flash-cards in this deck'
                    : 'There are no unstudied flash-cards';
                backSideText.textContent = '';
                statsButtons.style.visibility = 'hidden';
            }
            else {
                setFlashCardCounter(nextFlashCardIndex)
                showFlashCard();
            }
        })
});

alphabeticButton.addEventListener('click', ()=>{
    wordsOrderContainer.style.display = 'flex';
    wordsOrderText.style.display = 'block';
});

randomButton.addEventListener('click', ()=>{
    wordsOrderContainer.style.display = 'none';
    wordsOrderText.style.display = 'none';
});

flashcard.addEventListener('click', () => {
    if(isFrontSideShown){
        frontSide.style.display = 'none';
        backSide.style.display = 'flex';
        isFrontSideShown = false;
    }
    else {
        isFrontSideShown = true;
        backSide.style.display = 'none';
        frontSide.style.display = 'flex';
    }
});

nextButton.addEventListener('click', () => {
    if(flashCards && nextFlashCardIndex < flashCards.length-1){
        prepareForNextFlashCard(nextFlashCardIndex+1);
        showFlashCard();
    }
});

prevButton.addEventListener('click', () => {
    if(flashCards && nextFlashCardIndex > 0){
        prepareForNextFlashCard(nextFlashCardIndex-1);
        showFlashCard();
    }
});

stopButton.addEventListener('click', () => {
    urlRoute(`/groups/${groupId}/decks/${deckId}/flash-cards`)
});

confirmButton.addEventListener('click', () => {
    if(!flashCards[nextFlashCardIndex].studied){
        const flashCardId = flashCards[nextFlashCardIndex].id;
        const body = {
            id: flashCardId,
            deckId: deckId,
            isCorrectAnswer: true,
            mode : learnMode
        };
        flashCardHttpClient.updateFlashCard(flashCardId, body);
        flashCards[nextFlashCardIndex].studied = true;
    }
    statsButtons.style.visibility = 'hidden';
    nextButton.click();
});

cancelButton.addEventListener('click', () => {
    if(!flashCards[nextFlashCardIndex].studied){
        const flashCardId = flashCards[nextFlashCardIndex].id;
        const body = {
            id: flashCardId,
            deckId: deckId,
            isCorrectAnswer: false,
            mode : learnMode
        };
        flashCardHttpClient.updateFlashCard(flashCardId, body);
        flashCards[nextFlashCardIndex].studied = true;
    }
    statsButtons.style.visibility = 'hidden';
    nextButton.click();
});

function showFlashCard(){
    const flashCard = flashCards[nextFlashCardIndex];

    if(flashCard.studied){
        statsButtons.style.visibility = 'hidden';
    }
    else if (learnMode !== 'ALL_WORDS') {
        statsButtons.style.visibility = 'visible';
    }

    frontSideText.textContent = flashCard.frontSide;

    const baskSideTexts = flashCard.backSide.split('\n');
    baskSideTexts.forEach(text => {
        const newTextP = document.createElement('p');
        newTextP.textContent = text;
        backSideText.appendChild(newTextP);
    })

    if(flashCard.examples){
        const examples = flashCard.examples.split('\n');
        examples.forEach(example => {
            const newExampleP = document.createElement('p');
            newExampleP.textContent = example;
            backSideExamples.appendChild(newExampleP);
        })
    }
}

function prepareForNextFlashCard(flashCardCounter){
    isFrontSideShown = true;
    frontSide.style.display = 'flex';
    backSide.style.display = 'none';
    backSideText.innerHTML = "";
    backSideExamples.innerHTML = "";
    nextFlashCardIndex = flashCardCounter;
    setFlashCardCounter(flashCardCounter)
}

function setFlashCardCounter(flashCardCounter){
    const len = flashCardsSize;
    const current = flashCardCounter+1;
    flashCardCounterText.textContent = `${current} / ${len}`;
}