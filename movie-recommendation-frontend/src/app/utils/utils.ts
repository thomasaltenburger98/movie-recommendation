import {Crew} from "../../models/FilmDetail";

export function getFilmTitleAndYearFromTitle(title: string): { filmTitle: string, filmYear: string } {
  let filmYear = '';
  const filmTitle = title.replace(/\s*\(([^)]+)\)/g, (match, group) => {
    filmYear = group;
    return '';
  });

  return {
    filmTitle,
    filmYear
  };
}

export function getRuntimeAsTimeString(runtime: number|undefined): string {
  if (runtime == undefined) {
    return "";
  }
  const hours = Math.floor(runtime / 60);
  const minutes = runtime % 60;
  return `${hours}h ${minutes}min`;
}

export function getDirectorOfCrew(crew: Crew[]): string {
  const directors: Crew[] = crew.filter(member => member.job === 'Director');
  // concat all director names
  return directors.map(director => director.name).slice(0, 5).join(', ');
}
export function getScreenplayOfCrew(crew: Crew[]): string {
  const screenplay: Crew[] = crew.filter(member => member.job === 'Screenplay');
  // concat all screenplay names
  return screenplay.map(screenplay => screenplay.name).slice(0, 5).join(', ');
}

export function getCastsOfFilm(casts: any[]): string {
  // filter by known_for_department = acting & get 5 acts & concat all cast names
  return casts.filter(cast => cast.known_for_department === 'Acting').slice(0, 5).map(cast => cast.name).join(', ');
}
