<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:49
 */

require_once "Controller.php";

class FabricacionController extends Controller
{
    public function manageGetVerb(Request $request){
        $idRollo = $request->getAuthentication()->getId();
        /*
        Los rollos se obtienen según la autenticación, no según el URL ID
        No tiene sentido que alguien pida un ID concreto en la URL
        */
        if (isset($request->getUrlElements()[2])) {
            $idEquipable = $request->getUrlElements()[2];
            if(is_numeric($idEquipable)){
                $equipableDetalle = FabricacionHandlerModel::getEquipableDetalle($idRollo, $idEquipable);
                $response = new Response(200, null, $equipableDetalle, $request->getAccept(), $idRollo);
            }else{
                $response = new Response(400, null, null, $request->getAccept(), $idRollo);
            }

        }else{
            $equipables = FabricacionHandlerModel::getEquipablesDisponibles($idRollo);
            $response = new Response(200, null, $equipables, $request->getAccept(), $idRollo);
        }
        $response->generate();
    }

    /*public function managePostVerb(Request $request){
        if (isset($request->getUrlElements()[2])) {
            $response = new Response('404', null, null, $request->getAccept());
            $response->generate();
        }else{
            if(isset($request->getBodyParameters()['nombre'])){
                $nombreZona = $request->getBodyParameters()['nombre'];
                $idUsuario = $request->getAuthentication()->getId();
                if(ZonaHandlerModel::puedeCambiarZona($idUsuario, $nombreZona)){
                    ZonaHandlerModel::cambiarZona($idUsuario, $nombreZona);
                    $response = new Response('204', null, null, $request->getAccept(), $request->getAuthentication()->getId());
                }else{
                    $response = new Response('403', null, null, $request->getAccept(), null);
                }
            }else{
                $response = new Response('400', null, null, $request->getAccept(), null);
            }

            $response->generate();
        }
    }*/
}