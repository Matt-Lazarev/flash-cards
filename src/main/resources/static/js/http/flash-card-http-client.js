import {createHttpRequest} from "./http-client.js";

const FLASH_CARD_API_URL = "/flash-cards"

export class FlashCardHttpClient{
    constructor() {}

    getAllFlashCardByDeckId(deckId){
        return createHttpRequest("GET", `${FLASH_CARD_API_URL}?deckId=${deckId}`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    getAllFlashCardInOrderAndSort(deckId, mode, sort, order, option){
        let url = `${FLASH_CARD_API_URL}?deckId=${deckId}&mode=${mode}&sort=${sort}`
        if(order){
            url += `&order=${order}`;
        }
        if(option){
            url += `&option=${option}`;
        }
        return createHttpRequest("GET", url)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    getFlashCardById(flashCardId){
        return createHttpRequest("GET", `${FLASH_CARD_API_URL}/${flashCardId}`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    saveFlashCard(body){
        return createHttpRequest("POST", FLASH_CARD_API_URL, body)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    updateFlashCard(flashCardId, body){
        return createHttpRequest("PUT", `${FLASH_CARD_API_URL}/${flashCardId}`, body)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    deleteFlashCard(flashCardId){
        return createHttpRequest("DELETE", `${FLASH_CARD_API_URL}/${flashCardId}`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
}