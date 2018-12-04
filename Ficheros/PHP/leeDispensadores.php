<?php
require 'bbdd.php';


$arrMensaje = array();


$query = "SELECT * FROM dispensadores";

$result = $conn->query($query);

if (isset ($result) && $result) {

    if ($result->num_rows > 0) {

        $arrDispensadores = array();

        while ($row = $result->fetch_assoc()) {


            $arrDispensador = array();

            $arrDispensador["id"] = $row["id"];
            $arrDispensador["clave"] = $row["clave"];
            $arrDispensador["nombre"] = $row["nombre"];
            $arrDispensador["precio"] = $row["precio"];
            $arrDispensador["cantidad"] = $row["cantidad"];

            $arrDispensadores[] = $arrDispensador;

        }


        $arrMensaje["estado"] = "ok";
        $arrMensaje["coches"] = $arrDispensadores;


    } else {

        $arrMensaje["estado"] = "ok";
        $arrMensaje["depositos"] = [];
    }

} else {

    $arrMensaje["estado"] = "error";
    $arrMensaje["mensaje"] = "SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS";
    $arrMensaje["error"] = $conn->error;
    $arrMensaje["query"] = $query;

}

$mensajeJSON = json_encode($arrMensaje, JSON_PRETTY_PRINT);


echo $mensajeJSON;


$conn->close();

?>
