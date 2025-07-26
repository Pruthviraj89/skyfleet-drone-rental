import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

// Import components
import Navbar from './components/layout/Navbar';
import Footer from './components/layout/Footer';
import Home from './components/pages/Home';
import DroneList from './components/pages/DroneList';
import DroneDetail from './components/pages/DroneDetail';
import BookingForm from './components/pages/BookingForm';
import MyBookings from './components/pages/MyBookings';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import AdminDashboard from './components/admin/AdminDashboard';

// Import context
import { AuthProvider } from './context/AuthContext';
import { BookingProvider } from './context/BookingContext';

function App() {
  return (
    <AuthProvider>
      <BookingProvider>
        <Router>
          <div className="App">
            <Navbar />
            <main className="min-vh-100">
              <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/drones" element={<DroneList />} />
                <Route path="/drones/:id" element={<DroneDetail />} />
                <Route path="/book/:droneId" element={<BookingForm />} />
                <Route path="/my-bookings" element={<MyBookings />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/admin" element={<AdminDashboard />} />
              </Routes>
            </main>
            <Footer />
            
            {/* Toast Notifications */}
            <ToastContainer
              position="top-right"
              autoClose={5000}
              hideProgressBar={false}
              newestOnTop={false}
              closeOnClick
              rtl={false}
              pauseOnFocusLoss
              draggable
              pauseOnHover
              theme="light"
              className="toast-custom"
            />
          </div>
        </Router>
      </BookingProvider>
    </AuthProvider>
  );
}

export default App; 