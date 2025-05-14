import { Controller, Get, Post, Body, Patch, Param, Delete, ParseIntPipe } from '@nestjs/common';
import { MovieFavoriteService } from './movie-favorite.service';
import { CreateMovieFavoriteDto } from './dto/create-movie-favorite.dto';
import { UpdateMovieFavoriteDto } from './dto/update-movie-favorite.dto';

@Controller('movie-favorite')
export class MovieFavoriteController {
  constructor(private readonly movieFavoriteService: MovieFavoriteService) {}

  @Post()
  create(@Body() createMovieFavoriteDto: CreateMovieFavoriteDto) {
    return this.movieFavoriteService.create(createMovieFavoriteDto);
  }

  @Get(":userId")
  findAll(@Param('userId', ParseIntPipe) userId: number) {
    return this.movieFavoriteService.findAll(userId);
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.movieFavoriteService.findOne(+id);
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateMovieFavoriteDto: UpdateMovieFavoriteDto) {
    return this.movieFavoriteService.update(+id, updateMovieFavoriteDto);
  }

  @Delete()
  remove(@Body() createMovieFavoriteDto: CreateMovieFavoriteDto) {
    return this.movieFavoriteService.remove(createMovieFavoriteDto);
  }
}
