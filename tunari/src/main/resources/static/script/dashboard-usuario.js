// ===== Dashboard Tunari =====

// Ejemplo: guardar datos del perfil
document.addEventListener("DOMContentLoaded", () => {
const formPerfil = document.getElementById("formEditarPerfil");

if (formPerfil) {
  formPerfil.addEventListener("submit", (e) => {
    e.preventDefault();

    const nombre = document.getElementById("nombrePerfil").value.trim();
    const apellido = document.getElementById("apellidoPerfil").value.trim();
    const email = document.getElementById("emailPerfil").value.trim();
    const telefono = document.getElementById("telefonoPerfil").value.trim();

    // Validación
    if (!nombre || !apellido || !email || !telefono) {
      alertaWarn("Por favor completá todos los campos antes de guardar.");
      return;
    }

    // Simular guardado exitoso
    console.log("Datos guardados:", { nombre, apellido, email, telefono });
    alertaOk("Datos actualizados correctamente.");
  });
}

// Ejemplo: cerrar sesión con confirmación
const botonCerrarSesion = document.querySelector(".bi-house-slash");
if (botonCerrarSesion) {
  botonCerrarSesion.addEventListener("click", (e) => {
    e.preventDefault();
    alertaConfirmar("¿Seguro que querés cerrar sesión?", () => {
      alertaOk("Sesión cerrada correctamente");
    });
  });
}
});

document.addEventListener("DOMContentLoaded", () => {
    const steps = document.querySelectorAll(".timeline-step-h");
    const progressLine = document.getElementById("progressLineH");
    const lastStep = document.querySelector(".timeline-step-h.active") 
                  || document.querySelector(".timeline-step-h.completed:last-of-type");
    if (lastStep) {
      const index = Array.from(steps).indexOf(lastStep);
      const total = steps.length - 1;
      const ratio = index / total * 100;
      progressLine.style.width = "0%";
      void progressLine.offsetWidth; // forzar reflow
      setTimeout(() => {
        progressLine.style.width = `${ratio}%`;
      }, 100);
    }
  });
  document.querySelectorAll('.remove-fav-btn').forEach(btn => {
    btn.addEventListener('click', e => {
      e.preventDefault();
      e.stopPropagation();
const card = btn.closest('.feed-card-favoritos');
if (card) {
  card.classList.add('fade-out-card');
  setTimeout(() => card.remove(), 300);
  alertaOk("Auto eliminado de favoritos");
}    });
  });
  const trackingData = {
    "ABC123": { vehiculo: "Ford Bronco Sport Badlands", estado: 2 },
    "DEF456": { vehiculo: "Mustang Dark Horse", estado: 4 },
    "GHI789": { vehiculo: "Toyota Corolla", estado: 6 }
  };
  document.getElementById("buscarTracking").addEventListener("click", () => {
    const input = document.getElementById("trackingInput").value.trim().toUpperCase();
    const steps = document.querySelectorAll(".timeline-step-h");
    steps.forEach(step => {
      step.classList.remove("completed", "active");
    });
    if (trackingData[input]) {
      const data = trackingData[input];
      document.getElementById("vehiculoNombre").textContent = data.vehiculo;
      const maxStep = data.estado;
      steps.forEach((step, i) => {
        if (i + 1 < maxStep) {
          step.classList.add("completed");
        } else if (i + 1 === maxStep) {
          step.classList.add("active");
        }
      });
    } else {
      document.getElementById("vehiculoNombre").textContent = "—";
      alertaError("Numero de tracking no encontrado", "Error")
    }
    const lastStep = document.querySelector(".timeline-step-h.active") 
                  || document.querySelector(".timeline-step-h.completed:last-of-type");
    const progressLine = document.getElementById("progressLineH");
    if (lastStep) {
      const index = Array.from(steps).indexOf(lastStep);
      const total = steps.length - 1;
      const ratio = index / total * 100;
      progressLine.style.width = "0%";
      void progressLine.offsetWidth;
      setTimeout(() => {
        progressLine.style.width = `${ratio}%`;
      }, 100);
    }
  });