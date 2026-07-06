import React, { useEffect, useState } from "react";
import toast from "react-hot-toast";
import {
  addTable,
  searchTables,
  setTableAvailable,
  setTableBooked,
  setTableMaintenance,
} from "../../api/tableService.js";
import "./TableManagement.css";

const STATUS_OPTIONS = ["AVAILABLE", "BOOKED", "UNDER_MAINTENANCE"];

const TableManagement = () => {
  const [rows, setRows] = useState([]);
  const [meta, setMeta] = useState({ pageNo: 1, totalPages: 1, hasNext: false, hasPrev: false, total: 0 });
  const [filters, setFilters] = useState({ search: "", status: "", capacity: "" });
  const [loading, setLoading] = useState(false);

  const [newTable, setNewTable] = useState({ tableNumber: "", capacity: "", status: "AVAILABLE" });
  const [adding, setAdding] = useState(false);

  const load = async (pageNo = 1) => {
    setLoading(true);
    try {
      const result = await searchTables({
        search: filters.search || undefined,
        status: filters.status || undefined,
        capacity: filters.capacity ? Number(filters.capacity) : undefined,
        pageNo,
        pageSize: 10,
      });
      setRows(result.data);
      setMeta(result);
    } catch (err) {
      toast.error(err.message || "Could not load tables.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load(1);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    load(1);
  };

  const handleAddTable = async (e) => {
    e.preventDefault();
    setAdding(true);
    try {
      await addTable({ ...newTable, capacity: Number(newTable.capacity) });
      toast.success("Table added.");
      setNewTable({ tableNumber: "", capacity: "", status: "AVAILABLE" });
      load(meta.pageNo);
    } catch (err) {
      toast.error(err.message || "Could not add table.");
    } finally {
      setAdding(false);
    }
  };

  const handleStatusChange = async (id, status) => {
    try {
      if (status === "AVAILABLE") await setTableAvailable(id);
      else if (status === "BOOKED") await setTableBooked(id);
      else await setTableMaintenance(id);
      toast.success("Status updated.");
      load(meta.pageNo);
    } catch (err) {
      toast.error(err.message || "Could not update status.");
    }
  };

  return (
    <section className="tableMgmt">
      <div className="tableMgmtContainer">
        <h1>TABLE MANAGEMENT</h1>

        <form className="tmAddForm" onSubmit={handleAddTable}>
          <input
            type="text"
            placeholder="Table Number (e.g. T-12)"
            value={newTable.tableNumber}
            onChange={(e) => setNewTable((p) => ({ ...p, tableNumber: e.target.value }))}
            required
          />
          <input
            type="number"
            placeholder="Capacity"
            min="1"
            value={newTable.capacity}
            onChange={(e) => setNewTable((p) => ({ ...p, capacity: e.target.value }))}
            required
          />
          <select
            value={newTable.status}
            onChange={(e) => setNewTable((p) => ({ ...p, status: e.target.value }))}
          >
            {STATUS_OPTIONS.map((s) => (
              <option key={s} value={s}>{s.replace("_", " ")}</option>
            ))}
          </select>
          <button type="submit" disabled={adding}>
            {adding ? "ADDING..." : "ADD TABLE"}
          </button>
        </form>

        <form className="tmFilters" onSubmit={handleSearchSubmit}>
          <input
            type="text"
            placeholder="Search table number..."
            value={filters.search}
            onChange={(e) => setFilters((p) => ({ ...p, search: e.target.value }))}
          />
          <select
            value={filters.status}
            onChange={(e) => setFilters((p) => ({ ...p, status: e.target.value }))}
          >
            <option value="">All statuses</option>
            {STATUS_OPTIONS.map((s) => (
              <option key={s} value={s}>{s.replace("_", " ")}</option>
            ))}
          </select>
          <input
            type="number"
            placeholder="Min capacity"
            min="1"
            value={filters.capacity}
            onChange={(e) => setFilters((p) => ({ ...p, capacity: e.target.value }))}
          />
          <button type="submit">FILTER</button>
        </form>

        <div className="tmTableWrap">
          <table>
            <thead>
              <tr>
                <th>Table #</th>
                <th>Capacity</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {loading ? (
                <tr><td colSpan={4}>Loading...</td></tr>
              ) : rows.length === 0 ? (
                <tr><td colSpan={4}>No tables found.</td></tr>
              ) : (
                rows.map((t) => (
                  <tr key={t.id}>
                    <td>{t.tableNumber}</td>
                    <td>{t.capacity}</td>
                    <td><span className={`statusPill ${t.status}`}>{t.status.replace("_", " ")}</span></td>
                    <td className="tmActions">
                      {STATUS_OPTIONS.filter((s) => s !== t.status).map((s) => (
                        <button key={s} onClick={() => handleStatusChange(t.id, s)}>
                          Mark {s.replace("_", " ")}
                        </button>
                      ))}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>

        <div className="tmPagination">
          <button disabled={!meta.hasPrev} onClick={() => load(meta.pageNo - 1)}>Prev</button>
          <span>Page {meta.pageNo} of {meta.totalPages || 1} ({meta.total} total)</span>
          <button disabled={!meta.hasNext} onClick={() => load(meta.pageNo + 1)}>Next</button>
        </div>
      </div>
    </section>
  );
};

export default TableManagement;
