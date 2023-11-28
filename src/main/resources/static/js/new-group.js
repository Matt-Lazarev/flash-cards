import {GroupHttpClient} from "./http/group-http-client.js";
import {handleNewElementForm} from "./element/new-element.js";

const groupHttpClient = new GroupHttpClient()

document.getElementById("newElementForm").addEventListener("submit", (event) => {
    const saveGroupRq = (body) => groupHttpClient.saveGroup(body);
    handleNewElementForm(event, saveGroupRq,'/groups')
});
