import { IsInt, IsOptional, IsString } from "class-validator";

export class CreateTheaterDto {    
    @IsString()
    name: string;

    @IsString()
    location: string;

    @IsOptional()
    @IsInt()
    capacity?: number;
}
