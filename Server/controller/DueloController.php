<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:49
 */

require_once "Controller.php";

class DueloController extends Controller
{
    public function manageGetVerb(Request $request){
        $idUsuario = $request->getAuthentication()->getId();
        if (isset($request->getUrlElements()[2])) {
            $idOponente = $request->getUrlElements()[2];
            if(is_numeric($idOponente)){
                if (isset($request->getUrlElements()[3])) {
                    if($request->getUrlElements()[3] == "estado"){
                        if(DueloHandlerModel::oponenteHaElegidoEsteTurno($idUsuario)){
                            $estado = DueloHandlerModel::getEstado($idUsuario);
                            $response = new Response(200, null, $estado, $request->getAccept(), $idUsuario);
                        }else{
                            $response = new Response(204, null, null, $request->getAccept(), $idUsuario);
                        }
                    }else if($request->getUrlElements()[3] == "premio"){
                        $premio = DueloHandlerModel::calcularPremioDuelo($idUsuario);
                        if($premio > 0){
                            DueloHandlerModel::concederPremioDuelo($idUsuario, $premio);
                            $response = new Response(200, null, $premio, $request->getAccept(), $idUsuario);
                        }else{
                            $response = new Response(403, null, null, $request->getAccept(), $idUsuario);
                        }
                    }else{
                        $response = new Response(400, null, null, $request->getAccept(), $idUsuario);
                    }
                }else{
                    $duelo = DueloHandlerModel::getDuelo($idUsuario, $idOponente);
                    if($duelo != null){
                        $response = new Response(200, null, $duelo, $request->getAccept(), $idUsuario);
                    }else{
                        $response = new Response(403, null, null, $request->getAccept(), $idUsuario);
                    }
                }
            }else{
                $response = new Response(400, null, null, $request->getAccept(), $idUsuario);
            }
        }else{
            $lobbyDueloModel = DueloHandlerModel::getLobbyDuelo($idUsuario);
            $response = new Response(200, null, $lobbyDueloModel, $request->getAccept(), $idUsuario);
        }

        $response->generate();
    }

    public function managePostVerb(Request $request){
        $idUsuario = $request->getAuthentication()->getId();
        if (isset($request->getUrlElements()[2])) {
            $idOponente = $request->getUrlElements()[2];
            DueloHandlerModel::retarADuelo($idUsuario, $idOponente);
            $lobbyDueloModel = DueloHandlerModel::getLobbyDuelo($idUsuario);
            if($lobbyDueloModel != null){
                $response = new Response(200, null, $lobbyDueloModel, $request->getAccept(), $idUsuario);
            }else{
                $response = new Response(204, null, null, $request->getAccept(), $idUsuario);
            }
        }else{
            if(isset($request->getBodyParameters()['ataque'])){
                $ataque = $request->getBodyParameters()['ataque'];
                if($ataque === 1 || $ataque === 2 || $ataque === 3){
                    if(!DueloHandlerModel::yaHaJugadoTurno($idUsuario)){
                        if(DueloHandlerModel::oponenteHaElegidoSiguienteTurno($idUsuario)){
                            DueloHandlerModel::jugarTurno($idUsuario, $ataque);
                            $estado = DueloHandlerModel::getEstado($idUsuario);
                            $response = new Response(200, null, $estado, $request->getAccept(), $idUsuario);
                        }else{
                            DueloHandlerModel::elegirTurno($idUsuario, $ataque);
                            $response = new Response(204, null, null, $request->getAccept(), $idUsuario);
                        }
                    }else{
                        $response = new Response(403, null, null, $request->getAccept(), $idUsuario);
                    }
                }else{
                    $response = new Response(400, null, null, $request->getAccept(), $idUsuario);
                }
            }else{
                $response = new Response(400, null, null, $request->getAccept(), $idUsuario);
            }

        }
        $response->generate();
    }

    public function manageDeleteVerb(Request $request){
        $idUsuario = $request->getAuthentication()->getId();
        $idOponente = $request->getUrlElements()[2];
        if (isset($idOponente) && is_numeric($idOponente)) {
            DueloHandlerModel::rechazarDuelo($idUsuario, $idOponente);
            $lobbyDueloModel = DueloHandlerModel::getLobbyDuelo($idUsuario);
            $response = new Response(200, null, $lobbyDueloModel, $request->getAccept(), $idUsuario);
        }else{
            $response = new Response(400, null, null, $request->getAccept(), $idUsuario);
        }
        $response->generate();
    }
}