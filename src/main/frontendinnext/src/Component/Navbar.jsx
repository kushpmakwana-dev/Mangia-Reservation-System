'use client';

import { motion } from "framer-motion";
import { HeartIcon, LucideShoppingBag, UserPen } from "lucide-react";
import Link from "next/link";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

const Navbar = () => {

  const countCartItems = useSelector((state) => state.cart.count);
  const favouriteCartItems = useSelector(
    (state) => state.cart.favCount
  );
  const navigate = useRouter();

  console.log(countCartItems)
  const [scrolled, setScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      setScrolled(window.scrollY > 50);
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <motion.header
      initial={{ opacity: 0, y: -20 }}
      animate={{
        opacity: 1,
        y: 0,
        backgroundColor: scrolled
          ? "rgba(255,255,255,0.95)"
          : "rgba(255,255,255,0)",
      }}
      transition={{ duration: 0.3 }}
      className={`sticky top-0 z-[999] flex justify-around items-center p-3 transition-all duration-300 ${
        scrolled ? "backdrop-blur-md shadow-lg" : ""
      }`}
    >
      {/* Logo */}
      <h1 className="text-2xl font-bold font-poppins text-accent">
        Mangia Reservation System
      </h1>

      {/* Navigation */}
      <nav>
        <ul className="flex gap-5 text-lg font-light">
          {["Home", "Service", "About Us", "Contact Us"].map((item) => (
            <motion.li key={item} whileTap={{ scale: 0.9 }}>
              <Link
                href="/"
                className="p-2 text-sm rounded-xl transition-all duration-200 hover:bg-accent hover:text-background"
              >
                {item}
              </Link>
            </motion.li>
          ))}
        </ul>
      </nav>

      {/* Right Side */}
      <div className="flex items-center gap-10">
        <div className="flex gap-3">
          {/* Cart */}
          <motion.button whileTap={{ scale: 0.9 }} className="relative">
            <LucideShoppingBag
              size={40}
              className="rounded-full p-2 transition-colors duration-200 hover:bg-accent hover:text-background"
            />

            {countCartItems > 0 && (
              <span className="absolute top-0 right-0 flex h-4 w-4 items-center justify-center rounded-full bg-red-600 text-[10px] text-white">
                {countCartItems}
              </span>
            )}
          </motion.button>

          {/* Favourite */}
          <motion.button whileTap={{ scale: 0.9 }} className="relative">
            <HeartIcon
              size={40}
              className="rounded-full p-2 transition-colors duration-200 hover:bg-accent hover:text-background"
            />

            {favouriteCartItems > 0 && (
              <span className="absolute top-0 right-0 flex h-4 w-4 items-center justify-center rounded-full bg-red-600 text-[10px] text-white">
                {favouriteCartItems}
              </span>
            )}
          </motion.button>
        </div>

        {/* Sign Up */}
        <motion.button
        onClick={()=>{
          navigate.push("/auth/login")
        }}
          whileTap={{ scale: 0.9 }}
          className="w-20 rounded-full bg-accent p-2.5 text-xs text-background transition-all duration-300 hover:bg-foreground"
        >
          Sign Up
        </motion.button>
      </div>
    </motion.header>
  );
};

export default Navbar;