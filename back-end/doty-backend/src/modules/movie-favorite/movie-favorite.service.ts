import { Injectable } from '@nestjs/common';
import { CreateMovieFavoriteDto } from './dto/create-movie-favorite.dto';
import { UpdateMovieFavoriteDto } from './dto/update-movie-favorite.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { MovieFavorite } from './entities/movie-favorite.entity';
import { Repository } from 'typeorm';

@Injectable()
export class MovieFavoriteService {
  constructor(
    @InjectRepository(MovieFavorite) private movieFavoriteRepo: Repository<MovieFavorite>
  ){}
  create(createMovieFavoriteDto: CreateMovieFavoriteDto) {
    const { movieId, userId } = createMovieFavoriteDto;
    const movieFavorite =  this.movieFavoriteRepo.create({
      movie: { id: movieId },
      user: { id: userId },
    });
    return this.movieFavoriteRepo.save(movieFavorite);
  }

  async findAll(userId: number) {
    return this.movieFavoriteRepo.find({
      where: {
        user: {
          id: userId
        }
      },
      relations: ["movie", "movie.medias"]
    });
  }

  findOne(id: number) {
    return `This action returns a #${id} movieFavorite`;
  }

  update(id: number, updateMovieFavoriteDto: UpdateMovieFavoriteDto) {
    return `This action updates a #${id} movieFavorite`;
  }

  remove(createMovieFavoriteDto: CreateMovieFavoriteDto) {
    const { movieId, userId } = createMovieFavoriteDto;
    return this.movieFavoriteRepo.delete({
      user: {
        id: userId
      },
      movie: {
        id: movieId
      }
    });
  }
}
