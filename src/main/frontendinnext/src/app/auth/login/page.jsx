"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { useDispatch } from "react-redux";
import { useLoginMutation } from "@/redux/api/authApi";
import { setCredentials } from "@/redux/slices/authSlice";
import { jwtDecode } from "jwt-decode"; // npm i jwt-decode
import Link from "next/link";
import {
  KeyboardOffIcon,
  KeyIcon,
  KeySquareIcon,
  LogIn,
  LucideOctagonPause,
  UserKeyIcon,
} from "lucide-react";
import Image from "next/image";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const [login, { isLoading }] = useLoginMutation();
  const dispatch = useDispatch();
  const router = useRouter();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const res = await login({ email, password }).unwrap();

      const decoded = jwtDecode(res.token);
      const role = decoded.role || res.user?.role;

      dispatch(setCredentials({ user: res.user, token: res.token }));

      document.cookie = `token=${res.token}; path=/; max-age=86400`;

      router.push(`/${role}`); // e.g. /admin or /user
    } catch (err) {
      setError(err?.data?.message || "Invalid email or password");
    } 
  };

  return (
    <section className="h-screen p-10 w-full bg-linear-to-b from-sky-400/20 to-white flex justify-center items-center ">
      <section
        className="group card p-5 border border-black/10 rounded-3xl 
      shadow-2xl 
       flex flex-col justify-center  items-center gap-5  bg-linear-to-b from-sky-400/30 via-white to-white h-fit min-w-130 w-130 
       tranisition-all duration-300 transform hover:-translate-y-1.5 hover:shadow-2xl
       "
      >
        <div className="top mt-4 p-4 bg-white/80  group-hover:bg-black transform transition-colors duration-600  rounded-2xl shadow-2xl w-fit  ">
          <LogIn size={24} className=" group-hover:text-white  font-bold " />
        </div>
        <div className="content">
          <h1 className="font-bold text-2xl text-center text-black/90 ">
            Sign In With Email
          </h1>
          <p className="text-center text-black/40 text-lg  px-15">
            Welcome back! Please enter your details to sign in.
          </p>
        </div>
        <form
          onSubmit={handleSubmit}
          className="form  flex flex-col gap-3  w-full px-20 p-5 "
        >
          <div className="relative  ">
            <Image
              src="/images/Email.png"
              alt="icon-admin"
              sizes="100%"
              objectFit="contain"
              width={20}
              height={20}
              className="absolute top-3.5 left-3 w-5.5 h-5 "
            />

            <input
              type="text"
              placeholder="Email"
              autoComplete="email"
              autoFocus="true"
              required
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
              }}
              className="p-3  w-full  rounded-2xl px-10 bg-gray-200/50 "
            />
          </div>
          <div className="relative ">
            <Image
              src="/images/lock.png"
              alt="icon-admin"
              sizes="100%"
              objectFit="contain"
              width={20}
              height={20}
              className="absolute top-3.5 left-3 w-5.5 h-5 "
            />
            <input
              type="password"
              placeholder="Password"
              required
              value={password}
              onChange={(e) => {
                setPassword(e.target.value);
              }}
              className="p-3 w-full    rounded-2xl px-10 bg-gray-200/50  "
            />
          </div>
          <div className="forget p-2 w-full">
            <p className="text-right text-black/60"><Link href="/">forget password?</Link></p>
          </div>
          <button className="p-4 bg-black text-white text-xl rounded-3xl tranisition-all duration-300 transform hover:-translate-y-1.5 hover:shadow-2xl ">
            Get Started
          </button>
          <div className="register">
            <p className="text-center text-black/60">
              Or {" "}
              <span className="text-black/90">
                <Link href="/auth/register">Sign Up</Link>
              </span>
            </p>
          </div>
        </form>
      </section>
    </section>
  );
}
