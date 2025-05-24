import { Injectable } from '@nestjs/common';
import { MovieService } from '../movie/movie.service';
import { TicketService } from '../ticket/ticket.service';
import { Stats } from './entities/stat.entity';

@Injectable()
export class StatsService {
  constructor(
    private movieService: MovieService,
    private ticketService: TicketService,
  ) {}
  async getStats(): Promise<Stats> {
    // Fetch total number of movies
    const totalMovies = await this.movieService.count();

    // Fetch total tickets sold and total revenue
    const ticketStats = await this.ticketService.getTicketStats();

    // Fetch monthly revenue for revenueData
    const monthlyRevenue = await this.ticketService.getMonthlyRevenue();

    return {
      revenueData: monthlyRevenue.map((item) => ({
        month: item.month,
        value: item.revenue.toString(),
      })),
      totalMovies: totalMovies.toString(),
      totalTicketsSold: ticketStats.totalTicketsSold.toString(),
      totalRevenue: ticketStats.totalRevenue.toString(),
    };
  }
}
