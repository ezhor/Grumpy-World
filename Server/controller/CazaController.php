<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:49
 */

require_once "Controller.php";

class CazaController extends Controller
{
    public function manageGetVerb(Request $request){
        $idUsuario = $request->getAuthentication()->getId();
        if (isset($request->getUrlElements()[2])) {
            $code = '404';
            $atributos = null;
            $response = new Response($code, null, null, $request->getAccept(), $idUsuario);
        }else{
            $rollo = RolloHandlerModel::getRollo($idUsuario);
            $enemigo = CazaHandlerModel::getEnemigoEnCaza($idUsuario);
            if($enemigo->getNombre() == null){
                CazaHandlerModel::asignarCaza($idUsuario);
                $enemigo = CazaHandlerModel::getEnemigoEnCaza($idUsuario);
            }
            $estado = CazaHandlerModel::getEstadoCaza($idUsuario);

            $caza = new CazaModel($rollo, $enemigo, $estado);
            $code = '200';
            $response = new Response($code, null, $caza, $request->getAccept(), $idUsuario);
        }

        $response->generate();
    }

    public function managePostVerb(Request $request){
        $idUsuario = $request->getAuthentication()->getId();
        if (isset($request->getUrlElements()[2])) {
            $response = new Response('404', null, null, $request->getAccept());
            $response->generate();
        }else{
            if(isset($request->getBodyParameters()['ataque'])){
                $ataque = $request->getBodyParameters()['atributo'];
                if($ataque === 1 || $ataque === 2 || $ataque === 3){
                    $conseguido = CazaHandlerModel::
                    /*if($conseguido){
                        $atributos = AtributosHandlerModel::getAtributos($request->getAuthentication()->getId());
                        $response = new Response('200', null, $atributos, $request->getAccept(), $request->getAuthentication()->getId());

                    }else{
                        $response = new Response('403', null, null, $request->getAccept(), null);
                    }*/
                }
            }else{
                $response = new Response('400', null, null, $request->getAccept(), null);
            }

            $response->generate();
        }
    }
}