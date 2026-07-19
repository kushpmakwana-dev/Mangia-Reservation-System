"use client";

import { motion } from "framer-motion";
import Image from "next/image";

const container = {
  hidden: {},
  show: {
    transition: {
      staggerChildren: 0.2,
    },
  },
};

const item = {
  hidden: {
    opacity: 0,
    y: 50,
  },
  show: {
    opacity: 1,
    y: 0,
    transition: {
      duration: 0.8,
      ease: "easeOut",
    },
  },
};

const Banner = () => {
  return (
    <section className="relative overflow-hidden h-screen flex justify-center items-center bg-background px-10">

      {/* Animated Glow */}
      <motion.div
        animate={{
          scale: [1, 2.5, 1],
          opacity: [0.5, 0.8, 0.5],
        }}
        transition={{
          duration: 5,
          repeat: Infinity,
          ease: "easeInOut",
        }}
        className="absolute right-24 w-[420px] h-[420px] rounded-full bg-accent blur-[120px] opacity-30"
      />

      <motion.div
        variants={container}
        initial="hidden"
        animate="show"
        className="relative z-10 flex items-center justify-between w-full max-w-7xl"
      >
        {/* Left */}
        <div className="w-1/2 flex flex-col gap-8">
          <motion.h1
            variants={item}
            className="text-6xl font-bold leading-tight font-poppins"
          >
            It's not just{" "}
            <span className="text-accent">Food</span>,
            <br />
            it's an Experience.
          </motion.h1>

          <motion.p
            variants={item}
            className="text-lg text-gray-500 max-w-lg"
          >
            Discover delicious meals prepared with fresh ingredients,
            delivered with love and crafted for unforgettable moments.
          </motion.p>

          <motion.div
            variants={item}
            className="flex gap-5"
          >
            <motion.button
              whileHover={{
                scale: 1.08,
              }}
              whileTap={{
                scale: 0.95,
              }}
              className="bg-accent text-white px-7 py-4 rounded-xl text-lg font-semibold
              shadow-[5px_10px_10px_black]
              "
            >
              Order Now
            </motion.button>

            <motion.button
              whileHover={{
                scale: 1.08,
              }}
              whileTap={{
                scale: 0.95,
              }}
              className=" border-accent text-accent px-7 py-4 rounded-xl text-lg font-semibold
                            shadow-[5px_10px_10px_gray]

              "
            >
              Explore Menu
            </motion.button>
          </motion.div>
        </div>

        {/* Right */}
        <div className="relative w-[450px] h-[450px] flex justify-center items-center">

          {/* Rotating Ring */}
          <motion.div
            animate={{
              rotate: 360,
            }}
            transition={{
              duration: 20,
              repeat: Infinity,
              ease: "linear",
            }}
            className="absolute inset-0 rounded-full border-2 border-dashed border-accent"
          />

          {/* Second Ring */}
          <motion.div
            animate={{
              rotate: -360,
            }}
            transition={{
              duration: 25,
              repeat: Infinity,
              ease: "linear",
            }}
            className="absolute inset-8 rounded-full border border-accent/40"
          />

          {/* Floating Image */}
          <motion.div
            initial={{
              opacity: 0,
              scale: 0.6,
              rotate: -15,
            }}
            animate={{
              opacity: 1,
              scale: 1,
              rotate: 0,
              y: [0, -18, 0],
            }}
            transition={{
              opacity: {
                duration: 1,
              },
              scale: {
                duration: 1,
              },
              rotate: {
                duration: 1,
              },
              y: {
                duration: 4,
                repeat: Infinity,
                ease: "easeInOut",
              },
            }}
            whileHover={{
              scale: 1.08,
              rotate: 8,
            }}
            className="relative w-[360px] h-[360px] rounded-full overflow-hidden shadow-2xl"
          >
            <Image
              src="/images/img1.jpg"
              alt="Food"
              fill
              priority
              sizes="1"
              className="object-cover rounded-full"
            />
          </motion.div>

          {/* Floating Decorative Circle */}
          <motion.div
            animate={{
              y: [0, -15, 0],
              x: [0, 10, 0],
            }}
            transition={{
              duration: 3,
              repeat: Infinity,
            }}
            className="absolute top-4 right-10 w-6 h-6 rounded-full bg-accent"
          />

          {/* Floating Decorative Circle */}
          <motion.div
            animate={{
              y: [0, 20, 0],
              x: [0, -12, 0],
            }}
            transition={{
              duration: 4,
              repeat: Infinity,
            }}
            className="absolute bottom-10 left-6 w-4 h-4 rounded-full bg-primary"
          />
        </div>
      </motion.div>
    </section>
  );
};

export default Banner;
  