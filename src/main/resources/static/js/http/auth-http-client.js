import {createHttpRequest} from "./http-client.js";

const AUTH_API_URL = "/auth"

export class AuthHttpClient{
    constructor() {}

    register(body){
        return createHttpRequest("POST", `${AUTH_API_URL}/register`, body)
            .then(data => {
                console.log('Response:', data);
                return data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    login(body){
        return createHttpRequest("POST", `${AUTH_API_URL}/login`, body)
            .then(data => {
                console.log('Response:', data);
                return data;
            });
    }

    checkAccess(){
        return createHttpRequest("POST", `${AUTH_API_URL}/check-access`)
            .then(data => {
                console.log('Response:', data);
                return data;
            });
    }
}