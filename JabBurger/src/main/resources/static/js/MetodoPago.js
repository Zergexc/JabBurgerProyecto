document.getElementById('payment-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Evitar el envío del formulario

    // Obtener valores de los campos
    const cardNumber = document.getElementById('card-number').value.replace(/\s/g, ''); // Eliminamos los espacios
    const expiryDate = document.getElementById('expiry-date').value;
    const cvv = document.getElementById('cvv').value;
    const ruc = document.getElementById('ruc').value;

    // Reiniciar el aviso de error
    const errorAlert = document.getElementById('error-alert');
    errorAlert.style.display = 'none';
    document.getElementById('error-message').textContent = '';

    // Validaciones
    const isCardNumberValid = validateCardNumber(cardNumber);
    const isExpiryDateValid = validateExpiryDate(expiryDate);
    const isCvvValid = validateCvv(cvv);
    const isRucValid = validateRUC(ruc);

    // Mostrar mensajes de error
    if (!isCardNumberValid) {
        showError('Número de tarjeta inválido');
        return;
    }
    if (!isExpiryDateValid) {
        showError('Fecha de expiración inválida');
        return;
    }
    if (!isCvvValid) {
        showError('CVV inválido');
        return;
    }
    if (!isRucValid) {
        showError('RUC inválido');
        return;
    }

    // Si todas las validaciones son correctas
    alert('Pago procesado con éxito');
    // Aquí puedes agregar el código para procesar el pago
});

// Función para mostrar el mensaje de error
function showError(message) {
    document.getElementById('error-message').textContent = message;
    const errorAlert = document.getElementById('error-alert');
    errorAlert.style.display = 'block'; // Mostrar el aviso

    // Se puede mantener el mensaje visible por 5 segundos o hasta que se cierre
    setTimeout(() => {
        errorAlert.style.display = 'none';
    }, 5000); // Opcional: Oculta el aviso después de 5 segundos
}

// Validar número de tarjeta (Luhn Algorithm)
function validateCardNumber(cardNumber) {
    const regex = /^\d{16}$/; // 16 dígitos
    if (!regex.test(cardNumber)) return false;

    let sum = 0;
    let alternate = false;
    for (let i = cardNumber.length - 1; i >= 0; i--) {
        let n = parseInt(cardNumber.charAt(i), 10);
        if (alternate) {
            n *= 2;
            if (n > 9) n -= 9;
        }
        sum += n;
        alternate = !alternate;
    }
    return sum % 10 === 0;
}

// Validar fecha de expiración
function validateExpiryDate(expiryDate) {
    const regex = /^(0[1-9]|1[0-2])\/\d{2}$/; // Formato MM/AA
    if (!regex.test(expiryDate)) return false;

    const [month, year] = expiryDate.split('/').map(num => parseInt(num, 10));
    const expiry = new Date(`20${year}`, month - 1);
    const now = new Date();
    return expiry >= now;
}

// Validar CVV
function validateCvv(cvv) {
    const regex = /^\d{3,4}$/; // 3 o 4 dígitos
    return regex.test(cvv);
}

// Validar RUC (validación básica para formato)
function validateRUC(ruc) {
    const regex = /^\d{11}$/; // RUC debe tener 11 dígitos
    return regex.test(ruc);
}

// Formatear la fecha de expiración
document.getElementById('expiry-date').addEventListener('input', function(event) {
    // Quitar cualquier carácter que no sea número
    const value = this.value.replace(/\D/g, '');

    // Si hay más de 4 caracteres, mantener solo los primeros 4
    const formattedValue = value.length > 4 ? value.slice(0, 4) : value;

    // Añadir la barra después del segundo carácter
    if (formattedValue.length > 2) {
        this.value = formattedValue.slice(0, 2) + '/' + formattedValue.slice(2);
    } else {
        this.value = formattedValue;
    }
});
