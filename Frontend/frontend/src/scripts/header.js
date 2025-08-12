


class MyHeader extends HTMLElement {
    constructor() {
        super();
        this.userName = localStorage.getItem('userName') || "Login"
        this.cartQuantity = parseInt(localStorage.getItem('cartQuantity')) || 0;
        this.loginLink = "login.html"
        this.createLoginlink()
        this.render();
    }

    createLoginlink(){
        if (this.userName !== "Login"){
            this.loginLink = "item-adder.html"
        }
    }

    render() {
        this.innerHTML = `
            <header class="amazon-header">
                <div class="amazon-left-section">
                    <a class="header-link" href="amazon.html">
                        <img class="amazon-Image" src="../images/amazon-logo-white.png" alt="Amazon Logo">
                    </a>
                </div>
                <div class="amazon-middle-section">
                    <input class="search-bar" type="text" placeholder="Search">
                    <button class="search-button header-link">
                        <img class="search-icon" src="../images/icons/search-icon.png" alt="Search Icon">
                    </button>
                </div>
                <div class="amazon-right-section">
                    <a class="header-link login-link" href=${this.loginLink}>
                        <span class="login-text">${this.userName}</span>
                    </a>
                    <a class="orders-link header-link" href="amazon.html">
                        <span class="returns-text">Returns</span>
                        <span class="orders-text">& Orders</span>
                    </a>
                    <a class="cart-link header-link" href="amazon.html">
                        <img class="cart-icon" src="../images/icons/cart-icon.png" alt="Cart Icon">
                        <div class="cart-quantity">${this.cartQuantity}</div>
                        <div class="cart-text">Cart</div>
                    </a>
                </div>
            </header>
        `;
    }

    // ... rest of the methods (setupEventListeners, updateCartQuantity, replaceLoginLink) ...
}
customElements.define('my-header', MyHeader);