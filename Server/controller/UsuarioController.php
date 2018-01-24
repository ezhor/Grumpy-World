<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 24/01/18
 * Time: 9:47
 */

require_once "Controller.php";

class UsuarioController extends Controller
{
    public function managePostVerb(Request $request)
    {
        $response = new Response('405', null, null, $request->getAccept());
        $response->generate();
    }
}