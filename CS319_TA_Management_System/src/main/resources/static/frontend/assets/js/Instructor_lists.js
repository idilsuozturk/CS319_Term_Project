document.addEventListener('DOMContentLoaded', function() {
  // Get references to the menu links and content containers
  const taRequestFormContainer = document.getElementById('taRequestFormContainer');

  const historyContentContainer = document.getElementById('InstructorList');
  const ExamsViewLink = document.getElementById('ExamsViewLink');
  const CoursesViewLink = document.getElementById('CoursesViewLink');
  
  // Function to format date: yyyy-mm-dd to Mon dd, yyyy
  function formatDate(dateString) {
      const options = { year: 'numeric', month: 'short', day: 'numeric' };
      return new Date(dateString).toLocaleDateString(undefined, options);
  }

  function closeOtherForms() {
      
      if (taRequestFormContainer && taRequestFormContainer.style.display === 'flex') {
        taRequestFormContainer.style.display = 'none';
        taRequestFormContainer.innerHTML = '';
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
  async function LoadTasksView() {
    closeOtherForms();
    try {
        // Clear previous content e.g absence
        historyContentContainer.innerHTML = '';
        resetAnimation(historyContentContainer);
        // if server is lagging
        historyContentContainer.innerHTML = '<div class="loading">Loading tasks...</div>';
        
        // Fetch task data
        const response = await fetch('task_history.json');
        const data = await response.json();
        
        // Clear loading 
        historyContentContainer.innerHTML = '';
        
        const headerDiv = document.createElement('div');
        headerDiv.className = 'history-header';
        headerDiv.innerHTML = '<h2>TA Assignment Approval</h2>';
        historyContentContainer.appendChild(headerDiv);
        
        // Check if there are tasks
        if (!data.tasks || data.tasks.length === 0) {
            historyContentContainer.innerHTML += '<div class="no-data">No task pending</div>';
            return;
        }
        
        const table = document.createElement('table');
        table.className = 'history-table';
        
        const thead = document.createElement('thead');
        thead.innerHTML = `
            <tr>
                <th>Course</th>
                <th>TA Name</th>
                <th>Description</th>
                <th>Date</th>
                <th>Time Spent (hrs)</th>
                <th>Actions</th>
                <th>Status</th>
            </tr>
        `;
        table.appendChild(thead);
        
        const tbody = document.createElement('tbody');
        
        data.tasks.forEach(task => {
            const row = document.createElement('tr');
            
            const actionButtons = `
                <div class="action-buttons">
                    <button class="action-btn approve-btn" data-id="${task.id || ''}" data-status="approved">
                        <span class="btn-icon">✓</span>
                        <span class="btn-text">Approve</span>
                    </button>
                    <button class="action-btn reject-btn" data-id="${task.id || ''}" data-status="rejected">
                        <span class="btn-icon">✕</span>
                        <span class="btn-text">Reject</span>
                    </button>
                </div>
            `;
            
            row.innerHTML = `
                <td>${task.course}</td>
                <td>${task.TA || 'Unknown'}</td>
                <td>${task.description}</td>
                <td>${formatDate(task.date)}</td>
                <td>${task.timeSpent}</td>                    
                <td class="actions-cell">${actionButtons}</td>
                <td class="status-cell">${getStatusBadge(task.status)}</td>
            `;
            
            row.setAttribute('data-current-status', task.status.toLowerCase());
            
            tbody.appendChild(row);
        });
        
        table.appendChild(tbody);
        historyContentContainer.appendChild(table);
        

        setupActionButtons(data.tasks);
        
    } catch (error) {
        console.error('Error loading task history:', error);
        historyContentContainer.innerHTML = '<div class="error">Error loading task history. Please try again later.</div>';
    }
}

//action button clicks
function setupActionButtons(tasks) {
    // Approve button handler
    const actionButtons = document.querySelectorAll('.action-btn');
    actionButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const taskId = this.getAttribute('data-id');
            const newStatus = this.getAttribute('data-status');
            updateTaskStatus(this, taskId, newStatus);
        });
    });
}

// Update task status when buttons clicked
function updateTaskStatus(buttonElement, taskId, newStatus) {
    // Gets the row that has dis button
    const row = buttonElement.closest('tr');
    

    const currentStatus = row.getAttribute('data-current-status');
    
    // If the status is already set to this, does not change anythinh
    if (currentStatus === newStatus) {
        pulseButton(buttonElement);
        return;
    }
    

    const statusCell = row.querySelector('.status-cell');
    

    statusCell.innerHTML = getStatusBadge(newStatus);
    

    row.setAttribute('data-current-status', newStatus);
    

    row.classList.remove('status-updated');
    void row.offsetWidth; 
    row.classList.add('status-updated');
    
    // Highlight the selected button
    highlightSelectedButton(row, newStatus);
    
    // sned the server the data
    console.log(`Task ${taskId} status updated to: ${newStatus}`);
    
    // Show a notification to use. 
    showNotification(`Task has been ${newStatus}`, newStatus);
}

// Highlight the selected button and dim the other one
function highlightSelectedButton(row, selectedStatus) {
    const approveBtn = row.querySelector('.approve-btn');
    const rejectBtn = row.querySelector('.reject-btn');
    

    approveBtn.classList.remove('selected');
    rejectBtn.classList.remove('selected');
    
    // Highlight the selected one
    if (selectedStatus === 'approved') {
        approveBtn.classList.add('selected');
    } else if (selectedStatus === 'rejected') {
        rejectBtn.classList.add('selected');
    }
}

function pulseButton(button) {
    button.classList.add('pulse');
    setTimeout(() => {
        button.classList.remove('pulse');
    }, 500);
}

// Show a notification
function showNotification(message, type) {

    const notification = document.createElement('div');
    notification.className = `notification-toast ${type}`;
    notification.innerHTML = `
        <div class="notification-icon">${type === 'approved' ? '✓' : '✕'}</div>
        <div class="notification-message">${message}</div>
    `;
    

    document.body.appendChild(notification);
    
    // Fading in
    setTimeout(() => {
        notification.classList.add('show');
    }, 10);
    
    // 3 seconds period for notif
    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => {
            document.body.removeChild(notification);
        }, 300);
    }, 3000);
}
  

  async function loadCoursesView() {
    closeOtherForms();

    try {
        historyContentContainer.innerHTML = '';
        resetAnimation(historyContentContainer);
     
        historyContentContainer.innerHTML = '<div class="loading">Loading courses...</div>';
        
        const response = await fetch('proctoring_assignments.json');
        const data = await response.json();
        
        historyContentContainer.innerHTML = '';
        

        const headerDiv = document.createElement('div');
        headerDiv.className = 'history-header';
        headerDiv.innerHTML = '<h2>Courses</h2>';
        historyContentContainer.appendChild(headerDiv);
        
        // Check if there are any courses
        if (!data.courses || data.courses.length === 0) {
            historyContentContainer.innerHTML += '<div class="no-data">No courses present.</div>';
            return;
        }
        
        const table = document.createElement('table');
        table.className = 'history-table';
        
        const thead = document.createElement('thead');
        thead.innerHTML = `
            <tr>
                <th>Course Code</th>
                <th>Course Name</th>
                <th>Credit</th>
                <th>Semester</th>
            </tr>
        `;
        table.appendChild(thead);
        
        const tbody = document.createElement('tbody');
        data.courses.forEach(assignment => {

            
            // Create the row 
            const row = document.createElement('tr');
            
            row.innerHTML = `
                <td class="course-column">${assignment.code}</td>
                <td class="exam-type-column">${assignment.name}</td>
                <td class="date-column">${assignment.credit}</td>
                <td class="location-column">${assignment.semester}</td>
            `;
            
            // Add each row to table
            tbody.appendChild(row);
        });
        
        table.appendChild(tbody);
        historyContentContainer.appendChild(table);
        
       
        
    } catch (error) {
        console.error('Error loading proctoring assignments:', error);
        historyContentContainer.innerHTML = '<div class="error">Error loading proctoring assignments. Please try again later.</div>';
    }
  }

  async function loadExamsView() {
    closeOtherForms();
      
      
    try {
        historyContentContainer.innerHTML = '';
        resetAnimation(historyContentContainer);
     
        historyContentContainer.innerHTML = '<div class="loading">Loading Exams...</div>';
        
        // Fetch exams
        const response = await fetch('proctoring_assignments.json');
        const data = await response.json();
        
        historyContentContainer.innerHTML = '';
        
        // header
        const headerDiv = document.createElement('div');
        headerDiv.className = 'history-header';
        headerDiv.innerHTML = '<h2>Exams</h2>';
        historyContentContainer.appendChild(headerDiv);
        
        // Check if there are any exams
        if (!data.proctoring || data.proctoring.length === 0) {
            historyContentContainer.innerHTML += '<div class="no-data">No exams available.</div>';
            return;
        }
        
        const table = document.createElement('table');
        table.className = 'history-table';
        
        const thead = document.createElement('thead');
        thead.innerHTML = `
            <tr>
                <th>Course</th>
                <th>Exam Type</th>
                <th>Proctors</th>
                <th>Date</th>
                <th>Time</th>
                <th>Location</th>
                <th>Students</th>
            </tr>
        `;
        table.appendChild(thead);
        
        const tbody = document.createElement('tbody');
        
        // Sort exams by date
        data.proctoring.sort((a, b) => new Date(a.date) - new Date(b.date));
        
        // Add rows for each 
        data.proctoring.forEach(assignment => {

            const startTime = assignment.startTime;
            const endTime = assignment.endTime;
            const timeRange = `${startTime} - ${endTime}`;
            
            let formattedDate;
            try {
                formattedDate = formatDate(assignment.date);
            } catch (error) {
                console.error('Error formatting date:', assignment.date);
                formattedDate = assignment.date || 'Date unavailable';
            }
            
            // Create the row 
            const row = document.createElement('tr');
            
            // Check if this is an upcoming assignment (within 3 days)
            const examDate = new Date(assignment.date);
            const today = new Date();
            const threeDaysFromNow = new Date();
            threeDaysFromNow.setDate(today.getDate() + 3);
            
 
            if (examDate >= today && examDate <= threeDaysFromNow) {
                row.classList.add('upcoming-assignment');
            }
            
            // Create all cells with proper classes
            row.innerHTML = `
                <td class="course-column">${assignment.course}</td>
                <td class="exam-type-column" data-type="${assignment.examType}">${assignment.examType}</td>
                <td class="date-column">${assignment.proctor}</td>
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
            <button class="action-btn secondary-btn">Request TAs</button>
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
        console.error('Error loading exams', error);
        historyContentContainer.innerHTML = '<div class="error">Please try again later.</div>';
    }
  }

  
if (ExamsViewLink) {
    ExamsViewLink.addEventListener('click', function(e) {
        e.preventDefault();
        loadExamsView();
        
        // Update active state in menu
        document.querySelectorAll('.submenu a').forEach(link => link.classList.remove('active'));
        this.classList.add('active');
    });
}
  
 
  if (TasksViewLink) {
    TasksViewLink.addEventListener('click', function(e) {
          e.preventDefault();
          LoadTasksView();
          
     
          document.querySelectorAll('.submenu a').forEach(link => link.classList.remove('active'));
          this.classList.add('active');
      });
  }
  
  if (CoursesViewLink) {
    CoursesViewLink.addEventListener('click', function(e) {
          e.preventDefault();
          loadCoursesView();
          
       
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
    //   closeOtherForms();

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