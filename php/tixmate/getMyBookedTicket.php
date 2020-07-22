<?php

include("connection.php");

$user_id = $_REQUEST['user_id'];

$pickTime = "";
$dropTime = "";

$sql = "SELECT * FROM bookings WHERE user_id = '$user_id' and status='booked' OR status = 'scaned' order by id desc ";
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
		// echo $stopsql."<br>";
		$stopres = mysqli_query($con,$stopsql);
		while($stoprow = mysqli_fetch_assoc($stopres))
		{
			if($stoprow['stop_name'] == $row['pickup'])
				$pickTime = $stoprow['time'];
			// echo " pickup ".$pickTime."<br>";

			if($stoprow['stop_name'] == $row['dropoff'])
				$dropTime = $stoprow['time'];
			// echo " dropoff ".$dropTime."<br>";	
		}


		$data['data'][] = array('id' => $row['id'] , 'bus_id' => $row['bus_id'] , 'user_id' => $user_id , 'bus_name' => $busrow['bus_name'], 'bus_no' => $busrow['bus_no'], 'latitude' => $busdatarow['latitude'], 
										'longitude' => $busdatarow['longitude'], 'pickup_point' => $row['pickup'],  'dropoff_point' => $row['dropoff'],  'pickup_time' => $pickTime,  'dropoff_time' => $dropTime, 
										'booking_date' => $row['bdate'], 'price' => $row['price'], 'seats' => $row['seats'], 'status' => $row['status']);


		$pickTime = "";
		$dropTime = "";

	}
	echo json_encode($data);
}
else{
	echo "No data";
}

?>