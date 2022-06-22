# 4Health

----------------
## Brief description of the application

The "4Health" application for Android systems (in the mobile version and for tablets) has been implemented fully using the Kotlin language, it is designed to support the physical activity of people practicing jogging, hiking, Nordic walking, cycling...  The tasks carried out by the application include, m.in: 

<ul>
  <li>Create route runs</li>
  
 <li>Storing created routes </li>

 <li>Controlling user activity </li>

 <li>Visualization of The User's Weekly Activity </li>

 <li>Create activity time measurements  </li>

 <li>Visualize your route with Google Maps </li>
  
</ul>

----------------
## Implemented libraries / APIs

The application uses a number of libraries to improve performance, visually improve the UI and provide data using external APIs, which include:

<ul>
  <li><b>Android Jetpack View Binding</b></li>

 <li><b>MPAndroidChart</b> – data visualization </li>

 <li><b>Maps SDK for Android</b> - Access to Google Maps. </li>

 <li><b>Directions API</b> – Google API, request in the form of JSON provides component points between the beginning and end of the route, estimates the time of the route, determines the distance and returns postal addresses of specific points. </li>

<li><b>Street View Static API</b> - Google API, request with an argument containing a specific location on the map returns its static view from Google Street View in the form of an image. </li>

<li><b>Glide</b> – a library that loads images from a link in imageView, additionally creates an image token for a given url, thus it does not require the Internet to reload / repeatedly the image.  </li>

<li><b>Volley</b> – Making requests/ queries to external APIs / https pages  </li>
  
</ul>

----------------------
## Preview screenshots of the finished mobile app - Start screen

<table>
  <tr>
    <td> <img src="https://user-images.githubusercontent.com/77066408/175125697-a63666be-a5f8-416a-953b-b535c0458a77.png"  alt="1"></td>
    <td> <img src="https://user-images.githubusercontent.com/77066408/175125066-80e2d2e7-477c-41d0-b754-5b9647980eec.png"  alt="2"></td>
    <td> <img src="https://user-images.githubusercontent.com/77066408/175125935-6762536d-83cd-42a6-a886-bdd7cb4977a7.png"  alt="3"></td>
    
  </tr> 
</table>

<i>1-2: Route detail card, presents the title of the route, its length, estimated travel time, postal addresses of the beginning and end of the route, also contains a fragment of the map with the route and a fragment of the timer 3: A fragment of the user's activity history on a given route, time of overcoming, date of setting the record and day of the week</i>

## Preview screenshots of the finished mobile application – Track Details Screen

<table>
  <tr>
    <td> <img src="https://user-images.githubusercontent.com/77066408/175127428-b86b4f1f-4faa-41b8-a062-da2317900d1a.png"  alt="1"></td>
    <td> <img src="https://user-images.githubusercontent.com/77066408/175127571-091ca9e5-9e45-4359-a20f-fca23479434a.png"  alt="2"></td>
    <td> <img src="https://user-images.githubusercontent.com/77066408/175127825-0a0d710c-b243-4019-b421-d5b8d6053f8e.png"  alt="3"></td>
    
  </tr> 
</table>

<i>1-2: The route detail card, presents the title of the route, its length, estimated travel time, postal addresses of the beginning and end of the route, also contains a fragment of the map with the route and a fragment of the timer 3: A fragment of the user's activity history on a given route, the time of overcoming, the date of setting the record and the day of the week. </i>


![4healthgithub_1_AdobeExpress](https://user-images.githubusercontent.com/77066408/175133342-0b2caaae-a5ed-4578-b12c-57469fd3f343.gif)

