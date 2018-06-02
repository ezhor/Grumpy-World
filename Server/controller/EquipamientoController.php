<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:49
 */

require_once "Controller.php";

class EquipamientoController extends Controller
{
    public function manageGetVerb(Request $request){
        $idRollo = $request->getAuthentication()->getId();

        if (isset($request->getUrlElements()[2])) {
            $idEquipable = $request->getUrlElements()[2];
            if(is_numeric($idEquipable)){
                if(EquipamientoHandlerModel::tieneEquipable($idRollo, $idEquipable)){
                    $equipableDetalle = EquipamientoHandlerModel::getEquipablePoseidoDetalle($idRollo, $idEquipable);
                    $response = new Response(200, null, $equipableDetalle, $request->getAccept(), $idRollo);
                }else{
                    $response = new Response(403, null, null, $request->getAccept(), $idRollo);
                }
            }else{
                $response = new Response(404, null, null, $request->getAccept(), $idRollo);
            }

        }else{
            $equipables = EquipamientoHandlerModel::getEquipablesPoseidos($idRollo);
            $response = new Response(200, null, $equipables, $request->getAccept(), $idRollo);
        }
        $response->generate();
    }

    public function managePostVerb(Request $request){
        $idRollo = $request->getAuthentication()->getId();
        if (isset($request->getUrlElements()[2])) {
            $idEquipable = $request->getUrlElements()[2];
            if(EquipamientoHandlerModel::tieneEquipable($idRollo, $idEquipable) && EquipamientoHandlerModel::puedeEquipar($idRollo, $idEquipable)){
                EquipamientoHandlerModel::equipar($idRollo, $idEquipable);
                $response = new Response(204, null, null, $request->getAccept(), $idRollo);
            }else{
                $response = new Response(403, null, null, $request->getAccept(), $idRollo);
            }
        }else{
            $response = new Response(404, null, null, $request->getAccept(), $idRollo);

        }
        $response->generate();
    }
}