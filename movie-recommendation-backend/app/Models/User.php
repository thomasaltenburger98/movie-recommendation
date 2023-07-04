<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class User extends Model
{
    use HasFactory;

    /**
     * Get all films that this user has rated
     *
     * @return \Illuminate\Database\Eloquent\Relations\BelongsToMany
     */
    public function films()
    {
        //return $this->hasMany(Film::class, 'user_rating')->withPivot('rating_value');
        return $this->belongsToMany(Film::class, 'user_rating', 'user_id', 'film_id')
            ->withPivot('rating_value');
            //->withPivot('user_id', 'film_id');
    }
}
