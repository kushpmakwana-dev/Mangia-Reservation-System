import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { HiOutlineArrowNarrowRight } from "react-icons/hi";
import toast from "react-hot-toast";
import { useAuth } from "../../context/AuthContext.jsx";
import "../Login/Login.css"; // shared auth-card styling

const Register = () => {
  const [form, setForm] = useState({
    firstName: "",
    secondName: "",
    email: "",
    password: "",
    phoneNumber: "",
  });
  const { register, loading } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await register(form);
      toast.success("Account created! Please log in.");
      navigate("/login");
    } catch (err) {
      toast.error(err.message || "Registration failed. Please try again.");
    }
  };

  return (
    <section className="authPage" id="register">
      <div className="authCard">
        <Link to="/" className="authLogo">
          ZEESH
        </Link>
        <h1>REGISTER</h1>
        <p>Create an account to make and manage reservations.</p>

        <form onSubmit={handleSubmit}>
          <div className="row">
            <input
              type="text"
              name="firstName"
              placeholder="First Name"
              value={form.firstName}
              onChange={handleChange}
              required
            />
            <input
              type="text"
              name="secondName"
              placeholder="Last Name"
              value={form.secondName}
              onChange={handleChange}
              required
            />
          </div>

          <div className="fullWidth">
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={form.email}
              onChange={handleChange}
              required
            />
          </div>

          <div className="fullWidth">
            <input
              type="password"
              name="password"
              placeholder="Password (min 6 characters)"
              value={form.password}
              onChange={handleChange}
              minLength={6}
              required
            />
          </div>

          <div className="fullWidth">
            <input
              type="tel"
              name="phoneNumber"
              placeholder="Phone Number (10 digits)"
              value={form.phoneNumber}
              onChange={handleChange}
              minLength={10}
              maxLength={10}
              required
            />
          </div>

          <button type="submit" disabled={loading}>
            {loading ? "CREATING ACCOUNT..." : "REGISTER"}
            <span>
              <HiOutlineArrowNarrowRight />
            </span>
          </button>
        </form>

        <p className="authSwitch">
          Already have an account? <Link to="/login">Login</Link>
        </p>
      </div>
    </section>
  );
};

export default Register;
