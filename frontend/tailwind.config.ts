import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./src/features/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {

        loginBackground: "#A4D1A5",
        loginBackgroundTransparent: "rgba(164, 209, 165, 0.45)",

        background: "var(--background)",
        foreground: "var(--foreground)",
      },
    },
  },
  plugins: [],
};
export default config;
