import { Showtime } from "src/modules/showtime/entities/showtime.entity";
import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";

@Entity('theater')
export class Theater {
    @PrimaryGeneratedColumn()
    id: number

    @Column({ unique: true })
    name: string;

    @Column({ unique: true })
    location: string;

    @Column({ default: 88 })
    capacity: number;

    @OneToMany(() => Showtime, (showtime) => showtime.theater, { onDelete: 'CASCADE' })
    showtimes: Showtime[];
}
