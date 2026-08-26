import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
export const baseApi = createApi({
    reducerPath: "api",
    baseQuery: fetchBaseQuery({
        baseUrl : process.env.NEXT_BASE_URL || "http://localhost:8080/",
        prepareHeaders: (headers,{getState})=>{
            const token = getState().auth?.token
            if(token){
                headers.set("Authorization",`bearer ${token}`)
            }
            headers.set("Content-Type","application/json")
            return headers
        }
    }),
    endpoints: ()=>({}) 
})


export default baseApi;