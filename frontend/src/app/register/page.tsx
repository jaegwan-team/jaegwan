"use client";

import videoSrc from "../../../public/video.mp4";
import RegisterLayout from "@/features/ui/layout/registerLayout";

const Register = () => {
  return (
    <div className="flex flex-col justify-center items-center p-24 min-h-screen overflow-hidden">
      <RegisterLayout />
      <video
        src={videoSrc}
        autoPlay
        muted
        loop
        className="absolute top-0 w-full h-full object-cover -z-10 blur-sm brightness-75"
      />
    </div>
  );
};

export default Register;
