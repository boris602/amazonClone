import dropContainerCss from './style.css?inline';

class DropContainer extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ mode: 'open' });
        this.render(); // Render first to populate Shadow DOM
        this.fileImage = null;
    }

    render() {
        this.shadowRoot.innerHTML = `
            <style>${dropContainerCss}</style>
            <div class="file-container">
                <label for="fileInput">Image File</label>
                <input type="file" id="fileInput" accept="image/*">
                <div class="drop-container" id="dropContainer">
                    <div class="upload-text">Drag & Drop or Click to Upload</div>
                </div>
            </div>
        `;
    }

    connectedCallback() {
        // Get elements from Shadow DOM
        this.dropContainer = this.shadowRoot.getElementById('dropContainer');
        this.fileInput = this.shadowRoot.getElementById('fileInput');

        // Add event listener for the drop-container
        this.dropContainer.addEventListener('dragover', (e) => {
            e.preventDefault();
            this.dropContainer.classList.add('dragover');
        });

        this.dropContainer.addEventListener('dragleave', () => {
            this.dropContainer.classList.remove('dragover');
        });

        this.dropContainer.addEventListener('drop', (e) => {
            e.preventDefault();
            this.dropContainer.classList.remove('dragover');
            const files = e.dataTransfer.files;
            if (files.length > 0) {
                this.handleFile(files[0]); // Process only the first file
            }
        });

        // Allow clicking the container to trigger file input
        this.dropContainer.addEventListener('click', () => {
            this.fileInput.click();
        });

        // Handle file selection via input
        this.fileInput.addEventListener('change', () => {
            if (this.fileInput.files.length > 0) {
                this.handleFile(this.fileInput.files[0]);
            }
        });
    }

    handleFile(file) {
        if (!file.type.startsWith('image/')) {
            alert('Please upload an image file');
            return;
        }
        this.fileImage = file;
        const url = URL.createObjectURL(file); 
        const img = new Image();
        img.src = url;
        img.addEventListener('load', () => {
            // Clear existing content(image and text)
            this.dropContainer.innerHTML = ''; 
            img.classList.add('draggable-img');
            img.draggable = true;
            this.dropContainer.appendChild(img);

            // Add dragend event to remove the image
            img.addEventListener('dragend', () => {
                img.remove();
                // Restore upload text
                const newUploadText = document.createElement('div');
                newUploadText.classList.add('upload-text');
                newUploadText.textContent = 'Drag & Drop or Click to Upload';
                this.dropContainer.appendChild(newUploadText);
            });
        });
    }

    getFileImage(){
        return this.fileImage;
    }
}

customElements.define('my-drop-container', DropContainer);