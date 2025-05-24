import { Outlet } from "react-router-dom";
import { Header } from "../components";

export const AppRouterRoot = () => {
    return (
        <div 
            className="w-[100%] h-screen flex justify-center items-center"
        >
            <div className="w-[90%] h-[90%] border-[2px] border-[#6C47DB] rounded-[16px] flex flex-col justify-start shadow-xl/30 shadow-[#6C47DB]">
                <Header />
                <Outlet />
            </div>
        </div>
    );
}