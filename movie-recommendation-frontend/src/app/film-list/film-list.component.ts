import {ChangeDetectorRef, Component} from '@angular/core';
import {FilmService} from "../film.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Film} from "../../models/Film";
import {UserService} from "../user.service";
import {FilmDetail} from "../../models/FilmDetail";
import {getFilmTitleAndYearFromTitle} from "../utils/utils";

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.scss'],
  animations: [
    trigger('fadeAnimation', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('400ms ease-in-out', style({ opacity: 1 })),
      ]),
      transition(':leave', [
        animate('400ms ease-in-out', style({ opacity: 0 }))
      ])
    ])
  ]
})
export class FilmListComponent {
  films: Film[] = [];
  filteredFilms: Film[] = [];
  //filteredFilms: Film[] = [];
  searchString: string = "";
  isLoading: boolean = false;
  currentSearchTerm: string = '';
  currentMovieIndex = 0;
  currentFilmDetail: FilmDetail = {
    Title: "", Director: "", Poster: "", Rated: "", Year: ""
  };
  progress = 0;
  currentCount = 0;
  totalCount = 10; // Gesamtzahl Ziel
  goalReached = false;

  movies = [
    {
      title: 'Avatar',
      year: 2009,
      duration: '2h 42min',
      rating: 'FSK 12+'
    },
    {
      title: 'Avatar',
      year: 2009,
      duration: '2h 42min',
      rating: 'FSK 12+'
    },
    {
      title: 'Avatar',
      year: 2009,
      duration: '2h 42min',
      rating: 'FSK 12+'
    },
    {
      title: 'Avatar',
      year: 2009,
      duration: '2h 42min',
      rating: 'FSK 12+'
    }
  ];
  filteredMovies: any[] = [];

  constructor(private filmService: FilmService, private userService: UserService) { }

  ngOnInit() {
    this.loadFilms(1);
    this.getRatingCount();
    //this.filteredMovies = this.movies;
    /*this.isLoading = true;
    this.filmService.getFilms().subscribe(films => {
      this.films = films;
      console.log(this.films);
      this.filteredFilms = this.films;

      this.getFilmDetails();
    });*/
  }

  // Load film by pagination
  loadFilms(page: number): void {
    this.isLoading = true;
    this.filmService.getFilmsPage(page).subscribe(films => {
      console.log(films);
      this.films = films;
      console.log(this.films);
      this.filteredFilms = this.films;

      //this.getFilmDetails();
    });
  }

  /**
   * used to search in film list
   */
  applyFilter(): void {
    if (this.searchString.length > 0) {
      this.filteredMovies = this.movies.filter((film) =>
        film.title.toLowerCase().includes(this.searchString.toLowerCase())
      );
    } else {
      this.filteredMovies = this.movies;
    }
  }

  likeMovie(filmID: number, ratingValue: number) {
    this.rateFilm(filmID, 1, ratingValue).subscribe(() => {
      this.getRatingCount();
    });
  }

  previousMovie() {
    this.currentMovieIndex = (this.currentMovieIndex - 1 + this.filteredMovies.length) % this.filteredMovies.length;
    this.getFilmDetails();
  }

  nextMovie() {
    this.currentMovieIndex = (this.currentMovieIndex + 1) % this.filteredMovies.length;
    this.getFilmDetails();
  }

  getFilmDetails() {
    let filmTitleAndYear = getFilmTitleAndYearFromTitle(this.filteredMovies[this.currentMovieIndex].title);
    let filmTitle = filmTitleAndYear.filmTitle;
    let filmYear = filmTitleAndYear.filmYear;

    this.isLoading = true;
    /*this.filmDetailService.getFilmDetailByTitleAndYear(filmTitle,filmYear).subscribe(filmDetail => {
      this.currentFilmDetail = filmDetail;
      this.isLoading = false;
    });*/
  }
   getRatingCount() {
      this.http.get<number>(`${this.baseUrl}/count`).subscribe(count => {
        this.currentCount = count;
        this.updateProgress();
      });
    }

  rateFilm(filmId: number, userId: number, ratingValue: number) {
    const rating = {
      filmId: filmId,
      userId: userId,
      ratingValue: ratingValue
    };
    return this.http.post<void>(this.baseUrl, rating);
  }

  updateProgress() {
    this.progress = (this.currentCount / this.totalCount) * 100;
    this.goalReached = this.currentCount >= this.totalCount;
  }

  onGoalReached() {
    console.log('Ziel erreicht!');
  }
}
