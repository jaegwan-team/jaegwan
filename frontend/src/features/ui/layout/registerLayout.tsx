"use client";

import Image from "next/image";
import "../../../../styles/globals.css";
import lolo from "../../../../public/logo.png";
import { useRouter } from "next/navigation";
import { useState } from "react";
import { registRestaurant } from "@/services/api";

const RegisterLayout = () => {
  const router = useRouter();
  const [restaurantName, setRestaurantName] = useState("");
  const [registerNumber, setRegisterNumber] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!restaurantName.trim() || !registerNumber.trim()) {
      alert("모든 필드를 입력해주세요.");
      return;
    }

    try {
      setIsLoading(true);
      await registRestaurant({
        name: restaurantName,
        registerNumber: registerNumber,
      });
      router.push("/main");
    } catch (error) {
      console.error("식당 등록 실패:", error);
      alert("식당 등록에 실패했습니다. 다시 시도해주세요.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="w-[500px] p-8 bg-loginBackgroundTransparent rounded-lg">
      <div className="max-w-sm mx-auto flex flex-col items-center drop-shadow-2xl">
        <div className="mb-12">
          <Image src={lolo} alt="Logo" width={150} height={150} priority />
        </div>

        <div className="text-white text-center mb-8">
          <h1 className="text-2xl font-bold mb-2">환영합니다!</h1>
          <p className="text-lg">식당 정보를 등록해주세요</p>
        </div>

        <form onSubmit={handleSubmit} className="w-full space-y-6">
          <div className="space-y-2">
            <label className="block text-white text-lg mb-1">식당 이름</label>
            <input
              type="text"
              value={restaurantName}
              onChange={(e) => setRestaurantName(e.target.value)}
              className="w-full px-4 py-3 rounded-lg bg-white/90 focus:outline-none focus:ring-2 focus:ring-green-500 text-black text-lg"
              placeholder="식당 이름을 입력하세요"
            />
          </div>

          <div className="space-y-2">
            <label className="block text-white text-lg mb-1">
              사업자 등록 번호
            </label>
            <input
              type="text"
              value={registerNumber}
              onChange={(e) => setRegisterNumber(e.target.value)}
              className="w-full px-4 py-3 rounded-lg bg-white/90 focus:outline-none focus:ring-2 focus:ring-green-500 text-black text-lg"
              placeholder="사업자 등록 번호를 입력하세요"
            />
          </div>

          <button
            type="submit"
            disabled={isLoading}
            className="w-full bg-[#1A5319] hover:bg-[#134313] text-white font-bold py-3 px-4 rounded-lg transition-all duration-200 ease-in-out transform hover:scale-105 disabled:opacity-50 disabled:cursor-not-allowed text-lg mt-4"
          >
            {isLoading ? "등록 중..." : "등록하기"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default RegisterLayout;
