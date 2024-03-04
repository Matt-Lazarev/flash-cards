import {handleEditElementForm, loadContent} from "./element/edit-element.js";
import {GroupHttpClient} from "./http/group-http-client.js";

const domainId =  window.location.pathname.match(/(\d+)/g)[0];
const groupId = window.location.pathname.match(/(\d+)/g)[1];
const groupHttpClient = new GroupHttpClient()

window.addEventListener("DOMContentLoaded", () => {
    const getGroupByIdRq = () => groupHttpClient.getGroupById(groupId)
    loadContent(getGroupByIdRq)
});

document.getElementById("newElementForm").addEventListener("submit", (event) => {
    const updateGroupRq = (body) => groupHttpClient.updateGroup(groupId, body);
    handleEditElementForm(event, updateGroupRq,`/domains/${domainId}/groups`)
});