// src/pages/CreateAccountPage/index.js (example path)
import createAccCss from './style.css?inline';   // CSS as a string for Shadow DOM
import '../../components/header/index.js';       // your header web component

class CreateAccComp extends HTMLElement {
  constructor() {
    super();
    this.attachShadow({ mode: 'open' });
  }

  connectedCallback() {
    this.render();

    // Grab elements from the component’s shadow tree
    const form = this.shadowRoot.querySelector('#createForm');
    const msg  = this.shadowRoot.querySelector('#msg');
    const btn  = this.shadowRoot.querySelector('button[type="submit"]');

    // Attach submit handler (don’t use onsubmit="" in HTML with Shadow DOM)
    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      msg.style.display = 'none';

      const username = this.shadowRoot.querySelector('#username').value.trim();
      const password = this.shadowRoot.querySelector('#password').value;

      // Quick client-side guard for UX (backend still validates)
      if (!username || !password) {
        this.show(msg, 'Please fill all fields.', 'error');
        return;
      }

      // Prevent double submits while the request is in-flight
      btn.disabled = true;

      try {
        // POST to Spring Boot — in dev, Vite proxy forwards /api to :8080
        const res = await fetch('/api/users', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ userName: username, password }),
        });
        
        console.log(res.status)
        if (res.status === 201) {
          // success: created
          this.show(msg, 'Account created! Redirecting…', 'success');
          localStorage.setItem('userName', username);   // keep only non-sensitive info
          setTimeout(() => { window.location.href = '/homepage/'; }, 800);
          return;
        }
        else if (res.status === 409) {
          // username already exists (backend decided this)
          this.show(msg, 'Username already taken. Try another.', 'error');
          return;
        }
        else if (res.status === 400) {
          // backend validation failed (missing/invalid fields)
          this.show(msg, 'Invalid data. Please check your input.', 'error');
          return;
        }

        // Any other server response
        this.show(msg, `Unexpected error (${res.status}).`, 'error');
      } catch (_) {
        // Network or proxy failed
        this.show(msg, 'Network error. Please try again.', 'error');
      } finally {
        btn.disabled = false;
      }
    });
  }

  show(el, text, kind) {
    el.textContent = text;     // put the message text
    el.className = kind;       // e.g. style .success and .error in your CSS
    el.style.display = 'block';
  }

  render() {
    this.shadowRoot.innerHTML = `
      <style>${createAccCss}</style>
      <div class="center-container">
        <div class="amazon-image-container">
          <a href="/homepage/">
            <img class="amazon-image" src="/images/amazon-logo.png" alt="Amazon" />
          </a>
        </div>

        <div class="login-container">
          <span class="login-text">Create a new account</span>

          <form id="createForm">
            <label for="username">username:</label>
            <input type="text" id="username" name="username" required />

            <label for="password">password:</label>
            <input type="password" id="password" name="password" required />

            <button type="submit">Create Account</button>
          </form>
          <p id="msg" class="info" style="display:none;"></p>
        </div>
      </div>
    `;
  }
}

customElements.define('my-create-acc', CreateAccComp);
