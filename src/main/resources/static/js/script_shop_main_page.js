document.addEventListener("DOMContentLoaded", () => {
    const carousel = document.querySelector(".carousel");
    const items = document.querySelectorAll(".carousel-item");
    const prevButton = document.querySelector(".carousel-btn.prev");
    const nextButton = document.querySelector(".carousel-btn.next");

    let currentIndex = 0;

    // Функция для обновления позиции карусели
    const updateCarousel = () => {
        const itemWidth = items[0].offsetWidth; // Ширина одного элемента
        const offset = -currentIndex * itemWidth;
        carousel.style.transform = `translateX(${offset}px)`;
    };

    // Обработчик кнопки "Назад"
    prevButton.addEventListener("click", () => {
        currentIndex = (currentIndex > 0) ? currentIndex - 1 : items.length - 1;
        updateCarousel();
    });

    // Обработчик кнопки "Вперед"
    nextButton.addEventListener("click", () => {
        currentIndex = (currentIndex < items.length - 1) ? currentIndex + 1 : 0;
        updateCarousel();
    });

    // Обновляем ширину на случай изменения размеров экрана
    window.addEventListener("resize", () => {
        updateCarousel();
    });

    // Устанавливаем стартовое положение
    updateCarousel();
});

document.addEventListener('DOMContentLoaded', () => {
    const preloader = document.getElementById('preloader');
    const mainContent = document.getElementById('main-content');

    async function loadCategories() {
        const categoryList = document.getElementById('category-list');
        try {
            const response = await fetch('/api/v1/edit-shop/get-all/categories/');
            const categories = await response.json();

            categoryList.innerHTML = ''; // Очищаем список перед загрузкой
            if (categories.length === 0) {
                categoryList.innerHTML = '<p>Категории появятся совсем скоро =)</p>';
                return;
            }

            categories.forEach(category => {
                const categoryItem = document.createElement('div');
                categoryItem.classList.add('category-card'); // Класс для стилизации карточки

                categoryItem.innerHTML = `
                <div class="category-value">
                    <a href="/main/shop/category-page/${category.id}">${category.category}</a>
                </div>
            `;
                categoryList.appendChild(categoryItem);
            });
        } catch (error) {
            console.error(error);
            categoryList.innerHTML = '<p>Ошибка загрузки категорий. Попробуйте позже.</p>';
        }
    }

    loadCategories();

    setTimeout(() => {
        preloader.classList.add('hidden');
        mainContent.classList.remove('hidden');
        mainContent.classList.add('show');
        document.body.style.overflow = 'auto';
    }, 2500);
});