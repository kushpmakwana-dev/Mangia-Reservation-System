'use client'
import {motion} from "framer-motion"
import { div, section } from "framer-motion/client"
import { Heart } from "lucide-react"
import Image from "next/image"
import { addToCart , removeFromCart } from "@/redux/cartSlice"
import { useDispatch , useSelector } from "react-redux"
const Card = () => {
    const cart = useSelector((state)=>state.cart.items)

    const dispatch = useDispatch();

        const variants = {
            initial : {
                opacity:1,
                x:-30,
                y:40
            },
            animate:{
                opacity:1,
                x:[0,40,0],
                y:[0,20,-0],
                transition:{
                    duration:4,
                    repeat:Infinity,
                    ease: "easeOut"
                }
            }
        }
        console.log("cart",cart);
  return (
    <section className="group overflow-hidden w-[350px] rounded-3xl bg-white shadow-lg hover:shadow-2xl transition-all duration-300 hover:-translate-y-2">

  {/* Image */}
  <div className="relative h-60 overflow-hidden">
    <Image
      src="/images/stefan-vladimirov-Q_Moi2xjieU-unsplash.jpg"
      alt="Burger"
      fill
      loading="eager"
      className="object-cover rounded-t-3xl transition-transform duration-500 group-hover:scale-110"
    />

    {/* Rating Badge */}
    <div className="absolute top-4 right-4 bg-white/90 backdrop-blur-sm px-3 py-1 rounded-full text-sm font-semibold">
      ⭐ 4.5
    </div>

    {/* Category Badge */}
    <div className="absolute top-4 left-4 bg-orange-500 text-white px-3 py-1 rounded-full text-sm font-semibold">
      Popular
    </div>
  </div>

  {/* Content */}
  <div className="p-5 flex flex-col gap-4">

    <div className="flex justify-between items-center">
      <h2 className="text-2xl font-bold">Classic Burger</h2>
      <span className="text-orange-500 text-xl font-bold">
        ₹299
      </span>
    </div>

    <p className="text-gray-600 leading-relaxed">
      Juicy grilled patty with fresh lettuce, tomatoes,
      melted cheese and our signature sauce.
    </p>

    {/* Footer */}
    <div className="flex gap-3 pt-2">

      <button
        type="button"
        className="flex-1 bg-orange-500 hover:bg-orange-600 hover:cursor-pointer text-white font-semibold py-3 rounded-xl transition-colors"
        onClick={
            ()=>{
                dispatch(
                    addToCart(
                        {"id":1,"name":"Burger","quantity":1,"price":250,"rating":4.5}
                    ))
            }
        }
      > 
        Add to Cart
      </button>

      <button
        type="button"
        onClick={()=>{dispatch(removeFromCart(1))}}

        className="px-4 border border-orange-500 text-orange-500 hover:cursor-pointer hover:bg-orange-50 rounded-xl transition-colors"
      >
        <Heart />
      </button>

    </div>

  </div>
</section>
    // <section className="card-container flex flex-col gap-2 h-108 w-[350px] rounded-2xl">

    //     <div className="img rounded-t-2xl relative w-full h-55">
    //         <Image className="rounded-t-2xl" src="/images/stefan-vladimirov-Q_Moi2xjieU-unsplash.jpg" alt={"Product-Img"} sizes="1" fill objectFit="cover" />
    //     </div>
    //     <div className="title">
    //         <h1 className="font-bold text-3xl">Burger</h1>
    //     </div>
    //     <div className="decription text-medium flex flex-row justify-between text-foreground/90">
    //         <p>Deliciosu Burger at just low price</p>
    //         <p>Rating : 4.5/5 </p>
    //     </div>
    //     <div className="button flex flex-row items-center justify-start gap-5 w-full ">
    //         <button type="button" className="p-3 bg-orange-500 text-foreground rounded-xl font-bold">Add to Cart</button>
    //         <button type="button" className="p-3 bg-orange-500 text-foreground rounded-xl font-bold">Watch List</button>
    //     </div>

    // </section>
    // // <div className="flex flex-col gap-10">
    // /* <motion.section 
    // variants={variants}
    //     initial={"initial"}
    //     animate={"animate"}  
    // className='card z-0 w-5 h-5 border bg-foreground border-black shadow-[0px_0px_50px_blue] rounded-full'>
      
    // </motion.section> */
  )
}

export default Card
