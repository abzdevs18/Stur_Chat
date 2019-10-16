<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	include '../inc/conn.php';
	date_default_timezone_set("Asia/Manila");
	$sender = mysqli_real_escape_string($conn,$_POST['sender']);
	$receiver = mysqli_real_escape_string($conn,$_POST['receiver']);
	$message = mysqli_real_escape_string($conn,$_POST['message']);
	$date = date("F j, Y");
	$time = date("g:i a");

	$response = array();

	$message = mysqli_query($conn,"INSERT INTO `message`(`reciever`, `sender`, `content`, `date`, `time`) VALUES ('$receiver','$sender','$message','$date','$time')");
	if ($message) {
		array_push($response, array("code"=>1));
	}else{
		array_push($response,array("code"=>0));
	}
	echo json_encode($response);
}