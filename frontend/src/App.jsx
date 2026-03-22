import { useState, useEffect } from 'react'
import './App.css'

const API_BASE = ''
const ORDER_API = `${API_BASE}/api`
const INVENTORY_API = `${API_BASE}/inventory`

function App() {
  const [orderStatus, setOrderStatus] = useState(null)
  const [inventoryStatus, setInventoryStatus] = useState(null)
  const [items, setItems] = useState([])
  const [orders, setOrders] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true)
      setError(null)
      try {
        const [orderRes, invRes, itemsRes, ordersRes] = await Promise.allSettled([
          fetch(`${ORDER_API}/v1/inventory`),
          fetch(`${INVENTORY_API}/api/v1/inventory/check?skuCode=TEST&qty=1`),
          fetch(`${INVENTORY_API}/api/v1/items`),
          fetch(`${ORDER_API}/v1/orders`),
        ])

        if (orderRes.status === 'fulfilled' && orderRes.value.ok) {
          setOrderStatus(await orderRes.value.text())
        }
        if (invRes.status === 'fulfilled' && invRes.value.ok) {
          setInventoryStatus(await invRes.value.json())
        }
        if (itemsRes.status === 'fulfilled' && itemsRes.value.ok) {
          const data = await itemsRes.value.json()
          setItems(Array.isArray(data) ? data : [])
        }
        if (ordersRes.status === 'fulfilled' && ordersRes.value.ok) {
          const data = await ordersRes.value.json()
          setOrders(Array.isArray(data) ? data : [])
        }
      } catch (e) {
        setError(e.message)
      } finally {
        setLoading(false)
      }
    }
    fetchData()
  }, [])

  if (loading) return <div className="app">Caricamento...</div>
  if (error) return <div className="app error">Errore: {error}</div>

  return (
    <div className="app">
      <header>
        <h1>Microservizi Demo</h1>
      </header>
      <main>
        <section>
          <h2>Stato servizi</h2>
          <div className="grid">
            <div className="card">
              <h3>Order Service</h3>
              <p>{orderStatus ?? 'Non disponibile'}</p>
            </div>
            <div className="card">
              <h3>Inventory Service</h3>
              <p>Stock check: {inventoryStatus !== null ? String(inventoryStatus) : 'Non disponibile'}</p>
            </div>
          </div>
        </section>
        <section>
          <h2>Items (Inventory DB)</h2>
          <ul className="list">
            {items.length === 0 ? (
              <li>Nessun item nel database</li>
            ) : (
              items.map((item) => (
                <li key={item.id}>
                  {item.skuCode} - Qty: {item.quantity}
                </li>
              ))
            )}
          </ul>
        </section>
        <section>
          <h2>Orders (Order DB)</h2>
          <ul className="list">
            {orders.length === 0 ? (
              <li>Nessun ordine nel database</li>
            ) : (
              orders.map((order) => (
                <li key={order.id}>
                  Order #{order.id} - {order.orderNumber ?? order.status ?? 'N/A'}
                </li>
              ))
            )}
          </ul>
        </section>
      </main>
    </div>
  )
}

export default App
