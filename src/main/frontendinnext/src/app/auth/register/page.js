"use client";
import React, { useState } from "react";
import { motion } from "framer-motion";
import { ArrowRightIcon } from "lucide-react";
import {useDispatch} from "react-redux";
import {register} from "@/redux/slices/authSlice";
import Link from "next/link";
import {useRegisterMutation} from "@/redux/api/authApi";
import { useRouter } from "next/navigation";

const RegisterPage = () => {


  const navigate = useRouter()
  const dispatch = useDispatch();
  const [registerData, setRegisterData] = useState({
    firstName:"",
    secondName:"",
    email: "",
    password: "",
    phoneNumber:""
  });

  const [registerUser]=  useRegisterMutation();

  const style = {
    outline:"none",
    backgroundColor:"white",
    border: "1px solid #ccc",
    padding: "8px",
    borderRadius: "10px"
  }

  const handleRegister = async (e)=>{

    e.preventDefault();

    try{

      const respone = await registerUser(registerData).unwrap()

      console.log("responese-?>",respone)

      dispatch(register(respone));
      navigate.push("/");

    }catch(err){
      console.error("Registration failed:", err);
    }
  }

  return (
    <section className="min-h-screen flex items-center justify-center bg-gray-50">

      <form onSubmit={handleRegister} className=" w-lg p-5 bg-white rounded-xl overflow-hidden flex flex-col shadow-2xl">
        <div className="register relative p-4 text-zinc-700 text-center rounded-t-xl">
          <h1 className="text-2xl font-bold">Register</h1>
        </div>
        <span className=" absolute bottom-0 h-1.5 w-full bg-linear-tor from-blue-800 to-violet-900"></span>
        <div className="form-container flex flex-col gap-2 bg-white">
          <div className="label-input flex flex-col gap-2">

            <label htmlFor="firstname" className="text-medium font-semibold">First Name :</label>
            <input
              type="text"
              id="firstname"
              value={registerData.firstName}
              onChange={(e) => setRegisterData({...registerData, firstName: e.target.value})}
              placeholder="Enter your name"
              style={style}
            />  

          </div>
          <div className="label-input flex flex-col gap-2">

            <label htmlFor="secondname" className="text-medium font-semibold">Second Name :</label>
            <input
              type="text"
              id="secondname"
              value={registerData.secondName}
              onChange={(e) => setRegisterData({...registerData, secondName: e.target.value})}
              placeholder="Enter your name"
              style={style}
            />  

          </div>
          
          <div className="label-input flex flex-col gap-2">

            <label htmlFor="email" className="text-sm font-semibold">Email :</label>
            <input
              type="email"
              id="email"
              value={registerData.email}
              onChange={(e) => setRegisterData({...registerData, email: e.target.value})}
              placeholder="Enter your email"
              style={style}
            />  

          </div>
          <div className="label-input flex flex-col gap-2">

            <label htmlFor="password" className="text-sm font-semibold">Password :</label>
            <input
              type="password"
              id="password"
              value={registerData.password}
              onChange={(e) => setRegisterData({...registerData, password: e.target.value})}
              placeholder="Enter your password"
              style={style}
            />  

          </div>
          <div className="label-input flex flex-col gap-2">
            <label htmlFor="phoneNumber" className="text-medium font-semibold">Phone Number :</label>
            <input
              type="text"
              id="phoneNumber"
              value={registerData.phoneNumber}
              onChange={(e) => setRegisterData({...registerData, phoneNumber: e.target.value})}
              placeholder="Enter your name"
              style={style}
            />  

          </div>
          <motion.button 
          whileTap={{ scale: 0.95 }}
          type="submit" className="mt-4 bg-black flex flex-row items-center justify-center gap-2 text-sm font-semibold
           text-white py-2.5  px-4 rounded-xl hover:bg-gray-800 transition-colors">
            {/* {isLoading ? 'Registering...' : 'Register'} */}
            Register
            <ArrowRightIcon size="20"/>
          </motion.button>
          <div className="login-redirect">
          <Link href="/auth/login">Already have an account? Sign In</Link>
        </div>
        </div>    
        
      </form>

    </section>
  );
};

export default RegisterPage;
