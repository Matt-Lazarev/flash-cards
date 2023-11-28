import {handleEditElementForm, loadContent} from "./element/edit-element.js";
import {GroupHttpClient} from "./http/group-http-client.js";

const groupId = window.location.pathname.match(/\d+/)[0];
const groupHttpClient = new GroupHttpClient()

window.addEventListener("DOMContentLoaded", () => {
    const getGroupByIdRq = () => groupHttpClient.getGroupById(groupId)
    loadContent(getGroupByIdRq)
});

document.getElementById("newElementForm").addEventListener("submit", (event) => {
    const updateGroupRq = (body) => groupHttpClient.updateGroup(groupId, body);
    handleEditElementForm(event, updateGroupRq,'/groups')
});