import {FilmDetail} from "./FilmDetail";
import {Genre} from "./Genre";

export class Film {
  id: number;
  tmdbId: number;
  title: string;
  beschreibung: string;
  //erscheinungsjahr: number;
  bewertung: number;
  isLoading: boolean;
  filmDetail: FilmDetail;
  isUserLiked: boolean;
  isUserDisliked: boolean;
  genres: Genre[];

constructor() {
    this.id = 0;
    this.tmdbId = 0;
    this.title = '';
    this.beschreibung = '';
    //this.erscheinungsjahr = 0;
    this.bewertung = 0;
    this.isLoading = true;
    this.isUserLiked = false;
    this.isUserDisliked = false;
    this.filmDetail = new FilmDetail();
    this.genres = [];
  }

}
