export const routes = [
    {
        path: "",
        lazy: async () => {
            const { AppRouterRoot } = await import("./root");
            return { Component: AppRouterRoot };
        },
        children: [
            {
                path: "",
                lazy: async () => {
                    const { Dashboard } = await import("../pages");
                    return { Component: Dashboard };
                },
            },
            {
                path: "/movies",
                lazy: async () => {
                    const { Movie } = await import("../pages");
                    return { Component: Movie };
                },
            },
            {
                path: "/tickets",
                lazy: async () => {
                    const { Ticket } = await import("../pages");
                    return { Component: Ticket };
                },
            },
        ]
    }
];