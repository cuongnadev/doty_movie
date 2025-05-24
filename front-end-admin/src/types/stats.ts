export interface Stats {
    revenueData: {
        month: string,
        value: number
    }[],
    totalMovies: number,
    totalTicketsSold: number,
    totalRevenue: number
}