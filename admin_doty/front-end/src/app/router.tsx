import { createBrowserRouter } from "react-router-dom"
import { routes } from "./routes"

export const createRouter = () => {
    return createBrowserRouter(routes);
}