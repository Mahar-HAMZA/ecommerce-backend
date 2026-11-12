
import { Link } from 'react-router-dom'
import novaLogo from '../assets/nova-logo.svg'

const categories = [
    'Electronics',
    'Fashion',
    'Accessories',
    'Home & Living',
]

function Footer() {
    return (
        <footer className="site-footer" id="footer">
            <div className="container footer-main">
                <div className="footer-brand">
                    <Link to="/">
                        <img src={novaLogo} alt="NOVA" />
                    </Link>

                    <p>
                        Discover products that make everyday life better.
                        Shop smarter. Live better.
                    </p>
                </div>

                <div className="footer-column">
                    <h3>Explore</h3>
                    <Link to="/">Home</Link>
                    <Link to="/products">Shop</Link>
                    <Link to="/#categories">Categories</Link>
                    <Link to="/#story">About NOVA</Link>
                </div>

                <div className="footer-column">
                    <h3>Categories</h3>

                    {categories.map((category) => (
                        <Link
                            to="/products"
                            key={category}
                        >
                            {category}
                        </Link>
                    ))}
                </div>

                <div className="footer-column">
                    <h3>Customer Care</h3>
                    <a href="mailto:support@nova.example">Contact Us</a>
                    <Link to="/#footer">Help Center</Link>
                    <Link to="/#footer">Privacy Policy</Link>
                    <Link to="/#footer">Terms & Conditions</Link>
                </div>
            </div>

            <div className="footer-bottom container">
                <p>
                    © {new Date().getFullYear()} NOVA. All rights reserved.
                </p>

                <p>Shop smarter. Live better.</p>
            </div>
        </footer>
    )
}

export default Footer
