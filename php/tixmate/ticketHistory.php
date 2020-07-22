<?php

include("connection.php");

$user_id = $_REQUEST['user_id'];

$sql = "SELECT * FROM bookings WHERE user_id = '$user_id' and status='completed' ";
$res = mysqli_query($con,$sql);
if(mysqli_num_rows($res) > 0)
{
	while($row = mysqli_fetch_assoc($res))
	{

		$bussql = "SELECT * FROM buses WHERE id = '$row[bus_id]'";
		$busres = mysqli_query($con,$bussql);
		$busrow = mysqli_fetch_assoc($busres);

		$busdatasql = " SELECT * FROM bus_data WHERE bus_id = '$row[bus_id]' ";
		$busdatares = mysqli_query($con,$busdatasql);
		$busdatarow = mysqli_fetch_assoc($busdatares);	

		$stopsql = " SELECT * FROM stops WHERE bus_id = '$row[bus_id]' AND route_id = '$row[route_id]'";
		$stopres = mysqli_query($con,$stopsql);
		while($stoprow = mysqli_fetch_assoc($stopres))
		{
			if($stoprow['stop_name'] == $row['pickup'])
				$pickTime = $stoprow['time'];

			if($stoprow['stop_name'] == $row['dropoff'])
				$dropTime = $stoprow['time'];
		}
		$data['data'][] = array(   'bus_name' => $busrow['bus_name'], 'pickup_point' => $row['pickup'],  'dropoff_point' => $row['dropoff'],  'pickup_time' => $pickTime,  'dropoff_time' => $dropTime, 
										'booking_date' => $row['bdate'], 'price' => $row['price'], 'seats' => $row['bseat'] );
	}
	echo json_encode($data);
}
else{
	echo "No data";
}
?>