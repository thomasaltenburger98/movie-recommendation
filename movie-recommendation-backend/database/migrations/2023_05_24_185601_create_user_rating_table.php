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
        Schema::create('user_rating', function (Blueprint $table) {
            $table->timestamps();
            $table->foreignId('user_id');
            $table->foreignId('film_id');
            $table->primary(['user_id', 'film_id']);
            $table->integer('rating_value');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('user_rating');
    }
};
