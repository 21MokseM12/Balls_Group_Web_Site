async function loadOrders() {
    try {
        const response = await fetch(`/api/v1/edit-shop/get-all/orders/`);
        const orders = await response.json();

        const tableBody = document.querySelector('#product-table tbody');
        tableBody.innerHTML = '';

        orders.forEach(order => {
            const row = document.createElement('tr');
            row.innerHTML = `
                    <td>${order.id}</td>
                    <td>${order.orderedProduct.title}</td>
                    <td>${order.orderedProduct.price}</td>
                    <td>${order.orderedProduct.size.size}</td>
                    <td>${order.customer.name}</td>
                    <td>${order.customer.phone}</td>
                    <td>${order.customer.address}</td>
                    <td>
                        <button onclick="deleteOrder(${order.id})">Удалить</button>
                    </td>
                `;
            tableBody.appendChild(row);
        });
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
}

async function deleteOrder(orderId) {
    try {
        const response = await fetch(`/api/v1/edit-shop/delete/order/${orderId}`, {
            method: 'DELETE'
        });
        const result = await response.text();

        alert(result);
        await loadOrders();
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }

}