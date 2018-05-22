<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:49
 */

require_once "Controller.php";

class MaterialController extends Controller
{
    public function manageGetVerb(Request $request){
        $idRollo = $request->getAuthentication()->getId();

        if (isset($request->getUrlElements()[2])) {
            $idSupermaterial = $request->getUrlElements()[2];
            if(is_numeric($idSupermaterial)){
                $supermaterial = MaterialHandlerModel::supermaterial($idRollo, $idSupermaterial);
                $response = new Response(200, null, $supermaterial, $request->getAccept(), $idRollo);
            }else{
                $response = new Response(400, null, null, $request->getAccept(), $idRollo);
            }

        }else{
            $response = new Response(404, null, null, $request->getAccept(), $idRollo);
        }
        $response->generate();
    }

    public function managePostVerb(Request $request){
        if (isset($request->getUrlElements()[2])) {
            $idRollo = $request->getAuthentication()->getId();
            $idSupermaterial = $request->getUrlElements()[2];
            $conseguido = MaterialHandlerModel::fabricar($idRollo, $idSupermaterial);
            if($conseguido){
                $supermaterial = MaterialHandlerModel::supermaterial($idRollo, $idSupermaterial);
                $response = new Response(200, null, $supermaterial, $request->getAccept(), $idRollo);
            }else{
                $response = new Response(403, null, null, $request->getAccept(), $idRollo);
            }
        }else{
            $response = new Response(404, null, null, $request->getAccept());

        }
        $response->generate();
    }
}