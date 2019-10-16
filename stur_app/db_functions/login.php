<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	include '../inc/conn.php';

	$email =  mysqli_real_escape_string($conn,$_POST['email']);
	$password = mysqli_real_escape_string($conn,$_POST['password']);

	$response = array();

	$user_check = mysqli_query($conn,"SELECT * FROM email_address WHERE email_add = '$email'");
	if (mysqli_num_rows($user_check) > 0) {
		$id = mysqli_fetch_assoc($user_check);
		$p_k = $id['p_k'];
		$verify_pass = mysqli_fetch_assoc(mysqli_query($conn,"SELECT password FROM users WHERE p_k = '$p_k'"));
		$check_pass_result = password_verify($password, $verify_pass['password']);
		if ($check_pass_result == true) {
			array_push($response, array("success_login"=>1,"email"=>$email,"p_k"=>$p_k));
		}else{
			array_push($response,array("success_login"=>0));
		}
	}else{
			array_push($response,array("success_login"=>0));
		}

	echo json_encode($response);

}