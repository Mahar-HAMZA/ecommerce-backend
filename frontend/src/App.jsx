
import { useState } from 'react'
import {
    Search,
    UserRound,
    ShoppingBag,
    ArrowRight,
    Heart,
    Plus,
    Star,
    Truck,
    ShieldCheck,
    Headphones,
    RefreshCw,
    X,
} from 'lucide-react'
import './App.css'

const categories = [
    {
        name: 'Electronics',
        image: 'https://images.unsplash.com/photo-1498049794561-7780e7231661?w=700',
        count: 'Technology & gadgets',
    },
    {
        name: 'Fashion',
        image: 'https://images.unsplash.com/photo-1483985988355-763728e1935b?w=700',
        count: 'Clothing & footwear',
    },
    {
        name: 'Accessories',
        image: 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=700',
        count: 'Everyday essentials',
    },
    {
        name: 'Home & Living',
        image: 'https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?w=700',
        count: 'For your living space',
    },
]

const products = [
    {
        id: 1,
        name: 'Premium Wireless Headphones',
        category: 'Electronics',
        price: '$89.00',
        oldPrice: '$119.00',
        rating: '4.9',
        reviews: '128',
        tag: 'BESTSELLER',
        image: 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=800',
    },
    {
        id: 2,
        name: 'Classic Minimal Watch',
        category: 'Accessories',
        price: '$120.00',
        oldPrice: '',
        rating: '4.8',
        reviews: '96',
        tag: 'POPULAR',
        image: 'https://images.unsplash.com/photo-1524592094714-0f0654e20314?w=800',
    },
    {
        id: 3,
        name: 'Everyday Sneakers',
        category: 'Fashion',
        price: '$75.00',
        oldPrice: '$95.00',
        rating: '4.7',
        reviews: '84',
        tag: 'SALE',
        image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=800',
    },
    {
        id: 4,
        name: 'Urban Travel Backpack',
        category: 'Accessories',
        price: '$65.00',
        oldPrice: '',
        rating: '4.8',
        reviews: '73',
        tag: 'NEW',
        image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=800',
    },
    {
        id: 5,
        name: 'Modern Wireless Speaker',
        category: 'Electronics',
        price: '$59.00',
        oldPrice: '$79.00',
        rating: '4.8',
        reviews: '64',
        tag: 'FEATURED',
        image: 'https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=800',
    },
    {
        id: 6,
        name: 'Contemporary Living Chair',
        category: 'Home & Living',
        price: '$149.00',
        oldPrice: '',
        rating: '4.9',
        reviews: '42',
        tag: 'NEW',
        image: 'https://images.unsplash.com/photo-1598300056393-4aac492f4344?w=800',
    },
    {
        id: 7,
        name: 'Classic Everyday Jacket',
        category: 'Fashion',
        price: '$95.00',
        oldPrice: '$115.00',
        rating: '4.7',
        reviews: '51',
        tag: 'POPULAR',
        image: 'https://images.unsplash.com/photo-1551028719-00167b16eac5?w=800',
    },
    {
        id: 8,
        name: 'Minimal Table Lamp',
        category: 'Home & Living',
        price: '$48.00',
        oldPrice: '',
        rating: '4.8',
        reviews: '38',
        tag: 'FEATURED',
        image: 'https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=800',
    },
]

function App() {
    const [selectedCategory, setSelectedCategory] = useState('All')
    const [searchTerm, setSearchTerm] = useState('')
    const [wishlist, setWishlist] = useState([])
    const [cartCount, setCartCount] = useState(0)
    const [menuOpen, setMenuOpen] = useState(false)

    const filteredProducts = products.filter((product) => {
        const matchesCategory =
            selectedCategory === 'All' || product.category === selectedCategory

        const matchesSearch = product.name
            .toLowerCase()
            .includes(searchTerm.toLowerCase())

        return matchesCategory && matchesSearch
    })

    const selectCategory = (category) => {
        setSelectedCategory(category)
        document.getElementById('products')?.scrollIntoView({ behavior: 'smooth' })
    }

    const toggleWishlist = (productId) => {
        setWishlist((current) =>
            current.includes(productId)
                ? current.filter((id) => id !== productId)
                : [...current, productId],
        )
    }

    return (
        <div className="store">
            <div className="announcement-bar">
                <span>Complimentary shipping on orders over $100</span>
                <a href="#products">Discover more <ArrowRight size={13} /></a>
            </div>

            <header className="navbar">
                <a href="#home" className="logo" aria-label="NOVA home">
                    NOVA<span>.</span>
                </a>

                <button
                    className="mobile-menu-button"
                    onClick={() => setMenuOpen(!menuOpen)}
                    aria-label={menuOpen ? 'Close navigation' : 'Open navigation'}
                >
                    {menuOpen ? <X size={23} /> : <span className="menu-lines">☰</span>}
                </button>

                <nav className={`nav-links ${menuOpen ? 'nav-open' : ''}`} aria-label="Main navigation">
                    <a className="active" href="#home" onClick={() => setMenuOpen(false)}>Home</a>
                    <a href="#categories" onClick={() => setMenuOpen(false)}>Categories</a>
                    <a href="#products" onClick={() => setMenuOpen(false)}>Shop</a>
                    <a href="#about" onClick={() => setMenuOpen(false)}>Our Story</a>
                    <a href="#footer" onClick={() => setMenuOpen(false)}>Contact</a>
                </nav>

                <div className="nav-actions">
                    <form className="search-box" onSubmit={(event) => {
                        event.preventDefault()
                        document.getElementById('products')?.scrollIntoView({ behavior: 'smooth' })
                    }}>
                        <Search size={17} />
                        <input
                            type="search"
                            placeholder="Search products..."
                            aria-label="Search products"
                            value={searchTerm}
                            onChange={(event) => {
                                setSearchTerm(event.target.value)
                                setSelectedCategory('All')
                            }}
                        />
                    </form>

                    <a href="#login" className="icon-action" aria-label="My account">
                        <UserRound size={21} />
                    </a>

                    <a href="#cart" className="icon-action bag-action" aria-label="Shopping bag">
                        <ShoppingBag size={21} />
                        <span className="bag-count">{cartCount}</span>
                    </a>
                </div>
            </header>

            <main>
                <section className="hero" id="home">
                    <div className="hero-overlay" />

                    <div className="hero-content">
            <span className="hero-kicker">
              <span className="kicker-line" />
              THE ART OF EVERYDAY SHOPPING
            </span>

                        <h1>
                            Find what you love.
                            <br />
                            <span>Love what you find.</span>
                        </h1>

                        <p>
                            Discover a world of thoughtfully selected products.
                            From everyday essentials to things that make life special,
                            find it all in one place.
                        </p>

                        <div className="hero-buttons">
                            <a href="#categories" className="primary-button">
                                Explore Collection <ArrowRight size={17} />
                            </a>
                            <a href="#about" className="hero-text-link">
                                Discover NOVA
                            </a>
                        </div>

                        <div className="hero-note">
                            <span className="hero-note-dot" />
                            A better shopping experience starts here.
                        </div>
                    </div>

                    <div className="hero-side-label">
                        DISCOVER YOUR NEXT FAVORITE
                    </div>

                    <a href="#categories" className="hero-scroll">
                        <span>SCROLL TO EXPLORE</span>
                        <span className="scroll-line" />
                    </a>
                </section>

                <section className="benefits">
                    <div className="benefit-item">
                        <Truck />
                        <div>
                            <h3>Free Shipping</h3>
                            <p>On qualifying orders</p>
                        </div>
                    </div>

                    <div className="benefit-item">
                        <ShieldCheck />
                        <div>
                            <h3>Secure Shopping</h3>
                            <p>Your safety matters</p>
                        </div>
                    </div>

                    <div className="benefit-item">
                        <RefreshCw />
                        <div>
                            <h3>Easy Returns</h3>
                            <p>A simple return process</p>
                        </div>
                    </div>

                    <div className="benefit-item">
                        <Headphones />
                        <div>
                            <h3>Customer Support</h3>
                            <p>Here when you need us</p>
                        </div>
                    </div>
                </section>

                <section className="categories-section section-container" id="categories">
                    <div className="section-heading">
                        <div>
                            <span className="section-kicker">EXPLORE NOVA</span>
                            <h2>Explore our <span>collections.</span></h2>
                            <p>Discover something you'll love in every category.</p>
                        </div>

                        <button className="outline-link" onClick={() => selectCategory('All')}>
                            Explore all products <ArrowRight size={17} />
                        </button>
                    </div>

                    <div className="category-circles">
                        {categories.map((category) => (
                            <button
                                className={`category-circle-card ${
                                    selectedCategory === category.name ? 'selected' : ''
                                }`}
                                key={category.name}
                                onClick={() => selectCategory(category.name)}
                            >
                <span className="category-circle-image">
                  <img src={category.image} alt={category.name} />
                  <span className="category-circle-arrow">
                    <ArrowRight size={19} />
                  </span>
                </span>
                                <strong>{category.name}</strong>
                                <span className="category-circle-description">{category.count}</span>
                            </button>
                        ))}
                    </div>
                </section>

                <section className="products-section" id="products">
                    <div className="section-container">
                        <div className="section-heading">
                            <div>
                                <span className="section-kicker">THE NOVA EDIT</span>
                                <h2>
                                    {selectedCategory === 'All'
                                        ? <>Favorites worth <span>finding.</span></>
                                        : <>Explore <span>{selectedCategory}.</span></>}
                                </h2>
                                <p>
                                    {selectedCategory === 'All'
                                        ? 'A selection of products to get your shopping started.'
                                        : `Discover our ${selectedCategory} collection.`}
                                </p>
                            </div>

                            <button className="outline-link" onClick={() => setSelectedCategory('All')}>
                                View all products <ArrowRight size={17} />
                            </button>
                        </div>

                        <div className="product-grid">
                            {filteredProducts.length > 0 ? (
                                filteredProducts.map((product) => (
                                    <article className="product-card" key={product.id}>
                                        <div className="product-image">
                                            <img src={product.image} alt={product.name} />
                                            <span className="product-tag">{product.tag}</span>

                                            <button
                                                className={`wishlist-button ${wishlist.includes(product.id) ? 'wishlisted' : ''}`}
                                                onClick={() => toggleWishlist(product.id)}
                                                aria-label={`Add ${product.name} to wishlist`}
                                            >
                                                <Heart size={19} fill={wishlist.includes(product.id) ? 'currentColor' : 'none'} />
                                            </button>
                                        </div>

                                        <div className="product-info">
                                            <span className="product-category">{product.category}</span>
                                            <h3>{product.name}</h3>

                                            <div className="product-rating">
                                                <Star size={13} fill="currentColor" />
                                                <strong>{product.rating}</strong>
                                                <span className="review-count">({product.reviews} reviews)</span>
                                            </div>

                                            <div className="product-bottom">
                                                <div className="product-prices">
                                                    <strong>{product.price}</strong>
                                                    {product.oldPrice && <del>{product.oldPrice}</del>}
                                                </div>

                                                <button
                                                    className="add-button"
                                                    onClick={() => setCartCount((count) => count + 1)}
                                                    aria-label={`Add ${product.name} to cart`}
                                                >
                                                    <Plus size={19} />
                                                </button>
                                            </div>
                                        </div>
                                    </article>
                                ))
                            ) : (
                                <div className="no-products">
                                    <Search size={30} />
                                    <h3>No products found</h3>
                                    <p>Try another search or explore all products.</p>
                                    <button className="primary-button" onClick={() => {
                                        setSearchTerm('')
                                        setSelectedCategory('All')
                                    }}>
                                        Show all products <ArrowRight size={17} />
                                    </button>
                                </div>
                            )}
                        </div>
                    </div>
                </section>

                <section className="story-section section-container" id="about">
                    <div className="story-image">
                        <img
                            src="https://images.unsplash.com/photo-1607082349566-187342175e2f?w=1100"
                            alt="Beautifully arranged shopping bags"
                        />
                        <div className="story-image-caption">
                            <span>THE NOVA EXPERIENCE</span>
                            <strong>Discover something different.</strong>
                        </div>
                    </div>

                    <div className="story-content">
                        <span className="section-kicker">MORE THAN JUST SHOPPING</span>
                        <h2>
                            Everyday finds.
                            <br />
                            <span>Exceptional moments.</span>
                        </h2>

                        <p>
                            We believe finding the right product should feel effortless.
                            Explore a carefully presented collection and enjoy a shopping
                            experience designed around you.
                        </p>

                        <a href="#products" className="primary-button">
                            Start exploring <ArrowRight size={17} />
                        </a>
                    </div>
                </section>

                <section className="newsletter">
                    <div className="newsletter-content">
                        <span className="newsletter-kicker">THE NOVA NEWSLETTER</span>
                        <h2>Good things are worth sharing.</h2>
                        <p>
                            Be the first to hear about new arrivals, featured collections
                            and updates from NOVA.
                        </p>

                        <form
                            className="newsletter-form"
                            onSubmit={(event) => event.preventDefault()}
                        >
                            <input
                                type="email"
                                placeholder="Your email address"
                                aria-label="Your email address"
                                required
                            />
                            <button type="submit">Join NOVA <ArrowRight size={17} /></button>
                        </form>

                        <span className="newsletter-small">
              Just the good stuff. No unnecessary emails.
            </span>
                    </div>
                </section>
            </main>

            <footer className="footer" id="footer">
                <div className="footer-main">
                    <div className="footer-brand">
                        <a href="#home" className="logo">NOVA<span>.</span></a>
                        <p>
                            Discover more. Shop better. Find the things that make
                            everyday life a little more special.
                        </p>
                    </div>

                    <div className="footer-column">
                        <h3>Discover</h3>
                        <a href="#home">Home</a>
                        <a href="#categories">Categories</a>
                        <a href="#products">Shop Products</a>
                    </div>

                    <div className="footer-column">
                        <h3>Customer Support</h3>
                        <a href="#footer">Contact Us</a>
                        <a href="#footer">Shipping & Delivery</a>
                        <a href="#footer">Returns & Refunds</a>
                    </div>

                    <div className="footer-column">
                        <h3>Your Account</h3>
                        <a href="#login">Sign In</a>
                        <a href="#register">Create Account</a>
                        <a href="#cart">Shopping Bag</a>
                    </div>
                </div>

                <div className="footer-bottom">
                    <p>© 2026 NOVA. All rights reserved.</p>
                    <p>Thoughtfully designed for everyday shopping.</p>
                </div>
            </footer>
        </div>
    )
}

export default App
