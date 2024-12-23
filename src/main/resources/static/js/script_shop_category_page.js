async function loadProductsByCategory() {
    try {
        const response = await fetch(`/api/v1/edit-shop/get-all-by/product/category/${categoryId}`);
        const products = await response.json();

        const categoryResponse = await fetch(`/api/v1/edit-shop/get/category/${categoryId}`);
        const category = await categoryResponse.json();

        const categoryLabel = document.getElementById('category-name');
        categoryLabel.innerHTML = `
            <h1>${category.category}</h1>
        `;

        document.title = category.category;

        const productList = document.getElementById('products-list');
        productList.innerHTML = '';
        for (const product of products) {
            try {
                const firstPhotoResponse = await fetch(`/api/v1/s3bucket-storage/nikonshop-s3-database/download/${product.productPhotoLinks[0]}`);
                const firstPhoto = await firstPhotoResponse.blob();
                const photoUrl = URL.createObjectURL(firstPhoto);

                const productItem = document.createElement('div');
                productItem.classList.add('product-item');

                productItem.innerHTML = `
                    <img src="${photoUrl}">
                    <p>${product.title}</p>
                    <p class="price">${product.price} ₽</p>
                    <div class="button-container">
<!--                      <button id="buy-button" class="add-to-cart-btn" onclick="openBuyModal(${product.id})"></button>-->
                      <a class="about-btn" href="/main/shop/product-page/${product.id}">О товаре</a>
                    </div>
                `;

                productList.appendChild(productItem);
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
        alert(`Произошла ошибка: ${error.message}`);
    }

    initializeCarousel();
}

document.addEventListener('DOMContentLoaded', () => {
    const preloader = document.getElementById('preloader');
    const mainContent = document.getElementById('main-content');

    loadCategories();
    loadProductsByCategory();

    setTimeout(() => {
        preloader.classList.add('hidden');
        mainContent.classList.remove('hidden');
        mainContent.classList.add('show');
        document.body.style.overflow = 'auto';
    }, 2500);
});