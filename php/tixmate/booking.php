<?php

include("connection.php");

date_default_timezone_set('Asia/kolkata');

$btime = date("h:i:s");
$bdate = date("d-m-Y");

$bus_id = $_REQUEST['bus_id'];
$user_id = $_REQUEST['user_id'];
$seats = $_REQUEST['seats'];
$pickout = $_REQUEST['pickout'];
$dropoff = $_REQUEST['dropoff'];
$route_id = $_REQUEST['route_id'];
$price = $_REQUEST['price'];

$trans_num = rand(1000000,9999999);

$bussql = " SELECT * FROM buses WHERE id = '$bus_id' ";
$busres = mysqli_query($con,$bussql);
$bubsrow = mysqli_fetch_assoc($busres);
$bus_name = $bubsrow['bus_name'];

$sql="select * from account_tbl where userid = '$user_id'";
$res=mysqli_query($con,$sql);
if(mysqli_num_rows($res) > 0 )
{
$row = mysqli_fetch_array($res);
$amt = $row['amount'];

	if($amt > $price)
	{

	$newAmt = $amt - $price;

/*	echo $amt;
	echo $newAmt;*/

	$updateSql = " UPDATE account_tbl SET amount = $newAmt WHERE userid = '$user_id' ";
	mysqli_query($con,$updateSql);

	$s = " SELECT * FROM account_tbl WHERE userid = '$user_id' ";
	$res = mysqli_query($con, $s);
	$row = mysqli_fetch_array($res);
	$id = $row['id'];

	date_default_timezone_set('Asia/Kolkata');
	$cdate = date("d-m-Y");
	$tran = "INSERT INTO transaction_tbl(uid, wid, trans_num, bus_name, stop_name, amount, tdate, status) VALUES ('$user_id', '$id', '$trans_num', '$bus_name', '$pickout', '$price', '$cdate', 'deduct') ";
	mysqli_query($con,$tran);

	$sql = " SELECT * FROM bus_data WHERE bus_id = '$bus_id' ";
	$res = mysqli_query($con,$sql);
	$row = mysqli_fetch_assoc($res);
	$as = $row['seats'];
	$us = $as - $seats;

	$stopsql = " SELECT * FROM stops WHERE bus_id = '$bus_id' AND route_id = '$route_id'";
	$stopres = mysqli_query($con,$stopsql);
	while($stoprow = mysqli_fetch_assoc($stopres))
	{
		if($stoprow['stop_name'] == $pickout )
			$pickTime = $stoprow['time'];
	}

	$stopsql=" INSERT INTO bookings(bus_id, user_id, seats, pickup, pickup_time, dropoff, route_id, price, bdate, btime, status) VALUES ('$bus_id', '$user_id', '$seats', '$pickout', '$pickTime', '$dropoff', '$route_id', '$price', '$bdate', '$btime', 'booked') ";
	// echo $stopsql;
		if(mysqli_query($con,$stopsql))
		{
			$updateBusSeat = " UPDATE bus_data SET seats = '$us' WHERE bus_id = '$bus_id' ";
			// echo "<br>".$updateBusSeat;
			mysqli_query($con,$updateBusSeat);

			$sel = " SELECT * FROM bookings WHERE user_id = '$user_id' AND status = 'booked' order by id desc";
			// echo "<br>".$sel;
			$resSel = mysqli_query($con,$sel);
			$row = mysqli_fetch_assoc($resSel);
			$data['data'][] = $row;
			echo json_encode($data);
		}

	}
	else{
		echo "insufficient Balance";
	}
}
else
{
echo "No Wallet Available";
}

mysqli_close($con);
?>