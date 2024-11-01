"use client";

import videoSrc from "../../../public/video.mp4";
import LoginLayout from "@/features/ui/layout/loginLayout";

const Login = () => {
    return (
        <div className="flex flex-col justify-between items-center p-24 min-h-screen overflow-hidden">
            <LoginLayout/>
            <video
                src={videoSrc}
                autoPlay
                muted
                loop
                className="absolute top-0 w-full h-full object-cover -z-10 blur-sm brightness-75"
            />
            
        </div>
    );
}

export default Login;