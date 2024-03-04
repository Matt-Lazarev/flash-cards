import {GroupHttpClient} from "./http/group-http-client.js";
import {handleNewElementForm} from "./element/new-element.js";

const domainId = window.location.pathname.match(/\d+/)[0];

const groupHttpClient = new GroupHttpClient()

document.getElementById("newElementForm").addEventListener("submit", (event) => {
    const saveGroupRq = (body) => {
        body.domainId = domainId;
        return groupHttpClient.saveGroup(body);
    }
    handleNewElementForm(event, saveGroupRq,`/domains/${domainId}/groups`)
});
