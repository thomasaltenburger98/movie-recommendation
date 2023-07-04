<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Support\Facades\DB;

class UserController extends Controller
{
    public function getNewUserID() {
        $newUserID = DB::table('users')->orderBy('user_id', 'DESC')->first()->user_id + 1;
        $user = new User;
        $user->user_id = $newUserID;
        $user->save();

        return response()->json(['user_id' => $newUserID]);
    }
}
