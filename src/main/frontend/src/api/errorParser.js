/**
 * The backend is inconsistent about how it reports errors right now:
 *
 * 1. Business exceptions (ResourcesNotFoundException, AlreadyExistsException,
 *    CustomException, UnmodifiableException, InvalidRoleException) are all
 *    caught by GlobalExceptionHandler, but returned as ResponseEntity.ok(err)
 *    — i.e. HTTP 200 with an error-shaped body:
 *      { statusCode, entityName, status, message, timeStamp }
 *
 * 2. Bean-validation failures (@Valid) have no custom handler, so Spring's
 *    default validation error format is used instead. Depending on version/
 *    config this may come back as { message, errors: [...] }, a
 *    ProblemDetail-style { detail, ... }, or the plain Spring Boot default
 *    { timestamp, status, error, path }.
 *
 * 3. Bad login credentials aren't caught at all (no AuthenticationException
 *    handler), so they currently surface as a raw 500 with no useful body.
 *
 * This module is the ONE place that knows how to make sense of all of that,
 * so components only ever deal with a clean { statusCode, message } shape.
 * When the backend error handling gets cleaned up, this is the only file
 * that should need updating.
 */

export const isErrorShapedBody = (data) =>
  data && typeof data === "object" && typeof data.statusCode === "number" && data.statusCode >= 400;

export const parseApiError = (error) => {
  const response = error?.response;

  // No response at all -> network/CORS/backend-down issue
  if (!response) {
    return {
      statusCode: 0,
      message: "Can't reach the server. Is the backend running?",
    };
  }

  const { data, status: httpStatus } = response;

  if (isErrorShapedBody(data)) {
    return { statusCode: data.statusCode, message: data.message };
  }

  if (data && typeof data === "object") {
    if (Array.isArray(data.errors) && data.errors.length) {
      const first = data.errors[0];
      return {
        statusCode: httpStatus,
        message: typeof first === "string" ? first : first.defaultMessage || first.message || "Validation failed.",
      };
    }
    if (typeof data.message === "string" && data.message) {
      return { statusCode: httpStatus, message: data.message };
    }
    if (typeof data.detail === "string" && data.detail) {
      return { statusCode: httpStatus, message: data.detail };
    }
    if (typeof data.error === "string" && data.error) {
      return { statusCode: httpStatus, message: data.error };
    }
  }

  if (typeof data === "string" && data.trim()) {
    return { statusCode: httpStatus, message: data };
  }

  // Known fallbacks for common cases the backend doesn't explain well yet
  if (httpStatus === 401) return { statusCode: 401, message: "Invalid email or password." };
  if (httpStatus === 403) return { statusCode: 403, message: "You don't have permission to do that." };
  if (httpStatus === 400) return { statusCode: 400, message: "Please check the form and try again." };
  if (httpStatus >= 500) return { statusCode: httpStatus, message: "Server error. Please try again shortly." };

  return { statusCode: httpStatus, message: "Something went wrong. Please try again." };
};
