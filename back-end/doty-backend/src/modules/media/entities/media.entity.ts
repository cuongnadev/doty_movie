import { Movie } from "src/modules/movie/entities/movie.entity";
import { Column, Entity, JoinColumn, ManyToOne, PrimaryGeneratedColumn } from "typeorm";

@Entity("media")
export class Media {
    @PrimaryGeneratedColumn()
    id: number;

    @Column({
        type: "simple-array",
        nullable: true,
        name: 'gallery_url'
    })
    galleryUrl: string[];

    @Column({ nullable: true })
    urlTrailer: string;

    @Column({ nullable: true })
    urlPoster: string;

    @Column({ nullable: true })
    urlPrimary: string;

    @ManyToOne(() => Movie, (movie) => movie.medias, { onDelete: "CASCADE" })
    @JoinColumn({ name: 'movie_id' })
    movie: Movie
}
