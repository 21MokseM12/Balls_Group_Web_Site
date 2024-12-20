function initializeCarousel() {
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
}

async function loadProducts() {
    const productsList = document.getElementById('products-list');
    try {
        const response = await fetch(`/api/v1/edit-shop/get-all/products/`);
        const products = await response.json();

        productsList.innerHTML = '';
        if (productsList.length === 0) {
            productsList.innerHTML = `<p>Товары появятся совсем скоро =)</p>`;
            return;
        }

        for (const product of products) {
            const s3Response = await fetch(`/api/v1/s3bucket-storage/nikonshop-s3-database/download/${product.productPhotoLinks[0]}`);
            const photo = await s3Response.blob();

            const productItem = document.createElement('div');
            productItem.classList.add('carousel-item'); // Класс для стилизации карточки

            productItem.innerHTML = `
                <img src="${URL.createObjectURL(photo)}">
                <p>${product.title}</p>
                <p class="price">${product.price} ₽</p>
                <div class="button-container">
                  <button class="add-to-cart-btn">Добавить в корзину</button>
                  <a class="about-btn" href="/main/shop/product-page/${product.id}">Подробнее...</a>
                </div>
            `;
            productsList.appendChild(productItem);
        }
    } catch (error) {
        console.error(error);
        productsList.innerHTML = '<p>Ошибка загрузки товаров. Попробуйте позже.</p>';
    }

    initializeCarousel();
}

document.addEventListener('DOMContentLoaded', () => {
    const preloader = document.getElementById('preloader');
    const mainContent = document.getElementById('main-content');

    loadCategories();
    loadProducts();

    setTimeout(() => {
        preloader.classList.add('hidden');
        mainContent.classList.remove('hidden');
        mainContent.classList.add('show');
        document.body.style.overflow = 'auto';
    }, 2500);
});