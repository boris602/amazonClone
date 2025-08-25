// src/pages/CreateAccountPage/index.js
import createAccCss from './style.css?inline';
import '../../components/header/index.js';

class CreateAccComp extends HTMLElement {
  constructor() {
    super();
    this.attachShadow({ mode: 'open' });
  }

  connectedCallback() {
    this.render();

    const form = this.shadowRoot.querySelector('#createForm');
    const msg  = this.shadowRoot.querySelector('#msg');
    const btn  = this.shadowRoot.querySelector('button[type="submit"]');

    form.addEventListener('submit', async (e) => {
      e.preventDefault();            // no page reload
      this.show(msg, '', '');        // clear message
      btn.disabled = true;

      const username = this.shadowRoot.querySelector('#username').value;
      const password = this.shadowRoot.querySelector('#password').value;

      try {
        const res = await fetch('/api/users', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ userName: username, password })
        });

        if (res.status === 201) {
          const data = await res.json();
          this.show(msg, 'Account created! Redirecting…', 'success');
          localStorage.setItem('userName', username);
          localStorage.setItem('userId', data.id);

          setTimeout(() => { window.location.href = '/homepage/'; }, 800);
          return;
        }

        // Parse JSON error once; otherwise show generic message
        let errText = `Unexpected error (${res.status})`;
        try {
          const data = await res.json();
          if (data && typeof data.error === 'string') {
            errText = data.error; // e.g., "Username already exists"
          }
        } catch { /* keep default errText */ }

        this.show(msg, errText, 'error');

      } catch {
        // Only when fetch itself fails (no HTTP response at all)
        this.show(msg, 'Network error. Please try again.', 'error');
      } finally {
        btn.disabled = false;
      }
    });
  }

  show(el, text, kind) {
    el.textContent = text;
    el.className = kind;
    el.style.display = text ? 'block' : 'none';
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

          <p id="msg"></p>
        </div>
      </div>
    `;
  }
}
customElements.define('my-create-acc', CreateAccComp);
