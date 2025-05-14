import { Type } from "class-transformer";
import { IsArray, IsBoolean, IsInt, ValidateNested } from "class-validator";

export class TicketCountDto {
    @IsInt()
    adult: number;

    @IsInt()
    child: number;
}

export class CreateTicketDto {
    @IsInt()
    userId: number;

    @ValidateNested()
    @Type(() => TicketCountDto)
    ticketCount: TicketCountDto;

    @IsInt()
    showtimeId: number;

    @IsArray()
    seatNumber: string[];

    @IsInt()
    amount: number;
}
