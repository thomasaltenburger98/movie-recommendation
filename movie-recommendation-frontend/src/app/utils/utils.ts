
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
