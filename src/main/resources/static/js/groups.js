import {GroupHttpClient} from "./http/group-http-client.js";
import {addNewElementButton, showElements, showNoElementsMessage} from "./element/elements.js";
import {downloadFile} from "./file/file-downloader.js";
import {DocumentHttpClient} from "./http/document-http-client.js";
import {urlRoute} from "./http/url-routes.js";

const domainId = window.location.pathname.match(/\d+/)[0];

const groupHttpClient = new GroupHttpClient();
const documentHttpClient = new DocumentHttpClient();

window.addEventListener("DOMContentLoaded",  () => {
    showGroups();
    addNewElementButton(`/domains/${domainId}/groups/new`);
});

const backButton = document.getElementById('backButton');
backButton.addEventListener('click', () => {
    urlRoute('/domains');
});

const elementName = document.getElementById("elementName");
function showGroups(){
    groupHttpClient.getAllGroups(domainId)
        .then(data => {
            elementName.textContent = data.domainName;

            if (data.groups.length === 0) {
                showNoElementsMessage();
                return;
            }
            const urls = {
                clickElementUrl : `/domains/${domainId}/groups/{id}/decks`,
                editElementUrl: `/domains/${domainId}/groups/{id}/edit`
            }
            data.groups.forEach(group => {
                group.childElementsCount = group.decksCount;
                group.childElementsName = 'decks';
            });

            const deleteElementAction = (id) => deleteGroup(id);
            const downloadDocumentAction = (id) => downloadGroup(id);
            showElements(data.groups, urls, deleteElementAction, downloadDocumentAction);
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