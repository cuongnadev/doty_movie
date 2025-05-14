import { PartialType } from '@nestjs/mapped-types';
import { CreateMovieFavoriteDto } from './create-movie-favorite.dto';

export class UpdateMovieFavoriteDto extends PartialType(CreateMovieFavoriteDto) {}
