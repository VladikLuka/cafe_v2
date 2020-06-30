<%--
  Created by IntelliJ IDEA.
  User: vladi
  Date: 26.06.2020
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Simple Map</title>
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA_2XpU40EtIluss6eip_Uh_AZMoqoM8Bc&callback=initMap&libraries=&v=weekly"
            defer
    ></script>
    <style type="text/css">
        /* Always set the map height explicitly to define the size of the div
         * element that contains the map. */
        #map {
            height: 100%;
        }

        /* Optional: Makes the sample page fill the window. */
        html,
        body {
            height: 100%;
            margin: 0;
            padding: 0;
        }
    </style>
    <script>
        (function(exports) {
            "use strict";

            function initMap() {
                let map = new google.maps.Map(document.getElementById("map"), {
                    center: {
                        lat: 53.901982,
                        lng: 27.552429

                    },
                    zoom: 12
                });

                let marker = new google.maps.Marker({
                    map:map,
                    position:new google.maps.LatLng(53.942348, 27.567071),
                    title:'epam-cafe',
                });

                var MinskDelimiters =[
                    { lng: 27.474579,  lat: 53.966737 },
                    { lng: 27.584080,  lat: 53.971340 },
                    { lng: 27.592717,  lat: 53.970732 },
                    { lng: 27.669570,  lat: 53.944468 },
                    { lng: 27.671483,  lat: 53.943719 },
                    { lng: 27.696157,  lat: 53.884069 },
                    { lng: 27.696733,  lat: 53.881158 },
                    { lng: 27.696580,  lat: 53.878991 },
                    { lng: 27.695469,  lat: 53.875595 },
                    { lng: 27.664593,  lat: 53.841851 },
                    { lng: 27.659179,  lat: 53.838240 },
                    { lng: 27.650192,  lat: 53.834879 },
                    { lng: 27.643122,  lat: 53.833875 },
                    { lng: 27.553313,  lat: 53.833188 },
                    { lng: 27.452293,  lat: 53.844133 },
                    { lng: 27.443345,  lat: 53.846472 },
                    { lng: 27.438017,  lat: 53.849045 },
                    { lng: 27.434584,  lat: 53.851328 },
                    { lng: 27.429009,  lat: 53.857245 },
                    { lng: 27.407278,  lat: 53.901699 },
                    { lng: 27.407645,  lat: 53.906802 },
                    { lng: 27.417663,  lat: 53.926322 },
                    { lng: 27.427810,  lat: 53.936950 },
                    { lng: 27.462549,  lat: 53.963007 },
                    { lng: 27.468004,  lat: 53.964978 },
                    { lng: 27.473448,  lat: 53.966307 },
                    { lng: 27.479439,  lat: 53.967108 },
                ];

                var polygon = new google.maps.Polygon({
                    paths: MinskDelimiters,
                    strokeColor:'#aeae0e',
                    strokeOpacity:0.8,
                    strokeWeight:3,
                    fillColor:'#aeae0e',
                    fillOpacity:0.35
                });
                polygon.setMap(map);


            }




            exports.initMap = initMap;
        })((this.window = this.window || {}));
    </script>
</head>
<body>
<div id="map" style="height: 100%; width: 100%"></div>
</body>
</html>


<script>



</script>