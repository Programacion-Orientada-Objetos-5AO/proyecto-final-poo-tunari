// ===== SweetAlert Tunari Pack =====

// ✅ Éxito
function alertaOk(mensaje = "Operación exitosa", titulo = "Listo!") {
  Swal.fire({
    icon: "success",
    title: titulo,
    toast: true,
    text: mensaje,
    background: "var(--color-secundario)",
    color: "var(--color-texto)",
    confirmButtonColor: "var(--color-acento)",
    iconColor: "var(--color-acento)",
    timer: 2500,
    showConfirmButton: false,
    timerProgressBar: true,
    customClass: {
      popup: 'alerta-tunari',
      icon: 'icono-tunari',
      title: 'titulo-tunari',
      confirmButton: 'boton-tunari'
    }
  });
}

// ⚠️ Advertencia
function alertaWarn(mensaje = "Completá todos los campos", titulo = "Atención") {
  Swal.fire({
    icon: "warning",
    title: titulo,
    text: mensaje,
    background: "var(--color-secundario)",
    color: "var(--color-texto)",
    confirmButtonColor: "var(--color-acento)",
    iconColor: "var(--color-acento)",
    customClass: {
      popup: 'alerta-tunari',
      icon: 'icono-tunari',
      title: 'titulo-tunari',
      confirmButton: 'boton-tunari'
    }
  });
}

// ❌ Error
function alertaError(mensaje = "Ocurrió un problema", titulo = "Error") {
  Swal.fire({
    icon: "error",
    title: titulo,
    text: mensaje,
    background: "var(--color-secundario)",
    color: "var(--color-texto)",
    confirmButtonColor: "var(--bad)",
    iconColor: "var(--bad)",
    customClass: {
      popup: 'alerta-tunari',
      icon: 'icono-tunari',
      title: 'titulo-tunari',
      confirmButton: 'boton-tunari'
    }
  });
}

// 🔄 Confirmación
function alertaConfirmar(texto = "¿Querés continuar?", accionConfirmada) {
  Swal.fire({
    icon: "question",
    title: "Confirmar acción",
    text: texto,
    background: "var(--color-secundario)",
    color: "var(--color-texto)",
    showCancelButton: true,
    confirmButtonText: "Sí",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "var(--color-acento)",
    cancelButtonColor: "var(--bad)",
    iconColor: "var(--color-acento)",
    customClass: {
      popup: 'alerta-tunari',
      icon: 'icono-tunari',
      title: 'titulo-tunari',
      confirmButton: 'boton-tunari'
    }
  }).then((result) => {
    if (result.isConfirmed && typeof accionConfirmada === "function") {
      accionConfirmada();
    }
  });
}
