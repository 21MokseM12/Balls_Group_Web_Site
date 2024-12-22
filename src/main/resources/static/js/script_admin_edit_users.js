async function loadUsers() {
    const response = await fetch('/api/v1/edit-users/get-all/accounts/');
    const users = await response.json();

    const tableBody = document.querySelector('#users-table tbody');
    tableBody.innerHTML = '';

    users.forEach(user => {
        const row = document.createElement('tr');
        const roles = Array.from(user.roles).map(role => role.role).join(", ");
        row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${roles}</td>
                    <td>
                        <button onclick="editUser(${user.id})">Редактировать</button>
                        <button onclick="deleteUser(${user.id})">Удалить</button>
                    </td>
                `;
        tableBody.appendChild(row);
    });
}

async function editUser(userId) {
    document.getElementById('modal-user').style.display = 'block';
    const response = await fetch(`/api/v1/edit-users/get/account/${userId}`);
    const user = await response.json();
    document.getElementById('edit-user-id').value = user.id;
    document.getElementById('edit-user-username').value = user.username;

    const rolesResponse = await fetch(`/api/v1/edit-users/get-all/roles/`);
    const roles = await rolesResponse.json();

    const rolesSelect = document.getElementById('edit-user-roles');
    rolesSelect.innerHTML = '';

    roles.forEach(role => {
        const label = document.createElement("label");
        label.className = "role-option";

        const input = document.createElement("input");
        input.type = "checkbox";
        input.name = "roles";
        input.value = role.role;

        const customCheckbox = document.createElement("span");
        customCheckbox.className = "custom-checkbox";

        label.appendChild(input);
        label.appendChild(customCheckbox);
        label.appendChild(document.createTextNode(` ${role.role}`));

        rolesSelect.appendChild(label);
    });
}

async function saveEditedUser() {
    const editedAccount = {
        id: document.getElementById('edit-user-id').value,
        username: document.getElementById('edit-user-username').value,
        password: document.getElementById('edit-user-password').value,
        roles: []
    }

    const roleCheckboxes = document.querySelectorAll('#edit-user-roles input[type="checkbox"]:checked');
    editedAccount.roles = Array.from(roleCheckboxes).map(checkbox => checkbox.value);

    if (!editedAccount.username) {
        alert('Никнейм не может быть пустым');
        return;
    }

    if (!editedAccount.password) {
        alert('Пароль не может быть пустым');
        return;
    }

    if (editedAccount.roles.length === 0) {
        alert('Выберите хотя бы одну роль');
        return;
    }

    try {
        const response = await fetch('/api/v1/edit-users/update/account/', {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                id: editedAccount.id,
                username: editedAccount.username,
                password: editedAccount.password,
                roles: editedAccount.roles
            })
        });
        const result = await response.text();
        alert(result);
        await loadUsers()
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }

    closeModalUser()
}

async function deleteUser(userId) {
    try {
        const response = await fetch(`/api/v1/edit-users/delete/account/${userId}`, {
            method: 'DELETE'
        });
        const result = await response.text();
        alert (result)
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
    await loadUsers()
}

async function toggleRegisterUserForm() {
    const form = document.getElementById('user-form');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';

    const rolesResponse = await fetch('/api/v1/edit-users/get-all/roles/');
    const roles = await rolesResponse.json();
    const rolesSelect = document.getElementById('roles-options');
    rolesSelect.innerHTML = '';

    roles.forEach(role => {
        const label = document.createElement("label");
        label.className = "role-option";

        const input = document.createElement("input");
        input.type = "checkbox";
        input.name = "role";
        input.value = role.role;

        const customCheckbox = document.createElement("span");
        customCheckbox.className = "custom-checkbox";

        label.appendChild(input);
        label.appendChild(customCheckbox);
        label.appendChild(document.createTextNode(` ${role.role}`));

        rolesSelect.appendChild(label);
    });
}

async function registerUser() {
    const userData = {
        username: document.getElementById('username').value.trim(),
        password: document.getElementById('password').value.trim(),
        roles: []
    };

    const roleCheckboxes = document.querySelectorAll('#roles-options input[type="checkbox"]:checked');
    userData.roles = Array.from(roleCheckboxes).map(checkbox => checkbox.value);

    // Валидация данных
    if (!userData.username) {
        alert('Никнейм не может быть пустым');
        return;
    }

    if (!userData.password) {
        alert('Пароль не может быть пустым');
        return;
    }

    if (userData.roles.length === 0) {
        alert('Выберите хотя бы одну роль');
        return;
    }

    try {
        const response = await fetch('/api/v1/edit-users/add/account/', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                username: userData.username,
                password: userData.password,
                roles: userData.roles
            })
        });
        const result = await response.text();
        alert(result);
        await loadUsers()
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
}

function closeModalUser() {
    document.getElementById('modal-user').style.display = 'none';
}

async function loadRoles() {
    const response = await fetch('/api/v1/edit-users/get-all/roles/');
    const roles = await response.json();

    const tableBody = document.querySelector('#roles-table tbody');
    tableBody.innerHTML = '';

    roles.forEach(role => {
        const row = document.createElement('tr');
        row.innerHTML = `
                    <td>${role.id}</td>
                    <td>${role.role}</td>
                    <td>
                        <button onclick="editRole(${role.id})">Редактировать</button>
                        <button onclick="deleteRole(${role.id})">Удалить</button>
                    </td>
                `;
        tableBody.appendChild(row);
    });
}

async function editRole(roleId) {
    document.getElementById('modal-role').style.display = 'block';
    const response = await fetch(`/api/v1/edit-users/get/role/${roleId}`);
    const role = await response.json();
    document.getElementById('edit-role-id').value = role.id;
    document.getElementById('edit-role-value').value = role.role;
}

async function saveEditedRole() {
    const editedRole = {
        id: document.getElementById('edit-role-id').value,
        role: document.getElementById('edit-role-value').value,
    }

    if (!editedRole.role) {
        alert('Роль не может быть пустой');
        return;
    }

    try {
        const response = await fetch('/api/v1/edit-users/update/role/', {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                id: editedRole.id,
                role: editedRole.role
            })
        });
        const result = await response.text();
        alert(result);
        await loadRoles()
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }

    closeModalRole()
}

async function deleteRole(roleId) {
    try {
        const response = await fetch(`/api/v1/edit-users/delete/role/${roleId}`, {
            method: 'DELETE'
        });
        const result = await response.text();
        alert (result)
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
    await loadRoles()
}

async function toggleAddRoleForm() {
    const form = document.getElementById('roles-form');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

async function addRole() {
    const roleData = {
        role: document.getElementById('role').value.trim(),
    };

    // Валидация данных
    if (!roleData.role) {
        alert('Роль не может быть пустой');
        return;
    }

    try {
        const response = await fetch('/api/v1/edit-users/add/role/', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                role: roleData.role
            })
        });
        const result = await response.text();
        alert(result);
        await loadRoles()
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
}

function closeModalRole() {
    document.getElementById('modal-role').style.display = 'none';
}