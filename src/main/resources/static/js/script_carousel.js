//todo поправить прокручивание
function initializeCarousel() {
    const carousel = document.querySelector(".carousel");
    const items = document.querySelectorAll(".product-item");
    const prevButton = document.querySelector(".carousel-btn.prev");
    const nextButton = document.querySelector(".carousel-btn.next");

    let currentIndex = 0;

    // Функция для обновления позиции карусели
    const updateCarousel = () => {
        const itemWidth = items[0].offsetWidth; // Ширина карточки
        const gap = 40; // Отступ между карточками
        const totalWidth = itemWidth + gap; // Общая ширина одного элемента с учетом отступа
        const offset = -(currentIndex) * totalWidth; // Расчёт смещения
        carousel.style.transform = `translateX(${offset}px)`; // Применение смещения
    };

    // Обработчик кнопки "Назад"
    prevButton.addEventListener("click", () => {
        // Уменьшаем индекс, если он больше 0
        currentIndex = (currentIndex > 0) ? currentIndex - 1 : items.length - 1;
        updateCarousel();
    });

    // Обработчик кнопки "Вперед"
    nextButton.addEventListener("click", () => {
        // Увеличиваем индекс, если он меньше длины карусели
        currentIndex = (currentIndex < items.length - 1) ? currentIndex + 1 : 0;
        updateCarousel();
    });

    // Обновляем ширину на случай изменения размеров экрана
    window.addEventListener("resize", () => {
        updateCarousel();
    });

    // Устанавливаем стартовое положение
    updateCarousel();
}