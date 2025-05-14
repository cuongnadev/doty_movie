import { IsInt, Min } from "class-validator";

export class CreateMovieFavoriteDto {
    @IsInt()
    @Min(1)
    userId: number;

    @IsInt()
    @Min(1)
    movieId: number;
}
