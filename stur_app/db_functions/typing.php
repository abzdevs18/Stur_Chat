<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	include '../inc/conn.php';
	$reciever = mysqli_real_escape_string($conn,$_POST['reciever']);
	$sender = mysqli_real_escape_string($conn,$_POST['sender']);
	$msg = array();

	$chec_recv = mysqli_query($conn,"SELECT * FROM `tmp_msgs` WHERE reciever = '$reciever' AND sender = '$sender'");

	if (mysqli_num_rows($chec_recv) > 0) {
			array_push($msg, array("typing"=>1,"p_k"=>$p_k));
	}else{
		$check_typing = mysqli_query($conn,"INSERT INTO `tmp_msgs`(`reciever`, `sender`, `typing`) VALUES ('$reciever','$sender',1)");
		if ($check_typing) {
			$p_k = mysqli_insert_id($conn);
			array_push($msg, array("typing"=>1,"p_k"=>$p_k));
		}
	}
	echo json_encode($msg);
}