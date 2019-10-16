<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	include '../inc/conn.php';
	// $p_k = 9;
	$p_k = mysqli_real_escape_string($conn,$_POST['p_k']);

	$target = "../uploaded_prof_pic/".basename($_FILES['photo']['name']);
	/*Fron the interface in the app*/
	$image = $_FILES['photo']['name'];
	/*Response Array*/

	$response = array();

	if (move_uploaded_file($_FILES["photo"]["tmp_name"], $target)) {

		$i_m = "SELECT * FROM profile_images WHERE user_pk = '$p_k' AND status = 1";
		$i_m_query = mysqli_query($conn, $i_m);
		$result = mysqli_num_rows($i_m_query);
		if ($result < 1) {
			$queryImage = "INSERT INTO `profile_images`(`user_pk`, `image_path`, `status`) VALUES ('$p_k','$image',1)";
			$queryUpdate = mysqli_query($conn,$queryImage);
			if ($queryUpdate) {
				array_push($response, array("success"=>1));
			}else {
				array_push($response, array("error"=>mysqli_error($conn)));
			}
		}else {
			if ($rows = mysqli_fetch_assoc($i_m_query)) {
				$image_name = $rows['image_path'];
				// Don't remove the photo from the DB. just update the value of profile_status into 0
				$u_p = "UPDATE `profile_images` SET status = 0 WHERE `image_path` = '$image_name'";
				$u_p_query = mysqli_query($conn, $u_p);
				if ($u_p_query) {
					// Serve us the update command for the profile_status
					$queryImage = "INSERT INTO `profile_images`(`user_pk`, `image_path`, `status`) VALUES ('$p_k','$image',1)";
					$queryUpdate = mysqli_query($conn,$queryImage);
					if ($queryUpdate) {
						array_push($response, array("success"=>1));
					}else {
						array_push($response, array("error"=>mysqli_error($conn)));
					}
				}else {
					array_push($response, array("error"=>mysqli_error($conn)));
				}
			}
		}
    }else {
		array_push($response, array("error"=>mysqli_error($conn)));
	}
	echo json_encode($response);
}
