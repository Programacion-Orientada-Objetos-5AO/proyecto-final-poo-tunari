document.addEventListener("DOMContentLoaded", () => {

  /* ===============================
     MENÚ DE USUARIO / SESIÓN
  =============================== */
  const menuEl   = document.getElementById('userMenu');   // el DIV del menú
  const iconEl   = document.getElementById('toggleMenu'); // el botón/ícono

  function actualizarMenu() {
      const loggedIn = localStorage.getItem('usuarioLogueado') === 'true';
      if (loggedIn) {
          const nombre = localStorage.getItem('usuarioNombre') || 'Usuario';
          menuEl.innerHTML = `
              <a href="dashboards/dashboard-usuario.html" class="user-menu-item">Dashboard</a>
              <a href="#" class="user-menu-item" id="logoutBtn">Cerrar sesión</a>
          `;
      } else {
          menuEl.innerHTML = `
              <a href="login/iniciar-sesion.html" class="user-menu-item">Iniciar sesión</a>
              <a href="registro/registrarse.html" class="user-menu-item">Registrarse</a>
          `;
      }
      menuEl.style.display = 'none';
  }
  actualizarMenu();

  // Toggle menú usuario
  iconEl?.addEventListener('click', () => {
      const isOpen = getComputedStyle(menuEl).display !== 'none';
      menuEl.style.display = isOpen ? 'none' : 'flex';
  });

  // Cerrar menú si clickeo afuera
  document.addEventListener('click', (e) => {
      if (!menuEl.contains(e.target) && !iconEl.contains(e.target)) {
          menuEl.style.display = 'none';
      }
  });

  // Logout
  document.addEventListener('click', (e) => {
      if (e.target && e.target.id === 'logoutBtn') {
          e.preventDefault();
          localStorage.removeItem('usuarioLogueado');
          actualizarMenu();
          menuEl.style.display = 'none';
          location.href = 'index.html';
      }
  });

  /* ===============================
     BÚSQUEDA (Overlay)
  =============================== */
  const overlay  = document.getElementById('searchOverlay');
  const btnOpen  = document.getElementById('searchToggle');
  const btnClose = document.getElementById('searchClose');
  const input    = document.getElementById('searchInput');

  function openSearch(){
      overlay?.classList.add('open');
      document.body.classList.add('noscroll');
      setTimeout(() => input && input.focus(), 50);
  }
  function closeSearch(){
      overlay?.classList.remove('open');
      document.body.classList.remove('noscroll');
  }

  btnOpen?.addEventListener('click', (e) => { e.preventDefault(); openSearch(); });
  btnClose?.addEventListener('click', closeSearch);
  document.addEventListener('keydown', (e) => { if(e.key === 'Escape') closeSearch(); });
  overlay?.addEventListener('click', (e) => { if(e.target === overlay) closeSearch(); });

  /* ===============================
     PARTICULAS
  =============================== */
  const canvas = document.getElementById("particles");
  if (canvas) {
    const ctx = canvas.getContext("2d");
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    let particles = [];
    for (let i = 0; i < 60; i++) {
      particles.push({
        x: Math.random() * canvas.width,
        y: Math.random() * canvas.height,
        r: Math.random() * 2 + 1,
        dx: (Math.random() - 0.5) * 1.5,
        dy: (Math.random() - 0.5) * 1.5
      });
    }

    function drawParticles() {
      ctx.clearRect(0,0,canvas.width,canvas.height);
      ctx.fillStyle = "rgba(255,255,255,0.7)";
      particles.forEach(p => {
        ctx.beginPath();
        ctx.arc(p.x, p.y, p.r, 0, Math.PI*2);
        ctx.fill();
        p.x += p.dx;
        p.y += p.dy;
        if(p.x<0||p.x>canvas.width) p.dx*=-1;
        if(p.y<0||p.y>canvas.height) p.dy*=-1;
      });
      requestAnimationFrame(drawParticles);
    }
    drawParticles();

    // Recalcular al redimensionar ventana
    window.addEventListener("resize", () => {
      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;
    });
  }

  /* ===============================
     ANIMACIONES AL SCROLL
  =============================== */
  const faders = document.querySelectorAll(".fade-up, .slide-left, .slide-right");
  const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add("appear");
        observer.unobserve(entry.target);
      }
    });
  },{ threshold: 0.2 });

  faders.forEach(f => observer.observe(f));

  /* ===============================
     CONTADORES (STATS)
  =============================== */
  const counters = document.querySelectorAll(".counter");
  const speed = 80;

  counters.forEach(counter => {
    const update = () => {
      const target = +counter.getAttribute("data-target");
      const count = +counter.innerText;
      const inc = Math.ceil(target / speed);

      if(count < target) {
        counter.innerText = count + inc;
        setTimeout(update, 40);
      } else {
        counter.innerText = target.toLocaleString();
      }
    };
    const obs = new IntersectionObserver(entries=>{
      if(entries[0].isIntersecting){
        update();
        obs.disconnect();
      }
    },{threshold:0.5});
    obs.observe(counter);
  });

}); // DOMContentLoaded END

document.addEventListener("DOMContentLoaded", () => {
  const car = document.querySelector(".car");
  const text = document.querySelector(".hero-text");

  car.addEventListener("animationend", () => {
    text.classList.add("show");
  });
});
