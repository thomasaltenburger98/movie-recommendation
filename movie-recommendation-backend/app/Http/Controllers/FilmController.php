<?php

namespace App\Http\Controllers;

use App\Models\Film;
use Illuminate\Http\Request;

class FilmController extends Controller
{
    // Methode zum Abrufen aller Filme
    public function index()
    {
        $filme = Film::limit(10)->get();

        return response()->json($filme);
    }

    // Methode zum Abrufen eines bestimmten Films anhand der ID
    public function show($id)
    {
        $film = Film::with('genres')->find($id);

        if (!$film) {
            return response()->json(['message' => 'Film nicht gefunden'], 404);
        }

        return response()->json($film);
    }

    // Methode zum Erstellen eines neuen Films
    public function store(Request $request)
    {
        return response()->json(['message' => 'Currently not supported'], 404);

        /*$this->validate($request, [
            'titel' => 'required',
            'beschreibung' => 'required',
            // Weitere Validierungsregeln hier
        ]);

        $film = Film::create($request->all());

        return response()->json($film, 201);*/
    }

    // Methode zum Aktualisieren eines vorhandenen Films
    public function update(Request $request, $id)
    {
        return response()->json(['message' => 'Currently not supported'], 404);

        /*$film = Film::find($id);

        if (!$film) {
            return response()->json(['message' => 'Film nicht gefunden'], 404);
        }

        $film->update($request->all());

        return response()->json($film);*/
    }

    // Methode zum Löschen eines Films
    public function destroy($id)
    {
        return response()->json(['message' => 'Currently not supported'], 404);

        /*$film = Film::find($id);

        if (!$film) {
            return response()->json(['message' => 'Film nicht gefunden'], 404);
        }

        $film->delete();

        return response()->json(['message' => 'Film erfolgreich gelöscht']);*/
    }
}
