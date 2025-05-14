import { Media } from "src/modules/media/entities/media.entity";
import { MovieFavorite } from "src/modules/movie-favorite/entities/movie-favorite.entity";
import { Showtime } from "src/modules/showtime/entities/showtime.entity";
import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";

export enum MovieStatus {
    HIGHLIGHT = 'highlight',
    NOW_SHOWING = 'now_showing',
    COMING_SOON = 'coming_soon'
}

@Entity("movie")
export class Movie {
    @PrimaryGeneratedColumn()
    id: number;

    @Column({ unique: true })
    title: string;

    @Column("text")
    description: string;

    @Column("int")
    duration: number;

    @Column({ type: "date", name: 'release_date' })
    releaseDate: Date;

    @Column('varchar', { array: true })
    genre: string[];

    @Column({ 
        type: "enum",
        enum: MovieStatus,
        default: MovieStatus.NOW_SHOWING
    })
    status: MovieStatus;

    @Column({ default: false, name: 'is_favorite' })
    isFavorite: boolean;

    @OneToMany(() => Media, (media) => media.movie, { onDelete: "CASCADE" })
    medias: Media[];

    @OneToMany(() => Showtime, (showtime) => showtime.movie, { onDelete: 'CASCADE' })
    showtimes: Showtime[];

    @OneToMany(() => MovieFavorite, (favorite) => favorite.movie, { cascade: true })
    movieFavorites: MovieFavorite[];
}
