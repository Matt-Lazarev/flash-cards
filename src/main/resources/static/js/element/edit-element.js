import {urlRoute} from "../http/url-routes.js";
import {showValidationErrors} from "./element-validator.js";

export function loadContent(getElementRq){
    const nameInput = document.getElementById("elementName");
    const descriptionInput = document.getElementById("elementDescription");

    getElementRq()
        .then(data => {
            nameInput.value = data.name;
            descriptionInput.textContent = data.description;
        })
}

export function handleEditElementForm(event, httpClientRq, url) {
    event.preventDefault();

    const formData = new FormData(event.target);

    const groupName = formData.get("name");
    const groupDescription = formData.get("description");

    const body = {
        "name": groupName,
        "description": groupDescription
    }

    httpClientRq(body).then((res) => {
        if (res.valid) {
            urlRoute(url);
        } else {
            showValidationErrors(res);
        }
    })
}
