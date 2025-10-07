    const $ = s => document.querySelector(s);
    const input = $('#password');
    const toggle = $('#togglePass');
    toggle.addEventListener('click', ()=>{
      const isPwd = input.type === 'password';
      input.type = isPwd ? 'text' : 'password';
      toggle.classList.toggle('bi-eye');
      toggle.classList.toggle('bi-eye-slash');
    });

    const loginForm = $('#loginForm');
    loginForm.addEventListener('submit', function(event) {
      event.preventDefault();
      const email = $('#email').value.trim();
      const password = $('#password').value;

      const demoEmail = 'demo@tunari.test';
      const demoPassword = 'tunari123';

      if(email === demoEmail && password === demoPassword) {
        localStorage.setItem('usuarioLogueado', 'true');
        localStorage.setItem('usuarioNombre', 'Usuario Demo');
        localStorage.setItem('usuarioEmail', email);
        window.location.href = '../index.html';
      } else {
        alert("Credenciales incorrectas. Usá demo@tunari.test / tunari123");
      }
    });