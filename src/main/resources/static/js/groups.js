import {GroupHttpClient} from "./http/group-http-client.js";
import {addNewElementButton, showElements, showNoElementsMessage} from "./element/elements.js";
import {downloadFile} from "./file/file-downloader.js";
import {DocumentHttpClient} from "./http/document-http-client.js";

const groupHttpClient = new GroupHttpClient();
const documentHttpClient = new DocumentHttpClient();

window.addEventListener("DOMContentLoaded",  () => {
    showGroups();
    addNewElementButton("/groups/new");
});

function showGroups(){
    groupHttpClient.getAllGroups()
        .then(data => {
            if(data.length === 0){
                showNoElementsMessage();
                return;
            }
            const urls = {
                clickElementUrl : '/groups/{id}/decks',
                editElementUrl: '/groups/{id}/edit'
            }
            data.forEach(group => {
                group.childElementsCount = group.decksCount;
                group.childElementsName = 'decks';
            });

            const deleteElementAction = (id) => deleteGroup(id);
            const downloadDocumentAction = (id) => downloadGroup(id);
            showElements(data, urls, deleteElementAction, downloadDocumentAction);
        })
}

function deleteGroup(groupId){
    groupHttpClient.deleteGroup(groupId)
        .then(() => {
            const groupCard = document.getElementById(`elementCard-${groupId}`);
            const elementCard = groupCard.parentNode;
            elementCard.removeChild(groupCard);
            elementCard.parentNode.removeChild(elementCard);

            const elements = document.getElementsByTagName("element-card");
            if(elements.length === 0){
                showNoElementsMessage();
            }
        });
}

function downloadGroup(groupId) {
    documentHttpClient.downloadGroup(groupId)
        .then(response => {
            downloadFile(response);
        });
}