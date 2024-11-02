// Función para mostrar/ocultar tablas
function mostrarTabla(tipo) {
    const tablaBoletas = document.getElementById('tabla-boletas');
    const tablaFacturas = document.getElementById('tabla-facturas');
    const btnBoletas = document.querySelector('.tipo-venta-buttons .btn:first-child');
    const btnFacturas = document.querySelector('.tipo-venta-buttons .btn:last-child');

    if (tipo === 'boletas') {
        tablaBoletas.style.display = 'block';
        tablaFacturas.style.display = 'none';
        btnBoletas.classList.add('active');
        btnFacturas.classList.remove('active');
    } else {
        tablaBoletas.style.display = 'none';
        tablaFacturas.style.display = 'block';
        btnBoletas.classList.remove('active');
        btnFacturas.classList.add('active');
    }
}

// Funciones para Boletas
function nuevaBoleta() {
    document.getElementById('boletaModalTitle').textContent = 'Nueva Boleta';
    document.getElementById('boletaForm').reset();
    document.getElementById('boletaId').value = '';
    document.getElementById('boletaModal').style.display = 'block';
}

function editarBoleta(id) {
    document.getElementById('boletaModalTitle').textContent = 'Editar Boleta';
    
    // Cargar datos de la boleta
    fetch(`/admin/ventas/boletas/${id}`)
        .then(response => response.json())
        .then(boleta => {
            document.getElementById('boletaId').value = boleta.boletaID;
            document.getElementById('numeroBoleta').value = boleta.numeroBoleta;
            document.getElementById('fechaEmision').value = boleta.fechaEmision.split('T')[0];
            document.getElementById('subTotal').value = boleta.subTotal;
            document.getElementById('igv').value = boleta.igv;
            document.getElementById('total').value = boleta.total;
            document.getElementById('boletaModal').style.display = 'block';
        })
        .catch(error => alert('Error al cargar la boleta: ' + error));
}

function eliminarBoleta(id) {
    if (confirm('¿Está seguro de que desea eliminar esta boleta?')) {
        fetch(`/admin/ventas/boletas/eliminar/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Boleta eliminada con éxito');
                location.reload();
            } else {
                throw new Error('Error al eliminar');
            }
        })
        .catch(error => alert('Error al eliminar la boleta: ' + error));
    }
}

// Funciones para Facturas
function nuevaFactura() {
    document.getElementById('facturaModalTitle').textContent = 'Nueva Factura';
    document.getElementById('facturaForm').reset();
    document.getElementById('facturaId').value = '';
    document.getElementById('facturaModal').style.display = 'block';
}

function editarFactura(id) {
    document.getElementById('facturaModalTitle').textContent = 'Editar Factura';
    
    fetch(`/admin/ventas/facturas/${id}`)
        .then(response => response.json())
        .then(factura => {
            document.getElementById('facturaId').value = factura.facturaID;
            document.getElementById('numeroFactura').value = factura.numeroFactura;
            document.getElementById('rucEmpresa').value = factura.rucEmpresa;
            document.getElementById('fechaEmisionFactura').value = factura.fechaEmision.split('T')[0];
            document.getElementById('subTotalFactura').value = factura.subTotal;
            document.getElementById('igvFactura').value = factura.igv;
            document.getElementById('totalFactura').value = factura.total;
            document.getElementById('facturaModal').style.display = 'block';
        })
        .catch(error => alert('Error al cargar la factura: ' + error));
}

function eliminarFactura(id) {
    if (confirm('¿Está seguro de que desea eliminar esta factura?')) {
        fetch(`/admin/ventas/facturas/eliminar/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Factura eliminada con éxito');
                location.reload();
            } else {
                throw new Error('Error al eliminar');
            }
        })
        .catch(error => alert('Error al eliminar la factura: ' + error));
    }
}

// Event Listeners
document.addEventListener('DOMContentLoaded', function() {
    // Cerrar modales al hacer clic en la X
    document.querySelectorAll('.close').forEach(function(closeBtn) {
        closeBtn.addEventListener('click', function() {
            document.querySelectorAll('.modal').forEach(function(modal) {
                modal.style.display = 'none';
            });
        });
    });

    // Cerrar modales al hacer clic fuera del contenido
    window.addEventListener('click', function(event) {
        if (event.target.classList.contains('modal')) {
            event.target.style.display = 'none';
        }
    });

    // Event listener para el formulario de boletas
    document.getElementById('boletaForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = {
            boletaID: document.getElementById('boletaId').value,
            numeroBoleta: document.getElementById('numeroBoleta').value,
            fechaEmision: document.getElementById('fechaEmision').value,
            subTotal: parseFloat(document.getElementById('subTotal').value),
            igv: parseFloat(document.getElementById('igv').value),
            total: parseFloat(document.getElementById('total').value)
        };

        fetch('/admin/ventas/boletas/guardar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (response.ok) {
                alert('Boleta guardada con éxito');
                document.getElementById('boletaModal').style.display = 'none';
                location.reload();
            } else {
                throw new Error('Error al guardar');
            }
        })
        .catch(error => alert('Error al guardar la boleta: ' + error));
    });

    // Event listener para el formulario de facturas
    document.getElementById('facturaForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = {
            facturaID: document.getElementById('facturaId').value,
            numeroFactura: document.getElementById('numeroFactura').value,
            rucEmpresa: document.getElementById('rucEmpresa').value,
            fechaEmision: document.getElementById('fechaEmisionFactura').value,
            subTotal: parseFloat(document.getElementById('subTotalFactura').value),
            igv: parseFloat(document.getElementById('igvFactura').value),
            total: parseFloat(document.getElementById('totalFactura').value)
        };

        fetch('/admin/ventas/facturas/guardar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (response.ok) {
                alert('Factura guardada con éxito');
                document.getElementById('facturaModal').style.display = 'none';
                location.reload();
            } else {
                throw new Error('Error al guardar');
            }
        })
        .catch(error => alert('Error al guardar la factura: ' + error));
    });

    // Mostrar boletas por defecto
    mostrarTabla('boletas');
});