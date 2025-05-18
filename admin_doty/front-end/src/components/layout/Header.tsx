import { Link } from "react-router-dom";

export const Header = () => {
    return (
        <div className="w-full flex items-center justify-between py-[20px] px-[24px]">
            <div className="text-[#6C47DB]">Logo</div>

            <nav>
                <ul className="flex items-center gap-[10px] text-[#6C47DB] hover:text-white">
                    <li>
                        <Link
                            to="/"
                            className="p-[8px] border border-[#6C47DB] rounded-[8px] hover:bg-[rgba(108,71,219,0.5)]"
                        >
                            Dashboard
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/movies"
                            className="p-[8px] border border-[#6C47DB] rounded-[8px] hover:bg-[rgba(108,71,219,0.5)]"
                        >
                            Movie
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/tickets"
                            className="p-[8px] border border-[#6C47DB] rounded-[8px] hover:bg-[rgba(108,71,219,0.5)]"
                        >
                            Ticket
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/settings"
                            className="p-[8px] border border-[#6C47DB] rounded-[8px] hover:bg-[rgba(108,71,219,0.5)]"
                        >
                            Setting
                        </Link>
                    </li>
                </ul>
            </nav>
        </div>
    );
};

export default Header;
