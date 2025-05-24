import type { Stats } from "../types/stats";
import { apiClient } from "./api-client"

export const fetchDashboardData = async () => {
    const response = await apiClient.get<Stats>('stats');

    const data = response.data;
    return {
        revenueData: data.revenueData,
        overviewData: [
        { label: 'Total movies', value: data.totalMovies },
        { label: 'Total ticket sold', value: data.totalTicketsSold },
        { label: 'Revenue (VNĐ)', value: data.totalRevenue },
        ],
    };
}
