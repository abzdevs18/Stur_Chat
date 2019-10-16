<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	include '../inc/conn.php';
	$p_k = mysqli_real_escape_string($conn,$_POST['p_k']);

	$user = array();

	$user_q = mysqli_query($conn,"SELECT users.p_k AS p_k,users.username AS username,users.lastname AS lastname, email_address.email_add AS email,profile_images.image_path AS image FROM users LEFT JOIN email_address ON email_address.user_pk = users.p_k LEFT JOIN profile_images ON profile_images.user_pk = users.p_k AND profile_images.status = 1 WHERE users.p_k = '$p_k'");

	if ($user_q) {
		$row = mysqli_fetch_assoc($user_q);
		$image_path = "";
		if ($row['image'] == "") {
			$image_path = "http://'".$ip_add ."'/stur_app/uploaded_prof_pic/default/user_default.png";
		}else{
			$image_path = "http://'".$ip_add ."'/stur_app/uploaded_prof_pic/".$row['image']."";
		}

		array_push($user,array("p_k"=>$row['p_k'],"username"=>$row['username'],"lastname"=>$row['lastname'],"email"=>$row['email'],"image"=>$image_path));
	}
	echo json_encode($user);
}