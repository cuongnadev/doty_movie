import { IsEnum, IsInt, IsNotEmpty, IsOptional, IsString } from "class-validator";
import { SeatStatus, SeatType } from "../entities/seat.entity";

export class CreateSeatDto {
    @IsString()
    @IsNotEmpty()
    seatNumber: string;

    @IsOptional()
    @IsEnum(SeatStatus)
    status?: SeatStatus

    @IsOptional()
    @IsEnum(SeatType)
    type?: SeatType

    @IsInt()
    @IsNotEmpty()
    showtimeId: number;
}
