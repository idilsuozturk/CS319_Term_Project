document.addEventListener('DOMContentLoaded', function () {
    const mainContent = document.getElementById('mainContent');
  
    function loadPage(viewName) {
      const path = {
        import: 'dean_import.html',
        semester: 'dean_semester.html',
        exam: 'dean_exam.html'
      }[viewName];
  
      if (path) {
        fetch(path)
          .then(response => response.text())
          .then(html => {
            mainContent.innerHTML = html;
  
            // 🧠 Eğer exam sayfası yüklendiyse, script'leri elle çalıştır
            if (viewName === 'exam') {
              const dynamicScript = document.createElement('script');
              dynamicScript.textContent = `
                (function () {
                  const form = document.getElementById('examForm');
                  const cancel = document.getElementById('cancelExam');
                  const container = document.getElementById('examFormContainer');
                  const proctorMode = document.getElementById('proctorMode');
                  const proctorListGroup = document.getElementById('proctorListGroup');
  
                  function toggleProctorList() {
                    if (proctorMode && proctorListGroup) {
                      proctorListGroup.style.display = proctorMode.value === 'manual' ? 'block' : 'none';
                    }
                  }
  
                  if (proctorMode) {
                    proctorMode.addEventListener('change', toggleProctorList);
                    toggleProctorList();
                  }
  
                  if (form) {
                    form.addEventListener('submit', function (e) {
                      e.preventDefault();
                      const data = new FormData(form);
  
                      const course = data.get("courseSelect");
                      const date = data.get("examDate");
                      const time = data.get("startTime");
                      const proctorType = data.get("proctorMode");
                      const proctor = data.get("proctorSelect");
  
                      let message = \`Exam scheduled for \${course} on \${date} at \${time}.\nProctor mode: \${proctorType}\`;
                      if (proctorType === "manual" && proctor) {
                        message += \`\\nManual proctor selected: \${proctor}\`;
                      }
  
                      alert(message);
                      container.innerHTML = '<p style="text-align:center; font-weight:bold;">Exam scheduled successfully!</p>';
                    });
                  }
  
                  if (cancel) {
                    cancel.addEventListener('click', () => {
                      container.innerHTML = '';
                    });
                  }
                })();
              `;
              document.body.appendChild(dynamicScript);
            }
          })
          .catch(err => console.error(`Error loading ${path}:`, err));
      }
    }
  
    // İlk açılışta URL parametresinden yükle
    const params = new URLSearchParams(window.location.search);
    const view = params.get('view');
    if (view) loadPage(view);
  
    // Tıklamalar
    document.getElementById('importLink').addEventListener('click', (e) => {
      e.preventDefault();
      loadPage('import');
      history.pushState(null, '', '?view=import');
    });
  
    document.getElementById('semesterLink').addEventListener('click', (e) => {
      e.preventDefault();
      loadPage('semester');
      history.pushState(null, '', '?view=semester');
    });
  
    document.getElementById('examLink').addEventListener('click', (e) => {
      e.preventDefault();
      loadPage('exam');
      history.pushState(null, '', '?view=exam');
    }); 
    fetch('dean_semester.html')
  .then(res => res.text())
  .then(html => {
    mainContent.innerHTML = html;

    const script = document.createElement('script');
    script.src = 'assets/js/dean_semester.js';
    script.defer = true;
    document.body.appendChild(script);
  });
  });
  