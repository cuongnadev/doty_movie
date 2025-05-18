import { QueryClientProvider } from "@tanstack/react-query";
import type React from "react";
import { queryClient } from "../utils/libs/react-query";

export const AppProvider = ({ children }: { children: React.ReactNode }) => {
    return(
        <>
            <QueryClientProvider client={queryClient} >{children}</QueryClientProvider>
        </>
    )
}