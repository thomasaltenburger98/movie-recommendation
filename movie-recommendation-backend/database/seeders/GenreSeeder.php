<?php

namespace Database\Seeders;

use App\Models\Genre;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class GenreSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $genres = [
            ['name' => 'Adventure'],
            ['name' => 'Action'],
            ['name' => 'Animation'],
            ['name' => 'Comedy'],
            ['name' => 'Fantasy'],
            // Add more genres as needed
        ];

        Genre::insert($genres);
    }
}
