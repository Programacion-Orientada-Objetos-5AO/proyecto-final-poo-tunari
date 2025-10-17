    // Helpers DOM
    const $ = (sel) => document.querySelector(sel);

    // Mostrar/ocultar contraseña
    function setupToggle(inputId, toggleId){
      const input = $("#"+inputId);
      const toggle = $("#"+toggleId);
      toggle.addEventListener('click', ()=>{
        const isPwd = input.getAttribute('type') === 'password';
        input.setAttribute('type', isPwd ? 'text' : 'password');
        toggle.classList.toggle('bi-eye');
        toggle.classList.toggle('bi-eye-slash');
      });
    }
    setupToggle('password','togglePass');
    setupToggle('password2','togglePass2');

    // Barra de fuerza de contraseña
    const pass = $('#password');
    const strengthBar = $('#strengthBar');
    const strengthLabel = $('#strengthLabel');
    function calcStrength(p){
      let score = 0;
      if(!p) return 0;
      if(p.length >= 8) score++;
      if(/[A-Z]/.test(p)) score++;
      if(/[a-z]/.test(p)) score++;
      if(/[0-9]/.test(p)) score++;
      if(/[^A-Za-z0-9]/.test(p)) score++;
      return score; // 0..5
    }
    pass.addEventListener('input', (e)=>{
      const v = e.target.value;
      const score = calcStrength(v);
      const pct = [0,20,40,60,80,100][score];
      strengthBar.style.width = pct + '%';
      strengthBar.className = 'progress-bar';
      const map = {
        0:['Débil',''],
        1:['Débil','bg-danger'],
        2:['Meh','bg-warning'],
        3:['Aceptable','bg-info'],
        4:['Firme','bg-primary'],
        5:['Sólida','bg-success']
      }
      const [txt, cls] = map[score];
      strengthBar.classList.add(cls);
      strengthLabel.querySelector('span').textContent = txt;
    });

    // Validación bootstrap + confirmación de contraseña
    const form = $('#registerForm');
    const pass2 = $('#password2');
    function passwordsMatch(){ return pass.value && pass.value === pass2.value; }

    pass2.addEventListener('input', ()=>{
      if(!passwordsMatch()){
        pass2.setCustomValidity('nope');
      } else {
        pass2.setCustomValidity('');
      }
    });

    form.addEventListener('submit', (event) => {
      if(!passwordsMatch()) pass2.setCustomValidity('nope');
      else pass2.setCustomValidity('');

      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
      } else {
        event.preventDefault(); // demo sin backend
        // Mostrar toast de OK
        const toast = new bootstrap.Toast(document.getElementById('toastOk'));
        toast.show();
        // scroll top
        window.scrollTo({ top: 0, behavior: 'smooth' });
        // reset suave
        setTimeout(()=> form.reset(), 600);
        strengthBar.style.width = '10%';
        strengthBar.className = 'progress-bar';
        strengthLabel.querySelector('span').textContent = 'Débil';
      }

      form.classList.add('was-validated');
    }, false);