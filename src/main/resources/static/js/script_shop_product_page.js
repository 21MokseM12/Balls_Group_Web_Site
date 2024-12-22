document.addEventListener('DOMContentLoaded', () => {
    const preloader = document.getElementById('preloader');
    const mainContent = document.getElementById('main-content');

    loadCategories();
    loadProductData().then(() => {
        setTimeout(() => {
            preloader.classList.add('hidden');
            mainContent.classList.remove('hidden');
            mainContent.classList.add('show');
            document.body.style.overflow = 'auto';
        }, 2500);
    });
});

async function loadProductData() {
    try {
        // Получаем данные о товаре
        const response = await fetch(`/api/v1/edit-shop/get/product/${productId}`);
        const product = await response.json();

        document.title = product.title;

        const photoUrls = await Promise.all(
            product.productPhotoLinks.map(async photo => {
                const response = await fetch(`/api/v1/s3bucket-storage/nikonshop-s3-database/download/${photo}`);
                const photoBlob = await response.blob();
                return URL.createObjectURL(photoBlob);
            })
        );

        const photosPlace = document.getElementById('image-gallery');
        photosPlace.innerHTML = `
            <div id="photos" class="thumbnails"></div>
            <div id="main-photo" class="main-photo">
                <img id="main-photo" src="${photoUrls[0]}" alt="Главное изображение">
            </div>
        `;

        const filteredPhotoUrls = photoUrls.slice(1);
        const mainPhotoWidth = document.getElementById('main-photo').width;

        const photoContainer = document.getElementById('photos');
        filteredPhotoUrls.forEach(photoUrl => {
            const img = new Image(
                mainPhotoWidth / filteredPhotoUrls.length,
                mainPhotoWidth / filteredPhotoUrls.length
            );
            img.src = photoUrl;
            img.alt = `Фото товара ${product.title}`;
            photoContainer.appendChild(img);
        });

        const productDetails = document.getElementById('product-details');
        productDetails.innerHTML = `
            <h1 id="product-title">${product.title}</h1>
            <div class="product-price">
                <span id="product-price">${product.price} ₽</span>
            </div>
            <div id="sizes" class="sizes"></div>
            <button id="buy-button" class="buy-button" onclick="openBuyModal(${product.id})"></button>
            <div class="description">
                <p>${product.description}</p>
            </div>
        `;

        const buyContainer = document.getElementById('buy-button');
        if (product.quantityInStock === 0) {
            buyContainer.textContent = "Нет в наличии";
            buyContainer.disabled = true;
        } else {
            buyContainer.textContent = "Купить";
        }

        const sizeContainer = document.getElementById('sizes');
        if (product.clothingSize.length > 0) {
            product.clothingSize.forEach(size => {
                const button = document.createElement("button");
                button.classList.add('size-button');
                button.textContent = size.size;
                button.addEventListener("click", function() {
                    this.classList.toggle("active");
                });

                sizeContainer.appendChild(button);
            });

            const sizeButtons = document.querySelectorAll('.size-button');
            sizeButtons.forEach(button => {
                button.addEventListener("click", () => {
                    sizeButtons.forEach(btn => btn.classList.remove('active'));
                    button.classList.add('active');
                });
            });
        } else {
            sizeContainer.style.display = "none";
        }
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
}