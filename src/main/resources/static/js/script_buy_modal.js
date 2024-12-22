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

    const userData = {
        fullName: document.getElementById('name').value,
        phone: document.getElementById('phone').value,
        address: document.getElementById('address').value,
        email: document.getElementById('email').value
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