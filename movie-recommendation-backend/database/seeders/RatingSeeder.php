<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class RatingSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $ratingData = $this->getRatingDataFromCSV(base_path('storage/app/ratings.csv'));
        $ratings = $ratingData['ratings'];
        $userIDs = $ratingData['user_ids'];

        /*$userIDs = [
            ['user_id' => 1],
            ['user_id' => 2],
            ['user_id' => 3],
            ['user_id' => 4],
        ];*/

        DB::table('users')->insert($userIDs);

        $chunkSize = 1000;
        foreach (array_chunk($ratings, $chunkSize) as $chunk) {
            DB::table('user_rating')->insert($chunk);
        }

        //DB::table('users')->insert($userIDs);

        /*$chunkSize = 2;
        foreach (array_chunk($userIDs, $chunkSize) as $chunk) {
            DB::table('users')->insert($chunk);
        }*/


    }

    /**
     * Read films and genres data from a CSV file.
     *
     * @param string $filePath The path to the CSV file.
     * @return array The array of films with associated genres.
     */
    protected function getRatingDataFromCSV($filePath)
    {
        $ratings = [];
        $userIDs = [];

        if (($handle = fopen($filePath, "r")) !== false) {
            // Skip the header row
            fgetcsv($handle);

            while (($data = fgetcsv($handle)) !== false) {
                $userID = $data[0];
                $filmID = $data[1];
                $ratingValue = $data[2];

                $rating = [
                    //'id' => $filmId,
                    'user_id' => $userID,
                    'film_id' => $filmID,
                    'rating_value' => $ratingValue
                ];
                if (!in_array(['user_id' => $userID], $userIDs, true)) {
                    $userIDs[] = ['user_id' => $userID];
                }

                $ratings[] = $rating;
            }

            fclose($handle);
        }

        return [
            'ratings' => $ratings,
            'user_ids' => $userIDs
        ];
    }

}
