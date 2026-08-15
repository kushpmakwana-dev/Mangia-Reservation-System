import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react';
export const authApi=createApi({

    reducerPath:'authApi',
    baseQuery:fetchBaseQuery({ baseUrl:process.env.NEXT_PUBLIC_API_URL || 'http://localhost:7000/api',}),
    endpoints:(builder)=>({
        login:builder.mutation({
            query:(credentials)=>({
                url:'/auth/login',
                method:'POST',
                body:credentials
            }),
            providesTags:['Auth']
        }),
        register:builder.mutation({
            query:(data)=>({
                url:    '/auth/register',
                method:'POST',  
                body:data
            }),
            invalidatesTags:['Auth']

        })

    })


})

export const { useLoginMutation, useRegisterMutation } = authApi;