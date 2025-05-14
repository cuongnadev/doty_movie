import { Module } from '@nestjs/common';
import { MovieFavoriteService } from './movie-favorite.service';
import { MovieFavoriteController } from './movie-favorite.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { MovieFavorite } from './entities/movie-favorite.entity';

@Module({
  controllers: [MovieFavoriteController],
  providers: [MovieFavoriteService],
  imports: [TypeOrmModule.forFeature([MovieFavorite])],
  exports: [TypeOrmModule]
})
export class MovieFavoriteModule {}
