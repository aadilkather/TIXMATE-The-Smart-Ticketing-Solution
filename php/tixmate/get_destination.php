<?php

include("connection.php");

$src_location = $_REQUEST['source'];

$stopsql="SELECT * FROM stops WHERE stop_name = '$src_location' ";
$stopres=mysqli_query($con,$stopsql);
while($row=mysqli_fetch_assoc($stopres))
{
	$route = $row['route_id'];		 
	$sql="SELECT stop_name FROM stops WHERE route_id='$route' and stop_name != '$src_location' "; 
	// echo "<br>".$sql."<br>";
	$res=mysqli_query($con,$sql);

	while ($roww = mysqli_fetch_assoc($res)) {
		$data['data'][] = $roww;	
	}
}

echo json_encode($data);

mysqli_close($con);

?>