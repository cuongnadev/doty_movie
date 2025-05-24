import { Type } from "class-transformer";
import { IsArray, IsString, ValidateNested } from "class-validator";

class Revenue {
    @IsString()
    month: string;

    @IsString()
    value: string;
}

export class Stats {
    @IsArray()
    @ValidateNested({ each: true })
    @Type(() => Revenue)
    revenueData: Revenue[];

    @IsString()
    totalMovies: string;

    @IsString()
    totalTicketsSold: string;

    @IsString()
    totalRevenue: string;
}
