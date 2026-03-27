document.addEventListener('DOMContentLoaded', () => {
  const btn = document.getElementById('btnEliminar');
  const barra = document.getElementById('barraProgreso');
  const texto = document.getElementById('btnTexto');
  const estado = document.getElementById('estado');
  const passInput = document.getElementById('confirmPassword');
  const textInput = document.getElementById('confirmText');
  const togglePass = document.getElementById('togglePass');
  const mainContainer = document.querySelector('main');
  const card = document.querySelector('.glass-card-borrar-cuenta');
  const overlay = document.getElementById('redirectOverlay');
  const overlaySeconds = document.getElementById('overlaySeconds');
  let countdownInterval;


  let timer;
  let progreso = 0;

  // 👁️ Mostrar / ocultar contraseña
  togglePass.addEventListener('click', () => {
    const type = passInput.getAttribute('type') === 'password' ? 'text' : 'password';
    passInput.setAttribute('type', type);
    togglePass.classList.toggle('bi-eye');
    togglePass.classList.toggle('bi-eye-slash');
  });

  // 🧨 Mantener presionado para confirmar
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
        confirmarEliminacion();
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

  // 🟩 Confirmación visual + eliminación
  function confirmarEliminacion() {
    btn.classList.add('confirm-vibe');
    btn.style.backgroundColor = 'var(--ok)';
    btn.style.borderColor = 'var(--ok)';
    texto.textContent = '¡Confirmado!';
    barra.style.backgroundColor = 'var(--ok)';

    setTimeout(() => eliminarCuenta(), 600);
  }

function eliminarCuenta() {
  texto.textContent = 'Cuenta eliminada exitosamente';
  estado.textContent = ''; // ya no lo usamos para el contador
  btn.disabled = true;
  barra.style.backgroundColor = 'var(--ok)';

  // 1) Fade solo de la card
  if (card) card.classList.add('fade-out');

  // 2) Mostrar overlay con contador visible
  overlay.hidden = false;

  // 3) Contador robusto
  let segundos = 3;
  overlaySeconds.textContent = segundos;

  countdownInterval = setInterval(() => {
    segundos--;
    if (segundos > 0) {
      overlaySeconds.textContent = segundos;
    } else {
      clearInterval(countdownInterval);
      window.location.href = "../../templates/index.html"; // ajustá si tu ruta es otra
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
