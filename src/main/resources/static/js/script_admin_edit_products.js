// products functions
//todo добавить размеры
async function loadProducts() {
    const response = await fetch('/api/v1/edit-shop/get-all/products/');
    const products = await response.json();

    const tableBody = document.querySelector('#product-table tbody');
    tableBody.innerHTML = '';

    products.forEach(product => {
        const row = document.createElement('tr');
        row.innerHTML = `
                    <td>${product.id}</td>
                    <td>${product.title}</td>
                    <td>${product.description}</td>
                    <td>${product.price}</td>
                    <td>${product.quantityInStock}</td>
                    <td>${product.category.category}</td>
                    <td>
                        <button onclick="editProduct(${product.id})">Редактировать</button>
                        <button onclick="deleteProduct(${product.id})">Удалить</button>
                    </td>
                `;
        tableBody.appendChild(row);
    });
}

async function toggleProductForm() {
    const form = document.getElementById('product-form');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';

    const categoryResponse = await fetch('/api/v1/edit-shop/get-all/categories/');
    const categories = await categoryResponse.json();
    const categorySelect = document.getElementById('categorySelect');
    categorySelect.innerHTML = '';

    categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.category;
        option.textContent = category.category;
        categorySelect.appendChild(option);
    });

    const sizesResponse = await fetch('/api/v1/edit-shop/get-all/clothing-sizes/');
    const sizes = await sizesResponse.json();
    const sizesSelect = document.getElementById('size-options');
    sizesSelect.innerHTML = '';

    sizes.forEach(size => {
        const label = document.createElement("label");
        label.className = "size-option";

        const input = document.createElement("input");
        input.type = "checkbox";
        input.name = "sizes";
        input.value = size.size;

        const customCheckbox = document.createElement("span");
        customCheckbox.className = "custom-checkbox";

        label.appendChild(input);
        label.appendChild(customCheckbox);
        label.appendChild(document.createTextNode(` ${size.size}`));

        sizesSelect.appendChild(label);
    });
}

async function addProduct() {
    // Собираем данные из формы
    const productData = {
        title: document.getElementById('title').value.trim(),
        description: document.getElementById('description').value.trim(),
        price: parseFloat(document.getElementById('price').value),
        quantityInStock: parseInt(document.getElementById('quantityInStock').value),
        category: document.getElementById('categorySelect').value,
        isSizeable: document.querySelector('input[name="isSizeable"]:checked')?.value === 'true',
        sizes: [],
        photos: []
    };

    // Проверяем, выбраны ли размеры, если товар имеет размеры
    if (productData.isSizeable) {
        const sizeCheckboxes = document.querySelectorAll('#size-options input[type="checkbox"]:checked');
        productData.sizes = Array.from(sizeCheckboxes).map(checkbox => checkbox.value);
    }

    // Собираем файлы фотографий
    const photosInput = document.getElementById('productPhotos');
    productData.photos = Array.from(photosInput.files);

    // Валидация данных
    if (!productData.title) {
        alert('Название товара не может быть пустым');
        return;
    }

    if (isNaN(productData.price) || productData.price <= 0) {
        alert('Цена должна быть положительным числом.');
        return;
    }

    if (isNaN(productData.quantityInStock) || productData.quantityInStock < 0) {
        alert('Количество на складе должно быть числом больше или равно нулю.');
        return;
    }

    if (!productData.category) {
        alert('Выберите категорию товара.');
        return;
    }

    if (productData.isSizeable && productData.sizes.length === 0) {
        alert('Выберите хотя бы один размер.');
        return;
    }

    if (productData.photos.length === 0) {
        alert('Добавьте хотя бы одну фотографию');
        return;
    }

    const formData = new FormData();
    productData.photos.forEach(photo => {
        formData.append('files', photo);
    });

    try {
        const s3Response = await fetch('/api/v1/s3bucket-storage/nikonshop-s3-database/upload-all/', {
            method: 'POST',
            body: formData
        })
        await s3Response;

        if (s3Response.ok) {
            try {
                const response = await fetch('/api/v1/edit-shop/add/product/', {
                    method: 'POST',
                    headers: {
                        'Content-type': 'application/json'
                    },
                    body: JSON.stringify({
                        title: productData.title,
                        description: productData.description,
                        price: productData.price,
                        quantityInStock: productData.quantityInStock,
                        category: productData.category,
                        clothingSize: productData.sizes,
                        productPhotoLinks: Array.from(productData.photos).map(photo => photo.name)
                    })
                });
                const result = await response.text();
                alert(result);
                await loadProducts()
            } catch (error) {
                alert(`Произошла ошибка: ${error.message}`);
            }
        }
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
}

async function editProduct(productId) {
    document.getElementById('modal-product').style.display = 'block';
    const response = await fetch(`/api/v1/edit-shop/get/product/${productId}`);
    const product = await response.json();
    document.getElementById('edit-product-id').value = product.id;
    document.getElementById('edit-product-title').value = product.title;
    document.getElementById('edit-product-description').value = product.description;
    document.getElementById('edit-product-price').value = product.price;
    document.getElementById('edit-product-stock').value = product.quantityInStock;
    document.getElementById('edit-product-category').value = product.category.category;

    const sizesResponse = await fetch(`/api/v1/edit-shop/get-all/clothing-sizes/`);
    const sizes = await sizesResponse.json();

    const sizesSelect = document.getElementById('edit-product-sizes');

    if (sizes.length === 0 || !sizes) {
        const label = document.querySelector('label[for="edit-product-sizes"]');
        if (label) {
            label.style.display = 'none';
        }
    } else {
        sizesSelect.innerHTML = '';

        sizes.forEach(size => {
            const label = document.createElement("label");
            label.className = "size-option";

            const input = document.createElement("input");
            input.type = "checkbox";
            input.name = "sizes";
            input.value = size.size;

            const customCheckbox = document.createElement("span");
            customCheckbox.className = "custom-checkbox";

            label.appendChild(input);
            label.appendChild(customCheckbox);
            label.appendChild(document.createTextNode(` ${size.size}`));

            sizesSelect.appendChild(label);
        });
    }
}

//todo поправить редактирование фотографий: при редактировании в бд фото не изменяются
async function saveEditedProduct() {
    const editedProduct = {
        id: document.getElementById('edit-product-id').value,
        title: document.getElementById('edit-product-title').value,
        description: document.getElementById('edit-product-description').value,
        price: document.getElementById('edit-product-price').value,
        quantityInStock: document.getElementById('edit-product-stock').value,
        category: document.getElementById('edit-product-category').value,
        sizes: [],
        photos: []
    }

    const sizeCheckboxes = document.querySelectorAll('#edit-product-sizes input[type="checkbox"]:checked');
    editedProduct.sizes = Array.from(sizeCheckboxes).map(checkbox => checkbox.value);

    const photosInput = document.getElementById('edit-product-photos');
    editedProduct.photos = Array.from(photosInput.files);

    if (!editedProduct.title) {
        alert('Название товара не может быть пустым');
        return;
    }

    if (isNaN(editedProduct.price) || editedProduct.price <= 0) {
        alert('Цена должна быть положительным числом.');
        return;
    }

    if (isNaN(editedProduct.quantityInStock) || editedProduct.quantityInStock < 0) {
        alert('Количество на складе должно быть числом больше или равно нулю.');
        return;
    }

    if (!editedProduct.category) {
        alert('Выберите категорию товара.');
        return;
    }

    if (editedProduct.sizes.length === 0) {
        alert('Выберите хотя бы один размер.');
        return;
    }

    if (editedProduct.photos.length === 0) {
        alert('Добавьте хотя бы одну фотографию');
        return;
    }

    const formData = new FormData();
    editedProduct.photos.forEach(photo => {
        formData.append('files', photo);
    });

    try {
        const s3Response = await fetch('/api/v1/s3bucket-storage/nikonshop-s3-database/upload-all/', {
            method: 'POST',
            body: formData
        })
        await s3Response;

        if (s3Response.ok) {
            const response = await fetch('/api/v1/edit-shop/update/product/', {
                method: 'PUT',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify({
                    id: editedProduct.id,
                    title: editedProduct.title,
                    description: editedProduct.description,
                    price: editedProduct.price,
                    quantityInStock: editedProduct.quantityInStock,
                    category: editedProduct.category,
                    clothingSize: editedProduct.sizes,
                    productPhotoLinks: Array.from(editedProduct.photos).map(photo => photo.name)
                })
            });
            const result = await response.text();

            alert(result);
            await loadProducts()
        }
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
    closeModalProduct()
}

async function deleteProduct(productId) {
    try {
        const getResponse = await fetch(`/api/v1/edit-shop/get/product/${productId}`);
        const product = await getResponse.json();

        for (const link of product.productPhotoLinks) {
            const response = await fetch(`/api/v1/s3bucket-storage/nikonshop-s3-database/delete/${link}`, {
                method: 'DELETE'
            });
            await response;
        }

        const response = await fetch(`/api/v1/edit-shop/delete/product/${productId}`, {
            method: 'DELETE'
        });
        const result = await response.text();
        alert (result)
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
    await loadProducts()
}

function closeModalProduct() {
    document.getElementById('modal-product').style.display = 'none';
}

// categories functions
async function loadCategories() {
    const response = await fetch('/api/v1/edit-shop/get-all/categories/');
    const categories = await response.json();
    const tableBody = document.querySelector('#category-table tbody');
    tableBody.innerHTML = '';

    categories.forEach(category => {
        const row = document.createElement('tr');
        row.innerHTML = `
                    <td>${category.id}</td>
                    <td>${category.category}</td>
                    <td>
                        <button onclick="editCategory(${category.id})">Редактировать</button>
                        <button onclick="deleteCategory(${category.id})">Удалить</button>
                    </td>
                `;
        tableBody.appendChild(row);
    });
}

function toggleCategoryForm() {
    const form = document.getElementById('category-form');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

async function addCategory() {
    const categoryInput = document.getElementById('category');
    const categoryName = categoryInput.value.trim();
    if (categoryName) {
        const categoryData = {
            category: categoryName
        }
        try {
            const response = await fetch('/api/v1/edit-shop/add/category/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(categoryData)
            });

            const result = await response.text();
            alert(result);
            await loadCategories()
        } catch (error) {
            alert(`Произошла ошибка: ${error.message}`);
        }
    } else {
        alert('Вы не указали название новой категории');
    }
}

async function editCategory(categoryId) {
    document.getElementById('modal-category').style.display = 'block';
    const response = await fetch(`/api/v1/edit-shop/get/category/${categoryId}`);
    const category = await response.json();
    document.getElementById('edit-category-id').value = category.id;
    document.getElementById('edit-category-value').value = category.category;
}

async function saveEditedCategory() {
    const categoryId = document.getElementById('edit-category-id').value;
    const categoryName = document.getElementById('edit-category-value').value;

    if (categoryName) {
        const updatedSize = {
            id: categoryId,
            category: categoryName,
        };

        try {
            const response = await fetch(`/api/v1/edit-shop/update/category/`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updatedSize)
            });

            const result = await response.text();
            alert(result);
            closeModalCategory();
            await loadCategories()
        } catch (error) {
            alert(`Произошла ошибка: ${error.message}`);
        }
    } else {
        alert("Поле категории пусто")
    }
}

async function deleteCategory(categoryId) {
    try {
        const response = await fetch(`/api/v1/edit-shop/delete/category/${categoryId}`, {
            method: 'DELETE'
        });
        const result = await response.text();
        alert (result)
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
    await loadCategories()
}

function closeModalCategory() {
    document.getElementById('modal-category').style.display = 'none';
}

//clothing sizes functions
async function loadSizes() {
    const response = await fetch('/api/v1/edit-shop/get-all/clothing-sizes/');
    const sizes = await response.json();
    const tableBody = document.querySelector('#size-table tbody');
    tableBody.innerHTML = '';

    sizes.forEach(size => {
        const row = document.createElement('tr');
        row.innerHTML = `
                    <td>${size.id}</td>
                    <td>${size.size}</td>
                    <td>
                        <button onclick="editSize(${size.id})">Редактировать</button>
                        <button onclick="deleteSize(${size.id})">Удалить</button>
                    </td>
                `;
        tableBody.appendChild(row);
    });
}

async function toggleSizeForm() {
    const form = document.getElementById('size-form');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

async function addSize() {
    const sizeInput = document.getElementById('size');
    const sizeName = sizeInput.value.trim();
    if (sizeName) {
        const sizeData = {
            size: sizeName
        }
        try {
            const response = await fetch('/api/v1/edit-shop/add/clothing-size/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(sizeData)
            });

            const result = await response.text();
            alert(result);
            await loadSizes()
        } catch (error) {
            alert(`Произошла ошибка: ${error.message}`);
        }
    } else {
        alert('Вы не указали размер одежды');
    }
}

async function editSize(sizeId) {
    document.getElementById('modal-clothing-size').style.display = 'block';
    const response = await fetch(`/api/v1/edit-shop/get/clothing-size/${sizeId}`);
    const size = await response.json();
    document.getElementById('edit-size-id').value = size.id;
    document.getElementById('edit-size-value').value = size.size;
}

async function saveEditedSize() {
    const sizeId = document.getElementById('edit-size-id').value;
    const sizeName = document.getElementById('edit-size-value').value;

    if (sizeName) {
        const updatedSize = {
            id: sizeId,
            size: sizeName,
        };

        try {
            const response = await fetch(`/api/v1/edit-shop/update/clothing-size/`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updatedSize)
            });

            const result = await response.text();
            alert(result);
            closeModalClothingSize();
            await loadSizes()
        } catch (error) {
            alert(`Произошла ошибка: ${error.message}`);
        }
    } else {
        alert("Поле размера пусто")
    }
}

async function deleteSize(sizeId) {
    try {
        const response = await fetch(`/api/v1/edit-shop/delete/clothing-size/${sizeId}`, {
            method: 'DELETE'
        });
        const result = await response.text();
        alert (result)
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
    await loadSizes()
}

function closeModalClothingSize() {
    document.getElementById('modal-clothing-size').style.display = 'none';
}

function toggleSizeSelect(hasSize) {
    const sizeSelectContainer = document.getElementById('sizeSelectContainer');

    if (hasSize) {
        sizeSelectContainer.style.display = 'block'; // Показать селект
    } else {
        sizeSelectContainer.style.display = 'none'; // Скрыть селект
    }
}

//todo добавить вывод текущих заказов