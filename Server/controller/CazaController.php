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
        if (isset($request->getUrlElements()[2])) {
            if($request->getUrlElements()[2] == "estado"){
                $code = 200;
            }else{
                $code = '404';
                $atributos = null;
                $response = new Response($code, null, null, $request->getAccept(), $request->getAuthentication()->getId());
            }
        }else{
            $rollo = RolloHandlerModel::getRollo($request->getAuthentication()->getId());
            $enemigo = CazaHandlerModel::getEnemigoEnCaza($request->getAuthentication()->getId());
            $estado = CazaHandlerModel::getEstadoCaza($request->getAuthentication()->getId());

            $caza = new CazaModel($rollo, $enemigo, $estado);
            $code = '200';
            $response = new Response($code, null, $caza, $request->getAccept(), $request->getAuthentication()->getId());
        }

        $response->generate();
    }

    /*public function managePostVerb(Request $request){
        if (isset($request->getUrlElements()[2])) {
            $response = new Response('405', null, null, $request->getAccept());
            $response->generate();
        }else{
            if(isset($request->getBodyParameters()['atributo'])){
                $atributo = $request->getBodyParameters()['atributo'];
                if($atributo == 'fuerza' || $atributo == 'constitucion' || $atributo == 'destreza'){
                    $id = $request->getAuthentication()->getId();
                    $conseguido = AtributosHandlerModel::entrenar($id, $atributo);
                    if($conseguido){
                        $atributos = AtributosHandlerModel::getAtributos($request->getAuthentication()->getId());
                        $response = new Response('200', null, $atributos, $request->getAccept(), $request->getAuthentication()->getId());

                    }else{
                        $response = new Response('403', null, null, $request->getAccept(), null);
                    }
                }
            }else{
                $response = new Response('400', null, null, $request->getAccept(), null);
            }

            $response->generate();
        }
    }*/
}