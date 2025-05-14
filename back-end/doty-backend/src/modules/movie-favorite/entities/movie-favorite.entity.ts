import { Movie } from "src/modules/movie/entities/movie.entity";
import { User } from "src/modules/user/entities/user.entity";
import { Entity, JoinColumn, ManyToOne, PrimaryGeneratedColumn } from "typeorm";

@Entity('movie_favorite')
export class MovieFavorite {
    @PrimaryGeneratedColumn()
    id: number;

    @ManyToOne(() => User, (user) => user.movieFavorites, { onDelete: 'CASCADE' })
    @JoinColumn({ name: 'user_id' })
    user: User;

    @ManyToOne(() => Movie, (movie) => movie.movieFavorites, { onDelete: 'CASCADE' })
    @JoinColumn({ name: 'movie_id' })
    movie: Movie;
}
