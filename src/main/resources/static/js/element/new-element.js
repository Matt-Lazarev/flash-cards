import {urlRoute} from "../http/url-routes.js";
import {showValidationErrors} from "./element-validator.js";

export function handleNewElementForm(event, httpClientRq, url){
    event.preventDefault();

    const formData = new FormData(event.target);

    const groupName = formData.get('name');
    const groupDescription = formData.get('description');

    const body = {
        'name' : groupName,
        'description' : groupDescription
    }

    httpClientRq(body).then((res) => {
        if (res.valid) {
            urlRoute(url);
        } else {
            showValidationErrors(res);
        }
    })
}
