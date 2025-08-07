import React from 'react';
import { useAuth } from '../../context/AuthContext';

const Profile = () => {
  const { user } = useAuth();

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card card-custom shadow-sm">
            <div className="card-body">
              <h2 className="mb-4 text-center">
                My Profile
              </h2>
              <ul className="list-group list-group-flush">
                <li className="list-group-item"><strong>Name:</strong> {user?.name}</li>
                <li className="list-group-item"><strong>Email:</strong> {user?.email}</li>
                <li className="list-group-item"><strong>Role:</strong> {user?.role}</li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;
