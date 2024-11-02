document.addEventListener('DOMContentLoaded', function() {
    // Botón para agregar nuevo producto
    const addButton = document.getElementById('add-product-btn');
    if (addButton) {
        addButton.addEventListener('click', function() {
            abrirModal();
        });
    }

    // Botones de editar
    const editButtons = document.querySelectorAll('.btn-editar');
    editButtons.forEach(button => {
        button.addEventListener('click', function() {
            const productoId = this.getAttribute('data-id');
            fetch(`/admin/productos/obtener/${productoId}`)
                .then(response => response.json())
                .then(producto => {
                    abrirModal(producto);
                })
                .catch(error => console.error('Error:', error));
        });
    });

    // Botones de eliminar
    const deleteButtons = document.querySelectorAll('.btn-eliminar');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            if (confirm('¿Estás seguro de que deseas eliminar este producto?')) {
                const productoId = this.getAttribute('data-id');
                window.location.href = `/admin/productos/eliminar/${productoId}`;
            }
        });
    });

    // Cerrar modal
    const closeButton = document.querySelector('.close');
    if (closeButton) {
        closeButton.addEventListener('click', cerrarModal);
    }

    // Cerrar modal al hacer clic fuera
    window.addEventListener('click', function(event) {
        const modal = document.getElementById('productoModal');
        if (event.target === modal) {
            cerrarModal();
        }
    });

    // Manejar envío del formulario
    const form = document.getElementById('productoForm');
    if (form) {
        form.addEventListener('submit', function(e) {
            e.preventDefault();

            // Crear objeto con los datos del formulario
            const formData = {
                productoID: document.getElementById('productoID').value || null,
                nombre: document.getElementById('nombre').value,
                descripcion: document.getElementById('descripcion').value,
                precio: parseFloat(document.getElementById('precio').value),
                fechaVencimiento: document.getElementById('fechaVencimiento').value,
                unidadMedida: document.getElementById('unidadMedida').value,
                fechaIngreso: document.getElementById('fechaIngreso').value,
                stock: parseInt(document.getElementById('stock').value),
                categoriaProductoID: parseInt(document.getElementById('categoriaProductoID').value),
                almacenID: parseInt(document.getElementById('almacenID').value)
            };

            console.log('Enviando datos:', formData); // Para debug

            fetch('/admin/productos/guardar', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(formData)
                })
                .then(async response => {
                    const text = await response.text();
                    console.log('Respuesta del servidor:', text); // Para debug

                    if (!response.ok) {
                        throw new Error(text || 'Error en el servidor');
                    }

                    return text;
                })
                .then(() => {
                    alert('Producto guardado exitosamente');
                    window.location.reload(); // Recargar la página para ver los cambios
                })
                .catch(error => {
                    console.error('Error completo:', error);
                    alert('Error al guardar el producto: ' + error.message);
                });
        });
    }

    // Agregar evento para la búsqueda
    document.getElementById('searchInput').addEventListener('keyup', buscarProductos);
});

function abrirModal(producto = null) {
    const modal = document.getElementById('productoModal');
    const form = document.getElementById('productoForm');
    const modalTitle = document.getElementById('modalTitle');

    // Limpiar el formulario
    form.reset();

    if (producto) {
        // Modo edición
        modalTitle.textContent = 'Editar Producto';
        document.getElementById('productoID').value = producto.productoID;
        document.getElementById('nombre').value = producto.nombre;
        document.getElementById('descripcion').value = producto.descripcion;
        document.getElementById('precio').value = producto.precio;
        document.getElementById('fechaVencimiento').value = producto.fechaVencimiento;
        document.getElementById('unidadMedida').value = producto.unidadMedida;
        document.getElementById('fechaIngreso').value = producto.fechaIngreso;
        document.getElementById('stock').value = producto.stock;
        document.getElementById('categoriaProductoID').value = producto.categoriaProductoID;
        document.getElementById('almacenID').value = producto.almacenID;
    } else {
        // Modo nuevo producto
        modalTitle.textContent = 'Agregar Nuevo Producto';
        document.getElementById('productoID').value = '';
    }

    modal.style.display = 'block';
}

function cerrarModal() {
    const modal = document.getElementById('productoModal');
    modal.style.display = 'none';
}

// Función de búsqueda agregada al final
function buscarProductos() {
    const searchTerm = document.getElementById('searchInput').value;

    fetch(`/admin/productos/buscar?searchTerm=${encodeURIComponent(searchTerm)}`)
        .then(response => response.json())
        .then(productos => {
            const tbody = document.querySelector('.product-table tbody');
            tbody.innerHTML = '';

            productos.forEach((producto, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${index + 1}</td>
                    <td>${producto.nombre}</td>
                    <td>${producto.descripcion}</td>
                    <td>S/${producto.precio}</td>
                    <td>${producto.fechaVencimiento}</td>
                    <td>${producto.unidadMedida}</td>
                    <td>${producto.fechaIngreso}</td>
                    <td>${producto.stock}</td>
                    <td>${producto.categoriaProductoID}</td>
                    <td>${producto.almacenID}</td>
                    <td>
                        <button class="btn btn-primary btn-editar" data-id="${producto.productoID}">Editar</button>
                        <button class="btn btn-danger btn-eliminar" data-id="${producto.productoID}">Eliminar</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        })
        .catch(error => console.error('Error:', error));
}