<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:49
 */

require_once "Controller.php";

class ZonaController extends Controller
{
    public function manageGetVerb(Request $request){
        /*
        Los rollos se obtienen según la autenticación, no según el URL ID
        No tiene sentido que alguien pida un ID concreto en la URL
        */
        if (isset($request->getUrlElements()[2])) {
            $code = '404';
            $zonas = null;
        }else{
            $zonas = ZonaHandlerModel::getZonasDisponibles($request->getAuthentication()->getId());
            $code = '200';
        }

        $response = new Response($code, null, $zonas, $request->getAccept(), $request->getAuthentication()->getId());
        $response->generate();
    }

    public function managePostVerb(Request $request){
        if (isset($request->getUrlElements()[2])) {
            $response = new Response('404', null, null, $request->getAccept());
            $response->generate();
        }else{
            if(isset($request->getBodyParameters()['nombre'])){
                $nombreZona = $request->getBodyParameters()['nombre'];
                $idUsuario = $request->getAuthentication()->getId();
                if(ZonaHandlerModel::puedeCambiarZona($idUsuario, $nombreZona)){
                    ZonaHandlerModel::cambiarZona($idUsuario, $nombreZona);
                    $response = new Response('204', null, null, $request->getAccept(), null);
                }else{
                    $response = new Response('403', null, null, $request->getAccept(), null);
                }
            }else{
                $response = new Response('400', null, null, $request->getAccept(), null);
            }

            $response->generate();
        }
    }
}