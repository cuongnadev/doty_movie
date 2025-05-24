import { Module } from '@nestjs/common';
import { StatsService } from './stats.service';
import { StatsController } from './stats.controller';
import { MovieModule } from '../movie/movie.module';
import { TicketModule } from '../ticket/ticket.module';

@Module({
  controllers: [StatsController],
  providers: [StatsService],
  imports: [MovieModule, TicketModule]
})
export class StatsModule {}
