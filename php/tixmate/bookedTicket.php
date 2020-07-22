<?php

include("connection.php");

$bus_id = $_REQUEST['bus_id'];
$user_id = $_REQUEST['user_id'];

$sql = "SELECT * FROM user WHERE id = '$user_id'";
$res = mysqli_query($con,$sql);
$row = mysqli_fetch_assoc($res);


$sql1 = "SELECT * FROM buses WHERE id = '$bus_id'";
$res1 = mysqli_query($con,$sql1);
$row1 = mysqli_fetch_assoc($res1);

$data['data'][] = array('bus_name' => $row1['bus_name'], 'bus_no' => $row1['bus_no'], 'user_name' => $row['name'] );

echo json_encode($data);

?>