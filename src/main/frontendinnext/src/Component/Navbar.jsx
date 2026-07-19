'use client'
import {motion} from "framer-motion"
import { Badge, LucideShoppingBag, ShoppingBag, ShoppingBagIcon, ShoppingBasket, ShoppingCart, ShoppingCartIcon } from "lucide-react";
import Link from "next/link";
import {useSelector} from "react-redux"
const Navbar = () => {

  const countCartItems = useSelector((state)=>state.cart.count)
  return (
    <motion.header
    initial={{opacity:0,y:-10}}
    animate={{opacity:1,y:0}}
    transition={{duration:1}}
    className="p-3 sticky top-0 z-[999] flex flex-row justify-around items-center md:text-4xl font-bold">
      <h1 className="text-2xl md:text-2xl font-bold font-poppins text-accent">Mangia Reservation System</h1>
      <nav>
        <ul className="flex flex-row text-lg gap-5 font-light">
          <motion.li 
          
          whileTap={{scale:0.9}}>
            <Link href="/" className="relative group p-2 text-sm transition-colors duration-200 transform hover:bg-accent hover:text-background hover:scale-y-1 rounded-xl">
              Home
              {/* <span className="absolute left-0 bottom-0 w-0 h-[1px] bg-current transition-all duration-300 group-hover:w-full"></span> */}
            </Link>
          </motion.li>
          <motion.li
                    whileTap={{scale:0.9}}>

            <Link href="/" className="relative group p-2 text-sm transition-colors duration-200 transform hover:bg-accent hover:text-background hover:scale-y-1 rounded-xl">
              Service
              {/* <span className="absolute left-0 bottom-0 w-0 h-[1px] bg-current transition-all duration-300 group-hover:w-full"></span> */}
            </Link>
          </motion.li>
          <motion.li
                    whileTap={{scale:0.9}}>
            <Link href="/" className="relative group p-2 text-sm transition-colors duration-200 transform hover:bg-accent hover:text-background hover:scale-y-1 rounded-xl">
              About Us
              {/* <span className="absolute left-0 bottom-0 w-0 h-[1px] bg-current transition-all duration-300 group-hover:w-full"></span> */}
            </Link>
          </motion.li>
          <motion.li
                    whileTap={{scale:0.9}}>
            <Link href="/" className="relative group p-2 text-sm transition-colors duration-200 transform hover:bg-accent hover:text-background hover:scale-y-1 rounded-xl">
              Contact Us
              {/* <span className="absolute left-0 bottom-0 w-0 h-[1px] bg-current transition-all duration-300 group-hover:w-full"></span> */}
            </Link>
          </motion.li>
        </ul>
      </nav>
      <div className="right flex flex-row items-center gap-10">
      <motion.button
            whileTap={{scale:0.9}}
            className="relative"
>
        <LucideShoppingBag size={40} className="transition-colors duration-200 hover:bg-accent hover:text-background rounded-full p-2"/>
        <span className={`count absolute top-0 right-0 ${countCartItems > 0 ? "bg-red-600 flex justify-center items-center rounded-full w-4 h-4 text-center text-[10px] text-background": "hidden"}`}>
        {countCartItems}
        </span>
      </motion.button>
      <motion.button
      whileTap={{scale:0.9}}
      className="w-20 text-xs bg-accent text-background hover:bg-foreground transition-all duration-300 p-2.5 rounded-full">
        Sign Up
      </motion.button>
      </div>
    </motion.header>
  );
};

export default Navbar;
