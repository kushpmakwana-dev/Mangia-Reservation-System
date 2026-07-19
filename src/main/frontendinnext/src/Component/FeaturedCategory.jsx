"use client";
import { easeIn, motion } from "framer-motion";
import Card from "./Card";
const variants = {
  initial: {
    opacity: 0.9,
    x: 0,
  },

  animate: {
    opacity: 1,
    x: [0, 20, 1],
    transition: {
      duration: 4,
      repeat: Infinity,
      ease: "easeInOut",
    },
  },
};
const featuredCategory = () => {
  return (
    <section className="relative w-full h-fit">
      <div>
        <motion.h1
          variants={variants}
          initial={"initial"}
          animate={"animate"}
          className="text-6xl font-bold"
        >
          FEATURED CATEGORY
        </motion.h1>
      </div>
      <div className="card-wrap mt-10 grid grid-cols-4 gap-10">
        
      <Card/>
      <Card/>
      <Card/>
      <Card/>
      </div>
    </section>
  );
};

export default featuredCategory;
