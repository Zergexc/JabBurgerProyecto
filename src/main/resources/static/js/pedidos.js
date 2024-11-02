$(document).ready(function() {
    console.log("Documento listo"); // Debug

    // Evento para el botón de agregar
    $("#btnAgregarPedido").click(function() {
        console.log("Botón Agregar clickeado"); // Debug
        $("#modalTitle").text("Agregar Pedido");
        clearForm();
        $("#pedidoModal").show();
    });

    // Evento para el formulario
    $("#pedidoForm").submit(function(e) {
        e.preventDefault();
        console.log("Formulario enviado"); // Debug
        savePedido();
    });

    // Cerrar modal
    $(".close").click(function() {
        $("#pedidoModal").hide();
    });
});

function savePedido() {
    var pedidoId = $("#pedidoId").val();
    var isNewPedido = !pedidoId;
    
    var pedidoData = {
        id: pedidoId || null,
        fecha: new Date().toISOString(),
        clienteId: parseInt($("#clienteId").val()),
        empleadoId: parseInt($("#empleadoId").val()),
        montoTotal: parseFloat($("#montoTotal").val()),
        estado: $("#estado").val(),
        metodoEntrega: $("#metodoEntrega").val(),
        proveedorPagoId: parseInt($("#proveedorPagoId").val()),
        transaccionPagoId: parseInt($("#transaccionPagoId").val())
    };

    console.log("Datos a enviar:", pedidoData);
    console.log("Es nuevo pedido:", isNewPedido);
    console.log("ID del pedido:", pedidoId);

    var url = isNewPedido ? "/admin/pedidos/crear" : "/admin/pedidos/actualizar/" + pedidoId;
    var type = isNewPedido ? "POST" : "PUT";

    $.ajax({
        url: url,
        type: type,
        contentType: "application/json",
        data: JSON.stringify(pedidoData),
        success: function(response) {
            console.log("Respuesta exitosa:", response);
            $("#pedidoModal").hide();
            location.reload();
        },
        error: function(xhr, status, error) {
            console.error("Error detallado:", {
                status: xhr.status,
                responseText: xhr.responseText,
                error: error
            });
            alert("Error al " + (isNewPedido ? "crear" : "actualizar") + " el pedido: " + error);
        }
    });
}

function clearForm() {
    $("#pedidoId").val('');
    $("#clienteId").val('');
    $("#empleadoId").val('');
    $("#montoTotal").val('');
    $("#estado").val('Pendiente');
    $("#metodoEntrega").val('Presencial');
    $("#proveedorPagoId").val('');
    $("#transaccionPagoId").val('');
}

function editPedido(id) {
    console.log("Editando pedido:", id);
    
    $.ajax({
        url: "/admin/pedidos/" + id,
        type: "GET",
        success: function(pedido) {
            $("#pedidoId").val(pedido.id);
            $("#clienteId").val(pedido.clienteId);
            $("#empleadoId").val(pedido.empleadoId);
            $("#montoTotal").val(pedido.montoTotal);
            $("#estado").val(pedido.estado);
            $("#metodoEntrega").val(pedido.metodoEntrega);
            $("#proveedorPagoId").val(pedido.proveedorPagoId);
            $("#transaccionPagoId").val(pedido.transaccionPagoId);
            
            $("#modalTitle").text("Editar Pedido #" + pedido.id);
            $("#pedidoModal").show();
        },
        error: function(xhr, status, error) {
            console.error("Error al obtener pedido:", error);
            alert("Error al cargar el pedido: " + error);
        }
    });
}

function deletePedido(id) {
    if (confirm("¿Está seguro de que desea eliminar este pedido?")) {
        console.log("Eliminando pedido:", id);
        
        $.ajax({
            url: "/admin/pedidos/eliminar/" + id,
            type: "DELETE",
            success: function(response) {
                console.log("Pedido eliminado:", response);
                location.reload();
            },
            error: function(xhr, status, error) {
                console.error("Error al eliminar:", error);
                alert("Error al eliminar el pedido: " + error);
            }
        });
    }
}