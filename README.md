## Description
- A website where the distribution and management of TA's duties can be done by faculty members of different ranks according to their authority levels. 
### Features (Additional Features are specified with (AF) written at the end of a feature, and the features Department Chairs will also be able to do are specified with (DC) written at the end of a feature)
**Features for TAs**
- TAs have the ability to view their schedule, student list of the class they are proctoring in and other proctoring TAs’ tasks to swap within the Dashboard for courses.  
- The ability to enter the work / tasks that get added to “Total Work Load” via Sidebar. TAs may view their added tasks in the Dashboard.
- TAs have the right to request a “Leave of Absence” due to a valid excuse such as medical reasons, conference, vacation etc via using the sidebar. 
- A TA may request to swap Proctoring tasks with another TA. Such requests done by TA only requires another TA to confirm the swap, without needing any other Staff Members' approval.
- TAs will be notified every time a change is made to the tasks they are assigned to, e.g., a manual or an automatic change of a task via an authorized faculty member or a change in the assigned tasks itself such as the task date or place via email.

**Features for Instructors**
- Instructor will be able to view the courses they are responsible for in the Dashboard.
- Instructor will be able to approve/decline pending requests coming from TA’s (for tasks, TA swaps and absence permit requests) via a Sidebar.
- Instructor will be able to assign proctors manually with restrictions or automatically by the algorithm going to implemented based on restrictions. 
- Instructor will be able to print exam distributions alphabetically ordered or random based on preference.
- Instructor will be able to replace proctors both manually and automatically and when a replacement occurs, TA’s will be notified by program and via email.
- Instructor will be able to change the date or the place of the assignments their assigned courses have if an unexpected situation occurs with the current date or place. (AF)

**Features for Authorized Staff**
- Authorized Staff will get notifications for leave of absence of TA's. (DC)
- Authorized Staff will be able to view all exam data, such as students lists, exam place, proctoring TA's and the exam date.
- Authorized Staff will hold priority over Instructors in Proctor Assignment and Swap. (AF)
- Authorized Staff will be able to initiate automatic or manual proctor swaps.
- Authorized Staff will be able to add/edit/delete student/staff/course/offering/classroom​.

**Features for Deans Office** 
- Deans Office will be able to choose the departments to be pooled in case of a need during the automated proctoring assignment.
- Deans Office will be able to make assignments in exams scheduled by the Deans Office.
- Deans Office will be able to schedule specific exams.

**Features for the Admins**
- Admins will be able to change some global parameters such as the current semester.
- Admins will be able to view records for a specific semester.
- Admins will be able to set maximum number of hours a TA can do proctoring/TA duty per semester/academic year at the beginning of an academic year.
- Admins will be able to ask for specific details for a specific semester/academic year such as total proctoring hours per semester/academic year, and total TA Duty of a course per semester/academic year/section/student.
- Admins will have the permission to view every detail in TA distribution, e.g., the TAs assigned for specific courses, the courses given in that semester, the instructors for these courses, and so on.

**General Features**
- Informations such as offerings in a semester, courses taken by each student in a semester, students, and faculty will be importable via Excel.
- The system will keep the informations about students, staff, classrooms, offerings, and courses and the necessary attributes of these types. 
- The record of all activities (logins, assignments, swaps... etc.) will be stored in the system.
- During the automatic assignment, “Total Work Load” of TAs determines how proctoring tasks will be distributed. Those with the least “Total Work Load” will have the priority during the automatic assignment.
- The Leave of Absence requests are needed to be approved by a Depeartment Chair or an Autharized Staff for it to be accepted.
- The Assignment details are student list, date, time and etc., if applicable e.g., student lists for proctoring assignments.
- When a swap occurs, the instructors of the courses which the proctoring assignment swap was done between will be notified via email. (AF)
- If assigning proctoring duties is not possible with restrictions, some of the restrictions can be breached based on the restrictions priority order had been defined by us during the implementation.
- During the manual assignment, a list of available TAs will be given in a presedence order based on how suitable a TA is for that specific task. (AF)
- In the manual assignment, if an assignment is made for a restricted TA or a TA who was assigned to that task before but swapped the task later on, e.g., a TA with Leave of Absence in that specific date, a Warning Message will be shown explaining the problem and the system will not allow this assignment.
- During the automatic assignment, if there is no solution with restrictions, the user will be asked to choose some of the breachable restrictions to override for reautomating the assigment distribution.

### Restrictions ((NB) stands for not breachable to specify non-breachable restrictions)
- Only PHD students can be assigned to MS/PHD level courses.​
- TAs who have no other proctoring assignment on one day before/after should have priority for a proctoring assignment scheduled for that day.
- If the TA is on leave, she/he cannot be assigned.
- If the TA is taking this course as a student, she/he cannot be assigned. (NB)
- If the TA is having an exam of another course as a student on the same date/time, she/he cannot be assigned. (NB)
