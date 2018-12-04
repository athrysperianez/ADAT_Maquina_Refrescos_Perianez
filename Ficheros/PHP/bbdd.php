<?php
/**
 * Created by PhpStorm.
 * User: Pablo
 * Date: 26/11/2018
 * Time: 14:22
 */

$servername = "localhost";
$user = "root";
$password = "";
$dbname = "adat_maquinarefrescos_jdbc";
$conn = new  mysqli($servername, $user, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Error: " . $conn->connect_error);
}

if (!$conn->set_charset("utf8")) {
    printf("Error cargando el conjunto de caracteres utf8: %s\n", $mysqli->error);
    exit();
}


?>
