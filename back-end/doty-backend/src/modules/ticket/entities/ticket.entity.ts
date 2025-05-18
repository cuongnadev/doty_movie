import { Showtime } from 'src/modules/showtime/entities/showtime.entity';
import { User } from 'src/modules/user/entities/user.entity';
import {
  Column,
  CreateDateColumn,
  Entity,
  JoinColumn,
  ManyToOne,
  PrimaryGeneratedColumn,
} from 'typeorm';

export enum TicketStatus {
  PENDING = 'pending',
  CONFIRMED = 'confirmed',
  CANCELLED = 'cancelled',
  PAID = 'paid'
}

@Entity('ticket')
export class Ticket {
  @PrimaryGeneratedColumn()
  id: number;

  @ManyToOne(() => User, (user) => user.tickets, { onDelete: 'CASCADE' })
  @JoinColumn({ name: 'user_id' })
  user: User;

  @ManyToOne(() => Showtime, (showtime) => showtime.tickets, {
    onDelete: 'CASCADE',
  })
  @JoinColumn({ name: 'showtime_id' })
  showtime: Showtime;

  @Column({ type: 'simple-array', name: 'seat_number' })
  seatNumber: string[];

  @Column({ type: 'int', name: 'adult_count' })
  adultCount: number;

  @Column({ type: 'int', name: 'child_count' })
  childCount: number;

  @Column({ type: 'int' })
  amount: number;

  @Column({ type: "boolean", default: false, name: "is_used" })
  isUsed: boolean;

  @Column({ type: 'enum', enum: TicketStatus, default: TicketStatus.PENDING })
  status: TicketStatus;

  @Column({ type: 'varchar', length: 50, name: 'ticket_code', unique: true })
  ticketCode: string;

  @CreateDateColumn({ name: 'created_at' })
  createdAt: Date;
}
