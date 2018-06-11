

<!--paste your code here for header-->
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Car Parking| AdminHome</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta charset="utf-8">
        <meta name="keywords" content="Car Parking" />
        <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>

        <link href="css/font-awesome.css" rel="stylesheet" /><!-- font-awesome css -->
        <!-- bootstrap css -->
        <link href="css/bootstrap.css" rel="stylesheet" />
        <!-- //bootstrap css -->
        <!-- style css -->
        <link href="css/style.css" rel="stylesheet" />
        <!-- //style css -->
        <!-- //lightbox files -->
        <link rel="stylesheet" href="css/lightbox.css">
        <!-- //lightbox files -->
        <!-- js -->
        <script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
        <!-- //js -->
        <link href="css/mislider.css" rel="stylesheet" type="text/css" />
        <link href="css/mislider-custom.css" rel="stylesheet" type="text/css" />
        <!-- bootstrap js -->
        <script src="js/bootstrap.js"></script>
        <!-- bootstrap js -->
        <script type="text/javascript">
            jQuery(document).ready(function ($) {
                $(".scroll").click(function (event) {
                    event.preventDefault();
                    $('html,body').animate({scrollTop: $(this.hash).offset().top}, 1000);
                });
            });
        </script>

    </head>
    <body>
        <!-- banner -->
        <div class="banner">
            <div class="banner-dott">
                <div class="container">
                    <!-- navigation -->
                    <nav class="navbar navbar-inverse">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>                        
                            </button>
                            <a class="navbar-brand" href="index.jsp"><h1>CAR PARKING</h1></a>

                        </div>
                        <div class="collapse navbar-collapse" id="myNavbar">
                            <ul class="nav navbar-nav link-effect-14" id="link-effect-14">
                                <li><a href="admin_home.jsp"><span>Home</span></a></li>
                                <li><a href="ViewUserServlet" ><span>View User</span></a></li>
                                <li><a href="ViewBookingServlet"><span>Booking</span></a></li>
                                <li><a href="AdminViewAreaRequestServlet"><span>View Requests</span></a></li>        
                                <li><a href="ViewFeedBackServlet"><span>View Feedback</span></a></li>        
                                <li><a href="LogoutServlet?page=index.jsp"><span>Logout</span></a></li>
                            </ul>
                            
                        </div>
                    </nav>	

                </div>
            </div>
        </div>