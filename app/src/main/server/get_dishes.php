<?php

error_reporting(E_ERROR | E_PARSE);
header('Content-type=application/json; charset=utf-8');

$response = array();

require_once 'db_config.php';
$db = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

if (mysqli_connect_errno()) {
    $response["success"] = 0;
    $response["message"] = "Database Connection Failed:\n" . mysqli_connect_error();
	echo json_encode($response);
    exit();
}

$charset = "SET character_set_results=utf8";
$db->query($charset);

$sql = "SELECT * FROM dishes";

if ($result = $db->query($sql)) {
    $response["success"] = 1;
    $response["dish_data"] = array();

    $rowCount = $result->num_rows;

    if ($rowCount > 0) {
        while ($row = $result->fetch_assoc()) {
            $dish = array();
            $dish["_id"] = (int) $row["_id"];
            $dish["name"] = $row["name"];
            $dish["file_name"] = $row["file_name"];

            array_push($response["dish_data"], $dish);
        }
    }
    $result->close();
} else {
    $response["success"] = 0;
    $response["message"] = "An error occurred while retrieving data.";
}

$db->close();
echo json_encode($response);

?>