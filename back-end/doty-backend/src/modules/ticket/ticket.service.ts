import { Injectable } from '@nestjs/common';
import { CreateTicketDto } from './dto/create-ticket.dto';
import { UpdateTicketDto } from './dto/update-ticket.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { Ticket } from './entities/ticket.entity';
import { Repository } from 'typeorm';
import { Seat, SeatType } from '../seat/entities/seat.entity';
import { ResponseTicketDto } from './dto/response-ticket.dto';
import { Showtime } from '../showtime/entities/showtime.entity';

@Injectable()
export class TicketService {
  constructor(
    @InjectRepository(Ticket) private ticketRepo: Repository<Ticket>,
    @InjectRepository(Seat) private seatRepo: Repository<Seat>,
    @InjectRepository(Showtime) private showtimeRepo: Repository<Showtime>,
  ) {}

  async create(createTicketDto: CreateTicketDto) {
    const { userId, showtimeId, ticketCount, seatNumber, amount } =
      createTicketDto;

    const seatEntities = await Promise.all(
      seatNumber.map(async (seatItem) => {
        const type =
          seatItem.charAt(0).toUpperCase() === 'A' ||
          seatItem.charAt(0).toUpperCase() === 'B'
            ? SeatType.VIP
            : SeatType.STANDARD;

        const seat = this.seatRepo.create({
          showtime: { id: showtimeId },
          seatNumber: seatItem,
          type: type,
        });

        return await this.seatRepo.save(seat);
      }),
    );

    const ticket = this.ticketRepo.create({
      user: { id: userId },
      showtime: { id: showtimeId },
      seatNumber,
      adultCount: ticketCount.adult,
      childCount: ticketCount.child,
      amount
    });
    return this.ticketRepo.save(ticket);
  }

  async findTicketsByUser(userId: number) {
    const tickets = await this.ticketRepo.find({
      where: {
        user: { id: userId }
      },
      relations: ["showtime"]
    });

    const responses: ResponseTicketDto[] = [];

    for (const ticket of tickets) {
      const showtime = await this.showtimeRepo.findOne({
        where: { id: ticket.showtime.id },
        relations: ['movie', 'theater'],
      });
  
      if (!showtime) continue;
  
      responses.push({
        id: ticket.id,
        ticketCount: {
          adult: ticket.adultCount,
          child: ticket.childCount,
        },
        selectedSeats: ticket.seatNumber,
        movie: showtime.movie.title,
        theater: showtime.theater.name,
        startTime: showtime.startTime.toISOString(),
        endTime: showtime.endTime.toISOString(),
        amount: ticket.amount,
        isUsed: ticket.isUsed
      });
    }

    return responses;
  }

  findAll() {
    return `This action returns all ticket`;
  }

  findOne(id: number) {
    return `This action returns a #${id} ticket`;
  }

  update(id: number, updateTicketDto: UpdateTicketDto) {
    return `This action updates a #${id} ticket`;
  }

  remove(id: number) {
    return `This action removes a #${id} ticket`;
  }
}
