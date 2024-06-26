import {ChangeDetectorRef, Component} from '@angular/core';
import {FilmService} from "../services/film.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Film} from "../../models/Film";
import {UserService} from "../services/user.service";
import {Cast, FilmDetail} from "../../models/FilmDetail";
import {RatingService} from "../services/rating.service";
import { getRuntimeAsTimeString } from '../utils/utils';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

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
  //filteredFilms: Film[] = [];
  searchString: string = "";
  timeoutFilmDetails: any;
  allFilmsLoaded: boolean = false;
  currentPage = 1;

  constructor(private http: HttpClient,
              private filmService: FilmService,
              private userService: UserService,
              private ratingService: RatingService,
              private router: Router) { }

  ngOnInit() {
    this.loadFilms();
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
  loadFilms(isLoadMore: boolean = false): void {
    if (typeof this.timeoutFilmDetails === "number") {
      clearTimeout(this.timeoutFilmDetails);
      console.log('cleared timeout');
    }
    this.filmService.getFilmsPageAndFilterByTitle(this.currentPage, this.searchString).subscribe({
      next: (films) => {
        console.log(films);
        // if all films are loaded
        if (films.length === 0) {
          this.allFilmsLoaded = true;
          return;
        }
        if (isLoadMore) {
          // set isLoading to true for all new films
          films.forEach(film => film.isLoading = true);
          this.timeoutFilmDetails = setTimeout(() => {
            films = this.getFilmDetailsForFilms(films);
          }, 700);
          this.films = [...this.films, ...films];
        } else {
          this.films = films;
          // set isLoading to true for all films
          this.films.forEach(film => film.isLoading = true);
          this.timeoutFilmDetails = setTimeout(() => {
            this.films = this.getFilmDetailsForFilms();
          }, 700);
        }
        //this.getFilmDetails();
      },
      error: (error) => {
        if (error.status === 403) {
          this.router.navigate(['/login']);
          this.userService.deleteToken();
        }
      }
    });
  }

  /**
   * used to search in film list
   */
  applyFilter(): void {
    this.currentPage = 1;
    this.loadFilms();
  }

  /**
   * Load more films
   */
  loadMore(): void {
    this.currentPage++;
    this.loadFilms(true);
  }

  likeMovie(film: Film) {
    const filmIsRated = film.isUserLiked || film.isUserDisliked;
    if (film.isUserLiked) {
      this.ratingService.unrateFilm(film.id, 5).subscribe({
        next: (result) => {
          // TODO check if successful
          film.isUserLiked = false;
        },
        error: (error) => {
          if (error.status === 403) {
            this.router.navigate(['/login']);
            this.userService.deleteToken();
          }
        }
      });
    } else {
      this.ratingService.rateFilm(film.id, 5, filmIsRated).subscribe({
        next: (result) => {
          // TODO check if successful
          film.isUserLiked = true;
          film.isUserDisliked = false;
        },
        error: (error) => {
          if (error.status === 403) {
            this.router.navigate(['/login']);
            this.userService.deleteToken();
          }
        }
      });
    }
  }

  dislikeMovie(film: Film) {
    const filmIsRated = film.isUserLiked || film.isUserDisliked;
    if (film.isUserDisliked) {
      this.ratingService.unrateFilm(film.id, 0).subscribe({
        next: (result) => {
          // TODO check if successful
          film.isUserDisliked = false;
        },
        error: (error) => {
          if (error.status === 403) {
            this.router.navigate(['/login']);
            this.userService.deleteToken();
          }
        }
      });
    } else {
      this.ratingService.rateFilm(film.id, 0, filmIsRated).subscribe({
        next: (result) => {
          // TODO check if successful
          film.isUserLiked = false;
          film.isUserDisliked = true;
        },
        error: (error) => {
          if (error.status === 403) {
            this.router.navigate(['/login']);
            this.userService.deleteToken();
          }
        }
      });
    }
  }

  getFilmDetails(film: Film, index: number) {
    film.isLoading = true;
    const filmId = film.id;

    // wait 5 seconds here
    setTimeout(() => {
      this.filmService.getFilmDetails(filmId).subscribe({
        next: (filmDetail) => {
          console.log(filmDetail);
          film.filmDetail = filmDetail;
          //console.log(this.filteredFilms[index]);
          film.isLoading = false;
        },
        error: (error) => {
          if (error.status === 403) {
            this.router.navigate(['/login']);
            this.userService.deleteToken();
          }
        }
      });
    }, 250);
  }

  getFilmDetailsForFilms(specificFilms: Film[] = []): Film[] {
    const films: Film[] = specificFilms.length > 0 ? specificFilms : this.films;
    films.forEach((film, index) => {
      this.getFilmDetails(film, index);
    });
    return films;
  }

  private getActors(values: Cast[]): Cast[] {
    return values.filter(cast => cast.known_for_department === 'Acting');
  }
  protected getActorsAsString(values: Cast[]|undefined): string {
    if (!values) {
      return '';
    }
    return this.getActors(values).slice(0, 6).map(cast => cast.name).join(', ') + '...';
  }
  protected getRuntimeAsTimeString(runtime: number|undefined): string {
    if (runtime != undefined) {
      const hours = Math.floor(runtime / 60);
      const minutes = runtime % 60;
      return `${hours}h ${minutes}min`;
    }
    return "";
  }

   getRatingCount() {
      /*this.http.get<number>(`${this.baseUrl}/count`).subscribe(count => {
        this.currentCount = count;
        this.updateProgress();
      });*/
    }

  getFunctionNames(obj: any): string[] {
    if (!obj) {
      return [];
    }

    const prototype = Object.getPrototypeOf(obj);
    return Object.getOwnPropertyNames(prototype);
  }

}
