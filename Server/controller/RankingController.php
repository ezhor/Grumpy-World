<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:49
 */

require_once "Controller.php";

class RankingController extends Controller
{
    public function manageGetVerb(Request $request){

        if (isset($request->getUrlElements()[2])) {
            $response = new Response(404, null, null, $request->getAccept(), $request->getAuthentication()->getId());
        }else{
            $ranking = RankingHandlerModel::getRankedWithTopRanking($request->getAuthentication()->getId());
            $response = new Response(200, null, $ranking, $request->getAccept(), $request->getAuthentication()->getId());
        }


        $response->generate();
    }
}