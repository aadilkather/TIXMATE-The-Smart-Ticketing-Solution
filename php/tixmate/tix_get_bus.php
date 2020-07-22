<?php

include("connection.php");

$stopsql="SELECT * FROM stops WHERE stop_name = 'KOLENCHERY' ";
$stopres=mysqli_query($con,$stopsql);
while($row=mysqli_fetch_assoc($stopres))
{
	$route = $row['route_id'];		 
	$sql="SELECT * FROM stops WHERE stop_name = 'ALUVA' and route_id='$route' "; // and id > '$row[id]'
	$res=mysqli_query($con,$sql);
	while ($roww = mysqli_fetch_assoc($res)) {
		// $data['data'][] = $roww;

		$bussql="SELECT * FROM bus_data WHERE route_id = '$roww[route_id]' ";
		$busres=mysqli_query($con,$bussql);
		while ($broww = mysqli_fetch_assoc($busres)) {
			$data['data'][] = $broww;
		}
	}

}

echo json_encode($data);
	mysqli_close($con);
?>