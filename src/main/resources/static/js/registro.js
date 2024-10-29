document.addEventListener('DOMContentLoaded', function() {
    // Validación para el campo celular
    const celularInput = document.getElementById('celular');
    celularInput.addEventListener('input', function(e) {
        // Elimina cualquier carácter que no sea número
        this.value = this.value.replace(/[^0-9]/g, '');
        
        // Limita a 9 dígitos
        if(this.value.length > 9) {
            this.value = this.value.slice(0, 9);
        }

        // Muestra/oculta mensaje de error
        const errorSpan = this.nextElementSibling;
        if (this.value.length !== 9) {
            errorSpan.textContent = "El celular debe tener exactamente 9 números y no debe contener letras";
            errorSpan.style.display = 'block';
        } else {
            errorSpan.style.display = 'none';
        }
    });

    // Validación para el campo DNI
    const dniInput = document.getElementById('dni');
    dniInput.addEventListener('input', function(e) {
        // Elimina cualquier carácter que no sea número
        this.value = this.value.replace(/[^0-9]/g, '');
        
        // Limita a 8 dígitos
        if(this.value.length > 8) {
            this.value = this.value.slice(0, 8);
        }

        // Muestra/oculta mensaje de error
        const errorSpan = this.nextElementSibling;
        if (this.value.length !== 8) {
            errorSpan.textContent = "El DNI debe tener exactamente 8 números y no debe contener letras";
            errorSpan.style.display = 'block';
        } else {
            errorSpan.style.display = 'none';
        }
    });

    // Validación para el campo email
    const emailInput = document.getElementById('email');
    const emailError = document.getElementById('email-error');
    
    const proveedoresEmail = {
        'gmail': ['gmail.com'],
        'hotmail': ['hotmail.com', 'hotmail.es'],
        'outlook': ['outlook.com', 'outlook.es', 'live.com'],
        'yahoo': ['yahoo.com', 'yahoo.es', 'yahoo.com.mx', 'yahoo.com.ar'],
        'icloud': ['icloud.com', 'me.com'],
        'proton': ['proton.me', 'protonmail.com'],
        'aol': ['aol.com']
    };

    const dominiosPermitidos = Object.values(proveedoresEmail).flat();

    emailInput.addEventListener('input', function(e) {
        const email = this.value.toLowerCase();
        const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        
        if (email.length > 0) {
            if (!emailRegex.test(email)) {
                emailError.textContent = "Por favor, ingrese un correo electrónico válido";
                emailError.style.display = 'block';
                return;
            }

            const dominio = email.split('@')[1];
            if (!dominiosPermitidos.includes(dominio)) {
                emailError.textContent = "Por favor, utilice un proveedor de correo válido (ejemplo: gmail.com, hotmail.com, outlook.com)";
                emailError.style.display = 'block';
            } else {
                emailError.style.display = 'none';
            }
        } else {
            emailError.style.display = 'none';
        }
    });
});