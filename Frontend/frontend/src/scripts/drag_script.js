

class DropZone extends HTMLElement
{
    constructor() {
        super();
        this.imageFile = null;;
        this.render();
        this.dropZone = null;
        this.imageZone = null;
    }
    addImageFile(){
        const fileInput = document.getElementById('fileInput');        
    }

    createDropZone(){
        
    }
}


const dropZone = document.getElementById('dropZone');
const fileInput = document.getElementById('fileInput');
const imageContainer = document.getElementById('imageContainer');

// Handle drag events
dropZone.addEventListener('dragover', (e) => {
    e.preventDefault();
    dropZone.classList.add('dragover');
});

dropZone.addEventListener('dragleave', () => {
    dropZone.classList.remove('dragover');
});

dropZone.addEventListener('drop', (e) => {
    e.preventDefault();
    dropZone.classList.remove('dragover');
    const files = e.dataTransfer.files;
    if (files.length > 0) {
        handleFiles(files);
    }
});

// Allow clicking the drop zone to select a file
dropZone.addEventListener('click', () => {
    fileInput.click();
});

fileInput.addEventListener('change', () => {
    handleFiles(fileInput.files);
});

// Process the uploaded file
function handleFiles(files) {
    const file = files[0];
    if (file && file.type.startsWith('image/')) {
        const reader = new FileReader();
        reader.onload = (e) => {
            // Clear previous image
            imageContainer.innerHTML = '';
            // Create and append new image
            const img = document.createElement('img');
            img.src = e.target.result;
            imageContainer.appendChild(img);
        };
        reader.readAsDataURL(file);
    } else {
        alert('Please upload a valid image file.');
    }
}