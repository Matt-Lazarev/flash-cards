import {urlRoute} from './http/url-routes.js';
import {FlashCardHttpClient} from "./http/flash-card-http-client.js";

const domainId = window.location.pathname.match(/\d+/g)[0];
const groupId = window.location.pathname.match(/\d+/g)[1];
const deckId = window.location.pathname.match(/\d+/g)[2];

const flashCardHttpClient = new FlashCardHttpClient()

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

    console.log(body);

    flashCardHttpClient.saveFlashCard(body)
        .then(() => {
            urlRoute(`/domains/${domainId}/groups/${groupId}/decks/${deckId}/flash-cards`)
        });
});