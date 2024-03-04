import {handleEditElementForm, loadContent} from "./element/edit-element.js";
import {DeckHttpClient} from "./http/deck-http-client.js";

const domainId =  window.location.pathname.match(/(\d+)/g)[0];
const groupId =  window.location.pathname.match(/(\d+)/g)[1];
const deckId = window.location.pathname.match(/(\d+)/g)[2];
const deckHttpClient = new DeckHttpClient()

window.addEventListener("DOMContentLoaded", () => {
    const getGroupByIdRq = () => deckHttpClient.getDeckById(deckId)
    loadContent(getGroupByIdRq)
});

document.getElementById("newElementForm").addEventListener("submit", (event) => {
    const updateDeckRq = (body) => deckHttpClient.updateDeck(deckId, body);
    handleEditElementForm(event, updateDeckRq,`/domains/${domainId}/groups/${groupId}/decks`)
});
