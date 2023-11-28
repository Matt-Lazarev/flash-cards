import {urlRoute} from "./url-routes.js";

const STAND_CONFIG = {
    dev: {
        baseUrl: 'http://localhost:8000',
        baseApiUrl: 'http://localhost:8000/api/v1',
    },
    prod: {
        baseUrl: 'https://api.example.com',
        baseApiUrl: 'https://api.example.com/api',
    },
};

// const isDev = (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1');

const isDev = true;

export const BASE_URL = isDev
    ? STAND_CONFIG["dev"].baseUrl
    : STAND_CONFIG["prod"].baseUrl;

export const BASE_API_URL = isDev
    ? STAND_CONFIG["dev"].baseApiUrl
    : STAND_CONFIG["prod"].baseApiUrl;


export function createMultipartHttpRequest(method, url, data) {
    const apiUrl = BASE_API_URL + url;
    const token = window.localStorage.getItem('token');

    const headers = {
        'Authorization': `Bearer ${token}`,
    };

    const options = {
        method,
        headers,
        body: data
    };

    return fetch(apiUrl, options)
        .then(response => handleResponse(response))
        .then(response => {
            console.log(response);
            return response;
        })
        .catch(error => {
            throw new Error(`Network Error: ${error.message}`);
        });
}

export function createHttpRequest(method, url, data) {
    const apiUrl = BASE_API_URL + url;
    const token = window.localStorage.getItem('token');
    const headers = {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'

    };

    const options = {
        method,
        headers,
        body: data ? JSON.stringify(data) : null
    };

    return fetch(apiUrl, options)
        .then(response => handleResponse(response))
        .then(response => {
            console.log(response);
            return response;
        })
        .catch(error => {
            throw new Error(`Network Error: ${error.message}`);
        });
}

function handleResponse(response) {
    if(response.headers.get('Content-Disposition')){
        console.log(response.headers.get('Content-Disposition'))
        return response;
    }
    if (response.ok) {
        return response.json()
            .then(data => {
                data.valid = true;
                return data;
            });
    } else if (response.status === 400) {
        console.log('Validation error');
        return response.json();
    } else if (response.status === 401) {
        console.log('Unauthorized request');
        urlRoute('/auth/login')
    } else if (response.status === 403) {
        urlRoute('/forbidden')
        console.log('Forbidden request.');
    } else if (response.status === 404) {
        console.log('An error occurred with status code:', response.status);
        urlRoute('/not-found')
    } else {
        console.log('An error occurred with status code:', response.status);
    }
    return response.json();
}