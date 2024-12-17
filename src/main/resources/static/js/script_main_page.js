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

    async function loadTours() {
        const tourList = document.getElementById('tour-list');
        try {
            const response = await fetch('/api/v1/edit-concerts/get-all/concerts/');
            const concerts = await response.json();

            tourList.innerHTML = ''; // Очищаем список перед загрузкой
            if (concerts.length === 0) {
                tourList.innerHTML = '<p>Скоро анонс наших ближайших концертов.</p>';
                return;
            }

            concerts.forEach(concert => {
                const concertItem = document.createElement('div');
                concertItem.classList.add('concert-card'); // Класс для стилизации карточки

                concertItem.innerHTML = `
                <div class="concert-date">
                    <h2>${new Date(concert.date).toLocaleDateString('ru-RU', { day: 'numeric', month: 'long', year: 'numeric' })}</h2>
                </div>
                <div class="concert-info">
                    <h2>${concert.city}</h2>
                    <p>${concert.concertVenue}</p>
                    <p>${concert.description}</p>
                </div>
                <div class="concert-buttons">
                    <a href="${concert.ticketsLink}" target="_blank" class="button">Билеты</a>
                    <a href="${concert.meetingLink}" target="_blank" class="social-icon">
                        <img src="/img/main_page/vk-logo.png" alt="VK встреча">
                    </a>
                </div>
            `;

                tourList.appendChild(concertItem);
            });
        } catch (error) {
            console.error(error);
            tourList.innerHTML = '<p>Ошибка загрузки концертов. Попробуйте позже.</p>';
        }
    }

    loadTours();

    setTimeout(() => {
        preloader.classList.add('hidden');
        mainContent.classList.remove('hidden');
        mainContent.classList.add('show');
        document.body.style.overflow = 'auto';
    }, 2500);
});