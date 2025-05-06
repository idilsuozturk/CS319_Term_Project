
document.addEventListener("DOMContentLoaded", function () {
  console.log("DOM fully loaded");
  function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
  }

  const pageType = getQueryParam("type");
  const titleElement = document.getElementById("page-title");
  const breadcrumbElement = document.getElementById("breadcrumb-title");
  const tableBody = document.getElementById("dynamic-table-body");
  const modalContent = document.getElementById("add-modal-form");

  if (pageType) {
    const formattedTitle = pageType.charAt(0).toUpperCase() + pageType.slice(1);
    if (titleElement) titleElement.innerText = formattedTitle;
    if (breadcrumbElement) breadcrumbElement.innerText = formattedTitle;

    fetch(`/api/${pageType}`)
      .then(response => response.json())
      .then(data => {
        tableBody.innerHTML = ""; // Önceki satırları temizle
        let row = ``;
        let addForm = ``;

        //this switch case arranges form inputs
        switch (pageType) {
          case "users":
            row = `
                  <thead>
                  <tr>
                  <th style="width: 10px">#</th>
                  <th style="width: 100px">Name</th>
                  <th style="width: 40px">Email</th>
                  <th style="width: 100px">Username</th>
                  <th style="width: 40px">Password</th>
                  <th style="width: 40px">Role</th>
                  <th style="width: 40px"></th>
                  </tr>
                  </thead>
                  <tbody id="dynamic-table-body"></tbody>
                  `;
            
            //shows the form for user
            addForm = ` <form id="addUserForm">
                  <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Add New User</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                  <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                      <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="mb-3">
                      <label for="username" class="form-label">User Name</label>
                      <input type="text" class="form-control" id="username" name="username" required>
                    </div>
                    <div class="mb-3">
                      <label for="email" class="form-label">Email</label>
                      <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                      <label for="role" class="form-label">Role</label>
                      <select class="form-select" id="role" name="role" required>
                        <option value="">Select Role</option>
                        <option value="ADMIN">Admin</option>
                        <option value="TA">TA</option>
                        <option value="INSTRUCTOR">Instructor</option>
                        <option value="DEPARTMENT_STAFF">Department Staff</option>
                        <option value="DEPARTMENT_CHAIR">Department Chair</option>
                        <option value="DEAN">Dean</option>
                      </select>
                    </div>
                    <!-- Extra fields will be injected here -->
                    <div id="extra-fields"></div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Create User</button>
                  </div>
                </form>`;
            break;
          case "courses":
            row = `
                  <thead>
                    <tr>
                      <th style="width: 10px">#</th>
                      <th style="width: 100px">Course Name</th>
                      <th style="width: 40px">Section</th>
                      
                      <th style="width: 40px">Instructor</th>
                      <th style="width: 40px">TA's</th>
                    </tr>
                  </thead>`;
                  //format of the form for courses
                  addForm = ` <form id="addCourseForm">
                  <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Add New Course</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <div class="mb-3">
                      <label for="username" class="form-label">Course Name</label>
                      <input type="text" class="form-control" id="courseName" name="courseName" required>
                    </div>
                    <div class="mb-3">
                      <label for="tcNumber" class="form-label">Section</label>
                      <input type="number" class="form-control" id="section" name="section">
                    </div>
                    <div class="mb-3">
                      <label for="username" class="form-label">Instructor Id</label>
                      <input type="number" class="form-control" id="instructor" name="instructor" required>
                    </div>
                    <div class="mb-3">
                      <label for="username" class="form-label">Course Id</label>
                      <input type="number" class="form-control" id="id" name="id" required>
                    </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                      <button type="submit" class="btn btn-primary">Create Course</button>
                    </div>
                </form>`;
            break;
          default:
            row = `<tr><td colspan="4">Unknown content type: ${pageType}</td></tr>`;
        }

        //logic to actually add the user
        tableBody.insertAdjacentHTML("beforeend", row);
        modalContent.insertAdjacentHTML("beforeend", addForm);
        const addUserForm = document.getElementById("addUserForm");
        if (addUserForm) {
          addUserForm.addEventListener("submit", function (e) {
            e.preventDefault(); // Prevent default form submission

            // Gather all form data
            const formData = new FormData(addUserForm);
            const dataForm = {};
            formData.forEach((value, key) => {
              if (
                ["currentAssistingCourses", "currentTakingCourses", "proctoringExams", "courses", "tas"].includes(key)
              ) {
                dataForm[key] = value ? value.split(",").map(v => v.trim()).filter(v => v !== "").map(Number) : [];
              } else if (["advisor", "totalWorkload", "tcNumber"].includes(key)) {
                dataForm[key] = value ? Number(value) : null;
              } else {
                dataForm[key] = value;
              }
            });

            // Send data with fetch
            fetch('/api/create-user', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(dataForm)
            })
              .then(response => response.json())
              .then(result => {

                let myModal = bootstrap.Modal.getInstance(document.getElementById('staticBackdrop'));
                myModal.hide();

                document.getElementById("modal-message").innerHTML = `
      <div class="alert alert-success d-flex align-items-center" role="alert">
  <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Success:"><use xlink:href="#check-circle-fill"/></svg>
  <div>
    User created successfully!
  </div>
</div>
      `;
                setTimeout(() => {
                  location.reload();
                }, 2000);

                console.log(result);
                // Optionally, refresh the user list here
              })
              .catch(error => {
                alert("Error creating user!");
                console.error(error);
              });
          });
        }

        const roleSelect = document.getElementById("role");
        const extraFields = document.getElementById("extra-fields");

        if (roleSelect) {
          roleSelect.addEventListener("change", function () {
            const role = this.value;
            let fields = "";
            switch (role) {
              case "ADMIN":
                fields = `
            <div class="mb-3">
              <label for="adminPassword" class="form-label">Password</label>
              <input type="password" class="form-control" id="adminPassword" name="password" required>
            </div>
          `;
                break;
              case "TA":
                fields = `
            <div class="mb-3">
              <label for="taPassword" class="form-label">Password</label>
              <input type="password" class="form-control" id="taPassword" name="password" required>
            </div>
            <div class="mb-3">
              <label for="currentAssistingCourses" class="form-label">Current Assisting Courses (comma separated IDs)</label>
              <input type="text" class="form-control" id="currentAssistingCourses" name="currentAssistingCourses">
            </div>
            <div class="mb-3">
              <label for="currentTakingCourses" class="form-label">Current Taking Courses (comma separated IDs)</label>
              <input type="text" class="form-control" id="currentTakingCourses" name="currentTakingCourses">
            </div>
            <div class="mb-3">
              <label for="advisor" class="form-label">Advisor (ID)</label>
              <input type="number" class="form-control" id="advisor" name="advisor">
            </div>    
            <div class="mb-3">
              <label for="totalWorkload" class="form-label">Total Workload</label>
              <input type="number" class="form-control" id="totalWorkload" name="totalWorkload">
            </div>
            <div class="mb-3">
              <label for="proctoringExams" class="form-label">Proctoring Exams (comma separated IDs)</label>
              <input type="text" class="form-control" id="proctoringExams" name="proctoringExams">
            </div>
          `;
                break;
              case "INSTRUCTOR":
                fields = `
            <div class="mb-3">
              <label for="instructorPassword" class="form-label">Password</label>
              <input type="password" class="form-control" id="instructorPassword" name="password" required>
            </div>
            <div class="mb-3">
              <label for="courses" class="form-label">Courses (comma separated IDs)</label>
              <input type="text" class="form-control" id="courses" name="courses">
            </div>
            <div class="mb-3">
              <label for="tas" class="form-label">TAs (comma separated IDs)</label>
              <input type="text" class="form-control" id="tas" name="tas">
            </div>
            <div class="mb-3">
              <label for="departmentCode" class="form-label">Department Code</label>
              <input type="text" class="form-control" id="departmentCode" name="departmentCode">
            </div>
            <div class="mb-3">
              <label for="tcNumber" class="form-label">TC Number</label>
              <input type="number" class="form-control" id="tcNumber" name="tcNumber">
            </div>
          `;
                break;
              case "DEPARTMENT_STAFF":
                fields = `
            <div class="mb-3">
              <label for="staffPassword" class="form-label">Password</label>
              <input type="password" class="form-control" id="staffPassword" name="password" required>
            </div>
            <div class="mb-3">
              <label for="departmentCode" class="form-label">Department Code</label>
              <input type="text" class="form-control" id="departmentCode" name="departmentCode">
            </div>
            <div class="mb-3">
              <label for="tcNumber" class="form-label">TC Number</label>
              <input type="number" class="form-control" id="tcNumber" name="tcNumber">
            </div>
          `;
                break;
              case "DEPARTMENT_CHAIR":
                fields = `
            <div class="mb-3">
              <label for="chairPassword" class="form-label">Password</label>
              <input type="password" class="form-control" id="chairPassword" name="password" required>
            </div>
            <div class="mb-3">
              <label for="departmentCode" class="form-label">Department Code</label>
              <input type="text" class="form-control" id="departmentCode" name="departmentCode">
            </div>
            <div class="mb-3">
              <label for="title" class="form-label">Title</label>
              <input type="text" class="form-control" id="title" name="title">
            </div>
            <div class="mb-3">
              <label for="tcNumber" class="form-label">TC Number</label>
              <input type="number" class="form-control" id="tcNumber" name="tcNumber">
            </div>
          `;
                break;
              case "DEAN":
                fields = `
            <div class="mb-3">
              <label for="deanPassword" class="form-label">Password</label>
              <input type="password" class="form-control" id="deanPassword" name="password" required>
            </div>
            <div class="mb-3">
              <label for="departmentCode" class="form-label">Department Code</label>
              <input type="text" class="form-control" id="departmentCode" name="departmentCode">
            </div>
            <div class="mb-3">
              <label for="title" class="form-label">Title</label>
              <input type="text" class="form-control" id="title" name="title">
            </div>
            <div class="mb-3">
              <label for="tcNumber" class="form-label">TC Number</label>
              <input type="number" class="form-control" id="tcNumber" name="tcNumber">
            </div>
          `;
                break;
              default:
                fields = "";
            }
            extraFields.innerHTML = fields;
          });
        }

        //logic to actually add the course
        const addCourseForm = document.getElementById("addCourseForm");
        if (addCourseForm) {
          addCourseForm.addEventListener("submit", function (e) {
            e.preventDefault(); 
            const formData = new FormData(addCourseForm);
            const dataForm = {};
            formData.forEach((value, key) => {
              if (["instructor", "section", "id"].includes(key)) {
                dataForm[key] = value ? Number(value) : null;
              } else {
                dataForm[key] = value;
              }
            });

            // Send data with fetch
            fetch('/api/create-course/${dataForm.id}', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(dataForm)
            })
              .then(response => response.json())
              .then(result => {

                let myModal = bootstrap.Modal.getInstance(document.getElementById('staticBackdrop'));
                myModal.hide();

                document.getElementById("modal-message").innerHTML = `
      <div class="alert alert-success d-flex align-items-center" role="alert">
  <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Success:"><use xlink:href="#check-circle-fill"/></svg>
  <div>
    Course created successfully!
  </div>
</div>
      `;
                setTimeout(() => {
                  location.reload();
                }, 2000);

                console.log(result);
                // Optionally, refresh the user list here
              })
              .catch(error => {
                alert("Error creating course!");
                console.error(error);
              });
          });
        }

        //switch case to create the table
        data.forEach((item, index) => {
          let row = "";

          switch (pageType) {
            case "users":
              row = `
                      <tr class="align-middle">
                        <td>${index + 1}.</td>
                        <td>${item.name}</td>
                        <td>${item.email}</td>
                        <td>${item.username}</td>
                        <td>${item.password}</td>
                        <td>${item.role}</td>
                        <td>
                        <div class="d-flex">
                        <button class="btn btn-warning d-flex align-items-center justify-content-center" style="border-top-right-radius: 0; border-bottom-right-radius: 0; background-color: #ffc107;">
                          <i class="bi bi-pencil-fill"></i>
                        </button>
                        <button class="btn btn-danger d-flex align-items-center justify-content-center" style="border-top-left-radius: 0; border-bottom-left-radius: 0; background-color: #dc3545;">
                          <i class="bi bi-x-lg"></i>
                        </button>
                      </div> </td>

                      </tr>
                    `;
              console.log(pageType);
              tableBody.insertAdjacentHTML("beforeend", row);
              break;

            case "courses":
              //TODO REMOVE BUTTON
              row = `
                      <tr class="align-middle">
                        <td>${index + 1}.</td>
                        <td>${item.courseName}</td>
                        <td>${item.section}</td>
                        <td>${item.instructor}</td>
                        <td>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#taModal-${index}">
                          View TAs
                        </button>

                          <div class="modal fade" id="taModal-${index}" tabindex="-1" aria-labelledby="taModalLabel-${index}">
                            <div class="modal-dialog">
                              <div class="modal-content">
                                <div class="modal-header">
                                  <h5 class="modal-title">Teaching Assistants - ${item.courseName}</h5>
                                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                  <table class="table table-bordered">
                                    <thead>
                                      <tr>
                                        <th>#</th>
                                        <th>Name</th>
                                        <th>Student ID</th>
                                        <th>Email</th>
                                      </tr>
                                    </thead>
                                    <tbody id="taTableBody-${index}">
                                      <!-- TA data will be loaded here -->
                                    </tbody>
                                  </table>
                                </div>
                                <div class="modal-footer">
                                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                </div>
                              </div>
                            </div>
                          </div>
                        </td>
                      <td>
    <div class="d-flex ms-auto">
      <button class="btn btn-warning d-flex align-items-center justify-content-center" style="border-top-right-radius: 0; border-bottom-right-radius: 0; background-color: #ffc107;">
        <i class="bi bi-pencil-fill"></i>
      </button>
      <button class="btn btn-danger d-flex align-items-center justify-content-center" style="border-top-left-radius: 0; border-bottom-left-radius: 0; background-color: #dc3545;">
        <i class="bi bi-x-lg"></i>
      </button>
    </div>
  </td>
                      </tr>

                    `;


              tableBody.insertAdjacentHTML("beforeend", row);

              if (item.tas && Array.isArray(item.tas) && item.tas.length > 0) {
                const taTableBody = document.getElementById(`taTableBody-${index}`);

                Promise.all(item.tas.map(taId =>
                  fetch(`/api/TA/${taId}`)
                    .then(res => {
                      if (!res.ok) {
                        throw new Error(`HTTP error! status: ${res.status}`);
                      }
                      return res.json();
                    })
                    .catch(error => {
                      console.error(`Error fetching TA ${taId}:`, error);
                      return null;
                    })
                ))                
                  .then(tasData => {
                    taTableBody.innerHTML = tasData
                      .filter(ta => ta !== null)
                      .map((ta, i) => `
                            <tr>
                              <td>${i + 1}</td>
                              <td>${ta.name || '-'}</td>
                              <td>${ta.tcNumber || '-'}</td>
                              <td>${ta.email || '-'}</td>
                            </tr>
                          `).join('');
                  })
                  .catch(error => {
                    console.error("Error loading TAs:", error);
                    taTableBody.innerHTML = '<tr><td colspan="4" class="text-center">Error loading TA data</td></tr>';
                  });
              }

              break;
            default:
              row = `<tr><td colspan="4">Unknown content type: ${pageType}</td></tr>`;
              tableBody.insertAdjacentHTML("beforeend", row);
              break;
          }


        });
      })
      .catch(error => {
        console.error("No data", error);
      });
  }
});


//function to get the page type
document.addEventListener("DOMContentLoaded", function () {
  function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
  }

  const pageType = getQueryParam("type");

  if (pageType) {
    const titleElement = document.getElementById("page-title");
    const breadcrumbElement = document.getElementById("breadcrumb-title");

    if (titleElement) titleElement.innerText = pageType;
    if (breadcrumbElement) breadcrumbElement.innerText = pageType;
  }
});






