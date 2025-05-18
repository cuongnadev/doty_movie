import {
  BadRequestException,
  Injectable,
  NotFoundException,
} from '@nestjs/common';
import { CreateTicketDto } from './dto/create-ticket.dto';
import { UpdateTicketDto } from './dto/update-ticket.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { Ticket, TicketStatus } from './entities/ticket.entity';
import { Repository } from 'typeorm';
import { Seat, SeatType } from '../seat/entities/seat.entity';
import { ResponseTicketDto } from './dto/response-ticket.dto';
import { Showtime } from '../showtime/entities/showtime.entity';
import { generateTicketCode } from 'src/utils/generator';

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

    const ticketCode = generateTicketCode();
    const ticket = this.ticketRepo.create({
      user: { id: userId },
      showtime: { id: showtimeId },
      seatNumber,
      adultCount: ticketCount.adult,
      childCount: ticketCount.child,
      amount,
      ticketCode,
    });

    const savedTicket = await this.ticketRepo.save(ticket);

    const bankId = '970418';
    const accountNo = 'V3CASS6263742940';
    const usdAmount = savedTicket.amount;
    const vndAmount = usdAmount * 1000;
    const accountName = 'Nguyen Anh Cuong';
    const description = `THANHTOAN TICKET ${ticketCode}`;
    const urlQR = `https://img.vietqr.io/image/${bankId}-${accountNo}-compact2.jpg?amount=${vndAmount}&addInfo=${description}&accountName=${accountName}`;

    return {
      ticketCode: savedTicket.ticketCode,
      amount: savedTicket.amount,
      urlQR,
    };
  }

  async handlePaymentWebhook(payload: any) {
    if (!payload?.data || !Array.isArray(payload.data)) {
      console.log('Webhook payload không hợp lệ:', payload);
      return;
    }

    for (const transaction of payload.data) {
      const { description, amount: creditAmount } = transaction;

      if (!description) {
        console.log('Thiếu mô tả trong giao dịch:', transaction);
        continue;
      }

      const match = description.match(/TICKET (\w+)/);
      if (!match) {
        console.log('Không tìm thấy mã vé trong mô tả:', description);
        continue;
      }

      const ticketCode = match[1];
      const ticket = await this.ticketRepo.findOne({ where: { ticketCode } });

      if (!ticket) {
        console.log(`Không tìm thấy vé với mã: ${ticketCode}`);
        continue;
      }

      const expectedAmount = ticket.amount * 1000;

      if (creditAmount === expectedAmount) {
        ticket.status = TicketStatus.PAID;
        await this.ticketRepo.save(ticket);
        console.log(`✅ Vé ${ticketCode} đã được thanh toán.`);
      } else {
        console.log(
          `❌ Số tiền không khớp cho vé ${ticketCode}: Nhận ${creditAmount} ≠ Dự kiến ${expectedAmount}`,
        );
      }
    }
  }

  async cancelTicket(ticketCode: string) {
    const ticket = await this.ticketRepo.findOne({
      where: {
        ticketCode,
      },
      relations: ['showtime'],
    });

    if (!ticket) {
      throw new NotFoundException(`Ticket with code ${ticketCode} not found`);
    }

    if (ticket.status === TicketStatus.CANCELLED)
      throw new BadRequestException('Ticket is already cancelled');

    const seats = await this.seatRepo.find({
      where: {
        showtime: {
          id: ticket.showtime.id,
        },
      },
    });

    if (seats.length > 0) {
      const seatIds = seats.map((seat) => seat.id);
      await this.seatRepo.delete(seatIds);
    }

    ticket.status = TicketStatus.CANCELLED;
    return await this.ticketRepo.save(ticket);
  }

  async getStatus(ticketCode: string) {
    const ticket = await this.ticketRepo.findOne({
      where: { ticketCode },
      select: ['status'],
    });
    if (!ticket) throw new NotFoundException('Ticket not found!');

    return {
      status: ticket.status,
    };
  }

  async findTicketsByUser(userId: number) {
    const tickets = await this.ticketRepo.find({
      where: {
        user: { id: userId },
      },
      relations: ['showtime'],
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
        isUsed: ticket.isUsed,
        ticketCode: ticket.ticketCode,
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

  async remove(ticketCode: string) {
    const ticket = await this.ticketRepo.findOne({
      where: {
        ticketCode: ticketCode,
      },
      relations: ['showtime'],
    });

    if (!ticket) {
      throw new NotFoundException(`Ticket with code ${ticketCode} not found`);
    }

    const seats = await this.seatRepo.find({
      where: {
        showtime: {
          id: ticket.showtime.id,
        },
      },
    });

    if (seats.length > 0) {
      const seatIds = seats.map((seat) => seat.id);
      await this.seatRepo.delete(seatIds);
    }
    await this.ticketRepo.delete(ticket.id);
  }
}
