import { ConflictException, Injectable } from '@nestjs/common';
import { CreateMovieDto } from './dto/create-movie.dto';
import { UpdateMovieDto } from './dto/update-movie.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { ILike, Repository } from 'typeorm';
import { Movie, MovieStatus } from './entities/movie.entity';
import { MovieFavorite } from '../movie-favorite/entities/movie-favorite.entity';

@Injectable()
export class MovieService {
  constructor(
    @InjectRepository(Movie) private movieRepo: Repository<Movie>,
    @InjectRepository(MovieFavorite) private movieFavoriteRepo: Repository<MovieFavorite>
  ) {}
  async create(createMovieDto: CreateMovieDto) {
    const existing = await this.movieRepo.findOne({
      where: { title: ILike(createMovieDto.title) }
    })

    if(existing) {
      throw new ConflictException("Movie with this title already exists")
    }
    const movie = this.movieRepo.create(createMovieDto);
    return this.movieRepo.save(movie);
  }

  findAll() {
    return this.movieRepo.find({
      relations: ["medias"]
    });
  }

  findMovieHighLights() {
    return this.movieRepo.find({
      where: { status: MovieStatus.HIGHLIGHT },
      relations: ["medias"]
    })
  }

  findNewMovies() {
    return this.movieRepo.find({
      where: { status: MovieStatus.NOW_SHOWING },
      relations: ["medias"]
    })
  }

  findComingSoonMovies() {
    return this.movieRepo.find({
      where: { status: MovieStatus.COMING_SOON },
      relations: ["medias"]
    })
  }

  findFavoriteMovies() {
    return this.movieRepo.find({
      where: { isFavorite: true },
      relations: ["medias"]
    })
  }

  async findOne(movieId: number, userId: number) {
    const movie = await this.movieRepo.findOne({
      where: { id: movieId },
      relations: ["medias"]
    });

    const movieFavorite = await this.movieFavoriteRepo.findOne({
      where: {
        movie: {
          id: movieId
        },
        user: {
          id: userId
        }
      },
      relations: ["movie"]
    });

    if (movieFavorite != null && movie) {
      movie.isFavorite = true;
    }
    
    return movie;
  }

  update(id: number, updateMovieDto: UpdateMovieDto) {
    return this.movieRepo.update(id, updateMovieDto);
  }

  remove(id: number) {
    return `This action removes a #${id} movie`;
  }
}
