import { useRouter } from "next/navigation";
import LoginLayoutButton from "../button/LoginLayoutButton";

const SignInLayout = () => {

    const router = useRouter();

    return (
        <div className="w-2/6 p-6 bg-white rounded-xl flex flex-col justify-center shadow-2xl">
            <form className="flex flex-col justify-center mx-10 drop-shadow-2xl">
                <div className="grid gap-6 mb-12 md:grid-cols-2">
                    <div>
                        <label htmlFor="first_name" className="block mb-2 text-2xl font-bold text-black ">First name</label>
                        <input type="text" id="first_name" className="bg-gray-100 border border-gray-300 text-gray-900
                text-xl rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="John" required />
                    </div>
                    <div>
                        <label htmlFor="last_name" className="block mb-2 text-2xl font-bold text-black ">Last name</label>
                        <input type="text" id="last_name" className="bg-gray-100 border border-gray-300 text-gray-900
                text-xl rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Doe" required />
                    </div>
                </div>
                <div className="mb-16">
                    <label htmlFor="phone" className="block mb-2 text-2xl font-bold text-black ">Phone number</label>
                    <input type="tel" id="phone" className="bg-gray-100 border border-gray-300 text-gray-900 text-xl rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="123-45-678" pattern="[0-9]{3}-[0-9]{2}-[0-9]{3}" required />
                </div>
                <div className="mb-16">
                    <label htmlFor="email" className="block mb-2 text-2xl font-bold text-black ">Email address</label>
                    <input type="email" id="email" className="bg-gray-100 border border-gray-300 text-gray-900 text-xl rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="john.doe@company.com" required />
                </div>
                <div className="mb-16">
                    <label htmlFor="password" className="block mb-2 text-2xl font-bold text-black">Password</label>
                    <input type="password" id="password" className="bg-gray-100 border border-gray-300 text-gray-900 text-xl rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="•••••••••" required />
                </div>
                <div className="mb-16">
                    <label htmlFor="confirm_password" className="block mb-2 text-2xl font-bold text-black">Confirm password</label>
                    <input type="password" id="confirm_password" className="bg-gray-100 border border-gray-300 text-gray-900 text-xl rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="•••••••••" required />
                </div>

                <div className="flex justify-around">
                    <LoginLayoutButton content={"Submit"} eventFunc={() => {router.push('login/')}}  />
                    <LoginLayoutButton content={"Close"} eventFunc={() => {router.push('login/')}}  />
                </div>
            </form>
        </div>
    );

};

export default SignInLayout;