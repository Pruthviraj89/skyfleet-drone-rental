import React, { useState, useEffect } from 'react';
import { penaltyAPI, ratingAPI, adminAPI } from '../../services/api';
import { toast } from 'react-toastify';

const PenaltyRatingRevenueTabs = () => {
  const [activeTab, setActiveTab] = useState('penalties');
  const [penalties, setPenalties] = useState([]);
  const [ratings, setRatings] = useState([]);
  const [revenue, setRevenue] = useState({ total: 0, periods: [] });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchPenalties();
    fetchRatings();
    fetchRevenue();
  }, []);

  const fetchPenalties = async () => {
    setLoading(true);
    try {
      const res = await penaltyAPI.getByBooking(''); // fallback to get all
      setPenalties(res.data || []);
    } catch (err) {
      // fallback: try getAllPenalties if available
      try {
        const res = await penaltyAPI.getAll ? penaltyAPI.getAll() : { data: [] };
        setPenalties(res.data || []);
      } catch {
        setPenalties([]);
      }
    } finally {
      setLoading(false);
    }
  };

  const fetchRatings = async () => {
    setLoading(true);
    try {
      const res = await ratingAPI.getByBooking(''); // fallback to get all
      setRatings(res.data || []);
    } catch (err) {
      // fallback: try getAllRatings if available
      try {
        const res = await ratingAPI.getAll ? ratingAPI.getAll() : { data: [] };
        setRatings(res.data || []);
      } catch {
        setRatings([]);
      }
    } finally {
      setLoading(false);
    }
  };

  const fetchRevenue = async () => {
    setLoading(true);
    try {
      const res = await adminAPI.getDashboardStats();
      setRevenue({ total: res.data.totalRevenue || 0, periods: [] });
    } catch {
      setRevenue({ total: 0, periods: [] });
    } finally {
      setLoading(false);
    }
  };

  const handleDeletePenalty = async (penalty) => {
    if (window.confirm('Delete this penalty?')) {
      try {
        await penaltyAPI.delete(penalty.id);
        toast.success('Penalty deleted');
        fetchPenalties();
      } catch {
        toast.error('Failed to delete penalty');
      }
    }
  };

  const handleDeleteRating = async (rating) => {
    if (window.confirm('Delete this rating?')) {
      try {
        await ratingAPI.delete(rating.id);
        toast.success('Rating deleted');
        fetchRatings();
      } catch {
        toast.error('Failed to delete rating');
      }
    }
  };

  return (
    <div className="mt-4">
      <ul className="nav nav-tabs mb-3">
        <li className="nav-item">
          <button className={`nav-link ${activeTab === 'penalties' ? 'active' : ''}`} onClick={() => setActiveTab('penalties')}>Penalties</button>
        </li>
        <li className="nav-item">
          <button className={`nav-link ${activeTab === 'ratings' ? 'active' : ''}`} onClick={() => setActiveTab('ratings')}>Ratings</button>
        </li>
        <li className="nav-item">
          <button className={`nav-link ${activeTab === 'revenue' ? 'active' : ''}`} onClick={() => setActiveTab('revenue')}>Revenue</button>
        </li>
      </ul>
      {activeTab === 'penalties' && (
        <div>
          <h5>Penalties</h5>
          {loading ? <div>Loading...</div> : (
            <table className="table table-bordered table-hover">
              <thead>
                <tr>
                  <th>Amount</th>
                  <th>Reason</th>
                  <th>Status</th>
                  <th>Booking</th>
                  <th>User</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {penalties.map((p) => (
                  <tr key={p.id}>
                    <td>₹{p.penaltyAmount}</td>
                    <td>{p.penaltyReason}</td>
                    <td>{p.penaltyStatus}</td>
                    <td>{p.bookingId}</td>
                    <td>{p.userName || ''}</td>
                    <td>
                      <button className="btn btn-sm btn-danger" onClick={() => handleDeletePenalty(p)}>
                        <i className="fas fa-trash"></i>
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      )}
      {activeTab === 'ratings' && (
        <div>
          <h5>Ratings</h5>
          {loading ? <div>Loading...</div> : (
            <table className="table table-bordered table-hover">
              <thead>
                <tr>
                  <th>Rating</th>
                  <th>Comment</th>
                  <th>Booking</th>
                  <th>User</th>
                  <th>Drone</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {ratings.map((r) => (
                  <tr key={r.id}>
                    <td>{r.rating}/5</td>
                    <td>{r.comment}</td>
                    <td>{r.bookingId}</td>
                    <td>{r.userName || ''}</td>
                    <td>{r.droneModel || ''}</td>
                    <td>
                      <button className="btn btn-sm btn-danger" onClick={() => handleDeleteRating(r)}>
                        <i className="fas fa-trash"></i>
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      )}
      {activeTab === 'revenue' && (
        <div>
          <h5>Revenue</h5>
          <div className="mb-2">Total Revenue: <strong>₹{revenue.total}</strong></div>
          {/* If you have period breakdown, show a table here */}
        </div>
      )}
    </div>
  );
};

export default PenaltyRatingRevenueTabs; 