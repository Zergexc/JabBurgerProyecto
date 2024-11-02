document.addEventListener('DOMContentLoaded', function() {
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
        document.getElementById('alertContainer').appendChild(alertDiv);

        setTimeout(() => {
            alertDiv.remove();
        }, 3000);
    }

    // Función para actualizar la tabla
    function updateTable() {
        fetch('/admin/api/cliente')
            .then(response => response.json())
            .then(clientes => {
                const tbody = document.querySelector('#clientesTable tbody');
                tbody.innerHTML = '';
                
                clientes.forEach(cliente => {
                    tbody.innerHTML += `
                        <tr>
                            <td>${cliente.id}</td>
                            <td>${cliente.nombre}</td>
                            <td>${cliente.apellido}</td>
                            <td>${cliente.email}</td>
                            <td>${cliente.celular}</td>
                            <td>${cliente.dni}</td>
                            <td>
                                <button onclick="editClient(${cliente.id})" class="btn btn-primary btn-sm">
                                    <i class="fas fa-edit"></i> Editar
                                </button>
                                <button onclick="deleteClient(${cliente.id})" class="btn btn-danger btn-sm">
                                    <i class="fas fa-trash"></i> Eliminar
                                </button>
                            </td>
                        </tr>
                    `;
                });
            })
            .catch(error => {
                showAlert('Error al actualizar la tabla: ' + error.message, 'danger');
            });
    }

    // Función para agregar cliente
    window.addClient = function() {
        document.getElementById('modalTitle').textContent = 'Agregar Cliente';
        document.getElementById('clientForm').reset();
        document.getElementById('clientId').value = '';
        openModal();
    }

    // Función para editar cliente
    window.editClient = function(id) {
        document.getElementById('modalTitle').textContent = 'Editar Cliente';

        fetch(`/admin/api/cliente/${id}`)
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text || 'Error al cargar el cliente');
                    });
                }
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
    window.deleteClient = function(id) {
        if (confirm('¿Está seguro de eliminar este cliente?')) {
            fetch(`/admin/api/cliente/${id}`, {
                    method: 'DELETE',
                    headers: {
                        'Accept': 'application/json'
                    }
                })
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(text => {
                            throw new Error(text || 'Error al eliminar el cliente');
                        });
                    }
                    showAlert('Cliente eliminado exitosamente');
                    updateTable();
                })
                .catch(error => {
                    showAlert(error.message, 'danger');
                });
        }
    }

    // Event listener para el formulario
    const clientForm = document.getElementById('clientForm');
    if (clientForm) {
        clientForm.addEventListener('submit', function(e) {
            e.preventDefault();
            if (this.checkValidity() === false) {
                e.stopPropagation();
                this.classList.add('was-validated');
                return;
            }

            const clientId = document.getElementById('clientId').value;
            const clientData = {
                nombre: document.getElementById('nombre').value,
                apellido: document.getElementById('apellido').value,
                email: document.getElementById('email').value,
                celular: document.getElementById('celular').value,
                dni: document.getElementById('dni').value
            };

            const url = clientId ? `/admin/api/cliente/${clientId}` : '/admin/api/cliente';
            const method = clientId ? 'PUT' : 'POST';

            fetch(url, {
                    method: method,
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(clientData)
                })
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(text => {
                            throw new Error(text || 'Error al procesar la solicitud');
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    showAlert(clientId ? 'Cliente actualizado exitosamente' : 'Cliente agregado exitosamente');
                    closeModal();
                    updateTable();
                })
                .catch(error => {
                    showAlert(error.message, 'danger');
                });
        });
    }
});