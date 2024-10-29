document.addEventListener('DOMContentLoaded', function() {
    const links = document.querySelectorAll('.sidebar a');
    const sections = document.querySelectorAll('main section');

    links.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const targetId = this.getAttribute('href').substring(1);

            // Ocultar todas las secciones y remover la clase active de todos los enlaces
            sections.forEach(section => section.classList.remove('active'));
            links.forEach(link => link.classList.remove('active'));

            // Mostrar la sección objetivo y marcar el enlace como activo
            document.getElementById(targetId).classList.add('active');
            this.classList.add('active');
        });
    });
});
