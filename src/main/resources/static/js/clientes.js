// Funciones para el modal
function openModal() {
    $('#clientModal').modal('show');
}

function closeModal() {
    $('#clientModal').modal('hide');
}

// Función para mostrar mensajes
function showAlert(message, type = 'success') {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.role = 'alert';
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    `;
    document.querySelector('.content-area').insertAdjacentElement('afterbegin', alertDiv);

    setTimeout(() => {
        alertDiv.remove();
    }, 3000);
}

// Función para agregar cliente
function addClient() {
    document.getElementById('modalTitle').textContent = 'Agregar Cliente';
    document.getElementById('clientForm').reset();
    document.getElementById('clientId').value = '';
    openModal();
}

// Función para editar cliente
function editClient(id) {
    document.getElementById('modalTitle').textContent = 'Editar Cliente';

    fetch(`/admin/api/clientes/${id}`)
        .then(response => {
            if (!response.ok) throw new Error('Error al cargar el cliente');
            return response.json();
        })
        .then(client => {
            document.getElementById('clientId').value = client.id;
            document.getElementById('nombre').value = client.nombre;
            document.getElementById('apellido').value = client.apellido;
            document.getElementById('email').value = client.email;
            document.getElementById('celular').value = client.celular;
            document.getElementById('dni').value = client.dni;
            openModal();
        })
        .catch(error => {
            showAlert(error.message, 'danger');
        });
}

// Función para eliminar cliente
function deleteClient(id) {
    if (confirm('¿Está seguro de eliminar este cliente?')) {
        fetch(`/admin/api/clientes/${id}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (!response.ok) throw new Error('Error al eliminar el cliente');
                showAlert('Cliente eliminado exitosamente');
                location.reload();
            })
            .catch(error => {
                showAlert(error.message, 'danger');
            });
    }
}

// Función para buscar clientes
function searchClients() {
    const searchTerm = document.getElementById('searchInput').value;
    window.location.href = `/admin/clientes?search=${encodeURIComponent(searchTerm)}`;
}

// Manejar la búsqueda con Enter
document.getElementById('searchInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        searchClients();
    }
});

// Manejar el formulario
document.getElementById('clientForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const clientId = document.getElementById('clientId').value;
    const clientData = {
        nombre: document.getElementById('nombre').value,
        apellido: document.getElementById('apellido').value,
        email: document.getElementById('email').value,
        celular: document.getElementById('celular').value,
        dni: document.getElementById('dni').value
    };

    const url = clientId ? `/admin/api/clientes/${clientId}` : '/admin/api/clientes';
    const method = clientId ? 'PUT' : 'POST';

    fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(clientData)
        })
        .then(response => {
            if (!response.ok) throw new Error('Error al guardar el cliente');
            return response.json();
        })
        .then(() => {
            showAlert(clientId ? 'Cliente actualizado exitosamente' : 'Cliente agregado exitosamente');
            closeModal();
            location.reload();
        })
        .catch(error => {
            showAlert(error.message, 'danger');
        });
});