function openBuyModal() {
    const modal = document.getElementById('buy-modal-window');
    modal.style.display = "block";
    const overlay = document.getElementById('modal-overlay');
    overlay.style.display = "block";
}

function closeBuyModal() {
    document.getElementById('buy-modal-window').style.display = "none";
    document.getElementById('modal-overlay').style.display = "none";
}

//todo поправить, чтобы при ошибке в бд в одном запросе не сохранялись остальные запросы
async function submitOrder() {
    const productData = {
        title: document.getElementById('product-title').textContent,
        price: document.getElementById('product-price').textContent.split(" ")[0],
        size: document.querySelector('.size-button.active')?.textContent
    }

    const sizeContainer = document.getElementById("sizes");
    if (productData.size === undefined && sizeContainer.style.display !== "none") {
        alert("Выберите размер");
        return;
    }

    const userData = {
        fullName: document.getElementById('name').value,
        phone: document.getElementById('phone').value,
        address: document.getElementById('address').value,
        email: document.getElementById('email').value
    }

    if (!userData.fullName) {
        alert("Введите свое ФИО");
        return;
    }

    if (!userData.address) {
        alert("Введите адрес доставки");
        return;
    }

    // Регулярные выражения для проверки телефона и email
    const phoneRegex = /^[+]?[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$/; // Для международных и локальных номеров
    const emailRegex = /^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$/;

    if (!phoneRegex.test(userData.phone) && !emailRegex.test(userData.email)) {
        alert('Введите корректный телефон или email!');
        return;
    }

    try {
        const response = await fetch(`/api/v1/edit-shop/add/order/customer/`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                name: userData.fullName,
                phone: userData.phone,
                address: userData.address,
                email: userData.email
            })
        });
        const customerResult = await response;
        const customerId = await customerResult.text();

        if (customerResult.ok) {
            const response = await fetch(`/api/v1/edit-shop/add/order/product/`, {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify({
                    title: productData.title,
                    price: productData.price,
                    size: productData.size
                })
            });
            const productResult = await response;
            const orderedProductId = await productResult.text();

            if (productResult.ok) {
                const response = await fetch(`/api/v1/edit-shop/add/order/`, {
                    method: 'POST',
                    headers: {
                        'Content-type': 'application/json'
                    },
                    body: JSON.stringify({
                        orderedProductId: orderedProductId,
                        customerId: customerId
                    })
                });
                const orderResult = await response;
                const result = await orderResult.text();

                if (orderResult.ok) {
                    const response = await fetch(`/api/v1/edit-shop/update/product/${productId}/decrement/stock/`, {
                        method: 'POST',
                        headers: {
                            'Content-type': 'application/json'
                        },
                        body: JSON.stringify({ decrementBy: 1 })
                    });
                    const message = await response.text();
                    console.log(message);
                    alert(result);
                } else {
                    alert("При оформлении заказа произошла ошибка");
                }
            } else {
                alert("При оформлении заказа произошла ошибка");
            }
        } else {
            alert("При оформлении заказа произошла ошибка");
        }

        closeBuyModal();
        location.href='/main/shop/';
    } catch(error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
}