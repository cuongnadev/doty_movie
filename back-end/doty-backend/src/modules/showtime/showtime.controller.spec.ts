import { Test, TestingModule } from '@nestjs/testing';
import { ShowtimeController } from './showtime.controller';
import { ShowtimeService } from './showtime.service';

describe('ShowtimeController', () => {
  let controller: ShowtimeController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [ShowtimeController],
      providers: [ShowtimeService],
    }).compile();

    controller = module.get<ShowtimeController>(ShowtimeController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
