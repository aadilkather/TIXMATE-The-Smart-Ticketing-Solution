<?php

include("connection.php");

$sql1 = "SELECT * FROM stops WHERE route_id = '2' ";

$res1 = mysqli_query($con,$sql1);

while ($row1 = mysqli_fetch_assoc($res1)) {
	$data['data'][] = $row1;
}

echo json_encode($data);
?>