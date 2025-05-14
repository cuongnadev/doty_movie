import { Module } from '@nestjs/common';
import { ShowtimeService } from './showtime.service';
import { ShowtimeController } from './showtime.controller';
import { TypeORMError } from 'typeorm';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Showtime } from './entities/showtime.entity';
import { MovieModule } from '../movie/movie.module';
import { TheaterModule } from '../theater/theater.module';

@Module({
  imports: [
    MovieModule,
    TheaterModule,
    TypeOrmModule.forFeature([Showtime])
  ],
  controllers: [ShowtimeController],
  providers: [ShowtimeService],
  exports: [TypeOrmModule]
})
export class ShowtimeModule {}
