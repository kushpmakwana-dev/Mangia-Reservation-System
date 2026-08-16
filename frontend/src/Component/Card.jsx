"use client";

import { Heart } from "lucide-react";
import Image from "next/image";
import { useDispatch, useSelector } from "react-redux";
import { addToCart, toggleFavItems } from "@/redux/slices/cartSlice";
import products from "@/json/product.json";

const Card = () => {
  const dispatch = useDispatch();

  const cart = useSelector((state) => state.cart.items);
  const favItems = useSelector((state) => state.cart.favItems);

  console.log("cart", cart);
  console.log("favItems", favItems);

  return (
    <section className="w-full grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
      {products.map((item) => {
        const isFavourite = favItems.some(
          (fav) => fav.id === item.id
        );

        return (
          <div
            key={item.id}
            className="group overflow-hidden rounded-3xl bg-white shadow-lg hover:shadow-2xl transition-all duration-300 hover:-translate-y-2"
          >
            {/* Image */}
            <div className="relative h-60 overflow-hidden">
              <Image
                src={item.image}
                alt={item.name}
                fill
                sizes="100%"
                loading="eager"
                className="object-cover rounded-t-3xl transition-transform duration-500 group-hover:scale-110"
              />

              {/* Rating */}
              <div className="absolute top-4 right-4 bg-white/90 backdrop-blur-sm px-3 py-1 rounded-full text-sm font-semibold">
                ⭐ {item.rating}
              </div>

              {/* Category */}
              <div className="absolute top-4 left-4 bg-orange-500 text-white px-3 py-1 rounded-full text-sm font-semibold">
                {item.category}
              </div>
            </div>

            {/* Content */}
            <div className="p-5 flex flex-col gap-4">
              <div className="flex justify-between items-center">
                <h2 className="text-2xl font-bold">
                  {item.name}
                </h2>

                <span className="text-orange-500 text-xl font-bold">
                  ₹{item.price}
                </span>
              </div>

              <p className="text-gray-600 leading-relaxed">
                {item.description}
              </p>

              <div className="flex gap-3 pt-2">
                {/* Add Cart */}
                <button
                  type="button"
                  onClick={() =>
                    dispatch(
                      addToCart({
                        id: item.id,
                        name: item.name,
                        price: item.price,
                        rating: item.rating,
                        image: item.image,
                        quantity: 1,
                      })
                    )
                  }
                  className="flex-1 bg-orange-500 hover:bg-orange-600 text-white font-semibold py-3 rounded-xl transition-colors"
                >
                  Add to Cart
                </button>

                {/* Favourite */}
                <button
                  type="button"
                  onClick={() => dispatch(toggleFavItems(item))}
                  className="px-4 border border-orange-500 text-orange-500 hover:bg-orange-50 rounded-xl transition-colors"
                >
                  <Heart
                    size={20}
                    fill={isFavourite ? "red" : "none"}
                    color={isFavourite ? "red" : "orange"}
                    className="transition-all duration-200"
                  />
                </button>
              </div>
            </div>
          </div>
        );
      })}
    </section>
  );
};

export default Card;