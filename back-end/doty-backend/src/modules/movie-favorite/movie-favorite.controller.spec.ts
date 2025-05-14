import { Test, TestingModule } from '@nestjs/testing';
import { MovieFavoriteController } from './movie-favorite.controller';
import { MovieFavoriteService } from './movie-favorite.service';

describe('MovieFavoriteController', () => {
  let controller: MovieFavoriteController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [MovieFavoriteController],
      providers: [MovieFavoriteService],
    }).compile();

    controller = module.get<MovieFavoriteController>(MovieFavoriteController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
