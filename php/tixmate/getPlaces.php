<?php

include("connection.php");

$sql1 = "SELECT stop_name FROM stops";

$res1 = mysqli_query($con,$sql1);

while ($row1 = mysqli_fetch_assoc($res1)) {
	$data['places'][] = $row1;
}

echo json_encode($data);
?>