import {ChangeDetectorRef, Component} from '@angular/core';
import {FilmService} from "../services/film.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Film} from "../../models/Film";
import {UserService} from "../services/user.service";
import {Cast, FilmDetail} from "../../models/FilmDetail";
import {getFilmTitleAndYearFromTitle} from "../utils/utils";
import {RatingService} from "../services/rating.service";

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
  searchString: string = "";
  isLoading: boolean = false;

  constructor(private filmService: FilmService, private userService: UserService, private ratingService: RatingService) { }

  ngOnInit() {
    this.loadFilms(1);
  }

  // Load film by pagination
  loadFilms(page: number): void {
    this.isLoading = true;
    this.filmService.getFilmsPage(page).subscribe(films => {
      this.filteredFilms = films;
      this.getFilmDetailsForAllFilms();

      //this.getFilmDetails();
    });
  }

  /**
   * used to search in film list
   */
  applyFilter(): void {

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

  getFilmDetailsForAllFilms(): void {
    this.filteredFilms.forEach((film, index) => {
      this.getFilmDetails(film.id, index);
    });
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
    }, 2000);
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
  protected getRuntimeAsTimeString(runtime: string|undefined): string {
    if (runtime != undefined) {
      const runTimeInt = parseInt(runtime);
      const hours = Math.floor(runTimeInt / 60);
      const minutes = runTimeInt % 60;
      return `${hours}h ${minutes}min`;
    }
    return "";
  }


  getFunctionNames(obj: any): string[] {
    if (!obj) {
      return [];
    }

    const prototype = Object.getPrototypeOf(obj);
    return Object.getOwnPropertyNames(prototype);
  }

}
