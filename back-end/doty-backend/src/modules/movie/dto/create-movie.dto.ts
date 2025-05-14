import { IsEnum, IsInt, IsOptional, IsString } from "class-validator";
import { MovieStatus } from "../entities/movie.entity";

export class CreateMovieDto {
    @IsString()
    title: string;

    @IsOptional()
    description?: string;

    @IsInt()
    duration: number;

    releaseDate: Date;

    @IsString({ each: true })
    @IsOptional()
    genre?: string[];

    @IsEnum(MovieStatus)
    status: MovieStatus;

    @IsOptional()
    isFavorite?: boolean;
}
