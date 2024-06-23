import {FilmDetail} from "./FilmDetail";

export class Film {
  id: number;
  title: string;
  beschreibung: string;
  //erscheinungsjahr: number;
  bewertung: number;
  isLoading: boolean;
  filmDetail: FilmDetail;
  isUserLiked: boolean;
  isUserDisliked: boolean;

constructor() {
    this.id = 0;
    this.title = '';
    this.beschreibung = '';
    //this.erscheinungsjahr = 0;
    this.bewertung = 0;
    this.isLoading = true;
    this.isUserLiked = false;
    this.isUserDisliked = false;
    this.filmDetail = new FilmDetail();
  }

}
