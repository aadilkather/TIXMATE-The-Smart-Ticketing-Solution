<?php

$con=mysqli_connect("localhost","root","","tixmate");

$route_id = '11';
$bus_id = $_REQUEST['bus_id'];
// $bus_id = '2';
$lat = $_REQUEST['lat'];
$lon = $_REQUEST['lon'];
$area = $_REQUEST['area'];

$busLoc = " UPDATE bus_data SET latitude = '$lat', longitude = '$lon', area = '$area' WHERE id = '$bus_id' ";
mysqli_query($con,$busLoc);

$sql = " SELECT stop_name,SQRT( POW(69.1 * (latitude - $lat), 2) + POW(69.1 * ($lon - longitude) * COS(latitude / 57.3), 2)) AS distance FROM stops WHERE route_id='$route_id'  AND bus_id='$bus_id'
		 HAVING distance < 0.1 ORDER BY distance DESC  ";

$res = mysqli_query($con,$sql);
$row = mysqli_fetch_assoc($res);
echo $row['distance'];
echo $sql;

$sel = " SELECT * FROM bookings WHERE dropoff = '$row[stop_name]' AND bus_id='$bus_id' ";
echo $sel;
$selRes = mysqli_query($con,$sel);
While($selrow = mysqli_fetch_assoc($selRes))
{
	$bs = $selrow['seats'];
	$busSel = " SELECT * FROM bus_data WHERE bus_id = '$selrow[bus_id]'  ";
	$resBus = mysqli_query($con,$busSel);
	$ress = mysqli_fetch_assoc($resBus);
	$bdId = $ress['id'];
	$as = $ress['seats'];

	if($bs != 0)
	{

		$us = $as + $bs;
		$up = " UPDATE bus_data SET seats = '$us' WHERE id = '$bdId' ";
		mysqli_query($con,$up);

	$u = " UPDATE bookings SET seats = '0', bseat = '$bs', status = 'completed'  WHERE id = '$selrow[id]' ";
	mysqli_query($con,$u);
	}

}

mysqli_close($con);

?>