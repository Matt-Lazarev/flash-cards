import {addNewElementButton, showElements, showNoElementsMessage} from "./element/elements.js";
import {DomainHttpClient} from "./http/domain-http-client.js";

const domainHttpClient = new DomainHttpClient();

window.addEventListener("DOMContentLoaded",  () => {
    showDomains();
    addNewElementButton("/domains/new");
});

function showDomains(){
    domainHttpClient.getAllDomains()
        .then(data => {
            if(data.length === 0){
                showNoElementsMessage();
                return;
            }
            const urls = {
                clickElementUrl : '/domains/{id}/groups',
                editElementUrl: '/domains/{id}/edit'
            }
            data.forEach(domain => {
                domain.childElementsCount = domain.groupsCount;
                domain.childElementsName = 'groups';
            });

            const deleteElementAction = (id) => deleteDomain(id);
            const downloadDocumentAction = (id) => downloadDomain(id);
            showElements(data, urls, deleteElementAction, downloadDocumentAction);
        })
}

function deleteDomain(domainId){
    domainHttpClient.deleteDomain(domainId)
        .then(() => {
            const domainCard = document.getElementById(`elementCard-${domainId}`);
            const elementCard = domainCard.parentNode;
            elementCard.removeChild(domainCard);
            elementCard.parentNode.removeChild(elementCard);

            const elements = document.getElementsByTagName("element-card");
            if(elements.length === 0){
                showNoElementsMessage();
            }
        });
}

//TODO: add download domain support
function downloadDomain(domainId) {
    // documentHttpClient.downloadGroup(groupId)
    //     .then(response => {
    //         downloadFile(response);
    //     });
}