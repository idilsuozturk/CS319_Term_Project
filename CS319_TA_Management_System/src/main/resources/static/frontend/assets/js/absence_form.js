document.addEventListener('DOMContentLoaded', function () {
    const requestAbsenceLink = document.getElementById('requestAbsenceLink');
    const absenceFormContainer = document.getElementById('absenceFormContainer');

    // Making sure CSS is loaded, dynamically worst case
    function ensureStylesLoaded() {
        const cssLoaded = Array.from(document.styleSheets).some(sheet => 
            sheet.href && sheet.href.includes('absence_form.css'));
        
        if (!cssLoaded) {
            const link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = 'assets/css/absence_form.css';
            document.head.appendChild(link);
        }
    }

    async function loadAbsenceForm() {

        ensureStylesLoaded();
        
        try {
            // Getting the template for absence in html fromat
            const response = await fetch('absence_form.html');
            const html = await response.text();
            const tempContainer = document.createElement('div');
            tempContainer.innerHTML = html;

            const template = tempContainer.querySelector('template');
            const formContent = template.content.cloneNode(true);

            absenceFormContainer.innerHTML = '';
            absenceFormContainer.appendChild(formContent);
            absenceFormContainer.style.display = 'flex';

            setupFormEventListeners();
        } catch (error) {
            console.error('Error loading absence form:', error);
            loadWithXHR();
        }
    }

    function loadWithXHR() {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', 'absence_form.html', true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200 || xhr.status === 0) { 
                    const tempContainer = document.createElement('div');
                    tempContainer.innerHTML = xhr.responseText;

                    const template = tempContainer.querySelector('template');
                    if (template) {
                        const formContent = template.content.cloneNode(true);
                        absenceFormContainer.innerHTML = '';
                        absenceFormContainer.appendChild(formContent);
                        absenceFormContainer.style.display = 'flex';
                        setupFormEventListeners();
                    } else {
                        console.error('Template not found in response');
                    }
                } else {
                    console.error('Error loading form with XHR:', xhr.status);
                }
            }
        };
        xhr.send();
    }

    function setupFormEventListeners() {
        // Cancel button event listener
        const cancelButton = document.getElementById('cancelAbsence');
        if (cancelButton) {
            cancelButton.addEventListener('click', function () {
                absenceFormContainer.style.display = 'none';
                absenceFormContainer.innerHTML = '';
            });
        }

        // Date picker funct
        const dateInput = document.getElementById('absenceStartDate');
        if (dateInput) {
            dateInput.addEventListener('click', function (e) {
                this.showPicker();
            });

            const dateField = document.querySelector('.date-field');
            if (dateField) {
                dateField.addEventListener('click', function (e) {
                    if (e.target !== dateInput) {
                        dateInput.focus();
                        dateInput.showPicker();
                    }
                });
            }
        }

        // File upload handles
        const fileInput = document.getElementById('absenceProof');
        const fileName = document.getElementById('fileName');
        const fileUploadButton = document.querySelector('.file-upload-button');
        
        if (fileInput && fileName) {
            fileInput.addEventListener('change', function () {
                if (this.files && this.files[0]) {
                    fileName.textContent = this.files[0].name;
                    
                    // check 5mb limit
                    if (this.files[0].size > 5 * 1024 * 1024) {
                        alert('File size exceeds 5MB limit. Please choose a smaller file.');
                        this.value = '';
                        fileName.textContent = 'No file chosen';
                    }
                } else {
                    fileName.textContent = 'No file chosen';
                }
            });
            

            if (fileUploadButton) {
                fileUploadButton.addEventListener('click', function () {
                    fileInput.click();
                });
            }
        }

        // Form submission
        const absenceForm = document.getElementById('absenceRequestForm');
        if (absenceForm) {
            absenceForm.addEventListener('submit', function (e) {
                e.preventDefault();
                const formData = new FormData(absenceForm);
                
                // for testing
                console.log('Form submitted with the following values:');
                for (let [key, value] of formData.entries()) {
                    if (key !== 'absenceProof') {
                        console.log(`${key}: ${value}`);
                    } else {
                        console.log(`${key}: ${value.name} (${value.type}, ${value.size} bytes)`);
                    }
                }
                
                // All dealing with backennd and sending data to server done here ->
              
                alert('Absence request submitted successfully!');
                absenceFormContainer.style.display = 'none';
                absenceFormContainer.innerHTML = '';
            });
        }
    }


    if (requestAbsenceLink) {
        requestAbsenceLink.addEventListener('click', function (e) {
            e.preventDefault();
            loadAbsenceForm();
        });
    }
});