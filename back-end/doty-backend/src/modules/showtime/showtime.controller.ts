import { Controller, Get, Post, Body, Patch, Param, Delete, ParseIntPipe, Query } from '@nestjs/common';
import { ShowtimeService } from './showtime.service';
import { CreateShowtimeDto } from './dto/create-showtime.dto';
import { UpdateShowtimeDto } from './dto/update-showtime.dto';

@Controller('showtime')
export class ShowtimeController {
  constructor(private readonly showtimeService: ShowtimeService) {}

  @Post('create')
  create(@Body() createShowtimeDto: CreateShowtimeDto[]) {
    return this.showtimeService.create(createShowtimeDto);
  }

  @Get()
  findAll() {
    return this.showtimeService.findAll();
  }

  @Get("movie-theater")
  findByMovieAndTheater(
    @Query('movieId', ParseIntPipe) movieId: number,
    @Query('theaterId', ParseIntPipe) theaterId: number,
  ) {
    return this.showtimeService.findByMovieAndTheater(movieId, theaterId);
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.showtimeService.findOne(+id);
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateShowtimeDto: UpdateShowtimeDto) {
    return this.showtimeService.update(+id, updateShowtimeDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.showtimeService.remove(+id);
  }
}
