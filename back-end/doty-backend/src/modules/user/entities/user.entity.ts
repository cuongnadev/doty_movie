import { MovieFavorite } from "src/modules/movie-favorite/entities/movie-favorite.entity";
import { Ticket } from "src/modules/ticket/entities/ticket.entity";
import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";

@Entity('user')
export class User {
    @PrimaryGeneratedColumn()
    id: number

    @Column({ unique: true })
    email: string

    @Column()
    password: string;

    @Column()
    name: string;

    @OneToMany(() => Ticket, (ticket) => ticket.user, { onDelete: "CASCADE" })
    tickets: Ticket[];

    @OneToMany(() => MovieFavorite, (favorite) => favorite.user, { cascade: true })
    movieFavorites: MovieFavorite[];
}
