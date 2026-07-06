import axiosClient from "./axiosClient.js";

/**
 * All auth-related backend calls live here, and nowhere else.
 * If a route, field name, or response shape changes on the backend,
 * this is the only file that needs updating — components and context
 * never talk to axios directly.
 */

// POST /auth  ->  { email, password }
// Backend returns the raw UserPrincipal on success, which includes
// a nested "user" object: { id, email, name, role }
//
// NOTE: wrong password currently throws an uncaught AuthenticationException
// on the backend (no handler in GlobalExceptionHandler), which surfaces as a
// generic HTTP 500 with no useful message. Unknown email IS handled (via
// ResourcesNotFoundException) and returns a real message. Until the backend
// adds an AuthenticationException handler, we treat an unexplained 500/0
// from this endpoint specifically as bad credentials, since that's by far
// the most likely cause here.
export const login = async ({ email, password }) => {
  try {
    const { data } = await axiosClient.post("/auth", { email, password });
    return data.user; // { id, email, name, role }
  } catch (err) {
    if (err.statusCode === 500 || err.statusCode === 0) {
      throw { ...err, message: "Invalid email or password." };
    }
    throw err;
  }
};

// POST /api/customer/register
// NOTE: backend SecurityConfig currently permits "/api/user/register"
// but the real controller is mapped at "/api/customer/register".
// That mismatch needs fixing on the backend side (SecurityConfig
// matcher), otherwise this call will be blocked as unauthenticated.
export const registerCustomer = async ({
  firstName,
  secondName,
  email,
  password,
  phoneNumber,
}) => {
  const { data } = await axiosClient.post("/api/customer/register", {
    firstName,
    secondName,
    email,
    password,
    phoneNumber,
  });
  return data;
};

// POST /logout — Spring Security's default logout endpoint
// (enabled automatically unless explicitly disabled in SecurityConfig).
export const logout = async () => {
  await axiosClient.post("/logout");
};

/**
 * There is currently no "who am I" endpoint on the backend
 * (e.g. GET /auth/me), so the frontend can't confirm a session is
 * still valid after a page refresh — it only remembers who logged in
 * during this browser tab's lifetime (see AuthContext).
 *
 * Recommended backend addition later:
 *   GET /auth/me -> 200 + CurrentLoggedInUser if session valid, 401 otherwise
 * Once that exists, wire it up here as `getCurrentUser()` and call it
 * from AuthContext on app load.
 */
