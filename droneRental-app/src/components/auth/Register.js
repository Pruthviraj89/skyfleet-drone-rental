import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';

const Register = () => {
  const { register } = useAuth();
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const initialValues = {
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    password: '',
    confirmPassword: '',
    acceptTerms: false,
    marketingEmails: false
  };

  const validationSchema = Yup.object({
    firstName: Yup.string()
      .min(2, 'First name must be at least 2 characters')
      .max(50, 'First name must be less than 50 characters')
      .required('First name is required'),
    lastName: Yup.string()
      .min(2, 'Last name must be at least 2 characters')
      .max(50, 'Last name must be less than 50 characters')
      .required('Last name is required'),
    email: Yup.string()
      .email('Invalid email address')
      .required('Email is required'),
    phone: Yup.string()
      .matches(/^[\+]?[1-9][\d]{0,15}$/, 'Invalid phone number')
      .required('Phone number is required'),
    password: Yup.string()
      .min(8, 'Password must be at least 8 characters')
      .matches(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/, 'Password must contain at least one uppercase letter, one lowercase letter, and one number')
      .required('Password is required'),
    confirmPassword: Yup.string()
      .oneOf([Yup.ref('password'), null], 'Passwords must match')
      .required('Confirm password is required'),
    acceptTerms: Yup.boolean()
      .oneOf([true], 'You must accept the terms and conditions')
  });

  const handleSubmit = async (values, { setSubmitting, setFieldError }) => {
    setIsLoading(true);
    try {
      const userData = {
        firstName: values.firstName,
        lastName: values.lastName,
        email: values.email,
        phone: values.phone,
        password: values.password,
        marketingEmails: values.marketingEmails
      };

      const success = await register(userData);
      if (success) {
        navigate('/');
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
    <div className="register-page py-5">
      <div className="container">
        <div className="row justify-content-center">
          <div className="col-md-8 col-lg-6">
            <div className="card card-custom">
              <div className="card-header text-center bg-primary-custom text-white">
                <h3 className="mb-0">
                  <i className="fas fa-user-plus me-2"></i>
                  Create Account
                </h3>
                <p className="mb-0 mt-2">Join SkyFleet Rentals and start your drone adventure</p>
              </div>
              <div className="card-body p-4">
                <Formik
                  initialValues={initialValues}
                  validationSchema={validationSchema}
                  onSubmit={handleSubmit}
                >
                  {({ isSubmitting, errors, touched, values }) => (
                    <Form>
                      {/* Name Fields */}
                      <div className="row mb-3">
                        <div className="col-md-6">
                          <label htmlFor="firstName" className="form-label fw-bold">
                            <i className="fas fa-user me-2"></i>
                            First Name
                          </label>
                          <Field
                            type="text"
                            id="firstName"
                            name="firstName"
                            className={`form-control ${
                              errors.firstName && touched.firstName ? 'is-invalid' : ''
                            }`}
                            placeholder="Enter first name"
                          />
                          <ErrorMessage name="firstName" component="div" className="invalid-feedback" />
                        </div>
                        <div className="col-md-6">
                          <label htmlFor="lastName" className="form-label fw-bold">
                            <i className="fas fa-user me-2"></i>
                            Last Name
                          </label>
                          <Field
                            type="text"
                            id="lastName"
                            name="lastName"
                            className={`form-control ${
                              errors.lastName && touched.lastName ? 'is-invalid' : ''
                            }`}
                            placeholder="Enter last name"
                          />
                          <ErrorMessage name="lastName" component="div" className="invalid-feedback" />
                        </div>
                      </div>

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

                      {/* Phone Field */}
                      <div className="mb-3">
                        <label htmlFor="phone" className="form-label fw-bold">
                          <i className="fas fa-phone me-2"></i>
                          Phone Number
                        </label>
                        <Field
                          type="tel"
                          id="phone"
                          name="phone"
                          className={`form-control ${
                            errors.phone && touched.phone ? 'is-invalid' : ''
                          }`}
                          placeholder="Enter phone number"
                        />
                        <ErrorMessage name="phone" component="div" className="invalid-feedback" />
                      </div>

                      {/* Password Fields */}
                      <div className="row mb-3">
                        <div className="col-md-6">
                          <label htmlFor="password" className="form-label fw-bold">
                            <i className="fas fa-lock me-2"></i>
                            Password
                          </label>
                          <div className="input-group">
                            <Field
                              type={showPassword ? 'text' : 'password'}
                              id="password"
                              name="password"
                              className={`form-control ${
                                errors.password && touched.password ? 'is-invalid' : ''
                              }`}
                              placeholder="Enter password"
                            />
                            <button
                              type="button"
                              className="btn btn-outline-secondary"
                              onClick={() => setShowPassword(!showPassword)}
                            >
                              <i className={`fas ${showPassword ? 'fa-eye-slash' : 'fa-eye'}`}></i>
                            </button>
                          </div>
                          <ErrorMessage name="password" component="div" className="invalid-feedback" />
                        </div>
                        <div className="col-md-6">
                          <label htmlFor="confirmPassword" className="form-label fw-bold">
                            <i className="fas fa-lock me-2"></i>
                            Confirm Password
                          </label>
                          <div className="input-group">
                            <Field
                              type={showConfirmPassword ? 'text' : 'password'}
                              id="confirmPassword"
                              name="confirmPassword"
                              className={`form-control ${
                                errors.confirmPassword && touched.confirmPassword ? 'is-invalid' : ''
                              }`}
                              placeholder="Confirm password"
                            />
                            <button
                              type="button"
                              className="btn btn-outline-secondary"
                              onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                            >
                              <i className={`fas ${showConfirmPassword ? 'fa-eye-slash' : 'fa-eye'}`}></i>
                            </button>
                          </div>
                          <ErrorMessage name="confirmPassword" component="div" className="invalid-feedback" />
                        </div>
                      </div>

                      {/* Password Requirements */}
                      <div className="mb-3">
                        <small className="text-muted">
                          <strong>Password requirements:</strong>
                          <ul className="mb-0 mt-1">
                            <li>At least 8 characters long</li>
                            <li>Contains at least one uppercase letter</li>
                            <li>Contains at least one lowercase letter</li>
                            <li>Contains at least one number</li>
                          </ul>
                        </small>
                      </div>

                      {/* Checkboxes */}
                      <div className="mb-3">
                        <div className="form-check">
                          <Field
                            type="checkbox"
                            id="acceptTerms"
                            name="acceptTerms"
                            className={`form-check-input ${
                              errors.acceptTerms && touched.acceptTerms ? 'is-invalid' : ''
                            }`}
                          />
                          <label className="form-check-label" htmlFor="acceptTerms">
                            I agree to the{' '}
                            <Link to="/terms" className="text-decoration-none">
                              Terms and Conditions
                            </Link>{' '}
                            and{' '}
                            <Link to="/privacy" className="text-decoration-none">
                              Privacy Policy
                            </Link>
                          </label>
                          <ErrorMessage name="acceptTerms" component="div" className="invalid-feedback" />
                        </div>
                        <div className="form-check">
                          <Field
                            type="checkbox"
                            id="marketingEmails"
                            name="marketingEmails"
                            className="form-check-input"
                          />
                          <label className="form-check-label" htmlFor="marketingEmails">
                            I would like to receive marketing emails about new drones and special offers
                          </label>
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
                              Creating Account...
                            </>
                          ) : (
                            <>
                              <i className="fas fa-user-plus me-2"></i>
                              Create Account
                            </>
                          )}
                        </button>
                      </div>

                      {/* Divider */}
                      <div className="text-center mb-3">
                        <span className="text-muted">or</span>
                      </div>

                      {/* Social Registration Buttons */}
                      <div className="d-grid gap-2 mb-3">
                        <button type="button" className="btn btn-outline-dark">
                          <i className="fab fa-google me-2"></i>
                          Sign up with Google
                        </button>
                        <button type="button" className="btn btn-outline-primary">
                          <i className="fab fa-facebook-f me-2"></i>
                          Sign up with Facebook
                        </button>
                      </div>

                      {/* Login Link */}
                      <div className="text-center">
                        <p className="mb-0">
                          Already have an account?{' '}
                          <Link to="/login" className="text-decoration-none fw-bold">
                            Sign in here
                          </Link>
                        </p>
                      </div>
                    </Form>
                  )}
                </Formik>
              </div>
            </div>

            {/* Benefits Section */}
            <div className="row mt-4">
              <div className="col-md-4 mb-3">
                <div className="text-center">
                  <i className="fas fa-shield-alt fa-2x text-success mb-2"></i>
                  <h6>Secure & Safe</h6>
                  <small className="text-muted">Your data is protected with industry-standard encryption</small>
                </div>
              </div>
              <div className="col-md-4 mb-3">
                <div className="text-center">
                  <i className="fas fa-bolt fa-2x text-warning mb-2"></i>
                  <h6>Quick Setup</h6>
                  <small className="text-muted">Get started in minutes with instant account activation</small>
                </div>
              </div>
              <div className="col-md-4 mb-3">
                <div className="text-center">
                  <i className="fas fa-headset fa-2x text-info mb-2"></i>
                  <h6>24/7 Support</h6>
                  <small className="text-muted">Our support team is always here to help you</small>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register; 