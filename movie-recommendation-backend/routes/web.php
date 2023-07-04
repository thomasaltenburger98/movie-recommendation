<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::get('/user/newid', [\App\Http\Controllers\UserController::class, 'getNewUserID']);
Route::get('/films/{film_id}/user/{user_id}/rate/{rating_value}', [\App\Http\Controllers\RatingController::class, 'rateFilm']);
Route::get('/user/{user_id}/rated', [\App\Http\Controllers\RatingController::class, 'getAllRatedFilmOfUser']);

Route::resource('films', \App\Http\Controllers\FilmController::class);
