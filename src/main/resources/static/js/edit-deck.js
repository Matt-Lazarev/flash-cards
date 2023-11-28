import {handleEditElementForm, loadContent} from "./element/edit-element.js";
import {DeckHttpClient} from "./http/deck-http-client.js";

const groupId =  window.location.pathname.match(/(\d+)/g)[0];
const deckId = window.location.pathname.match(/(\d+)/g)[1];
const deckHttpClient = new DeckHttpClient()

window.addEventListener("DOMContentLoaded", () => {
    const getGroupByIdRq = () => deckHttpClient.getDeckById(deckId)
    loadContent(getGroupByIdRq)
});

document.getElementById("newElementForm").addEventListener("submit", (event) => {
    const updateDeckRq = (body) => deckHttpClient.updateDeck(deckId, body);
    handleEditElementForm(event, updateDeckRq,`/groups/${groupId}/decks`)
});
