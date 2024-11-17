import type { NextConfig } from "next";
import withVideos from "next-videos";

const nextConfig: NextConfig = {
  reactStrictMode: true,
  images: {
    remotePatterns: [
      {
        protocol: "http",
        hostname: "k.kakaocdn.net",
        pathname: "/**",
      },
      {
        protocol: "https",
        hostname: "jaegwanbucket.s3.ap-northeast-2.amazonaws.com",
        pathname: "/receipt/**",
      },
    ],
  },
};

export default nextConfig;

module.exports = withVideos();
