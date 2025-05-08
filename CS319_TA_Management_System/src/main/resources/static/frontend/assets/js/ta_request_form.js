document.addEventListener('DOMContentLoaded', function () {
    const requestTALink = document.getElementById('requestTALink');
    const taRequestFormContainer = document.getElementById('taRequestFormContainer');

    const InstructorList = document.getElementById('InstructorList');

    // Check if CSS is already loaded, if not, load it dynamically
    function ensureStylesLoaded() {
        const cssLoaded = Array.from(document.styleSheets).some(sheet => 
            sheet.href && sheet.href.includes('ta_request_form.css'));
        
        if (!cssLoaded) {
            const link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = 'assets/css/ta_request_form.css';
            document.head.appendChild(link);
        }
    }

    // Close any other open forms or content
    function closeOtherForms() {
        
        // Clear history content if it's visible
        if (InstructorList && InstructorList.innerHTML !== '') {
            InstructorList.innerHTML = '';
        }
    }

    async function loadTARequestForm() {
        // Close any other forms first
        closeOtherForms();
        
        // Ensure CSS is loaded
        ensureStylesLoaded();

        try {
            // Getting the template for TA request form html
            const response = await fetch('ta_request_form.html');
            const html = await response.text();
            const tempContainer = document.createElement('div');
            tempContainer.innerHTML = html;

            const template = tempContainer.querySelector('template');
            const formContent = template.content.cloneNode(true);

            taRequestFormContainer.innerHTML = '';
            taRequestFormContainer.appendChild(formContent);
            taRequestFormContainer.style.display = 'flex';

            setupFormEventListeners();
        } catch (error) {
            console.error('Error loading TA request form:', error);
            // Fallback for file:// protocol or if fetch fails
            loadWithXHR();
        }
    }

    function loadWithXHR() {
        // Close other forms first
        closeOtherForms();
        
        const xhr = new XMLHttpRequest();
        xhr.open('GET', 'ta_request_form.html', true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200 || xhr.status === 0) { // 0 for file:// protocol
                    const tempContainer = document.createElement('div');
                    tempContainer.innerHTML = xhr.responseText;

                    const template = tempContainer.querySelector('template');
                    if (template) {
                        const formContent = template.content.cloneNode(true);
                        taRequestFormContainer.innerHTML = '';
                        taRequestFormContainer.appendChild(formContent);
                        taRequestFormContainer.style.display = 'flex';
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
        const cancelButton = document.getElementById('cancelRequest');
        if (cancelButton) {
            cancelButton.addEventListener('click', function () {
                taRequestFormContainer.style.display = 'none';
                taRequestFormContainer.innerHTML = '';
            });
        }

        // Date picker functionality
        const dateInput = document.getElementById('startDate');
        if (dateInput) {
            dateInput.addEventListener('click', function () {
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

        // Set up hour picker interactions
        setupHourPicker();

        // Form submission
        const taRequestForm = document.getElementById('taRequestForm');
        if (taRequestForm) {
            taRequestForm.addEventListener('submit', function (e) {
                e.preventDefault();
                const formData = new FormData(taRequestForm);
                
                // Log form data (for demonstration purposes)
                console.log('Form submitted with the following values:');
                for (let [key, value] of formData.entries()) {
                    console.log(`${key}: ${value}`);
                }
                
                // Show success message
                const hours = formData.get('weeklyHours');
                const course = formData.get('courseSelect');
                const taName = formData.get('taSelect') || 'an available TA';
                
                alert(`Success! Requested ${hours} hours from ${taName} for ${course}.`);
                
                // Here you would normally send the data to a server
                // For demonstration, we'll just hide the form
                taRequestFormContainer.style.display = 'none';
                taRequestFormContainer.innerHTML = '';
            });
        }
    }

    // Set up the improved hour picker
    function setupHourPicker() {
        const hoursSlider = document.getElementById('hoursSlider');
        const weeklyHoursInput = document.getElementById('weeklyHours');
        const increaseBtn = document.querySelector('.increase-btn');
        const decreaseBtn = document.querySelector('.decrease-btn');
        
        if (hoursSlider && weeklyHoursInput) {
            // Sync slider with number input
            hoursSlider.addEventListener('input', function() {
                weeklyHoursInput.value = this.value;
            });
            
            weeklyHoursInput.addEventListener('input', function() {
                // Make sure value is within range
                let value = parseFloat(this.value);
                
                if (value < 1) {
                    this.value = 1;
                    value = 1;
                } else if (value > 20) {
                    this.value = 20;
                    value = 20;
                }
                
                // Update slider position
                hoursSlider.value = value;
            });
            
            // Add increment/decrement functionality
            if (increaseBtn) {
                increaseBtn.addEventListener('click', function() {
                    let value = parseFloat(weeklyHoursInput.value) + 0.5;
                    if (value > 20) value = 20;
                    
                    weeklyHoursInput.value = value;
                    hoursSlider.value = value;
                    
                    // Trigger input event for any listeners
                    const event = new Event('input', { bubbles: true });
                    weeklyHoursInput.dispatchEvent(event);
                });
            }
            
            if (decreaseBtn) {
                decreaseBtn.addEventListener('click', function() {
                    let value = parseFloat(weeklyHoursInput.value) - 0.5;
                    if (value < 1) value = 1;
                    
                    weeklyHoursInput.value = value;
                    hoursSlider.value = value;
                    
                    // Trigger input event for any listeners
                    const event = new Event('input', { bubbles: true });
                    weeklyHoursInput.dispatchEvent(event);
                });
            }
        }
    }

    // Attach click event to the request link
    if (requestTALink) {
        requestTALink.addEventListener('click', function (e) {
            e.preventDefault();
            loadTARequestForm();
            
            // Update active state in menu
            document.querySelectorAll('.submenu a').forEach(link => link.classList.remove('active'));
            this.classList.add('active');
        });
    }
});