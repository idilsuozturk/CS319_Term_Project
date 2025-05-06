document.addEventListener('DOMContentLoaded', function() {
    // Get references to the menu links and content containers
    const taskHistoryLink = document.getElementById('taskHistoryLink');
    const absenceHistoryLink = document.getElementById('absenceHistoryLink');
    const historyContentContainer = document.getElementById('historyContentContainer');

    const absenceFormContainer = document.getElementById('absenceFormContainer');
    const taskFormContainer = document.getElementById('taskSubmissionForm');
    
    // Function to format date: yyyy-mm-dd to Mon dd, yyyy
    function formatDate(dateString) {
        const options = { year: 'numeric', month: 'short', day: 'numeric' };
        return new Date(dateString).toLocaleDateString(undefined, options);
    }

    function closeOtherForms() {
        
        if (absenceFormContainer && absenceFormContainer.style.display === 'flex') {
            absenceFormContainer.style.display = 'none';
            absenceFormContainer.innerHTML = '';
        }
        
        if (taskFormContainer && taskFormContainer.style.display === 'flex') {
            taskFormContainer.style.display = 'none';
            taskFormContainer.innerHTML = '';
        }
        // Add any other elements here too
    }
    
    // Get status colour
    function getStatusBadge(status) {
        let badgeClass;
        switch(status.toLowerCase()) {
            case 'approved':
                badgeClass = 'status-approved';
                break;
            case 'pending':
                badgeClass = 'status-pending';
                break;
            case 'rejected':
                badgeClass = 'status-rejected';
                break;
            default:
                badgeClass = 'status-pending';
        }
        return `<span class="status-badge ${badgeClass}">${status}</span>`;
    }
    
    // Loading task history
    async function loadTaskHistory() {
        closeOtherForms();
        try {
            // Clear previous content e.g absence
            historyContentContainer.innerHTML = '';
            
            // if server is lagging behind
            historyContentContainer.innerHTML = '<div class="loading">Loading task history...</div>';
            
            // Fetch task data
            const response = await fetch('task_history.json');
            const data = await response.json();
            
            // Clear loading 
            historyContentContainer.innerHTML = '';
            
            
            const headerDiv = document.createElement('div');
            headerDiv.className = 'history-header';
            headerDiv.innerHTML = '<h2>Task History</h2>';
            historyContentContainer.appendChild(headerDiv);
            
            // Check if there are tasks
            if (!data.tasks || data.tasks.length === 0) {
                historyContentContainer.innerHTML += '<div class="no-data">No task history available.</div>';
                return;
            }
            
    
            const table = document.createElement('table');
            table.className = 'history-table';
            
     
            const thead = document.createElement('thead');
            thead.innerHTML = `
                <tr>
                    <th>Form Submission Date</th>
                    <th>Course</th>
                    <th>Description</th>
                    <th>Date</th>
                    <th>Time Spent (hrs)</th>
                    <th>Status</th>
                </tr>
            `;
            table.appendChild(thead);
            
  
            const tbody = document.createElement('tbody');
            
  
            data.tasks.forEach(task => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${task.sub_date}</td>
                    <td>${task.course}</td>
                    <td>${task.description}</td>
                    <td>${formatDate(task.date)}</td>
                    <td>${task.timeSpent}</td>
                    <td>${getStatusBadge(task.status)}</td>
                `;
                tbody.appendChild(row);
            });
            
            table.appendChild(tbody);
            historyContentContainer.appendChild(table);
            
        } catch (error) {
            console.error('Error loading task history:', error);
            historyContentContainer.innerHTML = '<div class="error">Error loading task history. Please try again later.</div>';
        }
    }
    
    // Load and display absence history
    async function loadAbsenceHistory() {
        closeOtherForms();

        try {
            // Clear existing content
            historyContentContainer.innerHTML = '';
            
        
            historyContentContainer.innerHTML = '<div class="loading">Loading absence history...</div>';
            
          
            const response = await fetch('absence_history.json');
            const data = await response.json();
            
            // Clear loading 
            historyContentContainer.innerHTML = '';
            
       
            const headerDiv = document.createElement('div');
            headerDiv.className = 'history-header';
            headerDiv.innerHTML = '<h2>Leave of Absence History</h2>';
            historyContentContainer.appendChild(headerDiv);
            
         
            if (!data.absences || data.absences.length === 0) {
                historyContentContainer.innerHTML += '<div class="no-data">No absence history available.</div>';
                return;
            }
            
           
            const table = document.createElement('table');
            table.className = 'history-table';
            
            // table header
            const thead = document.createElement('thead');
            thead.innerHTML = `
                <tr>
                    <th>Form Submission Date</th>
                    <th>Reason</th>
                    <th>Start Date</th>
                    <th>Duration (days)</th>
                    <th>Document</th>
                    <th>Status</th>
                </tr>
            `;
            table.appendChild(thead);
            
            // Create table body
            const tbody = document.createElement('tbody');
            
            // Add rows for each absence
            data.absences.forEach(absence => {
                // Capitalize the first letter of absence type
                
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${absence.id}</td>
                    <td>${absence.reason}</td>
                    <td>${formatDate(absence.startDate)}</td>
                    <td>${absence.duration}</td>
                    <td><a href="#" class="document-link" title="View document">${absence.proofDocument}</a></td>
                    <td>${getStatusBadge(absence.status)}</td>
                `;
                tbody.appendChild(row);
            });
            
            table.appendChild(tbody);
            historyContentContainer.appendChild(table);
            
           
            
        } catch (error) {
            console.error('Error loading absence history:', error);
            historyContentContainer.innerHTML = '<div class="error">Error loading absence history. Please try again later.</div>';
        }
    }
    
   
    if (taskHistoryLink) {
        taskHistoryLink.addEventListener('click', function(e) {
            e.preventDefault();
            loadTaskHistory();
            
       
            document.querySelectorAll('.submenu a').forEach(link => link.classList.remove('active'));
            this.classList.add('active');
        });
    }
    
    if (absenceHistoryLink) {
        absenceHistoryLink.addEventListener('click', function(e) {
            e.preventDefault();
            loadAbsenceHistory();
            
         
            document.querySelectorAll('.submenu a').forEach(link => link.classList.remove('active'));
            this.classList.add('active');
        });
    }
    
    // Fallback for XHR if fetch is not supported or fails
    function loadWithXHR(url, callback) {
        closeOtherForms();

        const xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.responseType = 'json';
        
        xhr.onload = function() {
            if (xhr.status === 200 || xhr.status === 0) { 
                callback(xhr.response);
            } else {
                console.error('XHR Error:', xhr.statusText);
                historyContentContainer.innerHTML = '<div class="error">Error loading data. Please try again later.</div>';
            }
        };
        
        xhr.onerror = function() {
            console.error('XHR Request failed');
            historyContentContainer.innerHTML = '<div class="error">Error loading data. Please try again later.</div>';
        };
        
        xhr.send();
    }
});