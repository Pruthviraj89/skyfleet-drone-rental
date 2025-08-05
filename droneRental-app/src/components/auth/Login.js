import React, { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';

const Login = () => {
  const { login } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [isLoading, setIsLoading] = useState(false);

  // Get the intended destination from location state or default to home
  const from = location.state?.from?.pathname || '/';

  const initialValues = {
    email: '',
    password: ''
  };

  const validationSchema = Yup.object({
    email: Yup.string()
      .email('Invalid email address')
      .required('Email is required'),
    password: Yup.string()
      .min(6, 'Password must be at least 6 characters')
      .required('Password is required')
  });

  const handleSubmit = async (values, { setSubmitting, setFieldError }) => {
    setIsLoading(true);
    try {
      const success = await login(values.email, values.password);
      if (success) {
        navigate(from, { replace: true });
        console.log(navigate);
      }
    } catch (error) {
      if (error.response?.data?.errors) {
        error.response.data.errors.forEach(err => {
          setFieldError(err.field, err.message);
        });
      }
    } finally {
      setIsLoading(false);
      setSubmitting(false);
    }
  };

  return (
    <div className="login-page py-5">
      <div className="container">
        <div className="row justify-content-center">
          <div className="col-md-6 col-lg-5">
            <div className="card card-custom">
              <div className="card-header text-center bg-primary-custom text-white">
                <h3 className="mb-0">
                  <i className="fas fa-sign-in-alt me-2"></i>
                  Welcome Back
                </h3>
                <p className="mb-0 mt-2">Sign in to your SkyFleet Rentals account</p>
              </div>
              <div className="card-body p-4">
                <Formik
                  initialValues={initialValues}
                  validationSchema={validationSchema}
                  onSubmit={handleSubmit}
                >
                  {({ isSubmitting, errors, touched }) => (
                    <Form>
                      {/* Email Field */}
                      <div className="mb-3">
                        <label htmlFor="email" className="form-label fw-bold">
                          <i className="fas fa-envelope me-2"></i>
                          Email Address
                        </label>
                        <Field
                          type="email"
                          id="email"
                          name="email"
                          className={`form-control ${
                            errors.email && touched.email ? 'is-invalid' : ''
                          }`}
                          placeholder="Enter your email"
                        />
                        <ErrorMessage name="email" component="div" className="invalid-feedback" />
                      </div>

                      {/* Password Field */}
                      <div className="mb-3">
                        <label htmlFor="password" className="form-label fw-bold">
                          <i className="fas fa-lock me-2"></i>
                          Password
                        </label>
                        <Field
                          type="password"
                          id="password"
                          name="password"
                          className={`form-control ${
                            errors.password && touched.password ? 'is-invalid' : ''
                          }`}
                          placeholder="Enter your password"
                        />
                        <ErrorMessage name="password" component="div" className="invalid-feedback" />
                      </div>

                      {/* Remember Me & Forgot Password */}
                      <div className="row mb-3">
                        <div className="col-6">
                          <div className="form-check">
                            <Field
                              type="checkbox"
                              id="rememberMe"
                              name="rememberMe"
                              className="form-check-input"
                            />
                            <label className="form-check-label" htmlFor="rememberMe">
                              Remember me
                            </label>
                          </div>
                        </div>
                        <div className="col-6 text-end">
                          <Link to="/forgot-password" className="text-decoration-none">
                            Forgot Password?
                          </Link>
                        </div>
                      </div>

                      {/* Submit Button */}
                      <div className="d-grid mb-3">
                        <button
                          type="submit"
                          className="btn btn-primary-custom btn-lg"
                          disabled={isSubmitting || isLoading}
                        >
                          {isLoading ? (
                            <>
                              <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                              Signing In...
                            </>
                          ) : (
                            <>
                              <i className="fas fa-sign-in-alt me-2"></i>
                              Sign In
                            </>
                          )}
                        </button>
                      </div>

                      {/* Divider */}
                      <div className="text-center mb-3">
                        <span className="text-muted">or</span>
                      </div>

                      {/* Social Login Buttons */}
                      <div className="d-grid gap-2 mb-3">
                        <button type="button" className="btn btn-outline-dark">
                          <i className="fab fa-google me-2"></i>
                          Continue with Google
                        </button>
                        <button type="button" className="btn btn-outline-primary">
                          <i className="fab fa-facebook-f me-2"></i>
                          Continue with Facebook
                        </button>
                      </div>

                      {/* Register Link */}
                      <div className="text-center">
                        <p className="mb-0">
                          Don't have an account?{' '}
                          <Link to="/register" className="text-decoration-none fw-bold">
                            Sign up here
                          </Link>
                        </p>
                      </div>
                    </Form>
                  )}
                </Formik>
              </div>
            </div>

            {/* Additional Info */}
            <div className="text-center mt-4">
              <div className="row">
                <div className="col-md-4 mb-2">
                  <div className="d-flex align-items-center justify-content-center">
                    <i className="fas fa-shield-alt text-success me-2"></i>
                    <small className="text-muted">Secure Login</small>
                  </div>
                </div>
                <div className="col-md-4 mb-2">
                  <div className="d-flex align-items-center justify-content-center">
                    <i className="fas fa-clock text-info me-2"></i>
                    <small className="text-muted">24/7 Support</small>
                  </div>
                </div>
                <div className="col-md-4 mb-2">
                  <div className="d-flex align-items-center justify-content-center">
                    <i className="fas fa-mobile-alt text-warning me-2"></i>
                    <small className="text-muted">Mobile Friendly</small>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login; 