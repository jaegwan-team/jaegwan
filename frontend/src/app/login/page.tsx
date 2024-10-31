"use client";

import videoSrc from "../../../public/video.mp4";

const Login = () => {
    return (
        <div className="flex flex-col justify-between items-center p-24 min-h-screen overflow-hidden">
            <video
                src={videoSrc}
                autoPlay
                muted
                loop
                className="absolute top-0 w-full h-full object-cover -z-10"
            />
        </div>
    );
}

export default Login;