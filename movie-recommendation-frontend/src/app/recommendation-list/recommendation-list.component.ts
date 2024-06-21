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

@Component({
  selector: 'app-recommendation-list',
  standalone: true,
  imports: [
    MatIcon,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './recommendation-list.component.html',
  styleUrl: './recommendation-list.component.scss'
})
export class RecommendationListComponent {
  films: Film[] = [];
  filteredFilms: Film[] = [];
  //filteredFilms: Film[] = [];
  searchString: string = "";
  isLoading: boolean = false;
  currentMovieIndex = 0;
  progress = 0;
  currentCount = 0;
  totalCount = 10; // Gesamtzahl Ziel
  goalReached = false;

  constructor(private http: HttpClient,
              private filmService: FilmService,
              private recommendationService: RecommendationService,
              private userService: UserService,
              private ratingService: RatingService) { }

  ngOnInit() {
    this.loadFilms();
    this.getRatingCount();
  }

  // Load film by pagination
  loadFilms(): void {
    console.log('loadFilms');
    this.isLoading = true;
    this.recommendationService.getRecommendedFilms().subscribe(films => {
      console.log(films);
      this.films = films;
      this.filteredFilms = this.films;
      this.getFilmDetailsForAllFilms();

      //this.getFilmDetails();
    });
  }

  /**
   * used to search in film list
   */
  applyFilter(): void {
    if (this.searchString.length > 0) {
      // TODO
    } else {
      // TODO
    }
  }

  likeMovie(film: Film) {
    this.isLoading = true;
    this.ratingService.rateFilm(film.id, 5).subscribe((result) => {
      // TODO check if successful
      /*this.filteredFilms = this.filteredFilms.filter((film) =>
        film.id !== filmID
      );*/
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
      this.isLoading = false;
    });
  }

  previousMovie() {

  }

  nextMovie() {

  }

  getFilmDetails(filmId: number, index: number) {
    this.filteredFilms[index].isLoading = true;

    // wait 5 seconds here
    setTimeout(() => {
      this.filmService.getFilmDetails(filmId).subscribe(filmDetail => {
        console.log(filmDetail);
        this.filteredFilms[index].filmDetail = filmDetail;
        //console.log(this.filteredFilms[index]);
        this.filteredFilms[index].isLoading = false;
        console.log(this.filteredFilms);
      });
    }, 500);
  }

  getFilmDetailsForAllFilms(): void {
    this.filteredFilms.forEach((film, index) => {
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
