<?php

include("connection.php");

$username = $_POST['username'];
$pwd = $_POST['pwd'];

$sql = "SELECT * FROM buses WHERE username='$username' && password='$pwd'";

$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0)
{
	while($row = mysqli_fetch_assoc($result))
	{
		$data['data'][] = $row;
	}
	echo json_encode($data);
}
else{
echo "Failed";	
}


?>