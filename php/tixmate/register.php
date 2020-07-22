<?php

include("connection.php");

$fname = $_POST['fname'];
$lname = $_POST['lname'];
$phone = $_POST['phone'];
$email = $_POST['email'];
$username = $_POST['username'];
$password = $_POST['password'];

$sql = " INSERT INTO customer(username, password, name, Lname, phone_no, email) VALUES ('$username','$password','$fname','$lname','$phone','$email') ";

if(mysqli_query($con,$sql))
	echo "success";
else
	echo "Failed";



?>