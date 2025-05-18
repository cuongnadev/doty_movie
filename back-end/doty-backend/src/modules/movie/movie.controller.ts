import { Controller, Get, Post, Body, Patch, Param, Delete, Query } from '@nestjs/common';
import { MovieService } from './movie.service';
import { CreateMovieDto } from './dto/create-movie.dto';
import { UpdateMovieDto } from './dto/update-movie.dto';

@Controller('movie')
export class MovieController {
  constructor(private readonly movieService: MovieService) {}

  @Post("create")
  create(@Body() createMovieDto: CreateMovieDto) {
    return this.movieService.create(createMovieDto);
  }

  @Get()
  findAll() {
    return this.movieService.findAll();
  }

  @Get("high-light")
  findMovieHighLights() {
    return this.movieService.findMovieHighLights();
  }

  @Get("new-movie")
  findNewMovies() {
    return this.movieService.findNewMovies();
  }

  @Get("coming-soon")
  findComingSoonMovies() {
    return this.movieService.findComingSoonMovies();
  }

  @Get("favorites")
  findFavoriteMovies() {
    return this.movieService.findFavoriteMovies();
  }

  @Get(':movieId/:userId')
  findMovieByUser(
    @Param('movieId') movieId: string,
    @Param('userId') userId: string,
  ) {
    return this.movieService.findOne(+movieId, +userId);
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateMovieDto: UpdateMovieDto) {
    return this.movieService.update(+id, updateMovieDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.movieService.remove(+id);
  }

  @Get('search')
  search(@Query("q") query: string) {
    return this.movieService.search(query)
  }
}
