<?php

/*  Formato JSON esperado */

$arrEsperado = array();
$arrdepositoEsperado = array();

$arrEsperado["peticion"] = "add";

$arrdepositoEsperado["nombre"] = "Prueba";
$arrdepositoEsperado["valor"] = 0;
$arrdepositoEsperado["cantidad"] = 0;


$arrEsperado["depositoAnnadir"] = $arrdepositoEsperado;


/* Funcion para comprobar si el recibido es igual al esperado */

function JSONCorrectoAnnadir($recibido){

	$auxCorrecto = false;

	if(isset($recibido["peticion"]) && $recibido["peticion"] ="add" && isset($recibido["depositoAnnadir"])){

		$auxdeposito = $recibido["depositoAnnadir"];
		if(isset($auxdeposito["nombre"]) && isset($auxdeposito["valor"]) && isset($auxdeposito["cantidad"])){
			$auxCorrecto = true;
		}

	}


	return $auxCorrecto;

}
