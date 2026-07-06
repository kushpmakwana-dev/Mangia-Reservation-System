import axiosClient from "./axiosClient.js";

const BASE = "/api/management";

// POST /api/management  (requires ROLE_OWNER or ROLE_EMPLOYEE)
// { tableNumber, status: "AVAILABLE"|"BOOKED"|"UNDER_MAINTENANCE", capacity }
export const addTable = async (payload) => {
  const { data } = await axiosClient.post(BASE, payload);
  return data;
};

// PATCH /api/management/{id} — partial update, any field may be omitted
export const updateTable = async (id, payload) => {
  const { data } = await axiosClient.patch(`${BASE}/${id}`, payload);
  return data;
};

export const setTableBooked = async (id) => (await axiosClient.patch(`${BASE}/booked/${id}`)).data;
export const setTableAvailable = async (id) => (await axiosClient.patch(`${BASE}/available/${id}`)).data;
export const setTableMaintenance = async (id) => (await axiosClient.patch(`${BASE}/maintenance/${id}`)).data;

// GET /api/management?search=&status=&capacity=&pageNo=1&pageSize=10
// Returns a ListResponse<RestaurantTableResponseDTO>:
//   { total, pageNo, pageSize, totalPages, hasNext, hasPrev, data: [...] }
export const searchTables = async ({ search, status, capacity, pageNo = 1, pageSize = 10 } = {}) => {
  const { data } = await axiosClient.get(BASE, {
    params: { search, status, capacity, pageNo, pageSize },
  });
  return data;
};

export const getTableById = async (id) => (await axiosClient.get(`${BASE}/${id}`)).data;
