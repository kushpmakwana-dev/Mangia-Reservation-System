import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { HiOutlineArrowNarrowRight } from "react-icons/hi";
import toast from "react-hot-toast";
import { useAuth } from "../../context/AuthContext.jsx";
import "./Login.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { login, loading } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const user = await login({ email, password });
      toast.success(`Welcome back${user?.name ? ", " + user.name : ""}!`);
      navigate("/");
    } catch (err) {
      toast.error(err.message || "Login failed. Please try again.");
    }
  };

  return (
    <section className="authPage" id="login">
      <div className="authCard">
        <Link to="/" className="authLogo">
          ZEESH
        </Link>
        <h1>LOGIN</h1>
        <p>Welcome back. Please enter your details.</p>

        <form onSubmit={handleSubmit}>
          <div className="fullWidth">
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="fullWidth">
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button type="submit" disabled={loading}>
            {loading ? "LOGGING IN..." : "LOGIN"}
            <span>
              <HiOutlineArrowNarrowRight />
            </span>
          </button>
        </form>

        <p className="authSwitch">
          Don&apos;t have an account? <Link to="/register">Register</Link>
        </p>
      </div>
    </section>
  );
};

export default Login;
