import React from "react";

const Footer = () => {
  return (
    <footer className="bg-gray-900 text-gray-200 py-10 flex flex-col items-center justify-center p-20">
      <div className="container mx-auto grid grid-cols-1 md:grid-cols-3 gap-8 px-6 md:px-0">
        <div>
          <h3 className="text-xl font-semibold mb-4">About Us</h3>
          <p className="text-sm">
            Welcome to [Your Salon Name], your one-stop destination for premium
            salon services. Book appointments with ease and experience luxury at
            your fingertips.
          </p>
        </div>

        <div>
          <h3 className="text-xl font-semibold mb-4">Quick Links</h3>
          <ul className="space-y-2 text-sm">
            <li>
              <a href="/" className="hover:text-gray-400">
                Home
              </a>
            </li>
            <li>
              <a href="/" className="hover:text-gray-400">
                Services
              </a>
            </li>
            <li>
              <a href="/" className="hover:text-gray-400">
                Book Appointment
              </a>
            </li>
            <li>
              <a href="/" className="hover:text-gray-400">
                About Us
              </a>
            </li>
            <li>
              <a href="/" className="hover:text-gray-400">
                Contact
              </a>
            </li>
          </ul>
        </div>

        <div>
          <h3 className="text-xl font-semibold mb-4">Contact Us</h3>
          <ul className="space-y-2 text-sm">
            <li>
              <i className="fas fa-phone-alt"></i> +1 234 567 890
            </li>
            <li>
              <i className="fas fa-envelope"></i> support@salon.com
            </li>
            <li>
              <i className="fas fa-map-marker-alt"></i> 123 Salon Street, City,
              Country
            </li>
          </ul>
          <div className="mt-4 flex space-x-4">
            <a href="/" className="text-gray-400 hover:text-gray-200">
              <i className="fab fa-facebook-f"></i>
            </a>
            <a href="/" className="text-gray-400 hover:text-gray-200">
              <i className="fab fa-twitter"></i>
            </a>
            <a href="/" className="text-gray-400 hover:text-gray-200">
              <i className="fab fa-instagram"></i>
            </a>
            <a href="/" className="text-gray-400 hover:text-gray-200">
              <i className="fab fa-linkedin-in"></i>
            </a>
          </div>
        </div>
      </div>

      <div className="border-t border-gray-700 mt-8 pt-4 text-center text-sm">
        &copy; 2025 [Your Salon Name]. All Rights Reserved.
      </div>
    </footer>
  );
};

export default Footer;
