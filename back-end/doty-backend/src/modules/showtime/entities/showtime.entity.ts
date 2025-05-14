import { Movie } from "src/modules/movie/entities/movie.entity";
import { Seat } from "src/modules/seat/entities/seat.entity";
import { Theater } from "src/modules/theater/entities/theater.entity";
import { Ticket } from "src/modules/ticket/entities/ticket.entity";
import { Column, Entity, JoinColumn, ManyToOne, OneToMany, PrimaryGeneratedColumn } from "typeorm";

@Entity('showtime')
export class Showtime {
    @PrimaryGeneratedColumn()
    id: number;

    @Column({ type: 'timestamp', name: 'start_time' })
    startTime: Date;

    @Column({ type: 'timestamp', name: 'end_time' })
    endTime: Date;

    @ManyToOne(() => Movie, (movie) => movie.showtimes, { onDelete: 'CASCADE' })
    @JoinColumn({ name: 'movie_id' })
    movie: Movie;

    @ManyToOne(() => Theater, (theater) => theater.showtimes, { onDelete: 'CASCADE' })
    @JoinColumn({ name: 'theater_id' })
    theater: Theater;

    @OneToMany(() => Seat, (seat) => seat.showtime, { onDelete: "CASCADE" })
    seats: Seat[]

    @OneToMany(() => Ticket, (ticket) => ticket.showtime, { onDelete: "CASCADE" })
    tickets: Ticket[]
}
