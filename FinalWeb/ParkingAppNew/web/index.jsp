
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Car Parking| Index</title>
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
                            <a class="navbar-brand" href="index.html"><h1>CAR PARKING</h1></a>

                        </div>
                        <div class="collapse navbar-collapse" id="myNavbar">
                            <ul class="nav navbar-nav link-effect-14" id="link-effect-14">
                                <li><a href="#"><span>Home</span></a></li>
                                <li><a href="#about" class="scroll"><span>About us</span></a></li>
                                <li><a href="#services" class="scroll"><span>Services</span></a></li>
                                <li><a href="#gallery" class="scroll"><span>Gallery</span></a></li>
                                <li><a href="#contact" class="scroll"><span>Login</span></a></li>
                            </ul>
<!--                            <form class="navbar-form navbar-left w3_search" action="#" method="post">
                                <div class="form-group">
                                    <input type="search" class="form-control" placeholder="Search" required="">
                                </div>
                                <button type="submit" class="btn btn-default"><i class="fa fa-search" aria-hidden="true"></i></button>
                            </form>-->
                        </div>
                    </nav>	
                    <div class="banner-header">
                        <div class="slider">
                            <div class="callbacks_container">
                                <ul class="rslides callbacks callbacks1" id="slider4">
                                    <li>	
                                        <div class="banner_text">
                                            <p>Parking Where & When You Need It With Directions Directly To Your Space</p>
                                            <h3>PARK SMARTLY</h3>
                                        </div>	
                                    </li>
                                    <li>	
                                        <div class="banner_text">
                                            <p> Find & Book For The Perfect Spot</p>
                                            <h3>Park Smarter </h3>		
                                        </div>
                                    </li>	
                                </ul>
                            </div>

                            <script>
                                // You can also use "$(window).load(function() {"
                                $(function () {
                                    // Slideshow 4
                                    $("#slider4").responsiveSlides({
                                        auto: true,
                                        pager: true,
                                        nav: true,
                                        speed: 500,
                                        namespace: "callbacks",
                                        before: function () {
                                            $('.events').append("<li>before event fired.</li>");
                                        },
                                        after: function () {
                                            $('.events').append("<li>after event fired.</li>");
                                        }
                                    });
                                });
                            </script>
                            <!-- //banner Slider -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- //navigation -->
        <!-- //banner -->
        <!-- services -->
        <div class="services" id="services">
            <div class="container">
                <div class="w3l_heading">
                    <h3>Services </h3>
                </div>
                <div class="w3-services-grids">
                    <div class="col-md-4 w3services-left">
                        <div class="w3-services-info">
                            <i class="fa fa-cog  w3_icon" aria-hidden="true"></i>
                            <h3>Lorem Ipsum Dolor </h3>
                            <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>
                        </div>
                        <div class="w3-services-info">
                            <i class="fa fa-cogs  w3_icon " aria-hidden="true"></i>
                            <h3>Cras Id Augue Vel Lacus</h3>
                            <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>
                        </div>
                    </div>
                    <div class="col-md-4 w3services-center">
                        <img src="images/services1.jpg" alt="img">
                    </div>
                    <div class="col-md-4 w3services-right">
                        <div class="w3-services-info">
                            <i class="fa fa-wrench  w3_icon " aria-hidden="true"></i>
                            <h3>Nulla Eget Sem Nec Nibh </h3>
                            <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>
                        </div>
                        <div class="w3-services-info">
                            <i class="fa fa-tasks  w3_icon " aria-hidden="true"></i>
                            <h3> Augue Vel Egestas carse </h3>
                            <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
        <!-- //services -->
        <!-- about -->
        <div class="about" id="about">
            <div class="container">
                <div class="w3l_heading">
                    <h3>About Us </h3>
                </div>
                <div class="w3ls_banner_bottom_grids">
                    <div class="col-md-6 w3_agileits_about_grid_left">
                        <p>Duis turpis arcu, dictum eu tincidunt id, congue vel urna. Quisque posuere, 
                            ipsum eu faucibus cursus, ex tortor elementum leo, eget varius lorem quam a nisl. 
                            Mauris ut enim sed tortor auctor luctus at vitae est. <i>Duis dignissim auctor rhoncus. 
                                Curabitur diam lorem, ultricies eu pellentesque sed, elementum sodales urna. 
                                Pellentesque molestie maximus nisl at ultrices.</i>
                        </p>
                        <ul>
                            <li><i class="fa fa-long-arrow-right" aria-hidden="true"></i>Marketing</li>
                            <li><i class="fa fa-long-arrow-right" aria-hidden="true"></i>Professional approach</li>
                            <li><i class="fa fa-long-arrow-right" aria-hidden="true"></i>Production</li>
                            <li><i class="fa fa-long-arrow-right" aria-hidden="true"></i>Effective Solutions</li>
                        </ul>
                    </div>
                    <div class="col-md-6 w3_agileits_about_grid_right">
                        <iframe src="https://player.vimeo.com/video/117822597">
                        </iframe>
                    </div>
                    <div class="clearfix"> </div>
                </div>
            </div>
        </div>
        <!-- //about -->
        <!-- gallery -->
        <div class="gallery-top" id="gallery">
            <!-- container -->
            <div class="container">
                <div class="w3l_heading">
                    <h3>Our Gallery</h3>
                </div>
                <div class="gallery-grids-top">
                    <div class="gallery-grids">
                        <div class="col-md-6 gallery-grid-top">
                            <div class="gallery-grid-top-img">
                                <a class="example-image-link" href="images/g-top-1.jpg" data-lightbox="example-set" data-title=""><img class="example-image" src="images/g-top-1.jpg" alt=""/></a>
                            </div>
                        </div>
                        <div class="col-md-6 gallery-right">
                            <div class="gallery-right-grid">
                                <div class="col-md-6 gallery-grid-img">
                                    <a class="example-image-link" href="images/g-top-2.jpg" data-lightbox="example-set" data-title=""><img class="example-image" src="images/g-top-2.jpg" alt=""/></a>
                                </div>
                                <div class="col-md-6 gallery-grid-img">
                                    <a class="example-image-link" href="images/g-top-3.jpg" data-lightbox="example-set" data-title=""><img class="example-image" src="images/g-top-3.jpg" alt=""/></a>
                                </div>
                                <div class="clearfix"> </div>
                            </div>
                            <div class="gallery-right-grid gallery-right-top-grid">
                                <div class="col-md-6 gallery-grid-img">
                                    <a class="example-image-link" href="images/g-top-4.jpg" data-lightbox="example-set" data-title=""><img class="example-image" src="images/g-top-4.jpg" alt=""/></a>
                                </div>
                                <div class="col-md-6 gallery-grid-img">
                                    <a class="example-image-link" href="images/g-top-5.jpg" data-lightbox="example-set" data-title=""><img class="example-image" src="images/g-top-5.jpg" alt=""/></a>
                                </div>
                                <div class="clearfix"> </div>
                            </div>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                    <div class="gallery-grids gallery-grids-middle">
                        <div class="col-md-4 gallery-grid middle-gallery-grid">
                            <a class="example-image-link" href="images/g-top-7.jpg" data-lightbox="example-set" data-title=""><img class="example-image" src="images/g-top-7.jpg" alt=""/></a>
                        </div>
                        <div class="col-md-4 gallery-grid middle-gallery-grid">
                            <a class="example-image-link" href="images/g-top-8.jpg" data-lightbox="example-set" data-title=""><img class="example-image" src="images/g-top-8.jpg" alt=""/></a>
                        </div>
                        <div class="col-md-4 gallery-grid middle-gallery-grid">
                            <a class="example-image-link" href="images/g-top-9.jpg" data-lightbox="example-set" data-title=""><img class="example-image" src="images/g-top-9.jpg" alt=""/></a>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                    <div class="gallery-grids">
                        <div class="col-md-3 gallery-grid w3-last-gallery">
                            <a class="example-image-link" href="images/g-top-10.jpg" data-lightbox="example-set" data-title=""><img class="example-image" src="images/g-top-10.jpg" alt=""/></a>
                        </div>
                        <div class="col-md-3 gallery-grid w3-last-gallery">
                            <a class="example-image-link" href="images/g-top-11.jpg" data-lightbox="example-set" data-title=""><img class="example-image" src="images/g-top-11.jpg" alt=""/></a>
                        </div>
                        <div class="col-md-3 gallery-grid w3-last-gallery">
                            <a class="example-image-link" href="images/g-top-12.jpg" data-lightbox="example-set" data-title=""><img class="example-image" src="images/g-top-12.jpg" alt=""/></a>
                        </div>
                        <div class="col-md-3 gallery-grid w3-last-gallery">
                            <a class="example-image-link" href="images/g-top-13.jpg" data-lightbox="example-set" data-title=""><img class="example-image" src="images/g-top-13.jpg" alt=""/></a>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                    <script src="js/lightbox-plus-jquery.min.js"></script>
                </div>
                <div class="clearfix"> </div>
            </div>
            <!-- //container -->
        </div>
        <!-- //gallery -->
        <!-- clients-section -->
        <div class="clients-section">
            <div class="container">
                <div class="w3l_heading">
                    <h3>Our Customers</h3>
                </div>
                <div class="mis-stage"> 
                    <!-- The element to select and apply miSlider to - the class is optional -->
                    <ol class="mis-slider">
                        <!-- The slider element - the class is optional -->
                        <li class="mis-slide"> 
                            <!-- A slide element - the class is optional --> 
                            <a href="#" class="mis-container"> 
                                <!-- A slide container - this element is optional, if absent the plugin adds it automatically -->
                                <figure> 
                                    <!-- Slide content - whatever you want --> 
                                    <img src="images/t4.jpg" alt=" " class="img-responsive" />
                                    <figcaption>Carl Lii<span>Sed maximus eros quis leo congue ipsum sagittis.</span></figcaption>
                                </figure>
                            </a> </li>
                        <li class="mis-slide"> <a href="#" class="mis-container">
                                <figure> <img src="images/t1.jpg" alt=" " class="img-responsive" />
                                    <figcaption>Michael Paul<span>Sed maximus eros quis leo congue ipsum sagittis.</span></figcaption>
                                </figure>
                            </a> </li>
                        <li class="mis-slide"> <a href="link" class="mis-container">
                                <figure> <img src="images/t2.jpg" alt=" " class="img-responsive" />
                                    <figcaption>Henry Doe<span>Sed maximus eros quis leo congue ipsum sagittis.</span></figcaption>
                                </figure>
                            </a> </li>
                        <li class="mis-slide"> <a href="#" class="mis-container">
                                <figure> <img src="images/t3.jpg" alt=" " class="img-responsive" />
                                    <figcaption>Laura James<span>Sed maximus eros quis leo congue ipsum sagittis.</span></figcaption>
                                </figure>
                            </a> </li>
                        <li class="mis-slide"> <a href="#" class="mis-container">
                                <figure> <img src="images/t4.jpg" alt=" " class="img-responsive" />
                                    <figcaption>Thomas Carl<span>Sed maximus eros quis leo congue ipsum sagittis.</span></figcaption>
                                </figure>
                            </a> </li>
                        <li class="mis-slide"> <a href="#" class="mis-container">
                                <figure> <img src="images/t5.jpg" alt=" " class="img-responsive" />
                                    <figcaption>Rosy Crisp<span>Sed maximus eros quis leo congue ipsum sagittis.</span></figcaption>
                                </figure>
                            </a> </li>
                    </ol>
                </div>
            </div>
            <script src="js/mislider.js" type="text/javascript"></script>
            <script type="text/javascript">
                                                jQuery(function ($) {
                                                    var slider = $('.mis-stage').miSlider({
                                                        //  The height of the stage in px. Options: false or positive integer. false = height is calculated using maximum slide heights. Default: false
                                                        stageHeight: 380,
                                                        //  Number of slides visible at one time. Options: false or positive integer. false = Fit as many as possible.  Default: 1
                                                        slidesOnStage: false,
                                                        //  The location of the current slide on the stage. Options: 'left', 'right', 'center'. Defualt: 'left'
                                                        slidePosition: 'center',
                                                        //  The slide to start on. Options: 'beg', 'mid', 'end' or slide number starting at 1 - '1','2','3', etc. Defualt: 'beg'
                                                        slideStart: 'mid',
                                                        //  The relative percentage scaling factor of the current slide - other slides are scaled down. Options: positive number 100 or higher. 100 = No scaling. Defualt: 100
                                                        slideScaling: 150,
                                                        //  The vertical offset of the slide center as a percentage of slide height. Options:  positive or negative number. Neg value = up. Pos value = down. 0 = No offset. Default: 0
                                                        offsetV: -5,
                                                        //  Center slide contents vertically - Boolean. Default: false
                                                        centerV: true,
                                                        //  Opacity of the prev and next button navigation when not transitioning. Options: Number between 0 and 1. 0 (transparent) - 1 (opaque). Default: .5
                                                        navButtonsOpacity: 1,
                                                    });
                                                });
            </script>	
        </div>
        <!-- //clients-section -->
        <!-- contact -->
        <div class="contact" id="contact">
            <div class="container">
                <div class="w3l_heading">
                    <h3>Login</h3>
                </div>
                <div class="w3agile_contact_grid">
                    <div class="col-md-6 w3agile_contact_left">
                        <h2>Contact Info</h2>
                        <ul class="agileinfo_contact_grid_list">
                            <li><i class="fa fa-map-marker contact_icons" aria-hidden="true"></i>1234k Avenue, 4th block, <span>New York City.</span></li>
                            <li><i class="fa fa-envelope-o contact_icons" aria-hidden="true"></i><a href="mailto:info@example.com">info@example.com</a></li>
                            <li><i class="fa fa-phone contact_icons" aria-hidden="true"></i>+1234 567 567</li>
                        </ul>
                    </div>
                    <div class="col-md-6 w3agile_contact_right">
                        <%@include file="login.jsp" %>
                    </div>
                    <div class="clearfix"> </div>
                </div>
            </div>
        </div>
        <!-- //contact -->
        <!--google map section ends here-->
        <div class="agileits-w3layouts-map wow zoomIn animated" data-wow-delay=".5s">
            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3023.9503398796587!2d-73.9940307!3d40.719109700000004!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x89c25a27e2f24131%3A0x64ffc98d24069f02!2sCANADA!5e0!3m2!1sen!2sin!4v1441710758555" allowfullscreen></iframe>
        </div>
        <!--/google map section ends here-->
        <div class="footer-final">
            <div class="copyw3-agile"> 
                <div class="container">
                    <p>&copy; 2017 Car Parking. All rights reserved | Design by <a href="#">Car Parking</a></p>
                </div>
            </div>
        </div>
        <script src="js/SmoothScroll.min.js"></script>
        <script type="text/javascript" src="js/move-top.js"></script>

        <script type="text/javascript" src="js/easing.js"></script>
        <!-- here stars scrolling icon -->
        <script type="text/javascript">
                                                            $(document).ready(function () {
                                                                /*
                                                                 var defaults = {
                                                                 containerID: 'toTop', // fading element id
                                                                 containerHoverID: 'toTopHover', // fading element hover id
                                                                 scrollSpeed: 1200,
                                                                 easingType: 'linear' 
                                                                 };
                                                                 */

                                                                $().UItoTop({easingType: 'easeOutQuart'});

                                                            });
        </script>
        <!-- //here ends scrolling icon -->
        <script src="js/responsiveslides.min.js"></script>
    </body>
</html>
