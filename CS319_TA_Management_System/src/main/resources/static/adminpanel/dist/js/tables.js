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

    fetch(`/api/${pageType}`, {
    //credentials: 'include',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }
})
      .then(response => {
            console.log('Response status:', response.status);
            console.log('Response headers:', response.headers);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
      .then(data => {
        tableBody.innerHTML = "";
        let row = ``;
        let addForm = ``;

        console.log(pageType)

        document.getElementById("importModal").setAttribute("page-type", pageType);
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
                    <label for="firstName" class="form-label">First Name</label>
                      <input type="text" class="form-control" id="firstName" name="firstName" required>
                    </div>
                    <div class="mb-3">
                      <label for="lastName" class="form-label">Last Name</label>
                      <input type="text" class="form-control" id="lastName" name="lastName" required>
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
              case "tas":
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
                    <label for="firstName" class="form-label">First Name</label>
                      <input type="text" class="form-control" id="firstName" name="firstName" required>
                    </div>
                    <div class="mb-3">
                      <label for="lastName" class="form-label">Last Name</label>
                      <input type="text" class="form-control" id="lastName" name="lastName" required>
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

              case "instructors":
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
                    <label for="firstName" class="form-label">First Name</label>
                      <input type="text" class="form-control" id="firstName" name="firstName" required>
                    </div>
                    <div class="mb-3">
                      <label for="lastName" class="form-label">Last Name</label>
                      <input type="text" class="form-control" id="lastName" name="lastName" required>
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
                      <th style="width: 300px">#</th>
                      <th style="width: 300px">Course Code</th>
                      <th style="width: 300px">Section</th>
                      <th style="width: 300px">Instructor</th>
                      <th style="width: 300px">MS/PHD</th>
                      <th style="width: 300px">TA's</th>
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
                      <label for="code" class="form-label">Course Code</label>
                      <input type="text" class="form-control" id="code" name="code" required>
                    </div>
                    <div class="mb-3">
                      <label for="section" class="form-label">Section</label>
                      <input type="number" class="form-control" id="section" name="section">
                    </div>
                    <div class="mb-3">
                      <label for="instructorID" class="form-label">Instructor Id</label>
                      <input type="number" class="form-control" id="instructorID" name="instructorID" required>
                    </div>
                    <div class="mb-3">
                      <label for="taIDs" class="form-label">TAs</label>
                      <input type="text" class="form-control" id="taIDs" name="taIDs">
                    </div>
                    <div class="form-check mb-3">
                      <input class="form-check-input" type="checkbox" id="isGraduateLevel" name="isGraduateLevel">
                      <label class="form-check-label" for="isGraduateLevel">
                        MS/PhD Level Course
                      </label>
                  </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                      <button type="submit" class="btn btn-primary">Create Course</button>
                    </div>
                </form>`;
            break;
           
            case "exams":
  row = `
        <thead>
          <tr>
            <th style="width: 10px">#</th>
            <th style="width: 100px">Course</th>
            <th style="width: 40px">Start Date</th>
            <th style="width: 40px">End Date</th>
            <th style="width: 40px">Place</th>
            <th style="width: 40px">Proctors</th>
          </tr>
        </thead>`;
  addForm = ` <form id="addCourseForm">
              <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Add New Exam</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                <div class="mb-3">
                  <label for="courseID" class="form-label">Course (ID)</label>
                  <input type="text" class="form-control" id="courseID" name="courseID" required>
                </div>
                <div class="mb-3">
                  <label for="startDate" class="form-label">Start Hour</label>
                  <input type="string" class="form-control" id="startDate" name="startDate" required>
                </div>
                <div class="mb-3">
                  <label for="endDate" class="form-label">End Hour</label>
                  <input type="string" class="form-control" id="endDate" name="endDate" required>
                </div>
                <div class="mb-3">
                  <label for="examPlace" class="form-label">Place</label>
                  <input type="text" class="form-control" id="examPlace" name="examPlace" required>
                </div>
                <div class="mb-3">
                  <label for="proctors" class="form-label">Proctors ID(s)</label>
                  <input type="text" class="form-control" id="proctors" name="proctors" required>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Create Exam</button>
              </div>
            </form>`;
            break;
          case "classrooms":
             row = `
        <thead>
          <tr>
            <th style="width: 300px">#</th>
            <th style="width: 300px">Classroom Name</th>
            <th style="width: 300px">Capacity</th>
            <th style="width: 300px">Exam Capacity</th>
          </tr>
        </thead>`;
  addForm = ` <form id="addCourseForm">
              <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Add New Exam</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                <div class="mb-3">
                  <label for="courseID" class="form-label">Course (ID)</label>
                  <input type="text" class="form-control" id="courseID" name="courseID" required>
                </div>
                <div class="mb-3">
                  <label for="startDate" class="form-label">Start Hour</label>
                  <input type="string" class="form-control" id="startDate" name="startDate" required>
                </div>
                <div class="mb-3">
                  <label for="endDate" class="form-label">End Hour</label>
                  <input type="string" class="form-control" id="endDate" name="endDate" required>
                </div>
                <div class="mb-3">
                  <label for="examPlace" class="form-label">Place</label>
                  <input type="text" class="form-control" id="examPlace" name="examPlace" required>
                </div>
                <div class="mb-3">
                  <label for="proctors" class="form-label">Proctors ID(s)</label>
                  <input type="text" class="form-control" id="proctors" name="proctors" required>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Create Exam</button>
              </div>
            </form>`
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
              } else if (["advisor", "totalWorkload"].includes(key)) {
                dataForm[key] = value ? Number(value) : null;
              } else {
                dataForm[key] = value;
              }
            });
            
            console.log(dataForm);
            // Send data with fetch
            fetch('/api/create-user', {
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Accept': 'application/json',
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
              if (["instructorID", "section"].includes(key)) {
                dataForm[key] = value ? Number(value) : null;
              }
              else if (
                ["taIDs"].includes(key)
              ) {
                dataForm[key] = value ? value.split(",").map(v => v.trim()).filter(v => v !== "").map(Number) : [];
              }else {
                dataForm[key] = value;
              }
            });

            const isGraduateCheckbox = document.getElementById("isGraduateLevel");
            dataForm.masterphd = isGraduateCheckbox.checked;

            // Send data with fetch
            fetch('/api/create-course', {
              method: 'POST',
              credentials: 'include', 
              headers: {
                  'Accept': 'application/json',
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
                        <button class="btn btn-warning d-flex align-items-center justify-content-center" style="border-top-right-radius: 0; border-bottom-right-radius: 0; background-color: #ffc107;
                        on">
                          <i class="bi bi-pencil-fill"></i>
                        </button>
                        <button class="btn btn-danger d-flex align-items-center justify-content-center"
                                style="border-top-left-radius: 0; border-bottom-left-radius: 0; background-color: #dc3545;"
                                onclick="deleteUser(${item.id})">
                          <i class="bi bi-x-lg"></i>
                        </button>

                      </div> </td>

                      </tr>
                    `;
              console.log(pageType);
              tableBody.insertAdjacentHTML("beforeend", row);
              break;

            case "courses":
              row = `
                      <tr class="align-middle">
                        <td>${index + 1}.</td>
                        <td>${item.code}</td>
                        <td>${item.section}</td>
                        <td>${item.instructorID}</td>
                        <td>${item.masterphd ? "Yes" : "No"}</td>
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
                          <div class="d-flex justify-content-center gap-2">
                            <button class="btn btn-warning d-flex align-items-center justify-content-center"
                              style="border-top-right-radius: 0; border-bottom-right-radius: 0; background-color: #ffc107;">
                              <i class="bi bi-pencil-fill"></i>
                            </button>
                            <button class="btn btn-danger d-flex align-items-center justify-content-center"
                              style="border-top-left-radius: 0; border-bottom-left-radius: 0; background-color: #dc3545;"
                              onclick="deleteCourse(${item.id})">
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
                  fetch(`/api/TA/${taId}`, {
                      credentials: 'include',
                      headers: {
                          'Accept': 'application/json',
                          'Content-Type': 'application/json'
                      }
                  })
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
                              <td>${ta.firsName || '-'}</td>
                              <td>${ta.lastName || '-'}</td>
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

              case "classrooms":
              row = `
                      <tr class="align-middle">
                        <td>${index + 1}.</td>
                        <td>${item.classroomName}</td>
                        <td>${item.capacity}</td>
                        <td>${item.examCapacity}</td>
                                              <td>
                        <div class="d-flex">
                        <button class="btn btn-warning d-flex align-items-center justify-content-center" style="border-top-right-radius: 0; border-bottom-right-radius: 0; background-color: #ffc107;
                        on">
                          <i class="bi bi-pencil-fill"></i>
                        </button>
                        <button class="btn btn-danger d-flex align-items-center justify-content-center"
                                style="border-top-left-radius: 0; border-bottom-left-radius: 0; background-color: #dc3545;"
                                onclick="deleteUser(${item.id})">
                          <i class="bi bi-x-lg"></i>
                        </button>

                      </div> </td>

                      </tr>
                    `;
              console.log(pageType);
              tableBody.insertAdjacentHTML("beforeend", row);
              break;

            default:
              row = `<tr><td colspan="4">Unknown content type: ${pageType}</td></tr>`;
              tableBody.insertAdjacentHTML("beforeend", row);
              break;
          }


        });
      })
      .catch(error => {
        //console.error("No data", error);
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

function deleteUser(id) {
  const confirmDelete = window.confirm("Are you sure you want to delete this user?");
  
  if (confirmDelete) {
    fetch(`/api/delete-user/${id}`, {
        method: 'DELETE',
        credentials: 'include',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.text())
    .then(result => {
      console.log('Server response:', result);
      location.reload();
    })
    .catch(error => {
      console.error('Error:', error);
      alert('Error deleting the user.');
    });
  }
}

function deleteCourse(id) {
  const confirmation = window.confirm("Are you sure you want to delete this course?");
  
  if (confirmation) {
    fetch(`/api/delete-course/${id}`, {
        method: 'DELETE',
        credentials: 'include',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.text())
    .then(result => {
      console.log('Server response:', result);
      location.reload();
    })
    .catch(error => {
      console.error('Error:', error);
      alert('Error deleting the course.');
    });
  }
}












