import {handleNewElementForm} from "./element/new-element.js";
import {DomainHttpClient} from "./http/domain-http-client.js";

const domainHttpClient = new DomainHttpClient()

document.getElementById("newElementForm").addEventListener("submit", (event) => {
    const saveDomainRq = (body) => domainHttpClient.saveDomain(body);
    handleNewElementForm(event, saveDomainRq,'/domains')
});
