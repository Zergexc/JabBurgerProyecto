$(document).ready(function() {
    // Cerrar modal
    $(".close").click(function() {
        $("#userModal").hide();
    });

    // Manejar envío del formulario
    $("#userForm").submit(function(e) {
        e.preventDefault();
        saveUser();
    });

    // Funcionalidad para mostrar/ocultar contraseña
    $("#togglePassword").click(function() {
        var passwordField = $("#password");
        var passwordFieldType = passwordField.attr('type');
        if (passwordFieldType == 'password') {
            passwordField.attr('type', 'text');
            $(this).find('i').removeClass('fa-eye').addClass('fa-eye-slash');
        } else {
            passwordField.attr('type', 'password');
            $(this).find('i').removeClass('fa-eye-slash').addClass('fa-eye');
        }
    });
});

function addUser() {
    openUserModal("Agregar Usuario");
}

function editUser(userId) {
    $.ajax({
        url: "/admin/usuarios/" + userId,
        type: "GET",
        success: function(user) {
            openUserModal("Editar Usuario", user);
        },
        error: function(xhr, status, error) {
            console.error("Error al cargar los datos del usuario:", error);
            alert("Error al cargar los datos del usuario: " + xhr.responseText);
        }
    });
}

function openUserModal(title, user = null) {
    $("#modalTitle").text(title);
    $("#userForm")[0].reset();

    if (user) {
        $("#userId").val(user.id);
        $("#nombre").val(user.nombre);
        $("#apellido").val(user.apellido);
        $("#email").val(user.email);
        $("#celular").val(user.celular);
        $("#dni").val(user.dni);
        $("#password").val(''); // No mostramos la contraseña por seguridad
    } else {
        $("#userId").val('');
    }

    $("#userModal").show();
}

function saveUser() {
    var userId = $("#userId").val();
    var isNewUser = !userId;
    var userData = {
        id: userId || null,
        nombre: $("#nombre").val(),
        apellido: $("#apellido").val(),
        email: $("#email").val(),
        celular: $("#celular").val(),
        dni: $("#dni").val()
    };

    $.ajax({
        url: isNewUser ? "/admin/usuarios/crear" : "/admin/usuarios/actualizar/" + userId,
        type: isNewUser ? "POST" : "PUT",
        contentType: "application/json",
        data: JSON.stringify(userData),
        success: function(response) {
            alert(isNewUser ? "Usuario creado exitosamente" : "Usuario actualizado exitosamente");
            $("#userModal").hide();
            location.reload();
        },
        error: function(xhr, status, error) {
            let errorMessage = "";
            try {
                errorMessage = JSON.parse(xhr.responseText).message;
            } catch (e) {
                errorMessage = xhr.responseText;
            }
            console.error("Error al " + (isNewUser ? "crear" : "actualizar") + " el usuario:", errorMessage);
            alert("Error al " + (isNewUser ? "crear" : "actualizar") + " el usuario: " + errorMessage);
        }
    });
}


function deleteUser(userId) {
    if (confirm("¿Estás seguro de que quieres eliminar este usuario?")) {
        $.ajax({
            url: "/admin/usuarios/eliminar/" + userId,
            type: "DELETE",
            success: function(response) {
                location.reload();
            },
            error: function(xhr, status, error) {
                console.error("Error al eliminar el usuario:", error);
                alert("Error al eliminar el usuario: " + xhr.responseText);
            }
        });
    }
}

function searchUsers() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("userTableBody");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td");
        for (var j = 0; j < td.length; j++) {
            if (td[j]) {
                txtValue = td[j].textContent || td[j].innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                    break;
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
}