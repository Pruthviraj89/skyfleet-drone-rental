import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';

const PublicNavbar = () => {
  const location = useLocation();
  const [isNavCollapsed, setIsNavCollapsed] = useState(true);

  const handleNavCollapse = () => setIsNavCollapsed(!isNavCollapsed);

  const isActive = (path) => {
    return location.pathname === path ? 'active' : '';
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark navbar-custom">
      <div className="container">
        {/* Brand */}
        <Link className="navbar-brand" to="/">
          <i className="fas fa-drone me-2"></i>
          SkyFleet Rentals
        </Link>

        {/* Mobile Toggle */}
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#publicNavbarNav"
          aria-controls="publicNavbarNav"
          aria-expanded={!isNavCollapsed ? true : false}
          aria-label="Toggle navigation"
          onClick={handleNavCollapse}
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        {/* Navigation Links */}
        <div className={`${isNavCollapsed ? 'collapse' : ''} navbar-collapse`} id="publicNavbarNav">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            {/* Home */}
            <li className="nav-item">
              <Link className={`nav-link ${isActive('/')}`} to="/">
                <i className="fas fa-home me-1"></i>
                Home
              </Link>
            </li>

            {/* Browse Drones */}
            <li className="nav-item">
              <Link className={`nav-link ${isActive('/drones')}`} to="/drones">
                <i className="fas fa-drone me-1"></i>
                Browse Drones
              </Link>
            </li>

            {/* About */}
            <li className="nav-item">
              <Link className={`nav-link ${isActive('/about')}`} to="/about">
                <i className="fas fa-info-circle me-1"></i>
                About
              </Link>
            </li>

            {/* Services */}
            <li className="nav-item">
              <Link className={`nav-link ${isActive('/services')}`} to="/services">
                <i className="fas fa-cogs me-1"></i>
                Services
              </Link>
            </li>

            {/* Contact */}
            <li className="nav-item">
              <Link className={`nav-link ${isActive('/contact')}`} to="/contact">
                <i className="fas fa-envelope me-1"></i>
                Contact
              </Link>
            </li>

            {/* Help */}
            <li className="nav-item">
              <Link className={`nav-link ${isActive('/help')}`} to="/help">
                <i className="fas fa-question-circle me-1"></i>
                Help
              </Link>
            </li>
          </ul>

          {/* Authentication Buttons */}
          <ul className="navbar-nav">
            <li className="nav-item">
              <Link className="nav-link" to="/login">
                <i className="fas fa-sign-in-alt me-1"></i>
                Login
              </Link>
            </li>
            <li className="nav-item">
              <Link className="btn btn-outline-light btn-sm ms-2" to="/register">
                <i className="fas fa-user-plus me-1"></i>
                Register
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default PublicNavbar; 