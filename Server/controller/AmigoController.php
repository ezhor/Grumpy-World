<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:49
 */

require_once "Controller.php";

class AmigoController extends Controller
{
    public function manageGetVerb(Request $request){
        $idUsuario = $request->getAuthentication()->getId();
        $query = $request->getQueryString();
        $busqueda = isset($query["nombre"]) ? $query["nombre"] : null;

        if (isset($busqueda) && strlen($busqueda) > 0) {
            $usuariosBusqueda = AmigoHandlerModel::buscarUsuariosPorNombreParcial($busqueda, $idUsuario);
            $response = new Response(200, null, $usuariosBusqueda, $request->getAccept(), $idUsuario);
        }else{
            $listadoCompletoAmigos = AmigoHandlerModel::getListadoAmigosCompleto($idUsuario);
            $response = new Response(200, null, $listadoCompletoAmigos, $request->getAccept(), $idUsuario);
        }
        $response->generate();
    }

    public function managePostVerb(Request $request){
        if (isset($request->getUrlElements()[2])) {
            $idUsuario = $request->getAuthentication()->getId();
            $idAmigo = $request->getUrlElements()[2];
            if(is_numeric($idAmigo)){
                AmigoHandlerModel::agregarAmigo($idUsuario, $idAmigo);
                $listadoCompletoAmigos = AmigoHandlerModel::getListadoAmigosCompleto($idUsuario);
                $response = new Response(200, null, $listadoCompletoAmigos, $request->getAccept(), $idUsuario);
            }else{
                $response = new Response(400, null, null, $request->getAccept(), $idUsuario);
            }
        }else{
            $response = new Response(404, null, null, $request->getAccept());

        }
        $response->generate();
    }

    public function manageDeleteVerb(Request $request){
        if (isset($request->getUrlElements()[2])) {
            $idUsuario = $request->getAuthentication()->getId();
            $idAmigo = $request->getUrlElements()[2];
            if(is_numeric($idAmigo)){
                AmigoHandlerModel::borrarAmigo($idUsuario, $idAmigo);
                $listadoCompletoAmigos = AmigoHandlerModel::getListadoAmigosCompleto($idUsuario);
                $response = new Response(200, null, $listadoCompletoAmigos, $request->getAccept(), $idUsuario);
            }else{
                $response = new Response(400, null, null, $request->getAccept(), $idUsuario);
            }
        }else{
            $response = new Response(404, null, null, $request->getAccept());

        }
        $response->generate();
    }
}