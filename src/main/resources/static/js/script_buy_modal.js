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

async function submitOrder() {
    const userData = {
        fullName: document.getElementById('name').value,
        phone: document.getElementById('phone').value,
        address: document.getElementById('address').value,
        email: document.getElementById('email').value
    }

    try {
        //todo добавить в запрос выбранные размеры
        const response = await fetch(`/api/v1/edit-shop/add/order/product/${productId}`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                name: userData.fullName,
                phone: userData.phone,
                address: userData.address,
                email: userData.email,
            })
        });
        const result = await response;
        const text = await result.text();

        if (result.ok) {
            const response = await fetch(`/api/v1/edit-shop/update/product/${productId}/decrement/stock/`, {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify({ decrementBy: 1 })
            });
            const message = await response.text();
            console.log(message);
            alert(text);
        } else {
            alert("При оформлении заказа произошла ошибка");
        }

        closeBuyModal();
        location.href='/main/shop/';
    } catch(error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
}