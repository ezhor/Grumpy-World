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
            if($request->getUrlElements()[2] == "premio"){
                $merecePremio = PremioCazaHandlerModel::merecePremio($idUsuario);
                if($merecePremio == 1){
                    $premios = PremioCazaHandlerModel::asignarPremios($idUsuario);
                    CazaHandlerModel::borrarCaza($idUsuario);
                    $response = new Response(200, null, $premios, $request->getAccept(), $idUsuario);
                }else{
                    if($merecePremio == 0){
                        $premios = array();
                        CazaHandlerModel::borrarCaza($idUsuario);
                        $response = new Response(200, null, $premios, $request->getAccept(), $idUsuario);
                    }else{
                        $response = new Response(403, null, null, $request->getAccept(), $idUsuario);
                    }
                }
            }else{
                $response = new Response(404, null, null, $request->getAccept(), $idUsuario);
            }
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
            $response = new Response('404', null, null, $request->getAccept(), null);
            $response->generate();
        }else{
            if(isset($request->getBodyParameters()['ataque'])){
                $ataque = $request->getBodyParameters()['ataque'];
                if($ataque === 1 || $ataque === 2 || $ataque === 3){
                    CazaHandlerModel::jugarTurno($idUsuario, $ataque);
                    $estado = CazaHandlerModel::getEstadoCaza($idUsuario);
                    $response = new Response('200', null, $estado, $request->getAccept(), $idUsuario);
                }else{
                    $response = new Response('400', null, null, $request->getAccept(), null);
                }
            }else{
                $response = new Response('400', null, null, $request->getAccept(), null);
            }
            $response->generate();
        }
    }
}