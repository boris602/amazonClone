import addItemContCss from './style.css?inline';


class ItemAddContainer extends HTMLElement {
  constructor() {
    super();
    this.attachShadow({ mode: 'open'});
    this.render();
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
                 <label for="item-price">item-price</label>
                 <input type="number" id="item-price" name="item-price"  placeholder="0" min="0" step="0.01" required>
                 <label for="item-picture-link">item-picture-link:</label>
                 <input type="text" id="item-picture-link"  placeholder="example.com"  name="item-picture-link" required>
                 <label for="description">Description:</label>s
                 <textarea class="input-description" id="description" name="description"></textarea>
                <button type="submit">Login</button>
            </form>
    </div>`
  }
}

customElements.define('my-item-container', ItemAddContainer);