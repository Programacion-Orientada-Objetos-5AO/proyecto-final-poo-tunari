// Fondo animado (partículas)
function initBackground() {
  if(window.particlesJS){
    particlesJS.load('background', 'particles.json', function() {
      console.log('callback - particles.js config loaded');
    });
  }
}

// Inicializar fondo animado
initBackground();