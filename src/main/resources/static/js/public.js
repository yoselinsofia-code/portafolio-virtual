document.addEventListener('DOMContentLoaded', function () {
  const observer = new IntersectionObserver(function (entries) {
    entries.forEach(function (entry) {
      if (entry.isIntersecting) {
        entry.target.style.opacity = '1';
        entry.target.style.transform = 'translateY(0)';
      }
    });
  }, { threshold: 0.12 });

  document.querySelectorAll('.semana-publica-card, .actividad-publica-card').forEach(function (el, i) {
    el.style.opacity = '0';
    el.style.transform = 'translateY(24px)';
    el.style.transition = 'opacity .6s ease ' + (i % 4) * 0.08 + 's, transform .6s ease ' + (i % 4) * 0.08 + 's';
    observer.observe(el);
  });

  // ===== Conteo animado de la franja de estadísticas del hero =====
  document.querySelectorAll('.num-animado').forEach(function (el) {
    const objetivo = parseInt(el.textContent.replace(/\D/g, ''), 10);
    if (isNaN(objetivo)) return;
    let actual = 0;
    const incremento = Math.max(objetivo / 40, 1);
    el.textContent = '0';
    function animar() {
      actual += incremento;
      if (actual >= objetivo) {
        el.textContent = objetivo;
        return;
      }
      el.textContent = Math.floor(actual);
      requestAnimationFrame(animar);
    }
    requestAnimationFrame(animar);
  });
});
