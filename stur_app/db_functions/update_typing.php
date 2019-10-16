<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	include '../inc/conn.php';
	$p_k = mysqli_real_escape_string($conn,$_POST['p_k']);
	$msg = array();

	$chec_recv = mysqli_query($conn,"DELETE FROM `tmp_msgs` WHERE p_k = '$p_k'");
	if ($chec_recv) {
			array_push($msg, array("typing"=>0));
	}else{
			array_push($msg, array("typing"=>mysqli_error($conn)));
	}

	echo json_encode($msg);
}