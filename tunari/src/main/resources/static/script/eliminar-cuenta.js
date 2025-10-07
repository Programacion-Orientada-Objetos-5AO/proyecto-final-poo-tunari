document.addEventListener('DOMContentLoaded', () => {
  const btn = document.getElementById('btnEliminar');
  const barra = document.getElementById('barraProgreso');
  const texto = document.getElementById('btnTexto');
  const estado = document.getElementById('estado');
  const passInput = document.getElementById('confirmPassword');
  const textInput = document.getElementById('confirmText');
  const togglePass = document.getElementById('togglePass');
  const confirmModalEl = document.getElementById('confirmModal');
  const confirmModal = new bootstrap.Modal(confirmModalEl);
  const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');
  const mainContainer = document.querySelector('main');

  let timer;
  let progreso = 0;
  let confirmarEliminacion = false;

  // Mostrar / ocultar contraseña
  togglePass.addEventListener('click', () => {
    const type = passInput.getAttribute('type') === 'password' ? 'text' : 'password';
    passInput.setAttribute('type', type);
    togglePass.classList.toggle('bi-eye');
    togglePass.classList.toggle('bi-eye-slash');
  });

  // Mantener presionado
  btn.addEventListener('mousedown', () => {
    if (textInput.value.trim() !== 'ELIMINAR') {
      errorFeedback('Escribí "ELIMINAR" para poder borrar tu cuenta.');
      return;
    }

    if (passInput.value.trim() === "") {
      errorFeedback('Ingresá tu contraseña para continuar.');
      return;
    }

    progreso = 0;
    barra.style.width = '0%';
    texto.textContent = 'Manteniendo...';
    estado.textContent = '';
    estado.style.color = 'var(--color-texto)';

    timer = setInterval(() => {
      progreso += 2;
      barra.style.width = progreso + '%';

      if (progreso >= 100) {
        clearInterval(timer);
        confirmModal.show(); // ✨ muestra el modal
      }
    }, 50);
  });

  btn.addEventListener('mouseup', cancelar);
  btn.addEventListener('mouseleave', cancelar);

  function cancelar() {
    clearInterval(timer);
    if (progreso < 100) {
      barra.style.width = '0%';
      texto.textContent = 'Mantener presionado para eliminar';
    }
  }

  // ✅ Cuando el usuario confirma dentro del modal
  confirmDeleteBtn.addEventListener('click', () => {
    confirmarEliminacion = true;
    confirmModal.hide(); // cierra el modal
  });

  // 🚀 Cuando el modal termina de cerrarse visualmente
  confirmModalEl.addEventListener('hidden.bs.modal', () => {
    if (confirmarEliminacion) {
      // Esperar al final real de la transición de opacidad
      confirmModalEl.addEventListener(
        'transitionend',
        () => {
          eliminarCuenta();
          confirmarEliminacion = false;
        },
        { once: true } // solo se ejecuta una vez
      );
    }
  });

  function eliminarCuenta() {
    texto.textContent = 'Cuenta eliminada existosamente';
    estado.textContent = 'Cuenta eliminada permanentemente';
    estado.style.color = 'var(--ok)';
    btn.disabled = true;
    barra.style.backgroundColor = 'var(--ok)';

    // ✨ fade-out del contenido principal
    mainContainer.classList.add('fade-out');

    // Ahora sí: contador y redirección
    let segundos = 3;
    estado.innerHTML = `Cuenta eliminada permanentemente, esperamos verte pronto.<br><small>Redirigiendo a Tunari en ${segundos}...</small>`;

    const countdown = setInterval(() => {
      segundos--;
      if (segundos > 0) {
        estado.innerHTML = `Cuenta eliminada permanentemente, esperamos verte pronto.<br><small>Redirigiendo a Tunari en ${segundos}...</small>`;
      } else {
        clearInterval(countdown);
        window.location.href = "../index.html";
      }
    }, 1000);
  }

  // 🚨 Error visual
  function errorFeedback(mensaje) {
    estado.textContent = mensaje;
    estado.style.color = 'var(--bad)';
    btn.classList.add('shake');
    setTimeout(() => btn.classList.remove('shake'), 400);
  }
});
