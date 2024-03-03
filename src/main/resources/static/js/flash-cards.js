import {FlashCardHttpClient} from "./http/flash-card-http-client.js";
import {addNewElementButton} from "./element/elements.js";
import {urlRoute} from "./http/url-routes.js";
import {openConfirmModalWindow} from "./confirm-modal-window.js";

const groupId =  window.location.pathname.match(/(\d+)/g)[0];
const deckId = window.location.pathname.match(/(\d+)/g)[1];
const flashCardHttpClient = new FlashCardHttpClient()

const learnButton = document.getElementById('learnButton');
const backButton = document.getElementById('backButton');
const tableBody = document.getElementById('tableBody');

window.addEventListener("DOMContentLoaded",  () => {
    showFlashCards();
    addNewElementButton(`/groups/${groupId}/decks/${deckId}/flash-cards/new`);
});

function showFlashCards(){
    flashCardHttpClient.getAllFlashCardByDeckId(deckId)
        .then(data => {
            if(data.flashCards.length === 0){
                learnButton.style.visibility = 'hidden';
            }
            const elementName = document.getElementById("elementName");
            elementName.textContent = data.deckName;

            const elementsCount = document.getElementById("elementsCount");
            elementsCount.textContent = `Flash-cards: ${data.flashCards.length}`;

            addFlashCardsInTable(data.flashCards);
        })
}

function deleteFlashCard(flashCardId){
    flashCardHttpClient.deleteFlashCard(flashCardId)
        .then(() => {
            const flashCardRow = document.getElementById(`flashCard-${flashCardId}`);
            const table = flashCardRow.parentNode;
            table.deleteRow(flashCardRow.rowIndex-1);

            if(tableBody.rows.length === 0){
                learnButton.style.visibility = 'hidden';
                return;
            }

            updateRowNumbers();
            updateFlashCardsCounter();
        });
}

function updateFlashCardsCounter(){
    const elementsCount = document.getElementById("elementsCount");
    const counter = elementsCount.textContent.split(': ')[1];
    elementsCount.textContent = `Flash-cards: ${Number(counter) - 1}`
}

function addFlashCardsInTable(flashCards){
    let flashCardNumber = 1;
    flashCards.forEach(flashCard => {
        const tableRow = document.createElement('tr');
        tableRow.id = `flashCard-${flashCard.id}`;

        const actionsCell = document.createElement('td');
        const meatBallMenu = document.createElement("meat-ball-small-menu");
        actionsCell.appendChild(meatBallMenu);

        const numberCell = document.createElement('td');
        numberCell.textContent = (flashCardNumber++).toString();

        const wordCell = document.createElement('td');
        wordCell.classList.add('example-td');
        wordCell.textContent = flashCard.frontSide;

        const translateCell = document.createElement('td');
        translateCell.classList.add('example-td');
        translateCell.textContent = flashCard.backSide;

        const examplesCell = document.createElement('td');
        examplesCell.classList.add('example-td');
        examplesCell.textContent = flashCard.examples;

        [actionsCell, numberCell, wordCell, translateCell, examplesCell]
            .forEach(cell => tableRow.appendChild(cell));

        tableBody.appendChild(tableRow);

        addActionButtonsListeners(
            flashCard.id,
            `/groups/${groupId}/decks/${deckId}/flash-cards/${flashCard.id}/edit`,
            (id) => deleteFlashCard(id)
        );
    });
}

function addActionButtonsListeners(id, editUrl, deleteElementAction){
    const editButton = document.querySelector(`#flashCard-${id} .edit-button`);
    editButton.addEventListener('click', (event) => {
        urlRoute(editUrl, id);
    });

    const deleteButton = document.querySelector(`#flashCard-${id} .delete-button`);
    deleteButton.addEventListener('click', (event) => {
        openConfirmModalWindow(deleteElementAction, id)
    });
}

function updateRowNumbers(){
    for (let i = 0; i < tableBody.rows.length; i++) {
        const row = tableBody.rows[i];
        const firstCell = row.cells[1];
        firstCell.textContent = (i+1).toString()
    }
}

learnButton.addEventListener('click', ()=> {
    urlRoute(`/groups/${groupId}/decks/${deckId}/flash-cards/learn`)
});

backButton.addEventListener('click', () => {
    urlRoute(`/groups/${groupId}/decks`);
});