import {addNewElementButton, showElements, showNoElementsMessage} from "./element/elements.js";
import {DeckHttpClient} from "./http/deck-http-client.js";
import {urlRoute} from "./http/url-routes.js";
import {downloadFile} from "./file/file-downloader.js";
import {DocumentHttpClient} from "./http/document-http-client.js";

const groupId = window.location.pathname.match(/\d+/)[0];
const deckHttpClient = new DeckHttpClient()
const documentHttpClient = new DocumentHttpClient()

window.addEventListener("DOMContentLoaded", () => {
    showDecks();
    addNewElementButton(`/groups/${groupId}/decks/new`);
});

const backButton = document.getElementById('backButton');
backButton.addEventListener('click', () => {
    urlRoute('/groups');
});

const elementName = document.getElementById("elementName");
function showDecks() {
    deckHttpClient.getAllDecksByGroupId(groupId)
        .then(data => {
            elementName.textContent = data.groupName;

            if (data.decks.length === 0) {
                showNoElementsMessage();
                return;
            }

            const urls = {
                clickElementUrl: `/groups/${groupId}/decks/{id}/flash-cards`,
                editElementUrl: `/groups/${groupId}/decks/{id}/edit`
            }

            data.decks.forEach(deck => {
                deck.childElementsCount = deck.flashCardsCount;
                deck.childElementsName = 'flash-cards';
            });

            const deleteElementAction = (id) => deleteDeck(id);
            const downloadDocumentAction = (id) => downloadDeck(id);
            showElements(data.decks, urls, deleteElementAction, downloadDocumentAction);
        })
}

function deleteDeck(deckId) {
    deckHttpClient.deleteDeck(deckId)
        .then(() => {
            const deckCard = document.getElementById(`elementCard-${deckId}`);
            const elementCard = deckCard.parentNode;
            elementCard.removeChild(deckCard);
            elementCard.parentNode.removeChild(elementCard);

            const elements = document.getElementsByTagName("element-card");
            if (elements.length === 0) {
                showNoElementsMessage();
            }
        });
}

function downloadDeck(deckId) {
    documentHttpClient.downloadDeck(deckId)
        .then(response => {
            downloadFile(response);
        });
}