import { Type } from "class-transformer";
import { IsArray, IsBoolean, IsEnum, IsInt, IsString, ValidateNested } from "class-validator";
import { TicketCountDto } from "./create-ticket.dto";
import { TicketStatus } from "../entities/ticket.entity";

export class ResponseTicketDto {
    @IsInt()
    id: number;

    @ValidateNested()
    @Type(() => TicketCountDto)
    ticketCount: TicketCountDto;

    @IsArray()
    selectedSeats: string[];

    @IsString()
    movie: string;

    @IsString()
    theater: string;

    @IsString()
    startTime: string;

    @IsString()
    endTime: string;

    @IsInt()
    amount: number;

    @IsEnum(TicketStatus)
    status: TicketStatus;

    @IsBoolean()
    isUsed: boolean;

    @IsString()
    ticketCode: string;
}