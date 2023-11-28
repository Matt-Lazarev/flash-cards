import {urlRoute} from "../http/url-routes.js";
import {openConfirmModalWindow} from "../confirm-modal-window.js";

const elementList = document.getElementById("elementList");

export function showElements(data, urls, deleteElementAction, downloadDocumentAction) {
    fixAlignment();

    if (data.length === 0) {
        const header = document.createElement("h3");
        header.textContent = 'There are no elements, use button above to add';
        elementList.appendChild(header);
        return;
    }
    data.forEach(newElement => {
        const newElementCard = document.createElement("element-card");
        elementList.appendChild(newElementCard);

        newElementCard.childNodes[1].id = `elementCard-${newElement.id}`

        let elementDescriptionNode;
        let elementCountNode;
        newElementCard.childNodes[1].childNodes.forEach(node => {
            switch (node.tagName) {
                case 'H3':
                    node.textContent = newElement.name;
                    break;
                case 'P':
                    if(node.classList.contains('element-description')){
                        node.textContent = newElement.description;
                        elementDescriptionNode = node;
                    }
                    else {
                        node.textContent = `${newElement.childElementsCount} ${newElement.childElementsName}`
                        elementCountNode = node;
                    }
                    break;
            }
        });
        fixAlignment(elementDescriptionNode, elementCountNode);

        const menu = document.querySelector(`#${newElementCard.childNodes[1].id} .meatball-menu`);
        newElementCard.addEventListener('click', (event) => {
            if (!menu.contains(event.target)) {
                urlRoute(urls.clickElementUrl.replace('{id}', newElement.id));
            }
        });

        const editButton = document.querySelector(`#${newElementCard.childNodes[1].id} .edit-button`);
        editButton.addEventListener('click', (event) => {
            urlRoute(urls.editElementUrl.replace('{id}', newElement.id));
        });

        const deleteButton = document.querySelector(`#${newElementCard.childNodes[1].id} .delete-button`);
        deleteButton.addEventListener('click', (event) => {
            openConfirmModalWindow(deleteElementAction, newElement.id)
        });

        const downloadButton = document.querySelector(`#${newElementCard.childNodes[1].id} .download-button`);
        downloadButton.addEventListener('click', (event) => {
            downloadDocumentAction(newElement.id);
        });
    })
}

export function addNewElementButton(url) {
    const addElementButton = document.getElementById("addElementButton");
    addElementButton.addEventListener("click", () => {
        urlRoute(url);
    });
}

export function showNoElementsMessage() {
    const header = document.createElement("h3");
    header.textContent = 'There are no elements, use button above to add';
    elementList.appendChild(header);
}

function fixAlignment(upperElement, lowerElement){
    if(upperElement && lowerElement){
        const upperElementHeight = upperElement.clientHeight;
        lowerElement.style.bottom = (-68 + upperElementHeight) + "px"
    }
}