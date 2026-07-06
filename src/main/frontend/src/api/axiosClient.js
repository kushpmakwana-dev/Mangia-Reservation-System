import axios from "axios";
import { isErrorShapedBody, parseApiError } from "./errorParser.js";

/**
 * Single source of truth for talking to the Spring Boot backend.
 *
 * If your backend URL, port, or context-path ever changes, this is the
 * ONLY file you need to touch — every service (auth, reservation, tables,
 * etc.) imports this client instead of calling axios directly.
 *
 * `withCredentials: true` is required because the backend uses
 * session-cookie based auth (HttpSessionSecurityContextRepository),
 * not JWT. If you switch the backend to JWT later, this is also the
 * place to add the Authorization header interceptor.
 */
const axiosClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080",
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
  },
});

axiosClient.interceptors.response.use(
  (response) => {
    // Backend quirk: business errors currently come back as HTTP 200 with
    // an error-shaped body instead of a real error status. Catch that here
    // so callers can always just `catch` regardless of HTTP status.
    if (isErrorShapedBody(response.data)) {
      return Promise.reject({
        statusCode: response.data.statusCode,
        message: response.data.message,
        raw: { response },
      });
    }
    return response;
  },
  (error) => Promise.reject({ ...parseApiError(error), raw: error })
);

export default axiosClient;
