export class Provider {
  logo_path: string;
  provider_id: number;
  provider_name: string;
  display_priority: number;

  constructor() {
    this.logo_path = '';
    this.provider_id = 0;
    this.provider_name = '';
    this.display_priority = 0;
  }
}

export class WatchProviders {
  link: string;
  flatrate: Provider[];
  rent: Provider[];
  buy: Provider[];

  constructor() {
    this.link = '';
    this.flatrate = [];
    this.rent = [];
    this.buy = [];
  }
}
