import { ConflictException, Injectable } from '@nestjs/common';
import { CreateTheaterDto } from './dto/create-theater.dto';
import { UpdateTheaterDto } from './dto/update-theater.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { Theater } from './entities/theater.entity';
import { ILike, Repository } from 'typeorm';

@Injectable()
export class TheaterService {
  constructor(
    @InjectRepository(Theater) private theaterRepo: Repository<Theater>
  ) {}
  async create(createTheaterDto: CreateTheaterDto) {
    const existing = await this.theaterRepo.findOne({
      where: { name: ILike(createTheaterDto.name) },
    });
  
    if (existing) {
      throw new ConflictException('Theater with this name already exists');
    }
    const theater = this.theaterRepo.create(createTheaterDto);
    return this.theaterRepo.save(theater);
  }

  findAll() {
    return this.theaterRepo.find();
  }

  findOne(id: number) {
    return this.theaterRepo.findOne({
      where: { id: id }
    });
  }

  update(id: number, updateTheaterDto: UpdateTheaterDto) {
    return `This action updates a #${id} theater`;
  }

  remove(id: number) {
    return `This action removes a #${id} theater`;
  }
}
