import { useMemo } from "react";
import { AppProvider } from "./provider";
import { createRouter } from "./router";
import { RouterProvider } from "react-router-dom";

const AppRouter = () => {
    const router = useMemo(() => createRouter(), []);
    return (<RouterProvider router={router}/>)
}
export const App = () => {
    return(
        <AppProvider>
            <AppRouter/>
        </AppProvider>
    );
}