<%-- 
    Document   : game
    Created on : Mar 20, 2016, 10:13:23 PM
    Author     : Tanika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alien Invader</title>
        <style>
            #tblAliens {
                position: absolute;
                top: 10px;
                left: 200px;                  
            }
            #tblAliens tr td {
                padding: 5px;                  
            } 
            #tblAliens td {
                width: 50px;                  
            }             
            #ship {
                position: absolute;
                top: 10px;
                left: 200px;                  
            }
            #shot {
                position: absolute;
                top: 10px;
                left: 200px;                  
            }
        </style>
        <script src="js/jquery-2.2.3.min.js" type="text/javascript"></script>
        <script>
            var docWidth;
            var docHeight;
            var ALIENROWS = 3;
            var ALIENCOLS = 10;
            var TOP_MARGIN = 10;
            var LEFT_MARGIN = 10;
            var SHOT_HEIGHT = 23;
            var SHOT_WIDTH = 12;
            var SHIP_HEIGHT = 23;
            var SHIP_WIDTH = 33;
            var RIGHT = "RIGHT";
            var LEFT = "LEFT";
            var DOWN = 10;
            var ACROSS = 10;

            var userID = <%= request.getParameter("userID")%>;
            userID = 45;
            //console.log (userID);
            var gameID = createUUID();
            var bulletIdSeed = 1;

            var direction = RIGHT;
            var alienList = [];
            var bulletClip = [];
            var timer;

            var intervalAlients = null;
            $tblAliens = null;
            $ship = null;
            var gameOn = true;
            if (userID == null) {
               alert ("You must use index.jsp to start the game.");
                //
            }
                            $( window ).unload(function() {
                    return "You must use index.jsp to start the game.";
                });
            $(document).ready(function () {
                docHeight = $(document).height();
                docWidth = $(document).width();
                $(window).resize(function () {
                    docHeight = $(document).height();
                    docWidth = $(document).width();
                    drawShip();
                });
                $(document).keydown(function (evnt) {
                    if (evnt.keyCode == 32) {
                        if (gameOn == true)
                            createBullet();
                        else
                            document.getElementById("wonMsg").style.display = "show";
                    } else if (evnt.keyCode == 37) {
                        moveShip(LEFT);
                    } else if (evnt.keyCode == 39) {
                        moveShip(RIGHT);
                    }
                });
                initElements();
            });

            function initElements() {
                $tblAliens = $('#tblAliens');
                drawAliens();
                $tblAliens.css("top", TOP_MARGIN + "px");
                $tblAliens.css("left", LEFT_MARGIN + "px");
                drawShip();
                //createBullet();
                intervalAliens = setInterval(moveAliens, 300);
            }

            function drawShip() {
                $ship = $('#ship');
                $ship.css("top", ((docHeight - $ship.height() - TOP_MARGIN)) + "px");
                $ship.css("left", (docWidth / 2 - $ship.width() / 2) + "px");
            }
            function drawAliens() {
                for (var i = 0; i < ALIENROWS; i++) {
                    $tr = $('<tr></tr>');
                    for (var j = 0; j < ALIENCOLS; j++) {
                        $td = $('<td></td>');
                        $alien = $('<img>');
                        $alien.attr('src', 'images/alien.gif');
                        alienList.push($alien);
                        $td.append($alien);
                        $tr.append($td);
                    }
                    $tblAliens.append($tr);
                }
                $('body').append($tblAliens);
            }

            function moveAliens() {
                var pos = $tblAliens.position();

                if (direction == RIGHT) {
                    if ((pos.left + $tblAliens.width()) >= (docWidth - LEFT_MARGIN - 45)) {
                        $tblAliens.css("top", (pos.top + DOWN) + "px");
                        direction = LEFT;
                    } else {
                        $tblAliens.css("left", (pos.left + ACROSS) + "px");
                    }
                } else if (direction == LEFT) {
                    if (pos.left <= LEFT_MARGIN) {
                        $tblAliens.css("top", (pos.top + DOWN) + "px");
                        direction = RIGHT;
                    } else {
                        $tblAliens.css("left", (pos.left - ACROSS) + "px");
                    }
                }

                for (var i = 0; (i < alienList.length); i++) {
                    if (alienList[i] != null) {
                        $alien = alienList[i];
                        var shipTop = 0;
                        var alienBottom = 0;
                        shipTop = ($ship.offset()).top;
                        alienBottom = (($alien.offset()).top + 25);
                        if (alienBottom.valueOf() >= shipTop.valueOf()) {
                            //console.log("lost");
                            document.getElementById("wonMsg").innerHTML = "<h1>Oops! You've lost the game.</h1>";
                            document.getElementById("wonMsg").style.display = "block";
                            clearInterval(intervalAliens);
                            clearInterval(timer);
                            return false;
                        }
                    }
                }
            }

            function moveShip(direction) {
                var pos = $ship.position();

                if (direction == RIGHT) {
                    if ((pos.left + $ship.width()) <= docWidth - 20) {
                        $ship.css("left", (pos.left + ACROSS) + "px");
                    }
                } else if (direction == LEFT) {
                    if ((pos.left + $ship.width()) >= LEFT_MARGIN) {
                        $ship.css("left", (pos.left - ACROSS) + "px");
                    }
                }
            }

            function createBullet() {
                $shot = $('<img>');
                $shot.attr("src", "images/shot.gif");
                $shot.attr("id", bulletIdSeed);

                bulletIdSeed++

                $shot.css({
                    "position": "absolute",
                    "left": (($ship.position().left
                            + ($ship.width() / 2))
                            - (SHOT_WIDTH / 2))
                            + "px",
                    "top": ($ship.position().top -
                            SHOT_HEIGHT) + "px"
                });
                $('body').append($shot);
                bulletClip.push($shot);
                timer = setInterval(moveBullet, 100);
            }

            function moveBullet() {
                if ((aliensLeft()) == 0) {
                    clearInterval(intervalAliens);
                    clearInterval(timer);
                    gameOn = false;
                    for (var i = 0; i < bulletClip.length; i++) {
                        bulletClip[i] = null;
                    }
                    return;
                }

                for (var i = 0; i < bulletClip.length; i++) {
                    $bullet = bulletClip[i];
                    $bullet.css("top", ($bullet.position().top - 10) + "px");
                }

                if (bulletClip.length > 0) {
                    if (bulletClip[0].offset().top <= 0) {
                        //$bullet = bulletClip[0];
                        bulletClip[0].attr("src", "");
                        bulletClip.shift();
                        //saveScore(-1);
                        //console.log ("bullet wasted: " + bulletClip.length);
                    }
                }

                if (bulletClip.length > 0) {
                    $bullet = bulletClip[0];
                    for (var i = 0; i < alienList.length; i++) {
                        if (alienList[i] != null) {
                            $alien = alienList[i];
                            if ((($bullet.offset().left >= $alien.offset().left) &&
                                    ($bullet.offset().left <= $alien.offset().left + 45))) {

                                if ($bullet.offset().top <= $alien.offset().top) {
                                    alienList[i].attr("src", "");
                                    alienList[i] = null;
                                    bulletClip[0].attr("src", "");
                                    bulletClip.shift();
                                    saveScore(1);
                                    break;
                                }
                            }
                        }
                    }
                }
                if (aliensLeft() == 0) {
                    clearInterval(intervalAliens);
                    clearInterval(timer);
                    displayHighestScore();
                    document.getElementById("wonMsg").style.display = "block";
                    gameOn = false;
                    return;
                } else {
                    clearInterval(timer);
                    //timer = setInterval(moveBullet, bulletClip.length*50);
                    timer = setInterval(moveBullet, 100);
                    //console.log ("clip length" + bulletClip.length + " timer:" + timer.toString());
                }
            }

            function aliensLeft() {
                var aliens = 0;
                for (var i = 0; i < alienList.length; i++) {
                    if (alienList[i] != null) {
                        aliens++;
                    }
                }
                return aliens;
            }


            function saveScore(score) {
                var url = "ws/ws_savescore";
                var params = {
                    "userID": userID,
                    "gameID": gameID,
                    "score": score
                };
                //console.log (params);
                $.post(url, params, function (data) {

                });
            }

            function displayHighestScore() {
                $(document).ready(function () {
                    $.getJSON('ws/ws_readscore', function(data) {
                        var msg = document.getElementById("wonMsg").innerHTML;
                        document.getElementById("wonMsg").innerHTML = "<h1>" + msg + "\n " +
                                data.firstName + " " + data.lastName 
                                + " had the highest score of " + data.highestScore + "</h1>";
                    });
                });
            }
            function createUUID() {
                var d = new Date().getTime();
                if (window.performance && typeof window.performance.now === "function") {
                    d += performance.now(); //use high-precision timer if available
                }
                var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                    var r = (d + Math.random() * 16) % 16 | 0;
                    d = Math.floor(d / 16);
                    return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
                });
                return uuid;
            }

        </script>
    </head>
    <body>
        <div id="wonMsg" style="display:none">Congratulations! You've won the game.</div>
        <img src="images/ship.gif" id="ship" />
        <table id="tblAliens"></table>

    </body>
</html>

