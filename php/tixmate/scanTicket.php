<?php

include("connection.php");

date_default_timezone_set('Asia/kolkata');

$bdate = date("d-m-Y");

$bookId = $_REQUEST['bookId'];
$bus_id = $_REQUEST['bus_id'];

$sql = " SELECT * FROM bookings WHERE id = '$bookId' AND status = 'scaned'  ";
$res = mysqli_query($con,$sql);

if(mysqli_num_rows($res) > 0 ){
	echo "Already Scaned";
}
else{

	$sqll = " SELECT * FROM bookings WHERE id = '$bookId' AND status = 'completed'  ";
	$ress = mysqli_query($con,$sqll);
	if(mysqli_num_rows($ress) > 0 ){
	echo "Invalid Ticket";
	}
	else{

		$sql1 = " SELECT * FROM bookings WHERE id = '$bookId' AND bus_id = '1' AND  bdate = '$bdate' AND status = 'booked'  ";
		$res1 = mysqli_query($con,$sql1);
		if(mysqli_num_rows($res1) == 1 ){
			echo "valid Ticket";

			$u = " UPDATE bookings SET status = 'scaned'  WHERE id = '$bookId' ";
			mysqli_query($con,$u);
		}
	}

}

?>