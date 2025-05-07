const examList = [
    {
      course: "CS 101 - Introduction to Programming",
      room: "EA-409",
      proctor: ["Ahmet Yılmaz", "Elif Demir"],
      duration: "09:00 - 11:00",
      students: 35,
      type: "Midterm"
    },
    {
      course: "CS 223 - Digital Design",
      room: "EE-214",
      proctor: ["Zeynep Kaya", "Murat Şahin", "Ayşe Korkmaz"],
      duration: "13:30 - 16:30",
      students: 42,
      type: "Final"
    },
    {
      course: "CS 342 - Operating Systems",
      room: "EB-204",
      proctor: ["Mehmet Can"],
      duration: "09:00 - 12:00",
      students: 25,
      type: "Final"
    }
  ];
  
  function loadExamTable() {
    const tableBody = document.getElementById("examTableBody");
  
    examList.forEach(exam => {
      const row = document.createElement("tr");
  
      row.innerHTML = `
        <td>${exam.course}</td>
        <td>${exam.room}</td>
        <td>${exam.proctor.join(", ")}</td>
        <td>${exam.duration}</td>
        <td>${exam.students}</td>
        <td>${exam.type}</td>
      `;
  
      tableBody.appendChild(row);
    });
  }
  
  window.onload = loadExamTable;
  