import {createHttpRequest, createMultipartHttpRequest} from "./http-client.js";

const DOCUMENTS_API_URL = "/documents"

export class DocumentHttpClient{
    constructor() {}

    downloadDeck(deckId) {
        return createHttpRequest("GET", `${DOCUMENTS_API_URL}/download?deckId=${deckId}`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    downloadGroup(groupId) {
        return createHttpRequest("GET", `${DOCUMENTS_API_URL}/download?groupId=${groupId}`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    parseDocument(body){
        return createMultipartHttpRequest("POST", `${DOCUMENTS_API_URL}/parse`, body)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
}