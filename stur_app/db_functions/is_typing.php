<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	include '../inc/conn.php';
	$reciever = mysqli_real_escape_string($conn,$_POST['reciever']);
	$sender = mysqli_real_escape_string($conn,$_POST['sender']);
	$msg = array();

	$chec_recv = mysqli_query($conn,"SELECT * FROM `tmp_msgs` WHERE reciever = '$reciever' AND sender = '$sender'");

	if (mysqli_num_rows($chec_recv) > 0) {
		$fetch = mysqli_fetch_assoc($chec_recv);
		$p_k = $fetch['p_k'];
			array_push($msg, array("typing"=>1,"reciever"=>$reciever,"sender"=>$sender,"p_k"=>$p_k));
	}else{
			array_push($msg, array("typing"=>0,"reciever"=>$reciever,"sender"=>$sender));

	}
	echo json_encode($msg);
}?>