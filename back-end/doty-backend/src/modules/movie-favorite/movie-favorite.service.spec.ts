import { Test, TestingModule } from '@nestjs/testing';
import { MovieFavoriteService } from './movie-favorite.service';

describe('MovieFavoriteService', () => {
  let service: MovieFavoriteService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [MovieFavoriteService],
    }).compile();

    service = module.get<MovieFavoriteService>(MovieFavoriteService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
