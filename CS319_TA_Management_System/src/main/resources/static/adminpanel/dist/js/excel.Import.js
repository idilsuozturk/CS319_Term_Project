document.getElementById("importExcelButton").addEventListener("click", function () {
  const fileInput = document.getElementById("excelFileInput");
  const file = fileInput.files[0]; // Get the selected file

  if (!file) {
    console.error("No file selected.");
    alert("Please select a file before clicking 'Understood'.");
    return;
  }

  console.log("Selected file:", file.name);

   const reader = new FileReader(); 
  reader.onload = function (e) {
  const data = new Uint8Array(e.target.result);
  const workbook = XLSX.read(data, { type: "array" });

  // Assuming the first sheet contains the data
  const sheetName = workbook.SheetNames[0];
  const sheet = workbook.Sheets[sheetName];
  let jsonData = XLSX.utils.sheet_to_json(sheet, { header: 1 }); // Get data as an array of arrays

  // Remove the first row (header row)
  jsonData = jsonData.slice(1);
  const pageType = document.getElementById("importModal").getAttribute("page-type");
    switch (pageType) {
    case "courses":
  // Convert rows to objects, skipping empty rows
  const validData = jsonData
      .filter(row => Array.isArray(row) && row.some(cell => cell !== undefined && cell !== null && cell.toString().trim() !== ""))
    .map(row => ({
      "Course Name": row[0], // Adjust column indices based on your Excel structure
      "Section": row[1],
      "Instructor": row[2]
    }));

  console.log("Valid data:", validData);
  // Process valid rows
  validData.forEach(course => {
    console.log("Processing instructor try:", course["Instructor"]);
    fetch(`/api/userN/${encodeURIComponent(course["Instructor"])}`)
      .then(response => {
        if (!response.ok) {
          console.log("instructor is faulty: " .course["Instructor"]);
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then(userId => {
        console.log("Instructor ID:", userId);
       
        const courseData = {
          courseName: course["Course Name"],
          section: course["Section"],
          instructor: userId
        };

        console.log("Course Data being sent:", courseData);

        fetch("/api/create-course", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
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
      })
      .catch(error => {
        console.error("Fetch error:", error);
        });
    });
    break;
    case "users":
      // Handle user import logic here
      break;
  }
  };

  reader.readAsArrayBuffer(file);
});