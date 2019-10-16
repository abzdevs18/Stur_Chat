<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	include '../inc/conn.php';
	$reciever = mysqli_real_escape_string($conn,$_POST['reciever']);
	$sender = mysqli_real_escape_string($conn,$_POST['sender']);
	$msg = array();

	// $msgs = mysqli_query($conn,"SELECT * FROM message WHERE (reciever = '$reciever' AND sender = '$sender') OR (sender = '$reciever' AND reciever = '$sender') ORDER BY timestamp DESC");
	$msgs = mysqli_query($conn,"SELECT message.reciever AS reciever, message.sender AS sender,message.content AS content, message.time AS time, profile_images.image_path AS senderImg FROM message LEFT JOIN profile_images ON profile_images.user_pk = message.sender WHERE (message.reciever = '$reciever' AND message.sender = '$sender') OR (message.sender = '$reciever' AND message.reciever = '$sender') ORDER BY timestamp DESC");
	while ($row = mysqli_fetch_assoc($msgs)) {
		array_push($msg, array("reciever"=>$row['reciever'],"sender"=>$row['sender'],"content"=>$row['content'],"time"=>$row['time'],"senderImg"=>"http://'".$ip_add ."'/stur_app/uploaded_prof_pic/".$row['senderImg']));
	}
	echo json_encode($msg);
}