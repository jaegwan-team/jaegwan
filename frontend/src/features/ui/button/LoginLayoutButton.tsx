import "../../../../styles/globals.css";

interface ButtonProps {
    content: string,
    eventFunc : () => void,
}

const LoginLayoutButton = ( btnProps : ButtonProps ) => {

    const handleClick = (e : React.MouseEvent<HTMLElement, MouseEvent>) => {
        e.preventDefault();
        btnProps.eventFunc();
    }

    return (
        <button type="submit" className="
            text-white bg-loginBtnBackground hover:bg-loginClickedBackground focus:ring-4 focus:outline-none
            focus:ring-blue-300 font-medium text-2xl/10 rounded-lg
            text-xl w-full sm:w-auto px-8 py-5 text-center"
            onClick={handleClick}
        >
            {btnProps.content}
        </button>
    );
}

export default LoginLayoutButton;