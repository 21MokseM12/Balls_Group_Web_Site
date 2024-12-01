document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        document.querySelector(this.getAttribute('href')).scrollIntoView({
            behavior: 'smooth'
        });
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const preloader = document.getElementById('preloader');
    const mainContent = document.getElementById('main-content');

    setTimeout(() => {
        preloader.classList.add('hidden');
        mainContent.classList.remove('hidden');
        mainContent.classList.add('show');
        document.body.style.overflow = 'auto';
    }, 2500);
});


