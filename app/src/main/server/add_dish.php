<?php
 
error_reporting(E_ERROR | E_PARSE);
header('Content-type=application/json; charset=utf-8');

$response = array();

if (!isset($_POST['name']) || !isset($_FILES['file'])) {
    $response["success"] = 0;
    $response["message"] = "Required POST parameter is missing.";
	echo json_encode($response);
	exit();
}

$dishName = $_POST['name'];
$randomFilename = createRandomString(32) . '.jpg';
$destination = './images/' . $randomFilename;

    if (move_uploaded_file($_FILES['file']['tmp_name'], $destination)) {
        require_once 'db_config.php';
        $db = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

		if (mysqli_connect_errno()) {
			$response["success"] = 0;
			$response["message"] = "Database connection failed: " . mysqli_connect_error();
			echo json_encode($response);
			exit();
		}

        $db->query("SET character_set_results=utf8");
        $db->query('SET character set utf8'); // กำหนดเพื่อให้ข้อมูลที่เขียนลงฐานข้อมูลเป็นภาษาไทย

        $insertSql = "INSERT INTO dishes(name, file_name) VALUES('$dishName', '$randomFilename')";
     
        if ($result = $db->query($insertSql)) {
            $response["success"] = 1;
            $response["message"] = "Data added successfully.";
        } else {
            $response["success"] = 0;
            $response["message"] = "An error occurred while adding data: $insertSql";
        }
        
        $db->close();
    } else {
        $response["success"] = 0;
        $response["message"] = "There's a problem with the uploaded file.";
    }

echo json_encode($response);

function createRandomString($length) {
    $key = '';
    $keys = array_merge(range(0, 9), range('a', 'z'));

    for ($i = 0; $i < $length; $i++) {
        $key .= $keys[array_rand($keys)];
    }

    return $key;
}

?>