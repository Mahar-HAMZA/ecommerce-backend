
import { useState } from 'react'
import {
    SlidersHorizontal,
    ChevronDown,
    Star,
} from 'lucide-react'

import products from '../data/products'
import Header from '../components/Header'
import Footer from '../components/Footer'
import '../App.css'

function ProductListingPage() {
    const [selectedCategories, setSelectedCategories] = useState([])
    const [searchTerm, setSearchTerm] = useState('')
    const [filtersOpen, setFiltersOpen] = useState(false)
    const [minPrice, setMinPrice] = useState('')
    const [maxPrice, setMaxPrice] = useState('')

    const categories = [
        ...new Set(products.map((product) => product.category))
    ]

    const handleCategoryChange = (category) => {
        setSelectedCategories((previousCategories) => {
            if (previousCategories.includes(category)) {
                return previousCategories.filter(
                    (item) => item !== category
                )
            }

            return [...previousCategories, category]
        })
    }

    const isPriceRangeInvalid =
        minPrice !== '' &&
        maxPrice !== '' &&
        Number(minPrice) > Number(maxPrice)
    const filteredProducts = isPriceRangeInvalid ? [] : products.filter((product) => {
        const matchesCategory =
            selectedCategories.length === 0 ||
            selectedCategories.includes(product.category)

        const matchesSearch = product.name
            .toLowerCase()
            .includes(searchTerm.toLowerCase())
        const matchesMinPrice =
            minPrice === '' || product.price >= Number(minPrice)

        const matchesMaxPrice =
            maxPrice === '' || product.price <= Number(maxPrice)

        return (
            matchesCategory &&
            matchesSearch &&
            matchesMinPrice &&
            matchesMaxPrice
        )
    })

    return (
        <>
            <Header
                searchTerm={searchTerm}
                onSearchChange={setSearchTerm}
                wishlistCount={0}
                cartCount={0}
            />

            <main>
                <section className="container">
                    <div className="listing-banner">
                        <div className="listing-banner-content">
                            <span className="listing-eyebrow">
                                DISCOVER THE NOVA COLLECTION
                            </span>

                            <h1>
                                Find Everything
                                <br />
                                You Love.
                            </h1>

                            <p>
                                Explore our carefully selected collection of products,
                                designed to bring quality and style to your everyday life.
                            </p>

                            <div className="listing-breadcrumb">
                                <span>Home</span>
                                <span>/</span>
                                <strong>Our Products</strong>
                            </div>
                        </div>

                        <div className="listing-banner-decoration">
                            <span>NOVA</span>
                        </div>
                    </div>
                </section>

                <section className="container listing-section">
                    <button
                        type="button"
                        className="mobile-filter-toggle"
                        onClick={() => setFiltersOpen(!filtersOpen)}
                    >
                        <SlidersHorizontal size={17} />
                        Filters
                        <ChevronDown
                            size={17}
                            className={filtersOpen ? 'filter-chevron-open' : ''}
                        />
                    </button>

                    <aside
                        className={`listing-sidebar ${
                            filtersOpen ? 'listing-sidebar-open' : ''
                        }`}
                    >
                        <div className="listing-sidebar-heading">
                            <h2>Filters</h2>
                            <button
                                type="button"
                                onClick={() => {
                                    setSelectedCategories([])
                                    setMinPrice('')
                                    setMaxPrice('')
                                    setSearchTerm('')
                                }}
                            >
                                Clear All
                            </button>
                        </div>

                        <div className="listing-filter-group">
                            <h3>Categories</h3>

                            <label>
                                <input
                                    type="checkbox"
                                    checked={selectedCategories.length === 0}
                                    onChange={() => setSelectedCategories([])}
                                />
                                All Products
                            </label>

                            {categories.map((category) => (
                                <label key={category}>
                                    <input
                                        type="checkbox"
                                        checked={selectedCategories.includes(category)}
                                        onChange={() => handleCategoryChange(category)}
                                    />
                                    {category}
                                </label>
                            ))}
                        </div>

                        <div className="listing-filter-group">
                            <h3>Price Range</h3>

                            <div className="price-inputs">
                                <input
                                    type="number"
                                    placeholder="Min Price"
                                    value={minPrice}
                                    onChange={(e) => setMinPrice(e.target.value)}
                                />

                                <input
                                    type="number"
                                    placeholder="Max Price"
                                    value={maxPrice}
                                    onChange={(e) => setMaxPrice(e.target.value)}
                                />
                            </div>
                            {isPriceRangeInvalid && (
                                <p className="price-error">
                                    Please enter a valid price range.
                                </p>
                            )}
                        </div>

                        <div className="listing-filter-group">
                            <h3>Brands</h3>
                            <p>Brand filters coming soon</p>
                        </div>
                    </aside>

                    <div className="listing-products-area">
                        <div className="listing-toolbar">
                            <div>
                                <h2>Explore Our Products</h2>
                                <p>
                                    Find the perfect products for your lifestyle.
                                </p>
                            </div>

                            <select defaultValue="featured">
                                <option value="featured">Featured</option>
                                <option value="low-high">
                                    Price: Low to High
                                </option>
                                <option value="high-low">
                                    Price: High to Low
                                </option>
                            </select>
                        </div>

                        <div className="product-grid">
                            {filteredProducts.map((product) => (
                                <article
                                    className="product-card"
                                    key={product.id}
                                >
                                    <div className="product-image">
                                        <img
                                            src={product.image}
                                            alt={product.name}
                                        />

                                        {product.tag && (
                                            <span className="product-tag">
                                                {product.tag}
                                            </span>
                                        )}
                                    </div>

                                    <div className="product-info">
                                        <span className="product-category">
                                            {product.category}
                                        </span>

                                        <h3>{product.name}</h3>

                                        <div className="product-rating">
                                            <Star
                                                size={13}
                                                fill="currentColor"
                                            />
                                            <strong>{product.rating}</strong>
                                            <span>
                                                ({product.reviews} reviews)
                                            </span>
                                        </div>

                                        <div className="product-price">
                                            <strong>${product.price}</strong>

                                            {product.oldPrice && (
                                                <del>
                                                    ${product.oldPrice}
                                                </del>
                                            )}
                                        </div>
                                    </div>
                                </article>
                            ))}
                        </div>
                    </div>
                </section>
            </main>

            <Footer />
        </>
    )
}

export default ProductListingPage
