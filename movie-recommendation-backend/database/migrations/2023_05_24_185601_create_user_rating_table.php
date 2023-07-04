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
            //$table->unsignedBigInteger('user_id');
            //$table->unsignedBigInteger('film_id');

            $table->timestamps();

            $table->foreignId('user_id');
            $table->foreignId('film_id');

            $table->primary(['user_id', 'film_id']);

            $table->integer('rating_value');

            //$table->id();
            //$table->unsignedBigInteger('user_id');
            //$table->unsignedBigInteger('film_id');

            /*$table->foreignId('user_id')->nullable()->references('id')->on('users')->onDelete('cascade');
            $table->foreignId('film_id')->nullable()->references('id')->on('films')->onDelete('cascade');*/
            /*$table->foreignId('user_id')->constrained('users')->onDelete('cascade');
            $table->foreignId('film_id')->constrained('films')->onDelete('cascade');

            $table->integer('rating_value');
            $table->primary(['user_id','film_id']);
            $table->timestamps();*/
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
