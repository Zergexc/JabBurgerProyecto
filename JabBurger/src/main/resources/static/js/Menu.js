// El JavaScript permanece sin cambios
$(document).ready(function() {
    // -------------------- FILTRADO --------------------
    // Funcionalidad de filtrado
    $('.filter-menu a').click(function(e) {
        e.preventDefault();
        $('.filter-menu a').removeClass('active');
        $(this).addClass('active');
        
        const filter = $(this).data('filter');
        if (filter === 'all') {
            $('.card').show();
        } else {
            $('.card').hide();
            $(`.card[data-category="${filter}"]`).show();
        }
    });

    // -------------------- CARRITO --------------------
    // Funcionalidad del carrito
    let cart = [];

    // Agregar al carrito
    $('.card button').click(function() {
        const card = $(this).closest('.card');
        const name = card.find('h3').text();
        const price = parseFloat(card.find('.price').text().replace('$', ''));
        const image = card.find('img').attr('src');
        
        addToCart(name, price, image);
        updateCart();
        openCart();
    });

    function addToCart(name, price, image) {
        const existingItem = cart.find(item => item.name === name);
        if (existingItem) {
            existingItem.quantity++;
        } else {
            cart.push({ name, price, image, quantity: 1 });
        }
    }

    function updateCart() {
        $('#cart-items').empty();
        let total = 0;
        if (cart.length === 0) {
            $('#cart-items').html('<p class="cart-empty">Tu carrito está vacío</p>');
        } else {
            cart.forEach(item => {
                total += item.price * item.quantity;
                $('#cart-items').append(`
                    <li class="cart-item">
                        <img src="${item.image}" alt="${item.name}">
                        <div class="cart-item-details">
                            <div class="cart-item-name">${item.name}</div>
                            <div class="cart-item-price">$${item.price.toFixed(2)}</div>
                            <div class="cart-item-quantity">
                                <button class="decrease-quantity" data-name="${item.name}">-</button>
                                <span>${item.quantity}</span>
                                <button class="increase-quantity" data-name="${item.name}">+</button>
                            </div>
                        </div>
                    </li>
                `);
            });
        }
        $('#cart-total').text(total.toFixed(2));
    }

    // Manejar cambios de cantidad en el carrito
    $(document).on('click', '.increase-quantity', function() {
        const name = $(this).data('name');
        const item = cart.find(item => item.name === name);
        if (item) {
            item.quantity++;
            updateCart();
        }
    });

    $(document).on('click', '.decrease-quantity', function() {
        const name = $(this).data('name');
        const item = cart.find(item => item.name === name);
        if (item && item.quantity > 1) {
            item.quantity--;
        } else if (item) {
            cart = cart.filter(cartItem => cartItem.name !== name);
        }
        updateCart();
    });

    // Alternar carrito
    $('.menu-icons a[title="Carrito"]').click(function(e) {
        e.preventDefault();
        toggleCart();
    });

    function toggleCart() {
        $('#cart').toggleClass('open');
        $('body').toggleClass('cart-open');
    }

    function openCart() {
        $('#cart').addClass('open');
        $('body').addClass('cart-open');
    }

    // Cerrar carrito
    $('#close-cart').click(function() {
        closeCart();
    });

    function closeCart() {
        $('#cart').removeClass('open');
        $('body').removeClass('cart-open');
    }

    // Funcionalidad del botón de pagar
    $('#checkout-button').click(function() {
        // Redirigir a la página de pago
        window.location.href = 'pagina-de-pago.html';
    });
});