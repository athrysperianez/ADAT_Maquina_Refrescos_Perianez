<?php

/*  Formato JSON esperado */

$arrEsperado = array();
$arrEquipoEsperado = array();

$arrEsperado["peticion"] = "add";

$arrEquipoEsperado["nombre"] = "Prueba";
$arrEquipoEsperado["sede"] = "Prueba";


$arrEsperado["marcaAnnadir"] = $arrEquipoEsperado;


/* Funcion para comprobar si el recibido es igual al esperado */

function JSONCorrectoAnnadir1($recibido){

	$auxCorrecto = false;

	if(isset($recibido["peticion"]) && $recibido["peticion"] ="add" && isset($recibido["marcaAnnadir"])){

		$auxEquipo = $recibido["marcaAnnadir"];
		if(isset($auxEquipo["nombre"]) && isset($auxEquipo["sede"]) ){
			$auxCorrecto = true;
		}

	}


	return $auxCorrecto;

}
