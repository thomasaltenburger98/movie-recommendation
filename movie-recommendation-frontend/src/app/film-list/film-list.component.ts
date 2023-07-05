import {ChangeDetectorRef, Component} from '@angular/core';
import {FilmService} from "../film.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Film} from "../../models/Film";
import {UserService} from "../user.service";
import {FilmDetailService} from "../film-detail.service";
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
  //searchString: string = "";
  isLoading: boolean = false;
  currentSearchTerm: string = '';
  currentMovieIndex = 0;
  currentFilmDetail: FilmDetail = {
    Title: "", Director: "", Poster: "", Rated: "", Year: ""
  };

  constructor(private filmService: FilmService, private filmDetailService: FilmDetailService, private userService: UserService) { }

  ngOnInit() {
    this.isLoading = true;
    this.filmService.getFilms().subscribe(films => {
      this.films = films;
      console.log(this.films);
      this.filteredFilms = this.films;

      this.getFilmDetails();
    });
  }

  /**
   * used to search in film list
   */
  /*applyFilter(): void {
    if (this.searchString) {
      this.filteredFilms = this.films.filter((film) =>
        film.title.toLowerCase().includes(this.searchString.toLowerCase())
      );
    } else {
      this.filteredFilms = this.films;
    }
  }*/

  likeMovie(filmID: number, ratingValue: number) {
    this.isLoading = true;
    this.filmService.rateFilm(filmID, ratingValue).subscribe((result) => {
      console.log(result);
      // TODO check if successful
      /*this.filteredFilms = this.filteredFilms.filter((film) =>
        film.id !== filmID
      );*/
      this.isLoading = false;
    });
  }

  previousMovie() {
    this.currentMovieIndex = (this.currentMovieIndex - 1 + this.filteredFilms.length) % this.filteredFilms.length;
    this.getFilmDetails();
  }

  nextMovie() {
    this.currentMovieIndex = (this.currentMovieIndex + 1) % this.filteredFilms.length;
    this.getFilmDetails();
  }

  getFilmDetails() {
    let filmTitleAndYear = getFilmTitleAndYearFromTitle(this.filteredFilms[this.currentMovieIndex].title);
    let filmTitle = filmTitleAndYear.filmTitle;
    let filmYear = filmTitleAndYear.filmYear;
    //console.log(filmYear);

    this.isLoading = true;
    this.filmDetailService.getFilmDetailByTitleAndYear(filmTitle,filmYear).subscribe(filmDetail => {
      this.currentFilmDetail = filmDetail;
      this.isLoading = false;
    });
  }

}
