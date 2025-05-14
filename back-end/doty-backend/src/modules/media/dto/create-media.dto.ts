import { IsInt, IsUrl, Min } from "class-validator";

export class CreateMediaDto {
    @IsUrl({}, { each: true })
    galleryUrl: string[];

    @IsUrl()
    urlTrailer: string;

    @IsUrl()
    urlPoster: string;

    @IsUrl()
    urlPrimary: string;

    @IsInt()
    @Min(1)
    movieId: number
}
