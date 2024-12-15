document.getElementById("register-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Предотвращение стандартной отправки формы

    const formData = new FormData(event.target);
    const roles = Array.from(document.querySelectorAll("#register-form input[name='roles']:checked"))
        .map(input => input.value);

    const userData = {
        username: formData.get("username"),
        password: formData.get("password"),
        roles: roles
    };

    const response = await fetch("/api/v1/edit-users/register/", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userData)
    });

    const result = await response.text();
    alert(result);
    await loadUsers()
});

document.getElementById("edit-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const roles = Array.from(document.querySelectorAll("#edit-form input[name='edit-roles']:checked"))
        .map(input => input.value);

    const userData = {
        id: formData.get("edit-user-id"),
        username: formData.get("edit-username"),
        password: formData.get("edit-password"),
        roles: roles
    };

    const response = await fetch("/api/v1/edit-users/edit/", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userData)
    });

    const result = await response.text();
    alert(result);
    await loadUsers()
});

async function loadUsers() {
    const response = await fetch('/api/v1/edit-users/get-all/');
    const users = await response.json();
    const tbody = document.querySelector('#user-list tbody');
    tbody.innerHTML = ''; // Очистить таблицу

    users.forEach(user => {
        const row = document.createElement('tr');
        row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.roles}</td>
                    <td><img src="/img/edit_users/trash-icon.png" alt="Delete" class="delete-icon" data-id="${user.id}"></td>
                `;
        tbody.appendChild(row);
    });

    // Добавить обработчики на иконки удаления
    document.querySelectorAll('.delete-icon').forEach(icon => {
        icon.addEventListener('click', deleteUser);
    });
}

async function deleteUser(event) {
    const userId = event.target.getAttribute('data-id');

    const response = await fetch(`/api/v1/edit-users/delete/${userId}`, {
        method: 'DELETE'
    });

    const result = await response.text()
    alert(result)
    await loadUsers();
}

document.addEventListener('DOMContentLoaded', loadUsers);