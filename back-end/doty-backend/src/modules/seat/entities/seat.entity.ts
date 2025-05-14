import { Showtime } from "src/modules/showtime/entities/showtime.entity";
import { Column, Entity, JoinColumn, ManyToOne, PrimaryGeneratedColumn } from "typeorm";

export enum SeatStatus {
    BOOKED = "booked"
}

export enum SeatType {
    STANDARD = "standard",
    VIP = "vip"
}

@Entity('seat')
export class Seat {
    @PrimaryGeneratedColumn()
    id: number;

    @Column({ name: 'seat_number' })
    seatNumber: string;

    @Column({
        type: "enum",
        enum: SeatStatus,
        default: SeatStatus.BOOKED
    })
    status: SeatStatus;

    @Column({
        type: "enum",
        enum: SeatType,
        default: SeatType.STANDARD
    })
    type: SeatType

    @ManyToOne(() => Showtime, (showtime) => showtime.seats, { onDelete: "CASCADE" })
    @JoinColumn({ name: "showtime_id" })
    showtime: Showtime
}
