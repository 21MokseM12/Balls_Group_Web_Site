async function loadConcerts() {
    const response = await fetch('/api/v1/edit-concerts/get-all/concerts/');
    const concerts = await response.json();

    const tableBody = document.querySelector('#concert-table tbody');
    tableBody.innerHTML = '';

    concerts.forEach(concert => {
        const row = document.createElement('tr');
        row.innerHTML = `
                    <td>${concert.id}</td>
                    <td>${concert.city}</td>
                    <td>${concert.description}</td>
                    <td>${concert.concertVenue}</td>
                    <td>${concert.date}</td>
                    <td>
                        <button onclick="editConcert(${concert.id})">Редактировать</button>
                        <button onclick="deleteConcert(${concert.id})">Удалить</button>
                    </td>
                `;
        tableBody.appendChild(row);
    });
}

async function editConcert(concertId) {
    document.getElementById('modal-concert').style.display = 'block';
    const response = await fetch(`/api/v1/edit-concerts/get/concert/${concertId}`);
    const concert = await response.json();
    document.getElementById('edit-concert-id').value = concert.id;
    document.getElementById('edit-concert-city').value = concert.city;
    document.getElementById('edit-concert-description').value = concert.description;
    document.getElementById('edit-concert-venue').value = concert.concertVenue;
    document.getElementById('edit-concert-date').value = concert.date;
    document.getElementById('edit-concert-tickets').value = concert.ticketsLink;
    document.getElementById('edit-concert-meeting').value = concert.meetingLink;
}

async function saveEditedConcert() {
    const editedConcert = {
        id: document.getElementById('edit-concert-id').value,
        city: document.getElementById('edit-concert-city').value,
        description: document.getElementById('edit-concert-description').value,
        venue: document.getElementById('edit-concert-venue').value,
        date: document.getElementById('edit-concert-date').value,
        tickets: document.getElementById('edit-concert-tickets').value,
        meeting: document.getElementById('edit-concert-meeting').value
    }

    if (!editedConcert.city) {
        alert('Город не может быть пустым');
        return;
    }

    if (!editedConcert.description) {
        alert('Описание не может быть пустым');
        return;
    }

    if (!editedConcert.venue) {
        alert('Площадка не может не быть указана');
        return;
    }

    if (!editedConcert.date) {
        alert('Время не может не быть указано');
        return;
    }

    if (!editedConcert.tickets) {
        alert('Ссылка на билеты не может не быть указана');
        return;
    }

    try {
        const response = await fetch('/api/v1/edit-concerts/update/concert/', {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                id: editedConcert.id,
                city: editedConcert.city,
                description: editedConcert.description,
                concertVenue: editedConcert.venue,
                date: editedConcert.date,
                ticketsLink: editedConcert.tickets,
                meetingLink: editedConcert.meeting
            })
        });
        const result = await response.text();
        alert(result);
        await loadConcerts()
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }

    closeModalConcert()
}

async function deleteConcert(concertId) {
    try {
        const response = await fetch(`/api/v1/edit-concerts/delete/concert/${concertId}`, {
            method: 'DELETE'
        });
        const result = await response.text();
        alert(result)
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
    await loadConcerts()
}

async function toggleRegisterConcertForm() {
    const form = document.getElementById('concert-form');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

async function addConcert() {
    const concertData = {
        city: document.getElementById('city').value.trim(),
        description: document.getElementById('description').value.trim(),
        concertVenue: document.getElementById('venue').value.trim(),
        date: document.getElementById('date').value.trim(),
        ticketsLink: document.getElementById('tickets').value.trim(),
        meetingLink: document.getElementById('meeting').value.trim()
    };

    // Валидация данных
    if (!concertData.city) {
        alert('Город не может быть пустым');
        return;
    }

    if (!concertData.description) {
        alert('Описание не может быть пустым');
        return;
    }

    if (!concertData.concertVenue) {
        alert('Площадка не может не быть указана');
        return;
    }

    if (!concertData.date) {
        alert('Время не может не быть указано');
        return;
    }

    if (!concertData.ticketsLink) {
        alert('Ссылка на билеты не может не быть указана');
        return;
    }

    try {
        const response = await fetch('/api/v1/edit-concerts/add/concert/', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                city: concertData.city,
                description: concertData.description,
                concertVenue: concertData.concertVenue,
                date: concertData.date,
                ticketsLink: concertData.ticketsLink,
                meetingLink: concertData.meetingLink
            })
        });
        const result = await response.text();
        alert(result);
        await loadConcerts()
    } catch (error) {
        alert(`Произошла ошибка: ${error.message}`);
    }
}

function closeModalConcert() {
    document.getElementById('modal-concert').style.display = 'none';
}