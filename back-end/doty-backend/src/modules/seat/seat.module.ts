import { Module } from '@nestjs/common';
import { SeatService } from './seat.service';
import { SeatController } from './seat.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Seat } from './entities/seat.entity';
import { ShowtimeModule } from '../showtime/showtime.module';

@Module({
  imports: [
    ShowtimeModule,
    TypeOrmModule.forFeature([Seat])
  ],
  controllers: [SeatController],
  providers: [SeatService],
  exports: [TypeOrmModule]
})
export class SeatModule {}
