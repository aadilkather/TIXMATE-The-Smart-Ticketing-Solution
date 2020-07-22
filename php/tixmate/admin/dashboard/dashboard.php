<?php
error_reporting(0);
$status=$_REQUEST['status'];
if ($status == "logout")
{
    session_start();
    session_unset();
    session_destroy();
	header("location:../login/login.php");
}
?>
<?php
include("../common/menu.php");
	
?>


    <style>
#right
{
	
float:right;	
color:#333;
font-size:12px;
}
</style>

<style>
#right
{
	
float:right;	
color:#333;
font-size:12px;
}
.userd
{
color:#333;	
}
.red
{
background:#FFECF4;
padding:10px;	
}


#right
{
	
float:right;	
color:#333;
font-size:12px;
}
.userd
{
color:#333;	
}
.red
{
background:#F36;
padding:10px;	
}
.table
{
margin-bottom:10px;
background:#E6F4FF;	
}
.sep
{
height:30px;
background:#666;	
}
</style>
       


        <div class="content" >
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <!-- <div class="card"> -->

                            <div class="content all-icons">
                                <div class="row" style=" /*background-image: url(boutique.jpg);*/ ">
                                                            
                                  <div class="font-icon-list col-lg-2 col-md-3 col-sm-4 col-xs-6 col-xs-6" >
                                <a href="../tbl_user/select.php">    <div class="font-icon-detail" style=" background-color: #fff"><i class="pe-7s-users"></i>
                                      <input type="text" value="User Details">
                                    </div></a>
                                  </div>
                                                            
                                  <div class="font-icon-list col-lg-2 col-md-3 col-sm-4 col-xs-6 col-xs-6">
                                <a href="../tbl_bus/select.php">    <div class="font-icon-detail" style=" background-color: #fff"><i class="pe-7s-car"></i>
                                      <input type="text" value="Bus Details">
                                    </div></a>
                                  </div>
                                                            
                                  <div class="font-icon-list col-lg-2 col-md-3 col-sm-4 col-xs-6 col-xs-6">
                                <a href="../tbl_bus_data/select.php">    <div class="font-icon-detail" style=" background-color: #fff"><i class="pe-7s-server"></i>
                                      <input type="text" value="Bus Data">
                                    </div></a>
                                  </div>

                                  <div class="font-icon-list col-lg-2 col-md-3 col-sm-4 col-xs-6 col-xs-6">
                                <a href="../tbl_routes/select.php">    <div class="font-icon-detail" style=" background-color: #fff"><i class="pe-7s-way" ></i>
                                      <input type="text" value="Route Details">
                                    </div></a>
                                  </div>

                                  <div class="font-icon-list col-lg-2 col-md-3 col-sm-4 col-xs-6 col-xs-6">
                                <a href="../tbl_stops/select.php">    <div class="font-icon-detail" style=" background-color: #fff"><i class="pe-7s-map-2"></i>
                                      <input type="text" value="Bus Stops">
                                    </div></a>
                                  </div>

                                  <div class="font-icon-list col-lg-2 col-md-3 col-sm-4 col-xs-6 col-xs-6">
                                <a href="../tbl_bookings/select.php">    <div class="font-icon-detail" style=" background-color: #fff"><i class="pe-7s-ticket"></i>
                                      <input type="text" value="Bookings">
                                    </div></a>
                                  </div>


                                  <div class="font-icon-list col-lg-2 col-md-3 col-sm-4 col-xs-6 col-xs-6">
                                <a href="../tbl_account/select.php">    <div class="font-icon-detail" style=" background-color: #fff"><i class="pe-7s-wallet"></i>
                                      <input type="text" value="Account Details">
                                    </div></a>
                                  </div>   


                                  <div class="font-icon-list col-lg-2 col-md-3 col-sm-4 col-xs-6 col-xs-6">
                                <a href="../tbl_transaction/select.php">    <div class="font-icon-detail" style=" background-color: #fff"><i class="pe-7s-cash"></i>
                                      <input type="text" value="Transaction Details">
                                    </div></a>
                                  </div>   
        
                                </div>

                            </div>
                       <!--  </div> -->
                    </div>

                </div>
                
            </div>
        </div>
    </div>
</div>


</body>

    <!--   Core JS Files   -->
    <script src="../assets/js/jquery-1.10.2.js" type="text/javascript"></script>
	<script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>

	<!--  Checkbox, Radio & Switch Plugins -->
	<script src="../assets/js/bootstrap-checkbox-radio-switch.js"></script>

	<!--  Charts Plugin -->
	<script src="../assets/js/chartist.min.js"></script>

    <!--  Notifications Plugin    -->
    <script src="../assets/js/bootstrap-notify.js"></script>

    <!--  Google Maps Plugin    -->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>

    <!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
	<script src="../assets/js/light-bootstrap-dashboard.js"></script>

	<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
	<script src="../assets/js/demo.js"></script>

	

</html>
