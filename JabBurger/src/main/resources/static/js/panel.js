 // Datos de ejemplo
 const products = [{
     id: 1,
     nombre: 'Producto 1',
     descripcion: 'Descripción del producto 1',
     codigo: 'ABC123',
     material: 'Plástico',
     precio: 9.99,
     descuento: 10,
     stock: 25
 }, {
     id: 2,
     nombre: 'Producto 2',
     descripcion: 'Descripción del producto 2',
     codigo: 'DEF456',
     material: 'Metal',
     precio: 19.99,
     descuento: 5,
     stock: 15
 }];

 // Función para generar la tabla de productos
 function renderProductTable() {
     const productList = document.getElementById('product-list');
     productList.innerHTML = '';

     products.forEach(product => {
         const row = document.createElement('tr');
         row.innerHTML = `
  <td>${product.id}</td>
  <td>${product.nombre}</td>
  <td>${product.descripcion}</td>
  <td>${product.codigo}</td>
  <td>${product.material}</td>
  <td>$${product.precio.toFixed(2)}</td>
  <td>${product.descuento}%</td>
  <td>${product.stock}</td>
  <td>
    <button class="btn btn-danger btn-sm">Eliminar</button>
  </td>
`;
         productList.appendChild(row);
     });
 }

 // Cargar la tabla de productos inicialmente
 renderProductTable();

 // Evento para agregar un nuevo producto
 document.getElementById('add-product-btn').addEventListener('click', () => {
     // Lógica para agregar un nuevo producto aquí
     console.log('Agregar nuevo producto');
 });