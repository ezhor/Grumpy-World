<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:49
 */

require_once "Controller.php";

class RolloController extends Controller
{
    public function manageGetVerb(Request $request){
        /*
        Los rollos se obtienen según la autenticación, no según el URL ID
        No tiene sentido que alguien pida un ID concreto en la URL
        */
        if (isset($request->getUrlElements()[2])) {
            $code = '405';
        }else{
            $rollo = RolloHandlerModel::getRollo($request->getAuthentication()->getId());
            $code = '200';
        }

        $response = new Response($code, null, $rollo, $request->getAccept(), $request->getAuthentication()->getId());
        $response->generate();
    }
}