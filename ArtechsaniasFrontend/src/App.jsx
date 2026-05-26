import React, { useState } from 'react';

function App() {
  // Estado con las artesanías iniciales de prueba
  const [productos, setProductos] = useState([
    { id: 1, nombre: 'Bolsa de Crochet Elegante', precio: 85000, stock: 5, categoria: 'Accesorios' },
    { id: 2, nombre: 'Amigurumi Gato Cósmico', precio: 45000, stock: 3, categoria: 'Amigurumis' }
  ]);

  // Estados para controlar los campos del formulario
  const [nombre, setNombre] = useState('');
  const [precio, setPrecio] = useState('');
  const [stock, setStock] = useState('');
  const [categoria, setCategoria] = useState('Amigurumis');

  // Función para guardar una nueva artesanía
  const manejarEnvio = (e) => {
    e.preventDefault();

    if (!nombre || !precio || !stock) {
      alert('Por favor, llena los campos obligatorios.');
      return;
    }

    const nuevoProducto = {
      id: Date.now(),
      nombre,
      precio: parseFloat(precio),
      stock: parseInt(stock),
      categoria
    };

    setProductos([...productos, nuevoProducto]);

    // Limpiar formulario
    setNombre('');
    setPrecio('');
    setStock('');
  };

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', backgroundColor: '#f7fafc', minHeight: '100vh', color: '#2d3748' }}>
      
      {/* BARRA DE NAVEGACIÓN */}
      <nav style={{ backgroundColor: '#1a365d', color: 'white', padding: '15px 20px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h2 style={{ margin: 0 }}>🧵 ArTechsanías Digital</h2>
        <div>
          <span style={{ marginRight: '20px', cursor: 'pointer', fontWeight: 'bold' }}>Catálogo</span>
          <span style={{ cursor: 'pointer' }}>🛒 Carrito</span>
        </div>
      </nav>

      <div style={{ padding: '20px', maxWidth: '1000px', margin: '0 auto' }}>
        
        {/* FORMULARIO */}
        <div style={{ backgroundColor: 'white', padding: '20px', borderRadius: '8px', boxShadow: '0 2px 4px rgba(0,0,0,0.1)', marginBottom: '30px' }}>
          <h3 style={{ color: '#2c5282', marginTop: 0 }}>Registrar Nuevo Producto Artesanal</h3>
          <form onSubmit={manejarEnvio}>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '5px' }}>Nombre de la artesanía *</label>
              <input type="text" value={nombre} onChange={(e) => setNombre(e.target.value)} placeholder="Ej. Amigurumi Oso Tejido" style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e0', boxSizing: 'border-box' }} />
            </div>

            <div style={{ display: 'flex', gap: '15px', marginBottom: '10px' }}>
              <div style={{ flex: 1 }}>
                <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '5px' }}>Precio (COP) *</label>
                <input type="number" value={precio} onChange={(e) => setPrecio(e.target.value)} placeholder="0" style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e0', boxSizing: 'border-box' }} />
              </div>
              <div style={{ flex: 1 }}>
                <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '5px' }}>Stock Inicial *</label>
                <input type="number" value={stock} onChange={(e) => setStock(e.target.value)} placeholder="0" style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e0', boxSizing: 'border-box' }} />
              </div>
              <div style={{ flex: 1 }}>
                <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '5px' }}>Categoría</label>
                <select value={categoria} onChange={(e) => setCategoria(e.target.value)} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e0', height: '38px', boxSizing: 'border-box' }}>
                  <option value="Amigurumis">Amigurumis</option>
                  <option value="Prendas de Vestir">Prendas de Vestir</option>
                  <option value="Accesorios">Accesorios</option>
                </select>
              </div>
            </div>

            <button type="submit" style={{ backgroundColor: '#319795', color: 'white', border: 'none', padding: '10px 15px', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}>
              Guardar en Inventario
            </button>
          </form>
        </div>

        {/* CATÁLOGO */}
        <h3 style={{ color: '#1a365d' }}>Catálogo de Productos Disponibles</h3>
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '20px' }}>
          {productos.map((prod) => (
            <div key={prod.id} style={{ backgroundColor: 'white', border: '1px solid #e2e8f0', borderRadius: '8px', padding: '15px', width: '220px', boxShadow: '0 2px 4px rgba(0,0,0,0.05)' }}>
              <span style={{ fontSize: '11px', backgroundColor: '#e2e8f0', color: '#2c5282', padding: '2px 6px', borderRadius: '4px', fontWeight: 'bold' }}>{prod.categoria}</span>
              <h4 style={{ margin: '10px 0 5px 0', color: '#2d3748' }}>{prod.nombre}</h4>
              <p style={{ margin: '0 0 10px 0', color: '#4a5568', fontWeight: 'bold' }}>${prod.precio.toLocaleString()} COP</p>
              <p style={{ margin: '0 0 15px 0', fontSize: '13px', color: '#718096' }}>Disponibles: {prod.stock} unids</p>
              <button style={{ width: '100%', backgroundColor: '#2b6cb0', color: 'white', border: 'none', padding: '8px', borderRadius: '4px', cursor: 'pointer' }}>Ver Detalles</button>
            </div>
          ))}
        </div>

      </div>
    </div>
  );
}

export default App;