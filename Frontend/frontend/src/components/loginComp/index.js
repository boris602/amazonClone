import loginCss from './style.css?inline';


class MyLogin extends HTMLElement {
  constructor() {
    super();
    this.attachShadow({ mode: 'open' });
    this.render();
  }

  render() {
    this.shadowRoot.innerHTML = `
      <style>${loginCss}</style>
      <div class ="center-container">
         <div class="amazon-image-container">
        <a href="/homepage/">
            <img class= "amazon-image"src="/images/amazon-logo.png">
         </a>
         </div>
         <div class="login-container">
            <span class="login-text"> Login or create an account</span>
            <form id="loginForm" onsubmit="handleSubmit(event)">
               <label for="username">username:</label>
               <input type="text" id="username" name="username" required>
               <label for="password">password:</label>
               <input type="password" id="password" name="password" required>     
               <button type="submit">Login</button>
            </form>
        <p id="error" class="error">Invalid username or password</p>
        <div class = new-account-box>
            <a href="/createAccPage/"> Create a new account</a>
        </div>
        </div>
    </div>`
  }
}

customElements.define('my-login', MyLogin);
