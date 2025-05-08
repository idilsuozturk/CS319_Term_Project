document.addEventListener('DOMContentLoaded', function() {
    // Get references to the menu links and content containers
    const taskHistoryLink = document.getElementById('taskHistoryLink');
    const absenceHistoryLink = document.getElementById('absenceHistoryLink');
    const proctoringAssignmentsLink = document.getElementById('proctoringAssignmentsLink');
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
            resetAnimation(historyContentContainer);
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
            
            resetAnimation(historyContentContainer);
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

    // Replace only this specific function in history_viewer.js:

async function loadProctoringAssignments() {
    closeOtherForms()
    
    try {
        historyContentContainer.innerHTML = '';
        resetAnimation(historyContentContainer);
     
        historyContentContainer.innerHTML = '<div class="loading">Loading proctoring assignments...</div>';
        
        // Fetch proctoring
        const response = await fetch('proctoring_assignments.json');
        const data = await response.json();
        
        historyContentContainer.innerHTML = '';
        
        // header
        const headerDiv = document.createElement('div');
        headerDiv.className = 'history-header';
        headerDiv.innerHTML = '<h2>Current Proctoring Assignments</h2>';
        historyContentContainer.appendChild(headerDiv);
        
        // Check if there are any proctoring assignments
        if (!data.proctoring || data.proctoring.length === 0) {
            historyContentContainer.innerHTML += '<div class="no-data">No proctoring assignments available.</div>';
            return;
        }
        
        const table = document.createElement('table');
        table.className = 'history-table';
        
        const thead = document.createElement('thead');
        thead.innerHTML = `
            <tr>
                <th>Course</th>
                <th>Exam Type</th>
                <th>Date</th>
                <th>Time</th>
                <th>Location</th>
                <th>Students</th>
            </tr>
        `;
        table.appendChild(thead);
        
        const tbody = document.createElement('tbody');
        
        // Sort proctoring assignments by date of tha exams
        data.proctoring.sort((a, b) => new Date(a.date) - new Date(b.date));
        
        // Add rows for each proctoring assignment
        data.proctoring.forEach(assignment => {
            // Format the time range more nicely
            const startTime = assignment.startTime;
            const endTime = assignment.endTime;
            const timeRange = `${startTime} - ${endTime}`;
            
            // Format the date properly using the formatDate function
            let formattedDate;
            try {
                formattedDate = formatDate(assignment.date);
            } catch (error) {
                console.error('Error formatting date:', assignment.date);
                formattedDate = assignment.date || 'Date unavailable';
            }
            
            // Create the row element
            const row = document.createElement('tr');
            
            // Check if this is an upcoming assignment (within 3 days)
            const examDate = new Date(assignment.date);
            const today = new Date();
            const threeDaysFromNow = new Date();
            threeDaysFromNow.setDate(today.getDate() + 3);
            
            // Add the class BEFORE setting innerHTML
            if (examDate >= today && examDate <= threeDaysFromNow) {
                row.classList.add('upcoming-assignment');
            }
            
            // Create all cells with proper classes
            row.innerHTML = `
                <td class="course-column">${assignment.course}</td>
                <td class="exam-type-column" data-type="${assignment.examType}">${assignment.examType}</td>
                <td class="date-column">${formattedDate}</td>
                <td class="time-column">${timeRange}</td>
                <td class="location-column">${assignment.location}</td>
                <td class="students-column">${assignment.students}</td>
            `;
            
            // Add the completed row to the table body
            tbody.appendChild(row);
        });
        
        table.appendChild(tbody);
        historyContentContainer.appendChild(table);
        
        // Add actions button row
        const actionsDiv = document.createElement('div');
        actionsDiv.className = 'proctoring-actions';
        actionsDiv.innerHTML = `
            <button class="action-btn secondary-btn">Swap Assignments</button>
            <button class="action-btn export-btn">Export to Calendar</button>
        `;
        historyContentContainer.appendChild(actionsDiv);
        
        // Add event listeners to buttons
        const actionButtons = document.querySelectorAll('.action-btn');
        actionButtons.forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
            });
        });
        
    } catch (error) {
        console.error('Error loading proctoring assignments:', error);
        historyContentContainer.innerHTML = '<div class="error">Error loading proctoring assignments. Please try again later.</div>';
    }
}

    if (proctoringAssignmentsLink) {
        proctoringAssignmentsLink.addEventListener('click', function(e) {
            e.preventDefault();
            loadProctoringAssignments();
            
            // Update active state in menu
            document.querySelectorAll('.submenu a').forEach(link => link.classList.remove('active'));
            this.classList.add('active');
        });
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

    function resetAnimation(element) {
        // Reset the animation by removing and re-adding the element to the DOM
        element.style.animation = 'none';
        element.offsetHeight; // Trigger reflow
        element.style.animation = null;
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