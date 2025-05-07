const courses = [
    {
      code: "CS 101",
      name: "Introduction to Programming",
      credit: 4,
      semester: "Spring 2025"
    },
    {
      code: "CS 223",
      name: "Digital Design",
      credit: 3,
      semester: "Spring 2025"
    },
    {
      code: "CS 342",
      name: "Operating Systems",
      credit: 4,
      semester: "Spring 2025"
    }
  ];
  
  function loadCourses() {
    const tableBody = document.getElementById("courseTableBody");
  
    courses.forEach(course => {
      const row = document.createElement("tr");
  
      row.innerHTML = `
        <td>${course.code}</td>
        <td>${course.name}</td>
        <td>${course.credit}</td>
        <td>${course.semester}</td>
      `;
  
      tableBody.appendChild(row);
    });
  }
  
  window.onload = loadCourses;
  