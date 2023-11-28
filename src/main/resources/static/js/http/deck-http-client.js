import {createHttpRequest, createMultipartHttpRequest} from "./http-client.js";

const DECK_API_URL = "/decks"

export class DeckHttpClient{
    constructor() {}

    getAllDecksByGroupId(groupId){
        return createHttpRequest("GET", `${DECK_API_URL}?groupId=${groupId}`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    getDeckById(deckId){
        return createHttpRequest("GET", `${DECK_API_URL}/${deckId}`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    saveDeck(body){
        return createHttpRequest("POST", DECK_API_URL, body)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    updateDeck(deckId, body){
        return createHttpRequest("PUT", `${DECK_API_URL}/${deckId}`, body)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    deleteDeck(deckId){
        return createHttpRequest("DELETE", `${DECK_API_URL}/${deckId}`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    downloadDeck(deckId) {
        return createHttpRequest("GET", `${DECK_API_URL}/${deckId}/download`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
}