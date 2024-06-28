import { Component } from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Film} from "../../models/Film";
import {HttpClient} from "@angular/common/http";
import {FilmService} from "../services/film.service";
import {UserService} from "../services/user.service";
import {RatingService} from "../services/rating.service";
import {Cast} from "../../models/FilmDetail";
import {RecommendationService} from "../services/recommendation.service";
import {Router, RouterLink} from "@angular/router";
import {MatAnchor, MatButton} from "@angular/material/button";

@Component({
  selector: 'app-recommendation-list',
  standalone: true,
  imports: [
    MatIcon,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    FormsModule,
    RouterLink,
    MatButton,
    MatAnchor
  ],
  templateUrl: './recommendation-list.component.html',
  styleUrl: './recommendation-list.component.scss'
})
export class RecommendationListComponent {
  films: Film[] = [];
  //filteredFilms: Film[] = [];
  searchString: string = "";
  progress = 0;
  timeoutFilmDetails: any;
  allFilmsLoaded: boolean = false;
  currentPage = 1;

  constructor(private http: HttpClient,
              private filmService: FilmService,
              private recommendationService: RecommendationService,
              private userService: UserService,
              private ratingService: RatingService,
              private router: Router) { }

  ngOnInit() {
    this.loadFilms();
    this.getRatingCount();
  }

  // Load film by pagination
  loadFilms(isLoadMore: boolean = false): void {
    if (typeof this.timeoutFilmDetails === "number") {
      clearTimeout(this.timeoutFilmDetails);
      console.log('cleared timeout');
    }
    this.recommendationService.getRecommendedFilms(this.currentPage).subscribe({
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
