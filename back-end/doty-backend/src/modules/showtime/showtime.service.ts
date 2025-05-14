import { ConflictException, Injectable } from '@nestjs/common';
import { CreateShowtimeDto } from './dto/create-showtime.dto';
import { UpdateShowtimeDto } from './dto/update-showtime.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { Showtime } from './entities/showtime.entity';
import { Repository } from 'typeorm';
import { Movie } from '../movie/entities/movie.entity';
import { Theater } from '../theater/entities/theater.entity';

@Injectable()
export class ShowtimeService {
  constructor(
    @InjectRepository(Showtime) private showtimeRepo: Repository<Showtime>,
    @InjectRepository(Movie) private movieRepo: Repository<Movie>,
    @InjectRepository(Theater) private theaterRepo: Repository<Theater>
  ) {}
  async create(createShowtimeDto: CreateShowtimeDto[]) {
    const showtimes: Showtime[] = [];

    for (const dto of createShowtimeDto) {
      const movie = await this.movieRepo.findOne({ where: { id: dto.movieId } });
      const theater = await this.theaterRepo.findOne({ where: { id: dto.theaterId } });

      if (!movie) {
        throw new ConflictException(`Movie with id (${dto.movieId}) does not exist`);
      }

      if (!theater) {
        throw new ConflictException(`Theater with id (${dto.theaterId}) does not exist`);
      }

      const showtime = this.showtimeRepo.create({
        startTime: dto.startTime,
        endTime: dto.endTime,
        movie,
        theater,
      });

      showtimes.push(showtime);
    }
    return this.showtimeRepo.save(showtimes);
  }

  findAll() {
    return this.showtimeRepo.find({
      relations: ['movie', 'theater']
    });
  }

  findOne(id: number) {
    return this.showtimeRepo.findOne({
      where: { id: id },
      relations: ['movie', 'theater']
    });
  }

  findByMovieAndTheater(movieId: number, theaterId: number) {
    return this.showtimeRepo.find({
      where: {
        movie: { id: movieId },
        theater: { id: theaterId }
      }
    })
  }

  update(id: number, updateShowtimeDto: UpdateShowtimeDto) {
    return `This action updates a #${id} showtime`;
  }

  remove(id: number) {
    return `This action removes a #${id} showtime`;
  }
}
