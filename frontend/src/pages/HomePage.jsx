
import { useState } from 'react'
import {
    Search,
    ArrowRight,
    Heart,
    Plus,
    Star,
    Truck,
    ShieldCheck,
    Headphones,
    Menu,
    CreditCard,
    ArrowUpRight,
} from 'lucide-react'

import products from '../data/products'
import Header from '../components/Header'
import Footer from '../components/Footer'
import '../App.css'

const categories = [
    {
        name: 'Electronics',
        count: '120+ Products',
        image:
            'https://images.unsplash.com/photo-1498049794561-7780e7231661?w=600',
    },
    {
        name: 'Fashion',
        count: '250+ Products',
        image:
            'https://images.unsplash.com/photo-1483985988355-763728e1935b?w=600',
    },
    {
        name: 'Accessories',
        count: '85+ Products',
        image:
            'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600',
    },
    {
        name: 'Home & Living',
        count: '95+ Products',
        image:
            'https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?w=600',
    },
]

const promotions = [
    {
        title: 'Refresh Your Space',
        subtitle: 'Thoughtful details for everyday living.',
        category: 'Home & Living',
        image:
            'https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?w=900',
        className: 'promo-home',
    },
    {
        title: 'Everyday Style',
        subtitle: 'Discover pieces made to stand out.',
        category: 'Fashion',
        image:
            'https://images.unsplash.com/photo-1483985988355-763728e1935b?w=900',
        className: 'promo-fashion',
    },
    {
        title: 'Upgrade Your Tech',
        subtitle: 'Smart picks for modern living.',
        category: 'Electronics',
        image:
            'https://images.unsplash.com/photo-1498049794561-7780e7231661?w=900',
        className: 'promo-tech',
    },
]

function HomePage() {
    const [selectedCategory, setSelectedCategory] = useState('All')
    const [searchTerm, setSearchTerm] = useState('')
    const [wishlist, setWishlist] = useState([])
    const [cartCount, setCartCount] = useState(0)

    const filteredProducts = products.filter((product) => {
        const matchesCategory =
            selectedCategory === 'All' ||
            product.category === selectedCategory

        const matchesSearch = product.name
            .toLowerCase()
            .includes(searchTerm.toLowerCase())

        return matchesCategory && matchesSearch
    })

    const selectCategory = (category) => {
        setSelectedCategory(category)

        document.getElementById('products')?.scrollIntoView({
            behavior: 'smooth',
        })
    }

    const toggleWishlist = (productId) => {
        setWishlist((currentWishlist) =>
            currentWishlist.includes(productId)
                ? currentWishlist.filter((id) => id !== productId)
                : [...currentWishlist, productId],
        )
    }

    const handleNewsletterSubmit = (event) => {
        event.preventDefault()
    }

    return (
        <>
            <Header
                searchTerm={searchTerm}
                onSearchChange={setSearchTerm}
                wishlistCount={wishlist.length}
                cartCount={cartCount}
            />

            <main>
                <section className="hero-section container" id="home">
                    <aside className="category-sidebar">
                        <div className="sidebar-heading">
                            <Menu size={19} />
                            <h2>Categories</h2>
                        </div>

                        <button onClick={() => selectCategory('All')}>
                            <span>All Products</span>
                            <ArrowRight size={16} />
                        </button>

                        {categories.map((category) => (
                            <button
                                key={category.name}
                                onClick={() => selectCategory(category.name)}
                            >
                                <span>{category.name}</span>
                                <ArrowRight size={16} />
                            </button>
                        ))}

                        <div className="sidebar-note">
                            <span>FIND YOUR NEXT FAVORITE</span>
                            <p>Discover products that make everyday life better.</p>
                            <a href="#products">
                                Explore now <ArrowUpRight size={15} />
                            </a>
                        </div>
                    </aside>

                    <div className="hero-banner">
                        <div className="hero-overlay" />

                        <div className="hero-content">
                            <span className="hero-eyebrow">
                                THE NOVA COLLECTION
                            </span>

                            <h1>
                                Everything you love,
                                <br />
                                all in one place.
                            </h1>

                            <p>
                                Discover thoughtfully selected products for your
                                lifestyle, your space, and everything in between.
                            </p>

                            <div className="hero-buttons">
                                <a href="#products" className="primary-button">
                                    Shop Now <ArrowRight size={17} />
                                </a>

                                <a href="#categories" className="secondary-button">
                                    Explore Categories
                                </a>
                            </div>

                            <div className="hero-bottom">
                                <span>01 / 03</span>
                                <div className="hero-progress">
                                    <span />
                                </div>
                                <span>Curated for everyday living</span>
                            </div>
                        </div>
                    </div>
                </section>

                <section className="benefits-section container">
                    <div className="benefit-item">
                        <div className="benefit-icon">
                            <Truck size={23} />
                        </div>
                        <div>
                            <h3>Convenient Delivery</h3>
                            <p>Shopping made simple</p>
                        </div>
                    </div>

                    <div className="benefit-item">
                        <div className="benefit-icon">
                            <ShieldCheck size={23} />
                        </div>
                        <div>
                            <h3>Secure Shopping</h3>
                            <p>Your security matters</p>
                        </div>
                    </div>

                    <div className="benefit-item">
                        <div className="benefit-icon">
                            <CreditCard size={23} />
                        </div>
                        <div>
                            <h3>Flexible Payments</h3>
                            <p>Convenient checkout</p>
                        </div>
                    </div>

                    <div className="benefit-item">
                        <div className="benefit-icon">
                            <Headphones size={23} />
                        </div>
                        <div>
                            <h3>Customer Support</h3>
                            <p>Here to help you</p>
                        </div>
                    </div>
                </section>

                <section className="promotions-section container">
                    <div className="section-heading">
                        <div>
                            <span className="section-eyebrow">EXPLORE NOVA</span>
                            <h2>Discover your next favorite</h2>
                            <p>
                                Explore collections selected for every part of your life.
                            </p>
                        </div>

                        <a href="#categories" className="text-link">
                            View Categories <ArrowRight size={17} />
                        </a>
                    </div>

                    <div className="promotion-grid">
                        {promotions.map((promotion) => (
                            <button
                                className={`promotion-card ${promotion.className}`}
                                key={promotion.category}
                                onClick={() => selectCategory(promotion.category)}
                            >
                                <img src={promotion.image} alt={promotion.title} />

                                <div className="promotion-overlay" />

                                <div className="promotion-content">
                                    <span>{promotion.category}</span>
                                    <h3>{promotion.title}</h3>
                                    <p>{promotion.subtitle}</p>
                                    <div className="promotion-link">
                                        Explore Collection <ArrowRight size={16} />
                                    </div>
                                </div>
                            </button>
                        ))}
                    </div>
                </section>

                <section className="categories-section container" id="categories">
                    <div className="section-heading">
                        <div>
                            <span className="section-eyebrow">SHOP BY CATEGORY</span>
                            <h2>Find what you're looking for</h2>
                            <p>Browse our collections and discover more.</p>
                        </div>
                    </div>

                    <div className="category-grid">
                        {categories.map((category) => (
                            <button
                                className="category-card"
                                key={category.name}
                                onClick={() => selectCategory(category.name)}
                            >
                                <div className="category-image">
                                    <img src={category.image} alt={category.name} />
                                    <span className="category-arrow">
                                        <ArrowUpRight size={19} />
                                    </span>
                                </div>

                                <h3>{category.name}</h3>
                                <p>{category.count}</p>
                            </button>
                        ))}
                    </div>
                </section>

                <section className="products-section" id="products">
                    <div className="container">
                        <div className="section-heading product-heading">
                            <div>
                                <span className="section-eyebrow">OUR COLLECTION</span>
                                <h2>Popular products</h2>
                                <p>Explore some of our favorite picks.</p>
                            </div>

                            <a href="#categories" className="text-link">
                                Browse Categories <ArrowRight size={17} />
                            </a>
                        </div>

                        <div className="product-filters">
                            {['All', ...categories.map((category) => category.name)].map(
                                (category) => (
                                    <button
                                        key={category}
                                        className={
                                            selectedCategory === category ? 'filter-active' : ''
                                        }
                                        onClick={() => setSelectedCategory(category)}
                                    >
                                        {category}
                                    </button>
                                ),
                            )}
                        </div>

                        {filteredProducts.length > 0 ? (
                            <div className="product-grid">
                                {filteredProducts.map((product) => (
                                    <article className="product-card" key={product.id}>
                                        <div className="product-image">
                                            <img src={product.image} alt={product.name} />

                                            <span className="product-tag">{product.tag}</span>

                                            <button
                                                className={`wishlist-button ${
                                                    wishlist.includes(product.id)
                                                        ? 'wishlist-active'
                                                        : ''
                                                }`}
                                                onClick={() => toggleWishlist(product.id)}
                                                aria-label="Toggle wishlist"
                                            >
                                                <Heart size={19} />
                                            </button>

                                            <button
                                                className="quick-add-button"
                                                onClick={() => setCartCount((count) => count + 1)}
                                            >
                                                <Plus size={17} /> Add to Cart
                                            </button>
                                        </div>

                                        <div className="product-info">
                                            <span className="product-category">
                                                {product.category}
                                            </span>

                                            <h3>{product.name}</h3>

                                            <div className="product-rating">
                                                <Star size={14} fill="currentColor" />
                                                <strong>{product.rating}</strong>
                                                <span>({product.reviews} reviews)</span>
                                            </div>

                                            <div className="product-price">
                                                <strong>${product.price.toFixed(2)}</strong>

                                                {product.oldPrice && (
                                                    <del>${product.oldPrice.toFixed(2)}</del>
                                                )}
                                            </div>
                                        </div>
                                    </article>
                                ))}
                            </div>
                        ) : (
                            <div className="empty-products">
                                <Search size={30} />
                                <h3>No products found</h3>
                                <p>
                                    Try another search term or select a different category.
                                </p>
                                <button
                                    className="primary-button"
                                    onClick={() => {
                                        setSearchTerm('')
                                        setSelectedCategory('All')
                                    }}
                                >
                                    Clear Filters
                                </button>
                            </div>
                        )}
                    </div>
                </section>

                <section className="story-section container" id="story">
                    <div className="story-image">
                        <img
                            src="https://images.unsplash.com/photo-1607082349566-187342175e2f?w=1100"
                            alt="NOVA shopping collection"
                        />
                    </div>

                    <div className="story-content">
                        <span className="section-eyebrow">THE NOVA EXPERIENCE</span>
                        <h2>
                            Better choices.
                            <br />
                            Better everyday living.
                        </h2>

                        <p>
                            At NOVA, we believe shopping should be simple, inspiring,
                            and enjoyable. Our goal is to bring together products
                            that add value to your everyday life.
                        </p>

                        <a href="#products" className="primary-button">
                            Discover NOVA <ArrowRight size={17} />
                        </a>
                    </div>
                </section>

                <section className="newsletter-section">
                    <div className="container newsletter-content">
                        <div>
                            <span className="section-eyebrow">STAY IN THE LOOP</span>
                            <h2>Something good is coming.</h2>
                            <p>
                                Subscribe to stay updated with new collections and
                                product highlights.
                            </p>
                        </div>

                        <form
                            className="newsletter-form"
                            onSubmit={handleNewsletterSubmit}
                        >
                            <input
                                type="email"
                                placeholder="Enter your email address"
                                required
                            />

                            <button type="submit">
                                Subscribe <ArrowRight size={17} />
                            </button>
                        </form>
                    </div>
                </section>
            </main>

            <Footer />
        </>
    )
}

export default HomePage
