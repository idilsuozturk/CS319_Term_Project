document.getElementById("importExcelButton").addEventListener("click", function () {
  const fileInput = document.getElementById("excelFileInput");
  const file = fileInput.files[0]; // Get the selected file

  if (!file) {
    console.error("No file selected.");
    alert("Please select a file before clicking 'Understood'.");
    return;
  }

  console.log("Selected file:", file.name);

  // Process the file
  const reader = new FileReader();
  reader.onload = function (e) {
    const data = new Uint8Array(e.target.result);
    const workbook = XLSX.read(data, { type: "array" });

    // Assuming the first sheet contains the data
    const sheetName = workbook.SheetNames[0];
    const sheet = workbook.Sheets[sheetName];
    const jsonData = XLSX.utils.sheet_to_json(sheet);

    // Example: Sending data to the backend to create courses
    jsonData.forEach(course => {
      const courseData = {
        courseName: course["Course Name"],
        section: course["Section"],
        instructor: course["Instructor ID"],
        id: course["Course ID"]
      };

      fetch('/api/create-course', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(courseData)
      })
        .then(response => response.json())
        .then(result => {
          console.log("Course created:", result);
          alert(`Course "${courseData.courseName}" created successfully!`);
        })
        .catch(error => {
          console.error("Error creating course:", error);
          alert(`Error creating course "${courseData.courseName}".`);
        });
    });
  };

  reader.readAsArrayBuffer(file);
});