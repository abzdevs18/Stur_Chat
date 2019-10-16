<?php
define("user_db", "root");
define("password", "");
define("database_name", "stur_app");
define("server_name", "localhost");

$GLOBALS['ip_add'] = "192.168.0.35";

$conn = new mysqli(server_name,user_db,password,database_name);