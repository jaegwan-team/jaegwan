import { useRouter } from "next/navigation";
import "../../../../styles/globals.css";
import LoginLayoutButton from "../button/LoginLayoutButton";

const LoginLayout = () => {
  const router = useRouter();

  return (
    <div className="w-2/6 p-6 bg-loginBackgroundTransparent rounded-lg">
      <form className="max-w-sm mx-auto my-16 drop-shadow-2xl">
        <div className="mb-5">
          <label
            htmlFor="email"
            className="block mb-2 text-2xl font-bold text-white dark:text-white"
          >
            Your email
          </label>
          <input
            type="email"
            id="email"
            className="bg-inputFieldBackground text-gray-900 text-xl font-medium rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="name@flowbite.com"
            required
          />
        </div>
        <div className="mb-5">
          <label
            htmlFor="password"
            className="block mb-2 text-2xl font-bold text-white dark:text-white"
          >
            Your password
          </label>
          <input
            type="password"
            id="password"
            className="bg-inputFieldBackground text-gray-900 text-xl rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="********"
            required
          />
        </div>
        <div className="flex items-start mb-5">
          <div className="flex items-center h-5">
            <input
              id="remember"
              type="checkbox"
              value=""
              className="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800"
              required
            />
          </div>
          <label
            htmlFor="remember"
            className="ms-2 text-xl font-bold text-white dark:text-gray-300"
          >
            Remember me
          </label>
        </div>
        <div className="flex justify-between">
          <LoginLayoutButton
            content={"Sign in"}
            eventFunc={() => {
              router.push("signIn/");
            }}
          />
          <LoginLayoutButton
            content={"Log in"}
            eventFunc={() => {
              router.push("main/");
            }}
          />
        </div>
      </form>
    </div>
  );
};

export default LoginLayout;
