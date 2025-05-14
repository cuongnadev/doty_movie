import { ConflictException, Injectable } from '@nestjs/common';
import { CreateSeatDto } from './dto/create-seat.dto';
import { UpdateSeatDto } from './dto/update-seat.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { Seat } from './entities/seat.entity';
import { Repository } from 'typeorm';
import { Showtime } from '../showtime/entities/showtime.entity';

@Injectable()
export class SeatService {
  constructor(
    @InjectRepository(Seat) private seatRepo: Repository<Seat>,
    @InjectRepository(Showtime) private showtimeRepo: Repository<Showtime>
  ) {}
  async create(createSeatDto: CreateSeatDto) {
    const showtime = await this.showtimeRepo.findOne({
      where: { id: createSeatDto.showtimeId }
    })

    if(!showtime) {
      throw new ConflictException(`Showtime with id (${createSeatDto.showtimeId} does not exist)`);
    }

    const seat = this.seatRepo.create({
      ...createSeatDto,
      showtime
    })
    return this.seatRepo.save(seat);
  }

  findAll() {
    return this.seatRepo.find({
      relations: ['showtime']
    });
  }

  findByShowtimeId(showtimeId: number) {
    return this.seatRepo.find({
      where: {
        showtime: { id: showtimeId }
      }
    })
  }

  findOne(id: number) {
    return this.seatRepo.findOne({
      where: { id },
      relations: ['showtime']
    });
  }

  async update(id: number, updateSeatDto: UpdateSeatDto) {
    const seat = await this.seatRepo.findOne({
      where: { id },
      relations: ['showtime']
    });
  
    if (!seat) {
      throw new ConflictException(`Seat with id (${id}) does not exist`);
    }
  
    if (updateSeatDto.showtimeId) {
      const showtime = await this.showtimeRepo.findOne({
        where: { id: updateSeatDto.showtimeId }
      });
  
      if (!showtime) {
        throw new ConflictException(
          `Showtime with id (${updateSeatDto.showtimeId}) does not exist`
        );
      }
  
      seat.showtime = showtime;
    }
  
    if (updateSeatDto.seatNumber !== undefined) {
      seat.seatNumber = updateSeatDto.seatNumber;
    }
    if (updateSeatDto.status !== undefined) {
      seat.status = updateSeatDto.status;
    }
    if (updateSeatDto.type !== undefined) {
      seat.type = updateSeatDto.type;
    }
  
    return this.seatRepo.save(seat);
  }

  remove(id: number) {
    return `This action removes a #${id} seat`;
  }
}
