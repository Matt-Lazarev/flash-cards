import {logout} from "./auth.js";
import {urlRoute} from "./http/url-routes.js";


class Header extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        const authorizedTag = window.localStorage.getItem('auth') === 'true'
            ? 'user-authorized'
            : 'user-not-authorized';

        this.innerHTML =
        `
        <header class="dark-header">
            <div class="header-content">
                <div class="logo">
                    <img src="/images/website_logo.png" alt="Website Icon">
                    <h1>Flash Cards</h1>
                </div>
                <nav>
                    <ul>
                        <li><a href="/groups">Home</a></li>
                        <li><a href="#">About</a></li>
                        <li><a href="#">Contact</a></li>
                    </ul>
                </nav>
                <${authorizedTag}></${authorizedTag}>
            </div>
        </header>
        `;
    }
}

class Authorized extends HTMLElement{
    constructor() {
        super();
    }

    connectedCallback() {
        const username = window.localStorage.getItem('username');

        this.innerHTML =
        `
        <div class="auth-buttons">
            <span style="font-weight: bold">${username}</span>
            <button>
                <a href="" id="logoutButton">Log Out</a>
            </button>
        </div>
        `;

        document.getElementById('logoutButton')
            .addEventListener('click', (event) => {
                event.preventDefault();
                logout();
                urlRoute('/auth/login');
            })
    }
}

class NotAuthorized extends HTMLElement{
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML =
            `
        <div class="auth-buttons">
            <button>
                <a href="/auth/login">Sign In</a>
            </button>
            <button>
                <a href="/auth/register">Sign Up</a>
            </button>
        </div>
        `;
    }
}

class Footer extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML =
        `
        <footer>
            <p>© ${new Date().getFullYear()} Lazarev</p>
        </footer>
        `;
    }
}

class MeatBallMenu extends HTMLElement{
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML =
        `
        <div class="meatball-menu">
            <span class="meatball-icon">•••</span>
            <div class="menu">
                <ul>
                    <li><a class="edit-button"><ion-icon name="pencil-outline"></ion-icon></a></li>
                    <li><a class="delete-button"><ion-icon name="trash-outline"></ion-icon></a></li>
                    <li><a class="download-button"><ion-icon name="arrow-down-circle-outline"></ion-icon></ion-icon></a></li>
                </ul>
            </div>
        </div>
        `;
    }
}

class MeatBallSmallMenu extends HTMLElement{
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML =
            `
        <div class="meatball-menu">
            <span class="meatball-icon">•••</span>
            <div class="menu">
                <ul>
                    <li><a class="edit-button"><ion-icon name="pencil-outline"></ion-icon></a></li>
                    <li><a class="delete-button"><ion-icon name="trash-outline"></ion-icon></a></li>
                </ul>
            </div>
        </div>
        `;
    }
}

class ElementCard extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML =
        `
            <div class="element-card">
                <meat-ball-menu></meat-ball-menu>
                <h3>Element</h3>
                <p class="element-description">Description for Element</p>
                <p class="element-count">Number of elements</p>
            </div>
        `;
    }
}

class ConfirmModalWindow extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML =
        `
        <div id="myModal" class="modal">
            <div class="modal-content">
                <p>Are you sure you want to delete this element?</p>
                <button id="confirmYes">Yes</button>
                <button id="confirmNo">No</button>
            </div>
        </div>
        `;
    }
}

customElements.define('header-component', Header);
customElements.define('user-authorized', Authorized);
customElements.define('user-not-authorized', NotAuthorized);
customElements.define('footer-component', Footer);
customElements.define('meat-ball-menu', MeatBallMenu);
customElements.define('meat-ball-small-menu', MeatBallSmallMenu);
customElements.define('element-card', ElementCard);
customElements.define('confirm-modal-window', ConfirmModalWindow);