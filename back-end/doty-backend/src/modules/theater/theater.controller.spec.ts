import { Test, TestingModule } from '@nestjs/testing';
import { TheaterController } from './theater.controller';
import { TheaterService } from './theater.service';

describe('TheaterController', () => {
  let controller: TheaterController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [TheaterController],
      providers: [TheaterService],
    }).compile();

    controller = module.get<TheaterController>(TheaterController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
