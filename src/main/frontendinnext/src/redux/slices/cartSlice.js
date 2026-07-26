import { createReducer, createSlice } from "@reduxjs/toolkit";

const cart = createSlice({
  name: "cart",
  initialState: {
    count: 0,
    items: [],
    favItems : [],
    favCount : 0
  },
  reducers: {
    addToCart: (state, action) => {
      const existingItem = state.items.find(
        (item) => item.id === action.payload.id,
      );

      if (existingItem) {
        existingItem.quantity += 1;
        state.count = 1;
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
    toggleFavItems: (state, action) => {
      const exist = state.favItems.find((items) => items.id === action.payload.id);
      if (exist) {
         state.favItems = state.favItems.filter(
      (item) => item.id !== action.payload.id
    );
      } else {
        state.favItems.push(action.payload);
      }
      
      state.favCount = state.favItems.length;
    },
  },
});

export const { addToCart, removeFromCart, toggleFavItems } = cart.actions;

export default cart.reducer;
