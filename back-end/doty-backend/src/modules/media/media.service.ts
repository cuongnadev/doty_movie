import { Injectable } from '@nestjs/common';
import { CreateMediaDto } from './dto/create-media.dto';
import { UpdateMediaDto } from './dto/update-media.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { Media } from './entities/media.entity';
import { Repository } from 'typeorm';
import { Movie } from '../movie/entities/movie.entity';

@Injectable()
export class MediaService {
  constructor(
    @InjectRepository(Media) private mediaRepo: Repository<Media>,
    @InjectRepository(Movie) private movieRepo: Repository<Movie>
  ) {}
  async create(createMediaDto: CreateMediaDto) {
    const movie = await this.movieRepo.findOneByOrFail({ id: createMediaDto.movieId });

    const media = this.mediaRepo.create({
      ...createMediaDto,
      movie: movie
    })
    return this.mediaRepo.save(media);
  }

  findAll() {
    return this.mediaRepo.find();
  }

  findOne(id: number) {
    return this.mediaRepo.findOne({
      where: { id: id }
    });
  }

  async update(id: number, updateMediaDto: UpdateMediaDto) {
    const media = await this.mediaRepo.findOne({
      where: { id },
      relations: ['movie'],
    });
  
    if (!media) {
      throw new Error(`Media with id ${id} not found`);
    }
  
    Object.assign(media, updateMediaDto);
  
    return this.mediaRepo.save(media);
  }

  remove(id: number) {
    return `This action removes a #${id} media`;
  }
}
