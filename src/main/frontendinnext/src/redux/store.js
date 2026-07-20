import { configureStore } from "@reduxjs/toolkit";
import authReducer from "@/redux/slices/authSlice";
import { authApi } from "./api/authApi";
import cartReducer from "./slices/cartSlice";

export const makeStore = () =>
  configureStore({
    reducer: {
      auth: authReducer,
      cart: cartReducer,
      [authApi.reducerPath]: authApi.reducer,
    },
    middleware: (getDefaultMiddleware) =>
      getDefaultMiddleware().concat(authApi.middleware),
  });

export const store = makeStore();