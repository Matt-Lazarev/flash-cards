import {urlRoute} from './http/url-routes.js';
import {FlashCardHttpClient} from "./http/flash-card-http-client.js";

const groupId = window.location.pathname.match(/\d+/g)[0];
const deckId = window.location.pathname.match(/\d+/g)[1];
const flashCardId = window.location.pathname.match(/\d+/g)[2];

const flashCardHttpClient = new FlashCardHttpClient()

window.addEventListener("DOMContentLoaded", () => {
    const frontSideInput = document.getElementById("frontSideInput");
    const backSideInput = document.getElementById("backSideInput");
    const examplesInput = document.getElementById("examplesInput");

    flashCardHttpClient.getFlashCardById(flashCardId)
        .then(data => {
            frontSideInput.value = data.frontSide;
            backSideInput.value = data.backSide;
            examplesInput.textContent = data.examples;
        })
});

document.getElementById("newElementForm").addEventListener("submit", (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);

    const frontSide = formData.get('frontSide');
    const backSide = formData.get('backSide');
    const examples = formData.get('examplesText');

    const body = {
        'frontSide' : frontSide,
        'backSide' : backSide,
        'examples' : examples,
        'deckId': deckId
    }

    flashCardHttpClient.updateFlashCard(flashCardId, body)
        .then(() => {
            urlRoute(`/groups/${groupId}/decks/${deckId}/flash-cards`)
        });
});