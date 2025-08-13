import headerCss from './style.css?inline';
import './auth-link/index.js';


class MyHeader extends HTMLElement {
  constructor() {
    super();
    this.attachShadow({ mode: 'open' });
    this.cartQuantity = parseInt(localStorage.getItem('cartQuantity')) || 0;
    this.render(); 
  }

  render() {
    this.shadowRoot.innerHTML = `
      <style>${headerCss}</style>
      <header class="amazon-header">
        <div class="amazon-left-section">
          <a class="header-link" href="/homepage/">
            <img class="amazon-Image" src="/images/amazon-logo-white.png" alt="Amazon Logo">
          </a>
        </div>
        <div class="amazon-middle-section">
          <input class="search-bar" type="text" placeholder="Search">
          <button class="search-button header-link">
            <img class="search-icon" src="/images/icons/search-icon.png" alt="Search Icon">
          </button>
        </div>
        <div class="amazon-right-section">
             <auth-link></auth-link>
          <a class="orders-link header-link" href="/amazon.html">
            <span class="returns-text">Returns</span>
            <span class="orders-text">& Orders</span>
          </a>
          <a class="cart-link header-link" href="/amazon.html">
            <img class="cart-icon" src="/images/icons/cart-icon.png" alt="Cart Icon">
            <div class="cart-quantity">${this.cartQuantity}</div>
            <div class="cart-text">Cart</div>
          </a>
        </div>
      </header>
    `;
  }
}

customElements.define('my-header', MyHeader);
