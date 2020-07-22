<?php

// error_reporting(0);

$con = mysqli_connect("localhost","root","","tixmate");

date_default_timezone_set('Asia/kolkata');

$current_time = '06:20:00.000000';


$src_location = $_REQUEST['source'];
$dest_location = $_REQUEST['destn'];


$startsql = "SELECT * FROM stops WHERE stop_name = '$src_location' ";
$startres=mysqli_query($con,$startsql);
while($startrow=mysqli_fetch_assoc($startres))
{
	$route = $startrow['route_id'];		 
	$destsql="SELECT * FROM stops WHERE stop_name = '$dest_location' and route_id='$route' and id > '$startrow[id]' "; // and id > '$row[id]'
	$destres=mysqli_query($con,$destsql);
	while ($destrow = mysqli_fetch_assoc($destres)) {

		$busdatasql="SELECT * FROM bus_data WHERE route_id = '$destrow[route_id]' ";
		$busdatares=mysqli_query($con,$busdatasql);
		while ($busdatarow = mysqli_fetch_assoc($busdatares)) {
			// echo $broww['imei'];
			$bussql="SELECT * FROM buses WHERE id = '$busdatarow[bus_id]' ";
			$busres = mysqli_query($con,$bussql) ;
			while ($busrow = mysqli_fetch_assoc($busres)) {
				$routesql= "SELECT * FROM routes WHERE bus_id = '$busrow[id]' and src_location = '$src_location' ";
				$routeres = mysqli_query($con,$routesql);
				$routerow = mysqli_fetch_array($routeres); 
				$data['data'][] = array('bus_id' => $busrow['id'], 'bus_no' => $busrow['bus_no'], 'bus_name' => $busrow['bus_name'],'bus_image' => $busrow['image'], 'bus_seats' => $busdatarow['seats'],
										'src_location' => $src_location, 'dest_location' => $dest_location, 'start_time' => $startrow['time'], 
										'arr_time' => $destrow['time'], 'route_id' =>  $destrow['route_id'] );
			}
		}
	}

}

echo json_encode($data);
mysqli_close($con);

?>
