const submittedTasks = [
    {
      course: "CS 319",
      taName: "Ahmet Yılmaz",
      date: "05.05.2025",
      assignment: "Assignment 1",
      hours: 2,
      info: "Grading assignment submissions"
    },
    {
      course: "CS 223",
      taName: "Zeynep Kaya",
      date: "06.05.2025",
      assignment: "Project Review",
      hours: 3,
      info: "Reviewed project reports"
    },
    {
      course: "CS 101",
      taName: "Mehmet Can",
      date: "07.05.2025",
      assignment: "Lab Setup",
      hours: 1.5,
      info: "Prepared lab environment"
    }
  ];
  
  function populateTable() {
    const tableBody = document.getElementById("assignmentTableBody");
  
    submittedTasks.forEach(task => {
      const row = document.createElement("tr");
  
      row.innerHTML = `
        <td>${task.course}</td>
        <td>${task.taName}</td>
        <td>${task.date}</td>
        <td>${task.assignment}</td>
        <td>${task.hours}</td>
        <td>${task.info}</td>
        <td>
          <button class="approve">Approve</button>
          <button class="reject">Reject</button>
        </td>
      `;
  
      tableBody.appendChild(row);
    });
  
    document.querySelectorAll(".approve").forEach(button => {
      button.addEventListener("click", function () {
        const row = this.closest("tr");
        row.classList.remove("rejected");
        row.classList.add("approved");
      });
    });
  
    document.querySelectorAll(".reject").forEach(button => {
      button.addEventListener("click", function () {
        const row = this.closest("tr");
        row.classList.remove("approved");
        row.classList.add("rejected");
      });
    });
  }
  
  window.onload = populateTable;
  