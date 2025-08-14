import userLinkStyle from './style.css?inline';


class AuthLink extends HTMLElement
{
    constructor(){
        super()
        this.attachShadow({ mode: 'open' });
        this.userName = localStorage.getItem('userName') || 'Login';
        this.loginLink = this.userName === 'Login' ? '/loginPage/' : '/addItemPage/';

        this.render();
    }
    connectedCallback() {
        // clear user and allow normal navigation to /loginPage/
        this.shadowRoot.addEventListener('click', (e) => {
        if (e.target.closest('#logout')) {
            localStorage.removeItem('userName');
        }
    });
  }

   render() {
    this.shadowRoot.innerHTML = `
      <style>${userLinkStyle}</style>
      <div class="link-container">
        <a class="header-link" href="${this.loginLink}">
          <span class="text-style">${this.userName}</span>
        </a>

        ${this.userName !== 'Login' ? `
          <a id="logout" class="header-link link-style" href="/loginPage/">
            <span class="text-style">Logout</span>
          </a>
        ` : ``}
      </div>
    `;
  }
}
customElements.define('auth-link', AuthLink);
