/** @type {import('tailwindcss').Config} */

// temporary solution to fix tailwind I
export default {
  content: ["./index.html", "./src/**/.{js,ts,jsx,tsx}"],
  theme: {
    extend: {},
  },
  plugins: [],
};
