document.addEventListener('DOMContentLoaded', function () {
    const submitTaskLink = document.getElementById('submitTaskLink');
    const taskFormContainer = document.getElementById('taskSubmissionForm');

    const historyContentContainer = document.getElementById('historyContentContainer');
    const absenceFormContainer = document.getElementById('absenceFormContainer');

    // Making sure CSS is loaded, dynamically worst case
    function ensureStylesLoaded() {
        const cssLoaded = Array.from(document.styleSheets).some(sheet => 
            sheet.href && sheet.href.includes('task_form.css'));
        
        if (!cssLoaded) {
            const link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = 'assets/css/task_form.css';
            document.head.appendChild(link);
        }
    }

    function closeOtherForms() {
        
        if (absenceFormContainer && absenceFormContainer.style.display === 'flex') {
            absenceFormContainer.style.display = 'none';
            absenceFormContainer.innerHTML = '';
        }

        if (historyContentContainer && historyContentContainer.innerHTML !== '') {
            historyContentContainer.innerHTML = '';
        }
        
        // Add any other forms here too
    }
    async function loadTaskForm() {
        closeOtherForms()
        ensureStylesLoaded();

        try {
            // Getting the template for TASk in html fromat
            const response = await fetch('task_form.html');
            const html = await response.text();
            const tempContainer = document.createElement('div');
            tempContainer.innerHTML = html;

            const template = tempContainer.querySelector('template');
            const formContent = template.content.cloneNode(true);

            taskFormContainer.innerHTML = '';
            taskFormContainer.appendChild(formContent);
            taskFormContainer.style.display = 'flex';

            setupFormEventListeners();
        } catch (error) {
            console.error('Error loading task form:', error);
            // 
            loadWithXHR();
        }
    }

    function loadWithXHR() {
        closeOtherForms()
        const xhr = new XMLHttpRequest();
        xhr.open('GET', 'task_form.html', true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200 || xhr.status === 0) { // 0 for file:// protocol
                    const tempContainer = document.createElement('div');
                    tempContainer.innerHTML = xhr.responseText;

                    const template = tempContainer.querySelector('template');
                    if (template) {
                        const formContent = template.content.cloneNode(true);
                        taskFormContainer.innerHTML = '';
                        taskFormContainer.appendChild(formContent);
                        taskFormContainer.style.display = 'flex';
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
        const cancelButton = document.getElementById('cancelTask');
        if (cancelButton) {
            cancelButton.addEventListener('click', function () {
                taskFormContainer.style.display = 'none';
                taskFormContainer.innerHTML = '';
            });
        }

        const dateInput = document.getElementById('taskDate');
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

        const taskForm = document.getElementById('taskRequestForm');
        if (taskForm) {
            taskForm.addEventListener('submit', function (e) {
                e.preventDefault();
                const formData = new FormData(taskForm);
                const formValues = Object.fromEntries(formData.entries());
                console.log('Form Values:', formValues);

                // All dealing with backennd and sending data to server done here ->
             
                taskFormContainer.style.display = 'none';
                taskFormContainer.innerHTML = '';
            });
        }
    }

    submitTaskLink.addEventListener('click', function (e) {
        e.preventDefault();
        loadTaskForm();
    });
});