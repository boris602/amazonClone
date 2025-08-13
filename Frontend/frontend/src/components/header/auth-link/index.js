import userLinkStyle from './style.css?inline';


class AuthLink extends HTMLElement
{
    constructor(){
        super()
        this.attachShadow({ mode: 'open' });
        this.userName = localStorage.getItem('userName') || 'Login';
        this.loginLink ='Login' ? '/loginPage/' : '/addItemPage/';
        this.render();
    }

    render(){
        this.shadowRoot.innerHTML = `
            <div class = link-container>
               <style>${userLinkStyle}</style>
               <a class="header-link" href="${this.loginLink}">
                   <span class="text-style">${this.userName}</span>
               </a>
            `
        // add additional link in case if a user has logged into his account.
        if (this.userName){
            this.shadowRoot.innerHTML += 
            `
            <a class="header-link" href="/loginPage/">
                <span class="text-style">Logout</span>
            </a>
            `
        }

        // close the first div 
        this.shadowRoot.innerHTML += `</div>`
    }
}
customElements.define('auth-link', AuthLink);
