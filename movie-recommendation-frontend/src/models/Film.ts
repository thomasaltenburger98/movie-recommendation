import {FilmDetail} from "./FilmDetail";

export class Film {
  id: number;
  title: string;
  beschreibung: string;
  //erscheinungsjahr: number;
  bewertung: number;
  isLoading: boolean;
  filmDetail: FilmDetail;

constructor() {
    this.id = 0;
    this.title = '';
    this.beschreibung = '';
    //this.erscheinungsjahr = 0;
    this.bewertung = 0;
    this.isLoading = false;
    this.filmDetail = new FilmDetail();
  }

}
