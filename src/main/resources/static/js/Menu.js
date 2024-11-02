$(document).ready(function() {
    // -------------------- FILTRADO --------------------
    $('.filter-menu a').click(function(e) {
        e.preventDefault();
        $('.filter-menu a').removeClass('active');
        $(this).addClass('active');
        
        const filter = $(this).data('filter');
        
        // Mostrar/ocultar productos según el filtro
        if (filter === 'all') {
            $('.card').show();
        } else {
            $('.card').hide();
            $(`.card[data-category="${filter}"]`).show();
        }
    });

    // -------------------- CARRITO --------------------
    let cart = [];

    // Función para agregar al carrito
    window.agregarAlCarrito = function(button) {
        const item = {
            productoId: $(button).data('id'),
            nombre: $(button).data('nombre'),
            precio: parseFloat($(button).data('precio')),
            imagen: $(button).closest('.card').find('img').attr('src')
        };
        
        fetch('/api/carrito/agregar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(item)
        })
        .then(response => response.json())
        .then(carrito => {
            actualizarCarritoUI(carrito);
            openCart();
        });
    };

    function actualizarCarritoUI(carrito) {
        $('#cart-items').empty();
        if (carrito.items.length === 0) {
            $('#cart-items').html('<p class="cart-empty">Tu carrito está vacío</p>');
        } else {
            carrito.items.forEach(item => {
                $('#cart-items').append(`
                    <li class="cart-item">
                        <img src="${item.imagen}" alt="${item.nombre}">
                        <div class="cart-item-details">
                            <div class="cart-item-name">${item.nombre}</div>
                            <div class="cart-item-price">S/${item.precio.toFixed(2)}</div>
                            <div class="cart-item-quantity">
                                <button onclick="decrementarCantidad(${item.productoId})">-</button>
                                <span>${item.cantidad}</span>
                                <button onclick="incrementarCantidad(${item.productoId})">+</button>
                            </div>
                        </div>
                    </li>
                `);
            });
        }
        $('#cart-total').text(carrito.total.toFixed(2));
    }

    window.incrementarCantidad = function(productoId) {
        fetch(`/api/carrito/incrementar/${productoId}`, {
            method: 'POST'
        })
        .then(response => response.json())
        .then(carrito => actualizarCarritoUI(carrito));
    };

    window.decrementarCantidad = function(productoId) {
        fetch(`/api/carrito/decrementar/${productoId}`, {
            method: 'POST'
        })
        .then(response => response.json())
        .then(carrito => actualizarCarritoUI(carrito));
    };

    // Cargar carrito inicial
    fetch('/api/carrito')
        .then(response => response.json())
        .then(carrito => actualizarCarritoUI(carrito));

    // Botón de pago
    $('#checkout-button').click(function() {
        window.location.href = '/metodopago';
    });
});