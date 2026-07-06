import React, { useState } from "react";
import { HiOutlineArrowNarrowRight } from "react-icons/hi";
import { Link } from "react-router-dom";
import toast from "react-hot-toast";
import { useAuth } from "../context/AuthContext.jsx";
import { createReservation } from "../api/reservationService.js";

const initialForm = {
  firstName: "",
  secondName: "",
  reservationType: "SELF",
  totalNumberOfPeople: "",
  emailId: "",
  phoneNumber: "",
  reservationDate: "",
  reservationTime: "",
};

const Reservation = () => {
  const { isAuthenticated, user } = useAuth();
  const [form, setForm] = useState(initialForm);
  const [submitting, setSubmitting] = useState(false);

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleReservation = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    try {
      await createReservation({
        ...form,
        totalNumberOfPeople: Number(form.totalNumberOfPeople),
      });
      toast.success("Reservation confirmed! A table has been assigned to you.");
      setForm(initialForm);
    } catch (err) {
      toast.error(err.message || "Could not complete reservation.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <section className="reservation" id="reservation">
      <div className="container">
        <div className="banner">
          <img src="/reservation.png" alt="res" />
        </div>
        <div className="banner">
          <div className="reservation_form_box">
            <h1>MAKE A RESERVATION</h1>

            {!isAuthenticated ? (
              <>
                <p>You need an account to reserve a table.</p>
                <div style={{ display: "flex", justifyContent: "center", gap: "15px", marginTop: "20px" }}>
                  <Link to="/login" className="reservationCta">Login</Link>
                  <Link to="/register" className="reservationCta">Register</Link>
                </div>
              </>
            ) : (
              <>
                <p>Booking as {user?.name} ({user?.email})</p>
                <form onSubmit={handleReservation}>
                  <div>
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
                  <div>
                    <input
                      type="date"
                      name="reservationDate"
                      value={form.reservationDate}
                      onChange={handleChange}
                      required
                    />
                    <input
                      type="time"
                      name="reservationTime"
                      value={form.reservationTime}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div>
                    <input
                      type="email"
                      name="emailId"
                      placeholder="Email"
                      className="email_tag"
                      value={form.emailId}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div>
                    <input
                      type="number"
                      name="phoneNumber"
                      placeholder="Phone (10 digits)"
                      value={form.phoneNumber}
                      onChange={handleChange}
                      required
                    />
                    <input
                      type="number"
                      name="totalNumberOfPeople"
                      placeholder="Guests"
                      min="1"
                      value={form.totalNumberOfPeople}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div>
                    <select
                      name="reservationType"
                      value={form.reservationType}
                      onChange={handleChange}
                      className="reservationTypeSelect"
                    >
                      <option value="SELF">Booking for myself</option>
                      <option value="OTHERS">Booking for someone else</option>
                    </select>
                  </div>
                  <button type="submit" disabled={submitting}>
                    {submitting ? "RESERVING..." : "RESERVE NOW"}{" "}
                    <span>
                      <HiOutlineArrowNarrowRight />
                    </span>
                  </button>
                </form>
              </>
            )}
          </div>
        </div>
      </div>
    </section>
  );
};

export default Reservation;
