$(document).ready(function () {
    //seed creation
    /*$("[type=submit]").mousedown(function(){
     var r= Math.random();
     if(!$(this).parents("form").children("input").is(":input[name=seed]")){
     $(this).parents("form").append("<input type=hidden name=seed value="+r+"/>");
     }else{
     $("[name=seed]").attr("value",r);
     }
     })*/
    var selects = document.querySelectorAll('[select]');
    if (selects != null) {
        for (var i = 0; i < selects.length; i++) {
            var v = selects[i].getAttribute("select") != null ? selects[i].getAttribute("select") : '';
            var o = selects[i].querySelectorAll('option');
            for (var j = 0; j < o.length; j++) {
                if (o[j].value == v) {

                    o[j].setAttribute('selected', 'true');
                }

            }
        }
    }
    //page forwarder
    var url = $("[forwards]").attr("forwards");
    //alert(url);
    if (url != undefined && url != '') {

        $.get(url, function (result, status) {

            if (result != "") {
                location.href = result;
            }
        });
    }

    //change selector values to another
    $('[remote]').change(function () {
        var obj = $(this);
        var url = obj.attr("remote") + "?query=" + obj.val();
        var next = obj.attr("target");
        var sep = obj.attr("seperator");
        if (sep == null) {
            sep = ",";
        }
        var nextobj = $("#" + next);
        $.get(url, function (result, status) {
            var arr = result.split(sep);
            nextobj.html("");
            var str = "";
            for (var i = 0; i < arr.length; i++) {
                str += "<option> " + arr[i] + "</option>";
            }
            nextobj.html(str);
        });
    });
    //validation
    var status = [true];
    $("[type=submit]").click(function () {
        status = [true];
        var outerstatus = [true];
        var formVal = $(this).closest("form");
        var objects = formVal.find(":input[req]");
        $(objects).each(function (i, r) {

            var attrib = $(this).attr("req");
            if ($(this).is(":input")) {
                // alert(attrib)
                var len = $(this).val().length;

                var errMsg = $(this).attr('msg') == null ? "Please check this field" : $(this).attr('msg');

                var min = parseInt($(this).attr('minm') == null ? 1 : $(this).attr('minm'));

                var max = parseInt($(this).attr('maxm') == null ? 1 : $(this).attr('maxm'));

                if (len == 0) {

                    error($(this), errMsg);

                    status.push(false);

                } else if (len < min) {
                    error($(this), "Minimum " + min + " value required");
                    status.push(false);

                } else {
                    error($(this), "");
                    var r = checkEmail($(this))
                    status.push(r);
                }

            } else if ($(this).is(":select")) {
                if ($(this)[0].selectedIndex == 0) {
                    error($(this), errMsg);
                    status.push(false);
                } else {
                    error($(this), "");

                }
            } else {
            }
        });

        var s = status.indexOf(false, 0) > 0 ? false : true;
        return s;
    })
    //====================keybounce===================
    $("[req]").blur(function () {
        var errMsg = $(this).attr('msg') == null ? "Please check this field" : $(this).attr('msg');
        if ($(this).val() == '') {
            error($(this), errMsg);
        }
    });
    $('[req=text]').keydown(function () {
        var e = arguments[0];
        var code = (e.keyCode ? e.keyCode : e.which);
        var regex = /[a-z|A-Z]/i;
        var charCode = regex.test(String.fromCharCode(code));
        if (47 < code && code < 58 || 95 < code && code < 106) {
            error($(this), "Please enter alphabets");
            return false;
        } else {
            return maxmin($(this), null);
        }
    });
    $('[req=number]').keydown(function () {
        var e = arguments[0];
        var code = (e.keyCode ? e.keyCode : e.which);
        var regex = /[0-9]/i;
        var charCode = regex.test(String.fromCharCode(code));
        if (code > 30) {
            if (charCode || (code > 95 && code < 106)) {
                if (code != 8) {
                    return maxmin($(this), null);
                }
            } else if (code != 8 && code != 9) {
                error($(this), "Please enter digits");
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    });

    function maxmin(obj, msg) {
        var max = parseInt(obj.attr('maxm'));
        var s;
        if (obj.val().length + 1 > max) {
            s = msg == null ? "charecter allowed" : msg;
            error(obj, "Maximum " + max + " " + s);
            return false;
        } else {
            var min = parseInt(obj.attr('minm'));
            if (obj.val().length + 1 < min) {
                s = msg == null ? " charecter required" : msg;
                error(obj, "Minimum " + min + " " + s);
                return true;
            } else {
                error(obj, "");
                return true;
            }
        }
    }
    //mail checking...
    $('[req=email]').blur(function () {
        checkEmail($(this));
    });
    function checkEmail(obj) {
        var attrib = obj.attr("req");
        if (attrib != null && attrib == 'email') {
            var regex = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{1,3})$/;
            if (regex.test(obj.val()) && obj.val().length > 2) {
                error(obj, "");
                return true;
            } else {
                error(obj, "Please enter valid email");
                return false;
            }
        }

    }
    //file checking
    $('input[req=file]').change(function (e) {
        var isFormat = true;
        var vals = $(this).val();
        vals = vals.substring(vals.indexOf(".") + 1, vals.length);
        var format = $(this).attr('format');

        if (format != null) {
            var fmt = format.split(",");
            for (var i = 0; i < fmt.length; i++) {
                if (fmt[i] == vals) {
                    isFormat = false;
                }
            }
            if (!isFormat) {
                var tg = $(this).attr("target");
                if (tg != null) {
                    $("#" + tg).attr("src", URL.createObjectURL(e.target.files[0]));
                }
            }
            if (isFormat) {
                $(this).val("");
                error($(this), "Please select " + format + " format");
            }
        }
    });
    //confirm password chcking
    $('[req=confirm]').blur(function () {
        if ($(this).val() == ($('[req=password]').val())) {
            error($(this), "");
        } else {
            error($(this), "Password mismatch");
        }
    });
    //remote value gathering
    $('[remote]').blur(function () {
        if ($(this).is("input")) {
            var obj = $(this);
            var url = obj.attr("remote") + "?query=" + obj.val();
            $.get(url, function (result, status) {
                error(obj, result);
            });
        }

    });
    //=====================error messsage

    function error(element, msg) {
        if (msg == '') {
            element.css({
                'box-shadow': '0 0 1px 1px green'
            });
        } else {
            element.css({
                'box-shadow': '0 0 1px 1px #CC6565'
            });
        }
        msg = msg == undefined ? "Please fill this field" : msg;
        if (element.next().is(".error")) {
            element.next().text(msg);
        } else {
            $obj = $("document").add("<err class=error ></err>");
            element.after($obj);
            element.next().text(msg).css({
                'color': '#CC6565'
            });//text-transform:lowercase;
        }
        //element.next(".error").fadeOut().stop().fadeIn(200);

    }
    $("[showhide]").slideDown(0, function () {
        $(this).slideUp(6000).css('color', 'black');
    });




});
function getDate(format) {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd
    }
    if (mm < 10) {
        mm = '0' + mm
    }
    var today = dd + '/' + mm + '/' + yyyy;
    if (format.indexOf("Y") == 0 && format.indexOf("d") > format.length - 6) {
        today = yyyy + '-' + mm + '-' + dd;
    } else if (format.indexOf("Y") == 0 && format.indexOf("M") > format.length - 6) {
        today = yyyy + '-' + dd + '-' + mm;
    } else if (format.indexOf("d") == 0 && format.indexOf("Y") > format.length - 6) {
        today = dd + '-' + mm + '-' + yyyy;
    }
    return today;
}
   