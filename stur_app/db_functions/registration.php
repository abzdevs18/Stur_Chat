<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	include '../inc/conn.php';
	$username = mysqli_real_escape_string($conn,$_POST['username']);
	$lastname = mysqli_real_escape_string($conn,$_POST['lastname']);
	$email = mysqli_real_escape_string($conn,$_POST['email']);
	$password = mysqli_real_escape_string($conn,$_POST['password']);
	$response = array();

	if (preg_match("/^[a-zA-Z]*$/", $username)) {
		if (!filter_var($email,FILTER_VALIDATE_EMAIL)) {
			/*make sure email is valid
			error json reporting*/
		}else{
			$email_check = mysqli_query($conn,"SELECT email_add FROM email_address WHERE email_add = '$email'");
			$row = mysqli_num_rows($email_check);
			if ($row < 0) {
				/*email taken
				error json email taken reporting*/
			}else{
				$password_encrypt = password_hash($password, PASSWORD_DEFAULT);
				$user = mysqli_query($conn,"INSERT INTO users(`username`, `lastname`, `password`) VALUES('$username','$lastname','$password_encrypt')");
				if ($user) {
					$fk = mysqli_insert_id($conn);
					$email_insert = mysqli_query($conn,"INSERT INTO email_address (`user_pk`, `email_add`, `status`) VALUES('$fk','$email',1)");
					if ($email_insert) {
						/*JSON success notification*/
						array_push($response, array("success"=>1));
					}else{
						/*JSON error notificaiton*/
					}
				}
			}
		}
	}else{
		/*JSON error only letters
		are allowed in username*/
	}
	echo json_encode($response);
}