import {DeckHttpClient} from "./http/deck-http-client.js";
import {urlRoute} from "./http/url-routes.js";
import {DocumentHttpClient} from "./http/document-http-client.js";
import {handleNewElementForm} from "./element/new-element.js";
import {showValidationErrors} from "./element/element-validator.js";

const domainId =  window.location.pathname.match(/(\d+)/g)[0];
const groupId = window.location.pathname.match(/(\d+)/g)[1];

const deckHttpClient = new DeckHttpClient();
const documentHttpClient = new DocumentHttpClient();

const fileInput = document.getElementById('file');
const selectedFile = document.getElementById('selectedFile');

document.getElementById('newElementForm').addEventListener('submit', (event) => {
    event.preventDefault();

    if(fileInput.files && fileInput.files[0]){
        const jsonFormData = new FormData(event.target);
        const formData = new FormData();

        formData.append('file', fileInput.files[0])
        formData.append('name', jsonFormData.get('name'));
        formData.append('description', jsonFormData.get('description'));
        formData.append('groupId', groupId);

        documentHttpClient.parseDocument(formData)
            .then((res) => {
                if(res.valid){
                    urlRoute(`/domains/${domainId}/groups/${groupId}/decks`)
                }
                else {
                    showValidationErrors(res);
                }
            });
    }
    else {
        const saveDeckRq = (body) => {
            body.groupId = groupId;
            return deckHttpClient.saveDeck(body);
        }
        handleNewElementForm(event, saveDeckRq,`/domains/${domainId}/groups/${groupId}/decks`)
    }
});

document.getElementById('fileButton').addEventListener('click', (event) => {
    event.preventDefault();
    fileInput.click();
})

fileInput.addEventListener('change', () => {
    selectedFile.textContent = fileInput.files[0].name;
});