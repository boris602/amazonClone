import addItemContCss from './style.css?inline';
import './dragComp/index.js';


class ItemAddContainer extends HTMLElement {
  constructor() {
    super();
    this.attachShadow({ mode: 'open'});
    this.render();
  }

  
  connectedCallback() {
    this.form = this.shadowRoot.querySelector('.form-container');
    this.form.addEventListener('submit', this.onSubmit.bind(this));
  }

  show(el, text, kind) {
    el.textContent = text;
    el.className = kind;
    el.style.display = text ? 'block' : 'none';
  }

  async onSubmit(event) {
     event.preventDefault();

    const msg  = this.shadowRoot.querySelector('#msg');
    const btn  = this.shadowRoot.querySelector('button[type="submit"]');
    btn.disabled = true;


   // Get every object from the form inputs besides the image
    const itemName = this.shadowRoot.querySelector('#item-name').value;
    const itemAmount = this.shadowRoot.querySelector('#item-amount').value;
    const itemPrice = this.shadowRoot.querySelector('#item-price').value;
    const description = this.shadowRoot.querySelector('#description').value;
    const userId = localStorage.getItem('userId');
    const userName = localStorage.getItem('userName');

    // get image-Link from the drop-container
    const dropContainer = this.shadowRoot.querySelector('my-drop-container');
    const fileImage = dropContainer.getFileImage();

    console.log(fileImage==null);
    // Create product object
    const product = {
      prodName:itemName,
      amount: parseInt(itemAmount),
      prodPrice: parseFloat(itemPrice),
      description,
    };

    // create userObject to simplyfy the backend, s.t the user has not be found by the userId
    const user  = {
        userName,
        id: parseInt(userId),
    }
    

    // Create FormData and append parts
    const formData = new FormData();
    formData.append('product', new Blob([JSON.stringify(product)], { type: 'application/json' }));
    formData.append('user',  new Blob([JSON.stringify(user)], { type: 'application/json' })),
    formData.append('image', fileImage);

    try {
      const res= await fetch('/api/products', {
        method: 'POST',
        body: formData
    });
          if (res.status === 201) {
          this.show(msg, 'Item created! Redirecting…', 'success');
          setTimeout(() => { window.location.href = '/homepage/'; }, 800);
          return;
        }
         let errText = `Unexpected error (${res.status})`;
        try {
          const data = await res.json();
          if (data && typeof data.error === 'string') {
            errText = data.error;
          }
        } catch { /* keep default errText */ }

        this.show(msg, errText, 'error');

      } catch {
        // Only when fetch itself fails (no HTTP response at all)
        this.show(msg, 'Network error. Please try again.', 'error');
      } finally {
        btn.disabled = false;
      }
  }
  

  render() {
    this.shadowRoot.innerHTML = `
      <style>${addItemContCss}</style>
         <div class = "add-item-container">
            <span class ="add-item-text">
              Add Item
            </span>
              <form class="form-container">
                 <label for="item-name">item-name:</label>
                 <input type="text" id="item-name" name="item-name" placeholder="item-name" required>
                 <label for="item-amount">item-amount</label>
                 <input type="number" id="item-amount" min="1" step="1" name="item-amount" placeholder="1" required>
                 <label for="itaaem-price">item-price</label>
                 <input type="number" id="item-price" name="item-price"  placeholder="0" min="0" step="0.01" required>
                 <my-drop-container> </my-drop-container>
                 <label for="description">Description:</label>
                 <textarea class="input-description" id="description" name="description"></textarea>
                <button type="submit">Add item</button>
                <p id="msg"></p>
            </form>
    </div>`
  }
}

customElements.define('my-item-container', ItemAddContainer);