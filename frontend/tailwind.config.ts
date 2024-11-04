import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./src/features/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        
        loginClickedBackground : "#0F2D0F",
        loginBtnBackground : "#1A5319",
        loginBackground: "#A4D1A5",
        loginBackgroundTransparent: "rgba(164, 209, 165, 0.7)",
        inputFieldBackground: "rgba(26, 83, 25, 0.4)",

        background: "var(--background)",
        foreground: "var(--foreground)",
      },
      fontFamily: {
        pretendard: [
          "Pretendard-Regular",
          "-apple-system",
          "BlinkMacSystemFont",
          "system-ui",
          "sans-serif",
        ],
      },
    },
  },
  plugins: [],
};
export default config;
