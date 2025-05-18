import { Module } from '@nestjs/common';
import { TicketService } from './ticket.service';
import { TicketController } from './ticket.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Ticket } from './entities/ticket.entity';
import { SeatModule } from '../seat/seat.module';
import { ShowtimeModule } from '../showtime/showtime.module';

@Module({
  imports: [
    SeatModule,
    ShowtimeModule,
    TypeOrmModule.forFeature([Ticket])
  ],
  controllers: [TicketController],
  providers: [TicketService],
})
export class TicketModule {}
