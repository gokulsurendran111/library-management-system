import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/AuthPage.css';

import { login, getToken } from '../api/login';
import { useNavigate } from 'react-router-dom';

const LoginPage: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  
  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    const loginData = { username, password };
    console.log('Logging in with:', loginData);

    login(username, password)
      .then(() => {
        navigate('/books');
      })
      .catch((err) => {
        // handle error (e.g., show error message)
        console.error(err);
      });
    
  };

  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100">
      <div className="auth-card p-4 rounded">
        <h2 className="mb-4 text-center">Login</h2>
        <form onSubmit={handleLogin}>
          {/* Username */}
          <div className="mb-3">
            <label htmlFor="username" className="form-label">Username</label>
            <input
              type="text"
              id="username"
              className="form-control"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>

          {/* Password */}
          <div className="mb-4">
            <label htmlFor="password" className="form-label">Password</label>
            <input
              type="password"
              id="password"
              className="form-control"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          {/* Submit */}
          <button type="submit" className="btn btn-primary w-100">
            Login
          </button>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
