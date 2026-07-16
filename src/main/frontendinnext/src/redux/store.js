import { configureStore } from "@reduxjs/toolkit";
import authReducer from "@/redux/authSlice";
// import cartReducer from "./slices/cartSlice";

export const store = configureStore({
  reducer: {
    auth: authReducer,
    // cart: cartReducer,
  },
});