
export class Video {
  iso_639_1: string;
  iso_3166_1: string;
  name: string;
  key: string;
  site: string;
  size: number;
  type: string;
  official: boolean;
  published_at: string;
  id: string;

  constructor() {
    this.iso_639_1 = '';
    this.iso_3166_1 = '';
    this.name = '';
    this.key = '';
    this.site = '';
    this.size = 0;
    this.type = '';
    this.official = false;
    this.published_at = '';
    this.id = '';
  }
}
