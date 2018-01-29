<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 24/01/18
 * Time: 9:47
 */

require_once "Controller.php";
require_once __DIR__."/../Authenticacion.php";

class UsuarioController extends Controller
{
    public function managePostVerb(Request $request)
    {
        if (isset($request->getUrlElements()[2])) {
            $response = new Response('405', null, null, $request->getAccept());
            $response->generate();
        }else{
            $authentication = new Authenticacion($request->getBodyParameters()['usuario'], $request->getBodyParameters()['contrasena']);
            $conseguido = UsuarioHandlerModel::insertarUsuario($authentication);
            if($conseguido){
                $code = '200';

            }else{
                $code = '409';
            }
            $response = new Response($code, null, null, $request->getAccept());
            $response->generate();
        }
    }
}