import { Module } from '@nestjs/common';
import { MovieService } from './movie.service';
import { MovieController } from './movie.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Movie } from './entities/movie.entity';
import { MovieFavoriteModule } from '../movie-favorite/movie-favorite.module';

@Module({
  imports: [TypeOrmModule.forFeature([Movie]), MovieFavoriteModule],
  controllers: [MovieController],
  providers: [MovieService],
  exports: [TypeOrmModule]
})
export class MovieModule {}
