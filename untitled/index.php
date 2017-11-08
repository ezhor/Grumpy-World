<?php
/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 27/10/17
 * Time: 10:08
 */

require_once "Database.php";

$database = Database::getInstance();
$conn = $database->getConnection();
$sql_query = "
            SELECT
                E.Nombre, E.Fuerza, E.Constitucion, E.Destreza, Z.Nombre AS Zona
            FROM
                Enemigos AS E
                INNER JOIN Zonas AS Z
                ON E.ID_Zona = Z.ID";

if ($stmt = $conn->prepare($sql_query)) {
    $stmt->execute();
    $stmt->bind_result($nombre, $fuerza, $constitucion, $destreza, $zona);

    echo '<table border=\"1\">';
    echo '<tr>';
    echo '<td>Nombre</td>';
    echo '<td>Fuerza</td>';
    echo '<td>Constitucion</td>';
    echo '<td>Destreza</td>';
    echo '<td>Zona</td>';
    echo '</tr>';
    // output data of each row
    while ($stmt->fetch()) {
        echo '<tr>';
        echo '<td>' . $nombre . '</td>';
        echo '<td>' . $fuerza . '</td>';
        echo '<td>' . $constitucion . '</td>';
        echo '<td>' . $destreza . '</td>';
        echo '<td>' . $zona . '</td>';
        echo '</tr>';
    }
    echo '</table>';
}

$database->closeConnection();
