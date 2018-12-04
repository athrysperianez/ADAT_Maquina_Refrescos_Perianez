<?php

/*  Formato JSON esperado */

$arrEsperado = array();
$arrJugadorEsperado = array();

$arrEsperado["peticion"] = "add";

$arrJugadorEsperado["nombre"] = "Lorenzo (Un string)";
$arrJugadorEsperado["descripcion"] = "2 (Un int)";
$arrJugadorEsperado["caracteristica1"] = "2 (Un int)";
$arrJugadorEsperado["caracteristica2"] = "2 (Un int)";
$arrJugadorEsperado["idFabricante"] = "2 (Un int)";

$arrEsperado["cocheAnnadir"] = $arrJugadorEsperado;


/* Funcion para comprobar si el recibido es igual al esperado */

function JSONCorrectoAnnadir($recibido){

	$auxCorrecto = false;

	if(isset($recibido["peticion"]) && $recibido["peticion"] ="add" && isset($recibido["cocheAnnadir"])){

		$auxJugador = $recibido["cocheAnnadir"];
		if(isset($auxJugador["nombre"]) && isset($auxJugador["descripcion"]) && isset($auxJugador["caracteristica1"]) && isset($auxJugador["caracteristica2"]) && isset($auxJugador["idFabricante"])){
			$auxCorrecto = true;
		}

	}


	return $auxCorrecto;

}
