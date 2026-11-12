
import { Routes, Route } from 'react-router-dom'
import HomePage from './pages/HomePage'
import ProductListingPage from './pages/ProductListingPage'

function App() {
    return (
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/products" element={<ProductListingPage />} />
        </Routes>
    )
}

export default App
