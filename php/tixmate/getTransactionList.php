<?php

include("connection.php");

$uid = $_REQUEST['uid'];
$f = 0;

$sql=" SELECT * FROM transaction_tbl WHERE uid = '$uid' ";
$res=mysqli_query($con,$sql);
if(mysqli_num_rows($res) > 0)
{
	$f = 1;
	while($row=mysqli_fetch_assoc($res))
	{
		$data['data'][] = $row;
	}
}
else{
	echo "No Transactions ";
}

$wsql=" SELECT * FROM account_tbl WHERE userid = '$uid' ";
$wres=mysqli_query($con,$wsql);
if(mysqli_num_rows($wres) > 0)
{
	$f = 1;
	$wrow=mysqli_fetch_assoc($wres);
	$data['wallet'][] = $wrow;
}
else{
	echo "No wallet";
}

if($f == 1)
	echo json_encode($data);
mysqli_close($con);
?>