import {AuthHttpClient} from "./http/auth-http-client.js";
import {urlRoute} from "./http/url-routes.js";

const authHttpClient = new AuthHttpClient();

const registerForm = document.getElementById('registerForm');
registerForm?.addEventListener("submit", (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);

    const body = {
        'username': formData.get('username'),
        'email': formData.get('email'),
        'password': formData.get('password')
    }

    authHttpClient.register(body)
        .then((res) => {
            if (res.valid) {
                urlRoute('/auth/login');
            } else {
                showValidationErrors(res);
            }
        });
});

const loginForm = document.getElementById('loginForm');
loginForm?.addEventListener("submit", (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);

    const body = {
        'username': formData.get('username'),
        'password': formData.get('password')
    }

    authHttpClient.login(body)
        .then((res) => {
            if (res.valid) {
                window.localStorage.setItem('token', res.token);
                window.localStorage.setItem('username', res.username);
                window.localStorage.setItem('auth', 'true');
                urlRoute('/groups');
            } else {
                showValidationErrors(res);
            }
        })
        .catch(err => {
            console.log('Error: ', err);
            logout();
        })
});

export function logout(){
    window.localStorage.setItem('token', '');
    window.localStorage.setItem('username', '');
    window.localStorage.setItem('auth', 'false');
}

function showValidationErrors(res) {
    const usernameError = document.getElementById('usernameError');
    const passwordError = document.getElementById('passwordError');
    const emailError = document.getElementById('emailError');

    usernameError.textContent = res.username
        ? res.username
        : '';

    passwordError.textContent = res.password
        ? res.password
        : '';

    emailError.textContent = emailError && res.email
        ? res.email
        : '';
}