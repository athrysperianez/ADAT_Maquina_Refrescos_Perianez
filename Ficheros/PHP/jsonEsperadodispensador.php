<?php

/*  Formato JSON esperado */

$arrEsperado = array();
$arrDispensadorEsperado = array();

$arrEsperado["peticion"] = "add";

$arrDispensadorEsperado["nombre"] = "Prueba";
$arrDispensadorEsperado["clave"] = "Prueba";


$arrEsperado["dispensadorAnnadir"] = $arrDispensadorEsperado;


/* Funcion para comprobar si el recibido es igual al esperado */

function JSONCorrectoAnnadir($recibido){

	$auxCorrecto = false;

	if(isset($recibido["peticion"]) && $recibido["peticion"] ="add" && isset($recibido["dispensadorAnnadir"])){

		$auxDispensador = $recibido["dispensadorAnnadir"];
		if(isset($auxDispensador["nombre"]) && isset($auxDispensador["clave"]) ){
			$auxCorrecto = true;
		}

	}


	return $auxCorrecto;

}
