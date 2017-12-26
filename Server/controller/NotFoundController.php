<?php

require_once "Controller.php";

class NotFoundController extends Controller
{
    public function manage(Request $req)
    {
        $response = new Response('404', null, null, $req->getAccept());
        $response->generate();
    }

}