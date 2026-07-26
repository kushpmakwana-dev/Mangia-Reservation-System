"use client";

import Image from "next/image";
import React, { useState } from "react";

const RegisterPage = () => {
  const [registerData, setRegisterData] = useState({});

  return (
    <section className="w-full h-screen flex justify-center items-center ">
      <div className="p-2  container   bg-linear-to-r from-white to-sky-50  w-200 h-fit  flex flex-row gap-2 rounded-2xl shadow-2xl">
        <div className="img  relative w-1/2 h-122 rounded-2xl">
          <Image
            src="/images/register2.webp"
            alt="register Img"
            fill
            className="object-cover rounded-2xl"
            sizes="100%"
          />

          {/* Shadow overlay */}
          <div className="absolute inset-0 rounded-2xl" />
        </div>
        <div className="content flex flex-col gap-2 w-1/2">
          <div className=" title w-full h-fit rounded-2xl">
            <h1 className="p-2 font-bold text-accent font-poppins text-left text-3xl">
              SIGN UP
            </h1>
              <hr/>
          </div>
          <div className="input-box flex flex-col gap-3 px-2">
            <label htmlFor="name" className="font-extralight text-lg">
              Name:
            </label>
            <input
              type="text"
              name="name"
              required
              autoFocus
              aria-label="name"
              className="border rounded-lg h-10 p-2 "
              placeholder="Enter your Name"
            />
            <label htmlFor="name" className="font-medium text-lg">
              Email Id:
            </label>
            <input
              type="email"
              name="email"
              required
              autoFocus
              aria-label="name"
              className="border rounded-lg h-10 p-2 "
              placeholder="Enter your Email Id"
            />
            <label htmlFor="name" className="font-medium text-lg">
              Create a Password:
            </label>
            <input
              type="password"
              name="confirm-password"
              required
              autoFocus
              aria-label="name"
              className="border rounded-lg h-10 p-2 "
              placeholder="Enter Password "
            />
            <label htmlFor="name" className="font-medium text-lg">
              Confirm Password:
            </label>
            <input
              type="password"
              name="confirm-password"
              required
              autoFocus
              aria-label="name"
              className="border rounded-lg h-10 p-2 "
              placeholder="Enter Password "
            />
          <div className="button-container mt-3 w-full flex gap-2 ">
            <button className="p-2 bg-blue-400 text-white font-bold rounded-xl w-full transform transition-all duration-300 hover:-translate-y-2  shadow-2xl">
              Submit
            </button>
            <button className="p-2 bg-white text-black font-bold rounded-xl w-full transform transition-color duration-300 hover:-translate-y-2 shadow-2xl ">
              Reset
            </button>
          </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default RegisterPage;
