import { TestBed } from '@angular/core/testing';

import { FilmDetailService } from './film-detail.service';

describe('FilmDetailService', () => {
  let service: FilmDetailService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FilmDetailService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
