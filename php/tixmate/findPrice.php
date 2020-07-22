<?php

$con = mysqli_connect("localhost","root","","tixmate");



$src_location = $_REQUEST['source'];
$dest_location = $_REQUEST['destn'];
$bus_id = $_REQUEST['bus_id'];
$route_id = $_REQUEST['route_id'];

$count = 1;

$sql = "SELECT * FROM stops WHERE route_id = '$route_id' ";
$res=mysqli_query($con,$sql);
while($row=mysqli_fetch_assoc($res))
{
	$count++ ;

	if($row['stop_name'] == $src_location )
	{
			$s = $count;
	}
	if($row['stop_name'] == $dest_location )
	{
			$d = $count;
	}

}

$stop_count = $d-$s; 

//price effect change here
if ($stop_count < 3) {

	$price = 8;
	// $price = $stop_count * $amount;


}
else{

	$stop_count = $stop_count - 2;
	$price = ($stop_count * 3) + 8;

}



$sql = "SELECT * FROM buses WHERE id = '$bus_id' ";
$res=mysqli_query($con,$sql);
while($row=mysqli_fetch_assoc($res))
{
	
	$data['data'][] = array('busName' => $row['bus_name'] , 'busNo' => $row['bus_no'] , 'price' => $price );

}

echo json_encode($data);

mysqli_close($con);

?>
