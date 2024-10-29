document.addEventListener('DOMContentLoaded', function() {
    // Elementos del carrito
    const cartIcon = document.querySelector('.cart-icon');
    const cart = document.querySelector('#cart');
    const closeCart = document.querySelector('#close-cart');

    // Función para abrir el carrito
    function openCart() {
        cart.classList.add('open');
        document.body.classList.add('cart-open');
    }

    // Función para cerrar el carrito
    function closeCartFunc() {
        cart.classList.remove('open');
        document.body.classList.remove('cart-open');
    }

    // Event listeners
    cartIcon.addEventListener('click', function(e) {
        e.preventDefault();
        openCart();
    });

    closeCart.addEventListener('click', closeCartFunc);
});
