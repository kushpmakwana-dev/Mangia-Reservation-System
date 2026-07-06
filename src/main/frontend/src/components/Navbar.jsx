import React, { useState } from "react";
import { data } from "../restApi.json";
import { Link as ScrollLink } from "react-scroll";
import { Link as RouterLink, useNavigate } from "react-router-dom";
import { GiHamburgerMenu } from "react-icons/gi";
import { useAuth } from "../context/AuthContext.jsx";
import toast from "react-hot-toast";

const Navbar = () => {
  const [show, setShow] = useState(false);
  const { user, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const isStaff = user?.role === "OWNER" || user?.role === "EMPLOYEE";

  const handleLogout = async () => {
    try {
      await logout();
      toast.success("Logged out");
      navigate("/");
    } catch {
      toast.error("Could not log out. Please try again.");
    }
  };

  return (
    <>
      <nav>
        <div className="logo">ZEESH</div>
        <div className={show ? "navLinks showmenu" : "navLinks"}>
          <div className="links">
            {data[0].navbarLinks.map((element) => (
              <ScrollLink
                to={element.link}
                spy={true}
                smooth={true}
                duration={500}
                key={element.id}
              >
                {element.title}
              </ScrollLink>
            ))}
            {isStaff && (
              <RouterLink to="/admin/tables">Admin</RouterLink>
            )}
          </div>

          {isAuthenticated ? (
            <button className="menuBtn" onClick={handleLogout}>
              LOGOUT ({user?.name?.split(" ")[0] || "USER"})
            </button>
          ) : (
            <RouterLink to="/login">
              <button className="menuBtn">LOGIN</button>
            </RouterLink>
          )}
        </div>
        <div className="hamburger" onClick={() => setShow(!show)}>
          <GiHamburgerMenu />
        </div>
      </nav>
    </>
  );
};

export default Navbar;
