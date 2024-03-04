import {createHttpRequest} from "./http-client.js";

const DOMAIN_API_URL = "/domains"

export class DomainHttpClient {
    constructor() {}

    getAllDomains(){
        return createHttpRequest("GET", DOMAIN_API_URL)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    getDomainById(domainId){
        return createHttpRequest("GET", `${DOMAIN_API_URL}/${domainId}`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    saveDomain(body){
        return createHttpRequest("POST", DOMAIN_API_URL, body)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    updateDomain(domainId, body){
        return createHttpRequest("PUT", `${DOMAIN_API_URL}/${domainId}`, body)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    deleteDomain(domainId){
        return createHttpRequest("DELETE", `${DOMAIN_API_URL}/${domainId}`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    //TODO: add domain download support
    downloadDomain(domainId) {
        return createHttpRequest("GET", `${DOMAIN_API_URL}/${domainId}/download`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
}