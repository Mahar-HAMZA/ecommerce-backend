
import { useState } from 'react'
import {
    Search,
    UserRound,
    ShoppingBag,
    Heart,
    Truck,
    X,
    Menu,
    ChevronDown,
} from 'lucide-react'
import { Link } from 'react-router-dom'

import novaLogo from '../assets/nova-logo.svg'

function Header({ searchTerm, onSearchChange, wishlistCount, cartCount }) {
    const [menuOpen, setMenuOpen] = useState(false)

    return (
        <>
            <div className="announcement-bar">
                <div className="announcement-inner">
                    <p>
                        <Truck size={15} />
                        Discover something new with NOVA.
                    </p>

                    <div className="announcement-links">
                        <Link to="/products">Explore Products</Link>
                        <span>|</span>
                        <Link to="/#footer">Customer Support</Link>
                    </div>
                </div>
            </div>

            <header className="site-header">
                <div className="main-header container">
                    <Link to="/" className="brand-logo">
                        <img
                            src={novaLogo}
                            alt="NOVA - Shop smarter. Live better."
                        />
                    </Link>

                    <nav className={`main-nav ${menuOpen ? 'nav-open' : ''}`}>
                        <Link to="/" onClick={() => setMenuOpen(false)}>
                            Home
                        </Link>

                        <Link to="/#categories" onClick={() => setMenuOpen(false)}>
                            Categories <ChevronDown size={14} />
                        </Link>

                        <Link to="/products" onClick={() => setMenuOpen(false)}>
                            Shop
                        </Link>

                        <Link to="/#story" onClick={() => setMenuOpen(false)}>
                            About
                        </Link>

                        <Link to="/#footer" onClick={() => setMenuOpen(false)}>
                            Contact
                        </Link>
                    </nav>

                    <div className="header-actions">
                        <div className="header-search">
                            <Search size={18} />

                            <input
                                type="text"
                                placeholder="Search products..."
                                value={searchTerm}
                                onChange={(event) =>
                                    onSearchChange(event.target.value)
                                }
                            />
                        </div>

                        <Link
                            to="/products"
                            className="header-icon"
                            aria-label="Wishlist"
                        >
                            <Heart size={21} />

                            {wishlistCount > 0 && (
                                <span className="icon-count">
                                    {wishlistCount}
                                </span>
                            )}
                        </Link>

                        <Link
                            to="/products"
                            className="header-icon"
                            aria-label="Shopping cart"
                        >
                            <ShoppingBag size={21} />

                            {cartCount > 0 && (
                                <span className="icon-count">
                                    {cartCount}
                                </span>
                            )}
                        </Link>

                        <Link
                            to="/#footer"
                            className="header-icon account-icon"
                            aria-label="Account"
                        >
                            <UserRound size={21} />
                        </Link>

                        <button
                            className="mobile-menu-button"
                            onClick={() => setMenuOpen(!menuOpen)}
                            aria-label="Toggle navigation"
                        >
                            {menuOpen ? <X size={25} /> : <Menu size={25} />}
                        </button>
                    </div>
                </div>
            </header>
        </>
    )
}

export default Header
