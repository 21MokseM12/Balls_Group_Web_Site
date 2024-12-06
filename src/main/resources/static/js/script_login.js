function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.style.display = "none";
}

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