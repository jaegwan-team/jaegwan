/* eslint-disable @typescript-eslint/no-unused-vars */
import Image from "next/image";
import "../../../../styles/globals.css";
import kaka from "../../../../public/kakao_login_large_narrow.png";
import lolo from "../../../../public/logo.png";
import { useRouter } from "next/router";

const BACKEND_BASE_URL = process.env.NEXT_PUBLIC_ADDRESS;

const LoginLayout = () => {
  const router = useRouter();

  const doLogin = async () => {
    try {
      const response = await fetch(`${BACKEND_BASE_URL}/api/auth/kakao/login`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });
      if (!response.ok) {
        router.push("/login");
      }
      router.push("/main");
    } catch (error) {
      router.push("/login");
    }
  };

  return (
    <div className="w-2/7 p-4 bg-loginBackgroundTransparent rounded-lg">
      <div className="max-w-sm mx-auto my-10 flex flex-col items-center drop-shadow-2xl">
        <div className="mb-20">
          <Image src={lolo} alt="Logo" width={200} height={200} priority />
        </div>
        <button
          onClick={() => {
            doLogin();
          }}
          className="w-4/5 transition-transform hover:scale-105"
        >
          <Image
            src={kaka}
            alt="Kakao Login"
            width={300}
            height={45}
            className="rounded-lg"
          />
        </button>
      </div>
    </div>
  );
};

export default LoginLayout;
