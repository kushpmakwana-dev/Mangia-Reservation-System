import { createSlice } from "@reduxjs/toolkit";

const initialState={
    user:null,
    isAuthenticated:false,
}

const authSlice=createSlice({
    name:'auth',
    initialState:initialState,
    reducers:{
        setCredentials:(state,action)=>{
            const {user,token}=action.payload;
            state.user=user;
            state.isAuthenticated=true;
        },
        logout:(state)=>{
            state.user=null;
            state.isAuthenticated=false;
        },
        register : (state,action)=>{
            state.user = state.action.payload.user;
            state.isAuthenticated = true;
        }
    },
});

export const { setCredentials, logout, register } = authSlice.actions;
export default authSlice.reducer;