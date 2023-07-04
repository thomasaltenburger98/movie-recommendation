<?php

namespace Database\Seeders;

use App\Models\Film;
use App\Models\Genre;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class FilmSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $films = [
            [
                'title' => 'The Journey of Dreams'
            ],
            [
                'title' => 'Echoes of Destiny'
            ],
            [
                'title' => 'Whispering Shadows'
            ]
        ];
        $films = $this->getFilmsFromCSV(base_path('storage/app/movies.csv'));


        $filmIds = [];

        //Version 1
        /*foreach ($films as $film)
        {
            $genres = $film['genres'];
            unset($film['genres']);
            $filmModel = Film::create($film);
            $filmIds[] = $filmModel->id;

            foreach ($genres as $genreName) {
                $genre = Genre::firstOrCreate(['name' => $genreName]);
                $filmModel->genres()->attach($genre);
            }
        }*/

        // Version 2 - optimized import
        // Prepare arrays for bulk insert
        $filmsData = [];
        $genresData = [];

        foreach ($films as $film) {
            $genres = $film['genres'];
            unset($film['genres']);

            $filmsData[] = $film;

            foreach ($genres as $genreName) {
                if (!in_array(['name' => $genreName], $genresData, true)) {
                    $genresData[] = ['name' => $genreName];
                }
            }
        }

        // Perform bulk insert for films and genres
        DB::table('films')->insert($filmsData);
        DB::table('genres')->insertOrIgnore($genresData);

        // Retrieve the inserted film IDs
        $filmIds = DB::table('films')->pluck('film_id')->all();

        // Create associations between films and genres
        $filmGenreData = [];

        foreach ($films as $index => $film) {
            $genres = $film['genres'];
            $filmId = $filmIds[$index];

            foreach ($genres as $genreName) {
                $genreId = DB::table('genres')->where('name', $genreName)->value('id');
                $filmGenreData[] = ['film_id' => $filmId, 'genre_id' => $genreId];
            }
        }

        DB::table('film_genre')->insert($filmGenreData);




        // assign random genres
        /*$genres = Genre::all();
        foreach ($filmIds as $filmId) {
            $randomGenres = $genres->random(rand(1, 3));
            Film::find($filmId)->genres()->attach($randomGenres);
        }*/
    }


    /**
     * Read films and genres data from a CSV file.
     *
     * @param string $filePath The path to the CSV file.
     * @return array The array of films with associated genres.
     */
    protected function getFilmsFromCSV($filePath)
    {
        $films = [];

        if (($handle = fopen($filePath, "r")) !== false) {
            // Skip the header row
            fgetcsv($handle);

            while (($data = fgetcsv($handle)) !== false) {
                $filmId = $data[0];
                $filmTitle = $data[1];
                $genreNames = explode('|', $data[2]);

                $film = [
                    'film_id' => $filmId,
                    'title' => $filmTitle,
                    'genres' => $genreNames,
                ];

                $films[] = $film;
            }

            fclose($handle);
        }

        return $films;
    }

}
