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

    const response = await fetch("/api/v1/add-user/register/", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userData)
    });

    const result = await response.text();
    alert(result);
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

    const response = await fetch("/api/v1/add-user/edit/", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userData)
    });

    const result = await response.text();
    alert(result);
});