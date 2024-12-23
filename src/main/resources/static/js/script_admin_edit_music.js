async function loadAlbums() {
    const response = await fetch('/api/v1/edit-music/get-all/albums/');
    const albums = await response.json();

    const tableBody = document.querySelector('#album-table tbody');
    tableBody.innerHTML = '';

    albums.forEach(album => {
        const row = document.createElement('tr');
        row.innerHTML = `
                    <td>${album.id}</td>
                    <td>${album.title}</td>
                    <td>${album.description}</td>
                    <td>
                        <button onclick="editAlbum(${album.id})">Редактировать</button>
                        <button onclick="deleteAlbum(${album.id})">Удалить</button>
                    </td>
                `;
        tableBody.appendChild(row);
    });
}

async function editAlbum(albumId) {
    document.getElementById('modal-album').style.display = 'block';

    const response = await fetch(`/api/v1/edit-music/get/album/${albumId}`);
    const album = await response.json();

    const s3Response = await fetch(`/api/v1/s3bucket-storage/balls-group-storage-music/download/${album.logoFileName}`)
    const albumLogo = await s3Response.blob();
    const imageUrl = URL.createObjectURL(albumLogo);

    document.getElementById('edit-album-id').value = album.id;
    document.getElementById('edit-album-title').value = album.title;
    document.getElementById('edit-album-description').value = album.description;
    document.getElementById('edit-album-listen').value = album.listenLink;
    document.getElementById('edit-album-picture').src = imageUrl;
}

async function saveEditedAlbum() {
    const editedAlbum = {
        id: document.getElementById('edit-album-id').value,
        title: document.getElementById('edit-album-title').value,
        description: document.getElementById('edit-album-description').value,
        listenLink: document.getElementById('edit-album-listen').value
    }

    const formData = new FormData();
    const fileInput = document.getElementById("edit-album-logo");
    const file = fileInput.files[0];

    // Валидация данных
    if (!editedAlbum.title) {
        alert('Название не может быть пустым');
        return;
    }

    if (!editedAlbum.listenLink) {
        alert('Ссылка на медиа не может не быть указана');
        return;
    }

    if (!file) {
        alert('Альбом не может не иметь обложки');
        return;
    }

    formData.append('file', file);

    try {
        const s3response = await fetch('/api/v1/s3bucket-storage/balls-group-storage-music/upload/', {
            method: 'POST',
            body: formData
        })

        const response = await fetch('/api/v1/edit-music/update/album/', {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                id: editedAlbum.id,
                title: editedAlbum.title,
                description: editedAlbum.description,
                listenLink: editedAlbum.listenLink,
                logoFileName: file.name
            })
        });

        const result = await response.text();
        await s3response.text();
        alert(result);

        await loadAlbums()
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }

    closeModalAlbum()
}

async function deleteAlbum(albumId) {
    try {

        const getResponse = await fetch(`/api/v1/edit-music/get/album/${albumId}`, {
            method: 'GET'
        });
        const album = await getResponse.json();

        const s3Response = fetch(`/api/v1/s3bucket-storage/balls-group-storage-music/delete/${album.logoFileName}`, {
            method: 'DELETE'
        })
        await s3Response;

        const response = await fetch(`/api/v1/edit-music/delete/album/${albumId}`, {
            method: 'DELETE'
        });
        const result = await response.text();
        alert(result)
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
    await loadAlbums()
}

async function toggleRegisterAlbumForm() {
    const form = document.getElementById('album-form');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

async function addAlbum() {
    const albumData = {
        title: document.getElementById('title').value.trim(),
        description: document.getElementById('description').value.trim(),
        listenLink: document.getElementById('listen').value.trim(),
    };

    const formData = new FormData();
    const fileInput = document.querySelector('input[type="file"]');
    const file = fileInput.files[0];

    // Валидация данных
    if (!albumData.title) {
        alert('Название не может быть пустым');
        return;
    }

    if (!albumData.listenLink) {
        alert('Ссылка на медиа не может не быть указана');
        return;
    }

    if (!file) {
        alert('Альбом не может не иметь обложки');
        return;
    }

    if ((file.size / 1024) > 1000) {
        alert('Размер файла превышает 1 Мбайт')
        return;
    }

    formData.append('file', file);

    try {
        const s3response = await fetch('/api/v1/s3bucket-storage/balls-group-storage-music/upload/', {
            method: 'POST',
            body: formData
        })

        if (s3response.ok) {
            const response = await fetch('/api/v1/edit-music/add/album/', {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify({
                    title: albumData.title,
                    description: albumData.description,
                    logoFile: albumData.logoFile,
                    listenLink: albumData.listenLink,
                    logoFileName: file.name
                })
            });

            await s3response;
            const result = await response.text();
            alert(result);
        } else {
            const result = await s3response.text();
            alert(result);
        }
        await loadAlbums()
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
}

function closeModalAlbum() {
    document.getElementById('modal-album').style.display = 'none';
}