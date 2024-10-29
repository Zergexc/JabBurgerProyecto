$(document).ready(function() {
    let selectedIngredients = {};
    const basePrice = 5.00; // Precio base de la hamburguesa
    let burgers = [];

    // Funcionalidad de selección de ingredientes
    $('.ingredient-btn').click(function() {
        const ingredient = $(this).data('ingredient');
        const category = $(this).closest('.ingredient-category').find('h3').text();
        const name = $(this).text().split('S/')[0].trim();
        const price = parseFloat($(this).data('price'));

        if (!selectedIngredients[ingredient]) {
            selectedIngredients[ingredient] = {
                name: name,
                category: category,
                price: price,
                quantity: 1
            };
            $(this).addClass('active');
        } else {
            selectedIngredients[ingredient].quantity++;
        }

        updateIngredientCount(this, selectedIngredients[ingredient].quantity);
        updatePreview();
    });

    // Funcionalidad para disminuir ingredientes
    $('.ingredient-btn').on('contextmenu', function(e) {
        e.preventDefault();
        const ingredient = $(this).data('ingredient');
        
        if (selectedIngredients[ingredient] && selectedIngredients[ingredient].quantity > 0) {
            selectedIngredients[ingredient].quantity--;
            
            if (selectedIngredients[ingredient].quantity === 0) {
                delete selectedIngredients[ingredient];
                $(this).removeClass('active');
            }
            
            updateIngredientCount(this, selectedIngredients[ingredient] ? selectedIngredients[ingredient].quantity : 0);
            updatePreview();
        }
    });

    function updateIngredientCount(button, count) {
        let countElement = $(button).find('.ingredient-count');
        if (count > 1) {
            if (countElement.length === 0) {
                $(button).append('<span class="ingredient-count">' + count + '</span>');
            } else {
                countElement.text(count);
            }
        } else {
            countElement.remove();
        }
    }

    function updatePreview() {
        let total = basePrice;
        let ingredientsList = [];

        Object.values(selectedIngredients).forEach(item => {
            total += item.price * item.quantity;
            ingredientsList.push(item.name + (item.quantity > 1 ? ' x' + item.quantity : ''));
        });

        $('#burger-preview-ingredients').html(ingredientsList.join('<br>'));
        $('#burger-preview-price').text('S/' + total.toFixed(2));
    }

    // Funcionalidad de añadir al carrito
    $('#add-to-cart').click(function() {
        if (Object.keys(selectedIngredients).length > 0) {
            burgers.push({
                ingredients: {...selectedIngredients},
                quantity: 1
            });
            updateCart();
            resetCustomization();
        } else {
            alert('Por favor, selecciona al menos un ingrediente antes de añadir al carrito.');
        }
    });

    // Funcionalidad de nueva hamburguesa
    $('#new-burger').click(function() {
        resetCustomization();
    });

    function resetCustomization() {
        selectedIngredients = {};
        $('.ingredient-btn').removeClass('active').find('.ingredient-count').remove();
        updatePreview();
    }

    function updateCart() {
        let cartHtml = '';
        let total = 0;
        let totalItems = 0;

        burgers.forEach((burger, index) => {
            let burgerTotal = basePrice;
            let ingredientsList = [];

            Object.values(burger.ingredients).forEach(item => {
                burgerTotal += item.price * item.quantity;
                ingredientsList.push(item.name + (item.quantity > 1 ? ' x' + item.quantity : ''));
            });

            burgerTotal *= burger.quantity;
            total += burgerTotal;
            totalItems += burger.quantity;

            cartHtml += `
                <div class="cart-item">
                    <img src="../img/Hamburguesa Doble Carne.png" alt="Hamburguesa personalizada" class="cart-item-image">
                    <div class="cart-item-details">
                        <div class="cart-item-name">Hamburguesa Personalizada #${index + 1}</div>
                        <div class="cart-item-ingredients">${ingredientsList.join(', ')}</div>
                        <div class="cart-item-price">S/${burgerTotal.toFixed(2)}</div>
                        <div class="cart-item-quantity">
                            <button class="quantity-btn minus" data-index="${index}">-</button>
                            <span>${burger.quantity}</span>
                            <button class="quantity-btn plus" data-index="${index}">+</button>
                        </div>
                    </div>
                </div>
            `;
        });

        $('#cart-items').html(cartHtml);
        $('#cart-total').text(total.toFixed(2));
        updateCartCount(totalItems);
    }

    function updateCartCount(count) {
        $('.cart-count').text(count);
        if (count > 0) {
            $('.cart-count').show();
        } else {
            $('.cart-count').hide();
        }
    }

    // Funcionalidad de cantidad de hamburguesas en el carrito
    $(document).on('click', '.quantity-btn', function() {
        const index = $(this).data('index');
        if ($(this).hasClass('minus') && burgers[index].quantity > 1) {
            burgers[index].quantity--;
        } else if ($(this).hasClass('plus')) {
            burgers[index].quantity++;
        }
        updateCart();
    });

    // Funcionalidad de abrir/cerrar carrito
    $('.menu-icons a[title="Carrito"]').click(function(e) {
        e.preventDefault();
        toggleCart();
    });

    function toggleCart() {
        $('#cart').toggleClass('open');
        $('body').toggleClass('cart-open');
    }

    $('#close-cart').click(function() {
        closeCart();
    });

    function closeCart() {
        $('#cart').removeClass('open');
        $('body').removeClass('cart-open');
    }

    // Funcionalidad del botón de pagar
    $('#checkout-button').click(function() {
        if (burgers.length > 0) {
            alert('Gracias por tu compra!');
            burgers = [];
            updateCart();
            closeCart();
        } else {
            alert('Tu carrito está vacío. Añade algunas hamburguesas antes de pagar.');
        }
    });

    // Inicializar el contador del carrito
    updateCartCount(0);
});