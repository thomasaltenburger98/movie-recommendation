<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('film_genre', function (Blueprint $table) {
            //$table->unsignedBigInteger('film_id');
            //$table->unsignedBigInteger('genre_id');

            $table->timestamps();

            $table->foreignId('film_id');
            $table->foreignId('genre_id');

            $table->primary(['film_id','genre_id']);
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('film_genre');
    }
};
