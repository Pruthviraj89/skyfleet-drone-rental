import React from 'react';
import { Link } from 'react-router-dom';

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="footer">
      <div className="container">
        <div className="row">
          {/* Company Info */}
          <div className="col-lg-4 col-md-6 mb-4">
            <h5>
              <i className="fas fa-drone me-2"></i>
              SkyFleet Rentals
            </h5>
            <p className="text-muted">
              Premium drone rental service for professional and recreational use. 
              Experience the sky with our cutting-edge drone technology.
            </p>
            <div className="social-links">
              <a href="#" className="me-3" title="Facebook">
                <i className="fab fa-facebook-f"></i>
              </a>
              <a href="#" className="me-3" title="Twitter">
                <i className="fab fa-twitter"></i>
              </a>
              <a href="#" className="me-3" title="Instagram">
                <i className="fab fa-instagram"></i>
              </a>
              <a href="#" className="me-3" title="LinkedIn">
                <i className="fab fa-linkedin-in"></i>
              </a>
            </div>
          </div>

          {/* Quick Links */}
          <div className="col-lg-2 col-md-6 mb-4">
            <h5>Quick Links</h5>
            <ul className="list-unstyled">
              <li className="mb-2">
                <Link to="/">
                  <i className="fas fa-home me-2"></i>
                  Home
                </Link>
              </li>
              <li className="mb-2">
                <Link to="/drones">
                  <i className="fas fa-drone me-2"></i>
                  Drones
                </Link>
              </li>
              <li className="mb-2">
                <Link to="/about">
                  <i className="fas fa-info-circle me-2"></i>
                  About Us
                </Link>
              </li>
              <li className="mb-2">
                <Link to="/contact">
                  <i className="fas fa-envelope me-2"></i>
                  Contact
                </Link>
              </li>
            </ul>
          </div>

          {/* Services */}
          <div className="col-lg-2 col-md-6 mb-4">
            <h5>Services</h5>
            <ul className="list-unstyled">
              <li className="mb-2">
                <i className="fas fa-camera me-2"></i>
                Photography
              </li>
              <li className="mb-2">
                <i className="fas fa-video me-2"></i>
                Videography
              </li>
              <li className="mb-2">
                <i className="fas fa-map-marked-alt me-2"></i>
                Mapping
              </li>
              <li className="mb-2">
                <i className="fas fa-search me-2"></i>
                Inspection
              </li>
            </ul>
          </div>

          {/* Contact Info */}
          <div className="col-lg-4 col-md-6 mb-4">
            <h5>Contact Info</h5>
            <div className="contact-info">
              <p className="mb-2">
                <i className="fas fa-map-marker-alt me-2"></i>
                123 Drone Street, Tech City, TC 12345
              </p>
              <p className="mb-2">
                <i className="fas fa-phone me-2"></i>
                +1 (555) 123-4567
              </p>
              <p className="mb-2">
                <i className="fas fa-envelope me-2"></i>
                info@skyfleetrentals.com
              </p>
              <p className="mb-2">
                <i className="fas fa-clock me-2"></i>
                Mon-Fri: 9AM-6PM, Sat: 10AM-4PM
              </p>
            </div>
          </div>
        </div>

        {/* Bottom Footer */}
        <div className="row border-top pt-4">
          <div className="col-md-6">
            <p className="mb-0 text-muted">
              &copy; {currentYear} SkyFleet Rentals. All rights reserved.
            </p>
          </div>
          <div className="col-md-6 text-md-end">
            <ul className="list-inline mb-0">
              <li className="list-inline-item">
                <Link to="/privacy" className="text-muted">Privacy Policy</Link>
              </li>
              <li className="list-inline-item">
                <span className="text-muted">|</span>
              </li>
              <li className="list-inline-item">
                <Link to="/terms" className="text-muted">Terms of Service</Link>
              </li>
              <li className="list-inline-item">
                <span className="text-muted">|</span>
              </li>
              <li className="list-inline-item">
                <Link to="/faq" className="text-muted">FAQ</Link>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer; 