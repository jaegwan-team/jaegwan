import Image from "next/image";
import "../../../../styles/globals.css";
import kaka from "../../../../public/kakao_login_large_narrow.png";
import lolo from "../../../../public/logo.png";
const LoginLayout = () => {
  return (
    <div className="w-2/7 p-4 bg-loginBackgroundTransparent rounded-lg">
      <div className="max-w-sm mx-auto my-10 flex flex-col items-center drop-shadow-2xl">
        <div className="mb-20">
          <Image src={lolo} alt="Logo" width={200} height={200} priority />
        </div>
        <button
          onClick={() => {
            console.log("카카오 로그인");
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
