export class FilmDetail {
  adult: boolean;
  backdrop_path: string;
  belongs_to_collection: Collection;
  budget: number;
  genres: Genre[];
  homepage: string;
  id: number;
  imdb_id: string;
  origin_country: string[];
  original_language: string;
  original_title: string;
  overview: string;
  popularity: number;
  poster_path: string;
  production_companies: ProductionCompany[];
  production_countries: ProductionCountry[];
  release_date: string;
  revenue: number;
  runtime: number;
  spoken_languages: SpokenLanguage[];
  status: string;
  tagline: string;
  title: string;
  video: boolean;
  vote_average: number;
  vote_count: number;
  credits: Credits;
  image_url: string;

  constructor() {
    this.adult = false;
    this.backdrop_path = '';
    this.belongs_to_collection = new Collection();
    this.budget = 0;
    this.genres = [];
    this.homepage = '';
    this.id = 0;
    this.imdb_id = '';
    this.origin_country = [];
    this.original_language = '';
    this.original_title = '';
    this.overview = '';
    this.popularity = 0;
    this.poster_path = '';
    this.production_companies = [];
    this.production_countries = [];
    this.release_date = '';
    this.revenue = 0;
    this.runtime = 0;
    this.spoken_languages = [];
    this.status = '';
    this.tagline = '';
    this.title = '';
    this.video = false;
    this.vote_average = 0;
    this.vote_count = 0;
    this.credits = new Credits();
    this.image_url = '';
  }
}

export class Collection {
  id: number;
  name: string;
  poster_path: string;
  backdrop_path: string;

  constructor() {
    this.id = 0;
    this.name = '';
    this.poster_path = '';
    this.backdrop_path = '';
  }
}

export class Genre {
  id: number;
  name: string;

  constructor() {
    this.id = 0;
    this.name = '';
  }
}

export class ProductionCompany {
  id: number;
  logo_path: string;
  name: string;
  origin_country: string;

  constructor() {
    this.id = 0;
    this.logo_path = '';
    this.name = '';
    this.origin_country = '';
  }
}

export class ProductionCountry {
  iso_3166_1: string;
  name: string;

  constructor() {
    this.iso_3166_1 = '';
    this.name = '';
  }
}

export class SpokenLanguage {
  english_name: string;
  iso_639_1: string;
  name: string;

  constructor() {
    this.english_name = '';
    this.iso_639_1 = '';
    this.name = '';
  }
}

export class Credits {
  cast: Cast[];
  crew: Crew[];

  constructor() {
    this.cast = [];
    this.crew = [];
  }

  public getDirector(): Crew|undefined {
    return this.crew.find(crew => crew.job === 'Director');
  }

}

export class Cast {
  adult: boolean;
  gender: number;
  id: number;
  known_for_department: string;
  name: string;
  original_name: string;
  popularity: number;
  profile_path: string;
  cast_id: number;
  character: string;
  credit_id: string;
  order: number;

  constructor() {
    this.adult = false;
    this.gender = 0;
    this.id = 0;
    this.known_for_department = '';
    this.name = '';
    this.original_name = '';
    this.popularity = 0;
    this.profile_path = '';
    this.cast_id = 0;
    this.character = '';
    this.credit_id = '';
    this.order = 0;
  }
}

export class Crew {
  adult: boolean;
  gender: number;
  id: number;
  known_for_department: string;
  name: string;
  original_name: string;
  popularity: number;
  profile_path: string;
  credit_id: string;
  department: string;
  job: string;

  constructor() {
    this.adult = false;
    this.gender = 0;
    this.id = 0;
    this.known_for_department = '';
    this.name = '';
    this.original_name = '';
    this.popularity = 0;
    this.profile_path = '';
    this.credit_id = '';
    this.department = '';
    this.job = '';
  }
}
