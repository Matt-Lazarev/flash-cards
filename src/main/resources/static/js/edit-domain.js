import {handleEditElementForm, loadContent} from "./element/edit-element.js";
import {DomainHttpClient} from "./http/domain-http-client.js";

const domainId = window.location.pathname.match(/\d+/)[0];
const domainHttpClient = new DomainHttpClient()

window.addEventListener("DOMContentLoaded", () => {
    const getDomainByIdRq = () => domainHttpClient.getDomainById(domainId)
    loadContent(getDomainByIdRq)
});

document.getElementById("newElementForm").addEventListener("submit", (event) => {
    const updateDomainRq = (body) => domainHttpClient.updateDomain(domainId, body);
    handleEditElementForm(event, updateDomainRq,'/domains')
});