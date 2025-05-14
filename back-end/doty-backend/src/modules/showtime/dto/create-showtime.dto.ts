import { IsDateString, IsInt, IsNotEmpty } from "class-validator";

export class CreateShowtimeDto {
    @IsDateString()
    @IsNotEmpty()
    startTime: Date;

    @IsDateString()
    @IsNotEmpty()
    endTime: Date;

    @IsInt()
    @IsNotEmpty()
    movieId: number;

    @IsInt()
    @IsNotEmpty()
    theaterId: number;
}
