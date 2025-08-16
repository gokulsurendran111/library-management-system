import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/AuthPage.css';
import { registerUser } from '../api/register';

type Role = 'ADMIN' | 'LIBRARIAN' | 'PATRON';

const apiUrl = import.meta.env.VITE_API_URL;

const RegisterPage: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const [role, setRole] = useState<Role>('PATRON');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const userData = { username, password, email, role };
    console.log('Registering user:', userData);
    registerUser({
      userName: username,
      email,
      password,
      role,
    })
      .then((data) => {
        console.log('Registration successful:', data);
      })
      .catch((error) => {
        console.error('Registration failed:', error);
      });
  };

  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100">
      <div className="auth-card p-4 rounded">
        <h2 className="mb-4 text-center">Register</h2>
        <form onSubmit={handleSubmit}>
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
          <div className="mb-3">
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

          {/* Email */}
          <div className="mb-3">
            <label htmlFor="email" className="form-label">Email</label>
            <input
              type="email"
              id="email"
              className="form-control"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          {/* Role */}
          <div className="mb-4">
            <label htmlFor="role" className="form-label">Role</label>
            <select
              id="role"
              className="form-select"
              value={role}
              onChange={(e) => setRole(e.target.value as Role)}
            >
              <option value="ADMIN">ADMIN</option>
              <option value="LIBRARIAN">LIBRARIAN</option>
              <option value="PATRON">PATRON</option>
            </select>
          </div>

          {/* Submit */}
          <button type="submit" className="btn btn-primary w-100">
            Register
          </button>
        </form>
      </div>
    </div>
  );
};

export default RegisterPage;
