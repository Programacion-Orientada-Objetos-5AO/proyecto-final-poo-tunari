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
  const speed = 120;

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

    // Animar los contadores
    const counters = document.querySelectorAll('.counter');
    counters.forEach(counter => {
    const target = +counter.getAttribute('data-target');
    let count = 0;
    const updateCount = () => {
        if (count < target) {
        count += Math.ceil(target / 200); 
        counter.textContent = count > target ? target : count;
        requestAnimationFrame(updateCount);
        }
    };
    updateCount();
    });

    // Animaciones con scroll
const faders = document.querySelectorAll('.fade-up, .slide-left, .slide-right');

const options = {
  threshold: 0.2, // porcentaje visible para disparar animación
};

const appearOnScroll = new IntersectionObserver((entries, observer) => {
  entries.forEach(entry => {
    if (!entry.isIntersecting) return;
    entry.target.classList.add('appear');
    observer.unobserve(entry.target);
  });
}, options);

faders.forEach(el => {
  appearOnScroll.observe(el);
});

  const sections = document.querySelectorAll('.info-section');

  const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('appear');
      }
    });
  }, { threshold: 0.2 });

  sections.forEach(section => {
    observer.observe(section);
  });

  