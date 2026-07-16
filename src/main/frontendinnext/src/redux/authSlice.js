import { createSlice } from "@reduxjs/toolkit";

const auth = createSlice({
    name:"auth",
    initialState:{
        isLogin:false,
        user:[]
    },
    reducers:{
        login: (storage,action)=>{
                storage.isLogin = true
                storage.user = action.payload;
            },
            logout: (state,action)=>{
                state.isLogin = false
                state.user = null
            }
        
    }
})

export const {login , logout} = auth.actions;
export default auth.reducer ;