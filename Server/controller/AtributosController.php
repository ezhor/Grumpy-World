<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:49
 */

require_once "Controller.php";

class AtributosController extends Controller
{
    public function manageGetVerb(Request $request){
        /*
        Los rollos se obtienen según la autenticación, no según el URL ID
        No tiene sentido que alguien pida un ID concreto en la URL
        */
        if (isset($request->getUrlElements()[2])) {
            $code = '404';
            $atributos = null;
        }else{
            $atributos = AtributosHandlerModel::getAtributos($request->getAuthentication()->getUsuario());
            $code = '200';
        }

        $response = new Response($code, null, $atributos, $request->getAccept(), $request->getAuthentication()->getUsuario());
        $response->generate();
    }

    public function managePostVerb(Request $request){
        if (isset($request->getUrlElements()[2])) {
            $response = new Response('405', null, null, $request->getAccept());
            $response->generate();
        }else{
            if(isset($request->getBodyParameters()['atributo'])){
                $atributo = $request->getBodyParameters()['atributo'];
                if($atributo == 'fuerza' || $atributo == 'constitucion' || $atributo == 'destreza'){
                    $usuario = $request->getAuthentication()->getUsuario();
                    $conseguido = AtributosHandlerModel::entrenar($usuario, $atributo);
                    if($conseguido){
                        $atributos = AtributosHandlerModel::getAtributos($request->getAuthentication()->getUsuario());
                        $response = new Response('200', null, $atributos, $request->getAccept(), $request->getAuthentication()->getUsuario());

                    }else{
                        $response = new Response('403', null, null, $request->getAccept(), null);
                    }
                }
            }else{
                $response = new Response('400', null, null, $request->getAccept(), null);
            }

            $response->generate();
        }
    }
}