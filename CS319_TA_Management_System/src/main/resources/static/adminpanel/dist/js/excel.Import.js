document.getElementById("importExcelButton").addEventListener("click", function () {
  const fileInput = document.getElementById("excelFileInput");
  const file = fileInput.files[0];

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
  let jsonData = XLSX.utils.sheet_to_json(sheet, { header: 1 }); 

  jsonData = jsonData.slice(1);

  console
  const pageType = document.getElementById("importModal").getAttribute("page-type");
    switch (pageType) {
    case "courses":

  const validData = jsonData
    .filter(row => Array.isArray(row) && row.some(cell => cell !== undefined && cell !== null && cell.toString().trim() !== ""))
    .map(row => {
        // Get additional info from remaining cells
          const additionalInfo = new Array(98).fill("");
        for (let i = 0; i < 4; i++) {
            console.log("Row:", row[4+i*3], row[5+i*3], row[6+i*3]);
          if (row[4+i*3] != "" || row[5+i*3] != "" || row[6+i*3] !="") {
            console.log("Row:", row[4+i*3], row[5+i*3], row[6+i*3]);
          
            additionalInfo[getDayIndex(row[4+i*3])*14+getHourIndex(row[6+i*3])] = getCourseType(row[5+i*3]);
          }
          
        }

        for (let i = 0; i < calculateHourDifference(row[17], row[18]); i++) {
          if (row[16] != "" || row[17] != "" || row[18] !="") {
            additionalInfo[getDayIndex(row[16])*14+getHourIndex(row[17]) + i] ="XF";}
        }
         for (let i = 0; i < calculateHourDifference(row[32], row[33]); i++) {
          if (row[31] != "" || row[32] != "" || row[33] !="") {
            additionalInfo[getDayIndex(row[31])*14+getHourIndex(row[32]) + i] ="R";}
        }
        
      

        for (let i = 0; i < 4; i++) {
          if (row[19+i*3] != "" || row[20+i*3] != "" || row[21+i*3] !="") {
          additionalInfo[getDayIndex(row[19+i*3])*14+getHourIndex(row[21+i*3])] = getCourseType(row[20+i*3]);}
        }
        console.log("Additional Info:", additionalInfo);

        return {
            "Course Name": row[0], // Adjust column indices based on your Excel structure
            "Section": row[1],
            "firstname": row[2],
            "lastname": row[3],
            "schedule": additionalInfo // Include additional info in the object
        };
    });
   
console.log("Valid data:", validData);
  // Process valid rows
  validData.forEach(course => {
    //console.log("Processing instructor try:", course["Instructor"]);  
        
 const params = new URLSearchParams({
        firstName: course.firstname,
        lastName: course.lastname
    });

   // ...existing code...
fetch(`/api/userN?${params.toString()}`, {
    method: 'GET',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }
})
.then(res => {
    if (!res.ok) {
        throw new Error(`HTTP error! status: ${res.status}`);
    }
    return res.text();
})
.then(data => {
    const id = parseInt(data);
    if (isNaN(id)) {
        throw new Error('Invalid instructor ID received');
    }
    console.log("Instructor ID:", id);
    
    const courseData = {
        code: course["Course Name"],
        section: course["Section"],
        instructorID: id,  
        schedule: course.schedule
        
    };

    console.log("Course Data being sent:", courseData);

    return fetch("/api/create-course", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(courseData)
    });
})
.then(response => {
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
})
.then(result => {
    console.log("Course created:", result);
    alert(`Course "${course["Course Name"]}" created successfully!`);
})
.catch(error => {
    console.error("Error:", error);
    alert(`Error processing course "${course["Course Name"]}": ${error.message}`);
});
    });
    break;
    case "tas":
      const tadata = jsonData
      .filter(row => Array.isArray(row) && row.some(cell => cell !== undefined && cell !== null && cell.toString().trim() !== ""))
    .map(row => ({
      "First Name": row[0], // Adjust column indices based on your Excel structure
      "Last Name": row[1],
      "Email": row[2],
      "Username": row[3],
      "Master": row[4],
      "Advisor Id": row[5],
      "Part Time": row[6],
      "Department Code": row[7]

     
    }));
    tadata.forEach(ta => {
      let master = false;
      let partTime = false;
        if (ta["Master"] == "TRUE") {
          master = true;
        }
        if (ta["Part Time"] == "TRUE") {
          partTime = true;
        }

         const TAData = {
        firstName: ta["First Name"],
        lastName: ta["Last Name"],
        email: ta["Email"],
        username: ta["Username"],
        password: -1,
        master: master,
        advisorID: ta["Advisor Id"],
        partTime: partTime,
        departmentCode: ta["Department Code"]
    };

      console.log("TAData being sent:", TAData);
      fetch("/api/create-ta", {
      method: "POST",
      headers: {
             'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
            body: JSON.stringify(TAData)
          })
          .then(response => {
          if (!response.ok) {
              throw new Error(`HTTP error! status: ${response.status}`);
          }
          return response.json();
      })
      .then(result => {
          console.log("TA created:", result);
          alert(`TA "${ta["First Name"]} ${ta["Last Name"]}" created successfully!`);
      })
      .catch(error => {
          console.error("Error:", error);
          alert(`Error processing classroom "${classroom["Classroom Name"]}": ${error.message}`);
      });
    });
      break;
      
    case "classrooms":

       const classdata = jsonData
      .filter(row => Array.isArray(row) && row.some(cell => cell !== undefined && cell !== null && cell.toString().trim() !== ""))
    .map(row => ({
      "Classroom Name": row[0], // Adjust column indices based on your Excel structure
      "Capacity": row[1],
      "Exam Capacity": row[2]
    }));
    classdata.forEach(classroom => {


         const classroomData = {
        classroomName: classroom["Classroom Name"],
        capacity: classroom["Capacity"],
        examCapacity: classroom["Exam Capacity"]
    };
      fetch("/api/create-Classroom", {
      method: "POST",
      headers: {
             'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
            body: JSON.stringify(classroomData)
          })
          .then(response => {
          if (!response.ok) {
              throw new Error(`HTTP error! status: ${response.status}`);
          }
          return response.json();
      })
      .then(result => {
          console.log("Classroom created:", result);
          alert(`Classroom "${classroom["Classroom Name"]}" created successfully!`);
      })
      .catch(error => {
          console.error("Error:", error);
          alert(`Error processing classroom "${classroom["Classroom Name"]}": ${error.message}`);
      });
    });
    

    break;

    case "instructors":

    
    break;
    
    case "exams":

      break;  
  }
  };

  reader.readAsArrayBuffer(file);
});


function getDayIndex(day) {
  if (!day) {
    console.error("Invalid day input:", day);
    return -1; // Geçersiz bir değer için -1 döndür
  }

  const days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
  return days.indexOf(day.trim());
}

function getHourIndex(hour) {
  if (!hour || typeof hour !== "string") {
    console.error("Invalid hour input:", hour);
    return -1; // Geçersiz bir değer için -1 döndür
  }

  const hours = [
    "08.30 - 09.20", "09.30 - 10.20", "10.30 - 11.20", "11.30 - 12.20",
    "13.30 - 14.20", "14.30 - 15.20", "15.30 - 16.20", "16.30 - 17.20",
    "17.30 - 18.20", "18.30 - 19.20", "19.30 - 20.20", "20.30 - 21.20",
    "21.30 - 22.20"
  ];
  return hours.indexOf(hour.trim());
}

function getCourseType(type) {
  if (!type || typeof type !== "string") {
    console.error("Invalid course type input:", type);
    return ""; // Geçersiz bir değer için boş string döndür
  }

  switch (type.trim()) {
    case "Lecture":
      return "LF";
    case "Lecture O.":
      return "LO";
    case "Spare":
      return "SF";
    case "Spare O.":
      return "SO";
    default:
      console.error("Unknown course type:", type);
      return ""; 
  }
}



function calculateHourDifference(start, end) {
  if (!start || !end || typeof start !== "string" || typeof end !== "string") {
    console.error("Invalid time input: start =", start, ", end =", end);
    throw new Error("Invalid time input. Both start and end must be valid strings.");
  }

  const bhours = [
    "08.30", "09.30", "10.30", "11.30", "12.30", 
    "13.30", "14.30", "15.30", "16.30", "17.30", 
    "18.30", "19.30", "20.30", "21.30"
  ];

  const thours = [
    "09.20", "10.20", "11.20", "12.20", 
    "13.20", "14.20", "15.20", "16.20", "17.20", 
    "18.20", "19.20", "20.20", "21.20", "22.20"
  ];

  start = start.trim();
  end = end.trim();

  console.log("Start (trimmed):", start, "End (trimmed):", end);

  const startIndex = bhours.indexOf(start);
  const endIndex = thours.indexOf(end);

  if (startIndex === -1 || endIndex === -1) {
    throw new Error("Invalid time format. Please use one of the predefined times.");
  }

  return endIndex - startIndex;
}
