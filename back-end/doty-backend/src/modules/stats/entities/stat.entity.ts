import { Type } from "class-transformer";
import { IsArray, IsInt, IsString, ValidateNested } from "class-validator";

class Revenue {
    @IsString()
    month: string;

    @IsInt()
    value: number;
}

export class Stats {
    @IsArray()
    @ValidateNested({ each: true })
    @Type(() => Revenue)
    revenueData: Revenue[];

    @IsString()
    totalMovies: number;

    @IsString()
    totalTicketsSold: number;

    @IsString()
    totalRevenue: number;
}
