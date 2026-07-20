import { createReducer, createSlice } from "@reduxjs/toolkit";

const cart = createSlice({
  name: "cart",
  initialState: {
    count: 0,
    items: [],
  },
  reducers: {
    addToCart: (state, action) => {
      const existingItem = state.items.find(
        (item) => item.id === action.payload.id,
      );

      if (existingItem) {
        existingItem.quantity += 1;
        state.count = 1
      } else {
        state.items.push(action.payload);
      }

     state.count = state.items.length;
    },
    removeFromCart: (state, action) => {
      state.items = state.items.filter((id) => {
        return id.id !== action.payload;
      });
      state.count = state.items.length;
    },
  },
});

export const { addToCart, removeFromCart } = cart.actions;

export default cart.reducer;
