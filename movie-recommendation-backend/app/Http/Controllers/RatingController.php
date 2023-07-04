<?php

namespace App\Http\Controllers;

use App\Models\Film;
use App\Models\Rating;
use Illuminate\Http\Request;

class RatingController extends Controller
{
    public function rateFilm($film_id, $user_id, $rating_value)
    {
        Film::find($film_id)->users()->attach($user_id, ['rating_value' => $rating_value]);

        return response()->json(['film rated', $film_id, $user_id, $rating_value]);
    }

    public function getAllRatedFilmOfUser($user_id)
    {
        $films = Film::whereHas('users', function ($query) use ($user_id) {
            $query->where('user_id', $user_id);
        })->get();

        /**
         * (Connection: sqlite, SQL:
         * select * from "films"
         * where exists (
         *      select * from "users"
         *      inner join "user_rating" on "users"."id" = "user_rating"."user_id"
         *      where "films"."id" = "user_rating"."film_id" and "user_id" = 610)
         * )

         */

        return response()->json(['films rated by user ' . $user_id, $films]);
    }
}
