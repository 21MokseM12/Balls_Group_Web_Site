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