import {ChangeDetectorRef, Component} from '@angular/core';
import {FilmService} from "../services/film.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Film} from "../../models/Film";
import {UserService} from "../services/user.service";
import {Cast, FilmDetail} from "../../models/FilmDetail";
import {RatingService} from "../services/rating.service";
import { getRuntimeAsTimeString } from '../utils/utils';
import {HttpClient} from "@angular/common/http";

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
  isLoading: boolean = false;
  timeoutFilmDetails: any;
  currentMovieIndex = 0;
  progress = 0;
  currentCount = 0;
  totalCount = 10; // Gesamtzahl Ziel
  goalReached = false;

  constructor(private http: HttpClient, private filmService: FilmService, private userService: UserService, private ratingService: RatingService) { }

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
      this.films = films;
      console.log('loadFilms');
      console.log(this.films);
      this.getFilmDetailsForAllFilms();
      this.isLoading = false;
      //this.getFilmDetails();
    });
  }

  /**
   * used to search in film list
   */
  applyFilter(): void {
    if (typeof this.timeoutFilmDetails === "number") {
      clearTimeout(this.timeoutFilmDetails);
    }
    if (this.searchString.length > 0) {
      this.isLoading = true;
      console.log(this.searchString);
      this.filmService.getFilmsPageAndFilterByTitle(1, this.searchString).subscribe(films => {
        this.films = films;
        // set isLoading to true for all films
        this.films.forEach(film => film.isLoading = true);
        this.isLoading = false;
        this.timeoutFilmDetails = setTimeout(() => {
          this.getFilmDetailsForAllFilms();
        }, 500);
        //this.getFilmDetails();
      });
    } else {
      this.loadFilms(1);
      this.films.forEach(film => film.isLoading = true);
      this.timeoutFilmDetails = setTimeout(() => {
        this.getFilmDetailsForAllFilms();
      }, 500);
    }
  }

  likeMovie(film: Film) {
    this.isLoading = true;
    this.ratingService.rateFilm(film.id, 5).subscribe((result) => {
      // TODO check if successful
      /*this.filteredFilms = this.filteredFilms.filter((film) =>
        film.id !== filmID
      );*/
      film.isUserLiked = true;
      this.isLoading = false;
    });
  }

  dislikeMovie(film: Film) {
    this.isLoading = true;
    this.ratingService.rateFilm(film.id, 0).subscribe((result) => {
      // TODO check if successful
      /*this.filteredFilms = this.filteredFilms.filter((film) =>
        film.id !== filmID
      );*/
      film.isUserLiked = false;
      this.isLoading = false;
    });
  }

  previousMovie() {

  }

  nextMovie() {

  }

  getFilmDetails(filmId: number, index: number) {
    this.films[index].isLoading = true;

    // wait 5 seconds here
    setTimeout(() => {
      this.filmService.getFilmDetails(filmId).subscribe(filmDetail => {
        console.log(filmDetail);
        this.films[index].filmDetail = filmDetail;
        //console.log(this.filteredFilms[index]);
        this.films[index].isLoading = false;
        console.log(this.films);
      });
    }, 500);
  }

  getFilmDetailsForAllFilms(): void {
    this.films.forEach((film, index) => {
      this.getFilmDetails(film.id, index);
    });
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

  updateProgress() {
    this.progress = (this.currentCount / this.totalCount) * 100;
    this.goalReached = this.currentCount >= this.totalCount;
  }

  onGoalReached() {
    console.log('Ziel erreicht!');
  }

  getFunctionNames(obj: any): string[] {
    if (!obj) {
      return [];
    }

    const prototype = Object.getPrototypeOf(obj);
    return Object.getOwnPropertyNames(prototype);
  }

}
