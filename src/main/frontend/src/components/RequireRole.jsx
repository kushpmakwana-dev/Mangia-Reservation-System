import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext.jsx";

/**
 * Wrap any route that needs a logged-in user, optionally restricted
 * to specific roles:
 *
 *   <Route path="/admin/tables" element={
 *     <RequireRole roles={["OWNER", "EMPLOYEE"]}>
 *       <TableManagement />
 *     </RequireRole>
 *   } />
 *
 * Leave `roles` out to just require any logged-in user.
 */
const RequireRole = ({ roles, children }) => {
  const { user, isAuthenticated } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (roles && roles.length > 0 && !roles.includes(user?.role)) {
    return (
      <section className="authPage">
        <div className="authCard">
          <h1>403</h1>
          <p>
            Your account role ({user?.role || "unknown"}) doesn&apos;t have
            access to this page.
          </p>
        </div>
      </section>
    );
  }

  return children;
};

export default RequireRole;
