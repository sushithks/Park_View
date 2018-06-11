<%@include file="admin_header.jsp" %>
<style>
    .banner-dott:nth-child(0){
        height: 0px;
    }
</style>
<div class="banner">
    <div class="banner-dott">
        <div class="container">
            <!-- navigation -->

            <div class="banner-header">
                <div class="slider">
                    <div class="callbacks_container">
                        <ul class="rslides callbacks callbacks1" id="slider4">
                            <li>	
                                <div class="banner_text">
                                    <p>Parking Where & When You Need It With Directions Directly To Your Spac</p>
                                    <h3>Park Smarter </h3>
                                </div>	
                            </li>
                            <li>	
                                <div class="banner_text">
                                    <p> Find & Book For The Perfect Spot</p>
                                    <h3>PARK SMARTLY </h3>		
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
<%@include file="admin_footer.jsp" %>