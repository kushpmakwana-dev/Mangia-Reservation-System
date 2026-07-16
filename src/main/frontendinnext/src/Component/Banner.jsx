"use client";
import { motion } from "framer-motion";
import Image from "next/image";
const Banner = () => {
  return (
    <section className="relative h-screen flex flex-col justify-center items-center">
      {/* <Image
        src={"/images/banner2.jpg"}
        alt="Banner-Img"
        fill
        loading="eager"
        sizes="400"
        className="object-cover"
      /> */}
      <motion.section
        initial={{ opacity: 0, y: 80, x: 0 }}
        animate={{ opacity: 1, y: 0, x: 0 }}
        transition={{ duration: 1.2 }}
        className=" rounded-4xl flex flex-row justify-center items-center gap-10"
      >
        <div className="flex flex-col gap-15 w-1/2">
        <div className="w-fit" >
          <h1 className="font-poppins w-1/2 font-bold bg-clip-text bg-linear-to-br text-primary to-secondary text-xl md:text-6xl">
            It's not just{" "}
            <span className="text-accent font-extrabold">Food</span>, it's
            an Experience.
          </h1>
        </div>
        <div className="button  w-fit flex flex-row gap-5 ">
          <button className="font-bold p-3 text-xl bg-accent text-background hover:bg-secondary transition-colors duration-200  rounded-xl  ">
            Order now
          </button>
          <button className=" font-bold p-3 text-xl bg-background rounded-xl ">
            Explore more
          </button>
        </div>
        </div>
        <div className="img relative w-92 h-92">
          <Image src={"/images/img1.jpg"} alt="food image" sizes="100" fill loading="eager" className="object-cover rounded-full"/>
        </div>
      </motion.section>
    </section>
  );
};

export default Banner;
