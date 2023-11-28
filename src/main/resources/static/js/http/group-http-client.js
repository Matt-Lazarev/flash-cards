import {createHttpRequest} from "./http-client.js";

const GROUP_API_URL = "/groups"

export class GroupHttpClient {
    constructor() {}

    getAllGroups(){
        return createHttpRequest("GET", GROUP_API_URL)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    getGroupById(groupId){
        return createHttpRequest("GET", `${GROUP_API_URL}/${groupId}`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    saveGroup(body){
        return createHttpRequest("POST", GROUP_API_URL, body)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    updateGroup(groupId, body){
        return createHttpRequest("PUT", `${GROUP_API_URL}/${groupId}`, body)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    deleteGroup(groupId){
        return createHttpRequest("DELETE", `${GROUP_API_URL}/${groupId}`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    downloadGroup(deckId) {
        return createHttpRequest("GET", `${GROUP_API_URL}/${groupId}/download`)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
}