"use client";

import { useEffect, useRef } from "react";
import { gsap } from "gsap";
import styles from "./CustomCursor.module.css";


export default function CustomCursor() {
  const ringRef = useRef(null);
  const dotRef = useRef(null);

  useEffect(() => {
    const isCoarsePointer = window.matchMedia("(pointer: coarse)").matches;
    const prefersReducedMotion = window.matchMedia(
      "(prefers-reduced-motion: reduce)"
    ).matches;

    if (isCoarsePointer || prefersReducedMotion) return;

    document.body.classList.add(styles.cursorEnabled);

    const ring = ringRef.current;
    const dot = dotRef.current;

    const ringX = gsap.quickTo(ring, "x", { duration: 0.5, ease: "power3" });
    const ringY = gsap.quickTo(ring, "y", { duration: 0.5, ease: "power3" });
    const dotX = gsap.quickTo(dot, "x", { duration: 0.15, ease: "power3" });
    const dotY = gsap.quickTo(dot, "y", { duration: 0.15, ease: "power3" });

    const handleMove = (e) => {
      ringX(e.clientX);
      ringY(e.clientY);
      dotX(e.clientX);
      dotY(e.clientY);
    };

    const handleDown = () => {
      gsap.to(ring, { scale: 0.8, duration: 0.2, ease: "power2.out" });
    };
    const handleUp = () => {
      gsap.to(ring, { scale: 1, duration: 0.3, ease: "power2.out" });
    };


    const interactiveSelector =
      'a, button, [role="button"], input, textarea, select, [data-cursor="hover"]';

    const handleOver = (e) => {
      if (e.target.closest(interactiveSelector)) {
        ring.classList.add(styles.ringHover);
      }
    };
    const handleOut = (e) => {
      if (e.target.closest(interactiveSelector)) {
        ring.classList.remove(styles.ringHover);
      }
    };

    window.addEventListener("mousemove", handleMove);
    window.addEventListener("mousedown", handleDown);
    window.addEventListener("mouseup", handleUp);
    document.addEventListener("mouseover", handleOver);
    document.addEventListener("mouseout", handleOut);

    return () => {
      document.body.classList.remove(styles.cursorEnabled);
      window.removeEventListener("mousemove", handleMove);
      window.removeEventListener("mousedown", handleDown);
      window.removeEventListener("mouseup", handleUp);
      document.removeEventListener("mouseover", handleOver);
      document.removeEventListener("mouseout", handleOut);
    };
  }, []);

  return (
    <>
      <div ref={ringRef} className={styles.ring} aria-hidden="true" />
      <div ref={dotRef} className={styles.dot} aria-hidden="true" />
    </>
  );
}