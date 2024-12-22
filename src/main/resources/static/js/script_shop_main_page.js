async function loadProducts() {
    const productsList = document.getElementById('products-list');
    try {
        const response = await fetch(`/api/v1/edit-shop/get-all/products/`);
        const products = await response.json();

        productsList.innerHTML = '';

        for (const product of products) {
            try {
                const s3Response = await fetch(`/api/v1/s3bucket-storage/nikonshop-s3-database/download/${product.productPhotoLinks[0]}`);
                const photo = await s3Response.blob();

                const productItem = document.createElement('div');
                productItem.classList.add('product-item'); // Класс для стилизации карточки

                productItem.innerHTML = `
                    <img src="${URL.createObjectURL(photo)}">
                    <p>${product.title}</p>
                    <p class="price">${product.price} ₽</p>
                    <div class="button-container">
<!--                      <button id="buy-button" class="add-to-cart-btn" onclick="openBuyModal(${product.id})"></button>-->
                      <a class="about-btn" href="/main/shop/product-page/${product.id}">О товаре</a>
                    </div>
                `;

                productsList.appendChild(productItem);
            } catch (error) {
                alert(`Произошла ошибка: ${error.message}`);
            }
        }

        // const buyButtons = document.querySelectorAll('.add-to-cart-btn');
        // buyButtons.forEach((button, index) => {
        //     if (products[index].quantityInStock === 0) {
        //         button.textContent = "Нет в наличии";
        //     } else {
        //         button.textContent = "Купить";
        //     }
        // });
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