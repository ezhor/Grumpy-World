<?php
/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 27/10/17
 * Time: 10:08
 */

require_once "Database.php";

//Test de fichero
/*$config = parse_ini_file('./config/config.ini');
echo $config['host'].'<br>';
echo $config['username'].'<br>';
echo $config['password'].'<br>';
echo $config['dbname'].'<br>';*/


$database = Database::getInstance();
$mysqli = $database->getConnection();
$sql_query = "SELECT * FROM Enemigos";

$result = $mysqli->query($sql_query);

if ($result->num_rows > 0) {
    echo '<table border=\"1\">';
    echo '<tr>';
    echo '<td>Nombre</td>';
    echo '</tr>';
    // output data of each row
    while ($row = $result->fetch_assoc()) {
        echo '<tr>';
        echo '<td>' . $row['nombre'] . '</td>';
        echo '</tr>';
    }
    echo '</table>';
}

$database->closeConnection();
