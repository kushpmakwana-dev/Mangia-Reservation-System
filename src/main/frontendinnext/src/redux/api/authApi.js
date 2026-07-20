import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react';
export const authApi=createApi({

    reducerPath:'authApi',
    bawseQuery:fetchBaseQuery({ baseUrl:process.env.NEXT_PUBLIC_API_URL,}),
    endpoints:(builder)=>({
        login:builder.mutation({
            query:(credentials)=>({
                url:'/auth/login',
                method:'POST',
                body:credentials

            }),
        }),
        register:builder.mutation({
            query:(data)=>({
                url:    '/auth/register',
                method:'POST',  
                body:data
            })

        })

    })


})

export const { useLoginMutation, useRegisterMutation } = authApi;