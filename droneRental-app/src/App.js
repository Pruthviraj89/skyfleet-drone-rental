import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

// Import layouts
import SmartLayout from './components/layout/SmartLayout';
import ProtectedRoute from './components/auth/ProtectedRoute';

// Import components
import Home from './components/public_users/Home';
import DroneList from './components/user_pages/DroneList';
import DroneDetail from './components/user_pages/DroneDetail';
import BookingForm from './components/user_pages/BookingForm';
import MyBookings from './components/user_pages/MyBookings';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import AdminDashboard from './components/admin/AdminDashboard';
import Contact from './components/public_users/Contact';
import About from './components/public_users/About';
import Help from './components/public_users/Help';
import Term from './components/public_users/Term';
import PrivacyPolicy from './components/public_users/PrivacyPolicy';

// Import admin pages
import UsersPage from './components/admin/UsersPage';
import DronesPage from './components/admin/DronesPage';
import BookingsPage from './components/admin/BookingsPage';
import PaymentsPage from './components/admin/PaymentsPage';

// Import context
import { AuthProvider } from './context/AuthContext';
import { BookingProvider } from './context/BookingContext';

function App() {
  return (
    <AuthProvider>
      <BookingProvider>
        <Router>
          <div className="App">
            <SmartLayout>
              <Routes>
                {/* Public Routes */}
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/contact" element={<Contact />} />
                <Route path="/about" element={<About />} /> 
                <Route path="/help" element={<Help />} />
                <Route path="/term" element={<Term />} />
                <Route path="/privacy" element={<PrivacyPolicy />} />
                
                {/* User Routes - Protected for authenticated users */}
                <Route path="/drones" element={
                  <ProtectedRoute allowedRoles={['USER', 'ADMIN']}>
                    <DroneList />
                  </ProtectedRoute>
                } />
                <Route path="/drones/:id" element={
                  <ProtectedRoute allowedRoles={['USER', 'ADMIN']}>
                    <DroneDetail />
                  </ProtectedRoute>
                } />
                <Route path="/book/:droneId" element={
                  <ProtectedRoute allowedRoles={['USER', 'ADMIN']}>
                    <BookingForm />
                  </ProtectedRoute>
                } />
                <Route path="/my-bookings" element={
                  <ProtectedRoute allowedRoles={['USER', 'ADMIN']}>
                    <MyBookings />
                  </ProtectedRoute>
                } />
                
                {/* Admin Routes - Protected for admin users only */}
                <Route path="/admin" element={
                  <ProtectedRoute allowedRoles={['ADMIN']}>
                    <AdminDashboard />
                  </ProtectedRoute>
                } />
                <Route path="/admin/users" element={
                  <ProtectedRoute allowedRoles={['ADMIN']}>
                    <UsersPage />
                  </ProtectedRoute>
                } />
                <Route path="/admin/drones" element={
                  <ProtectedRoute allowedRoles={['ADMIN']}>
                    <DronesPage />
                  </ProtectedRoute>
                } />
                <Route path="/admin/bookings" element={
                  <ProtectedRoute allowedRoles={['ADMIN']}>
                    <BookingsPage />
                  </ProtectedRoute>
                } />
                <Route path="/admin/payments" element={
                  <ProtectedRoute allowedRoles={['ADMIN']}>
                    <PaymentsPage />
                  </ProtectedRoute>
                } />
              </Routes>
            </SmartLayout>
            
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