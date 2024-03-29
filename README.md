# 4Health

------------------
## Brief description of the application

The "4Health" application for Android systems (in the mobile version and for tablets) has been implemented fully using the Kotlin language, it is designed to support the physical activity of people practicing jogging, hiking, Nordic walking, cycling...  The tasks carried out by the application include, m.in: 

<ul>
  <li>Create route runs</li>
  
 <li>Storing created routes </li>

 <li>Controlling user activity </li>

 <li>Visualization of The User's Weekly Activity </li>

 <li>Create activity time measurements  </li>

 <li>Visualize your route with Google Maps </li>
 
<li> Connect to an external user authentication database (Firebase) </li>

<li> Data cache </li>

<li> Registration/ User Login </li>

<li> Keeping individual user statistics </li>
 
  
</ul>

----------------
## Implemented libraries / APIs

The application uses a number of libraries to improve performance, visually improve the UI and provide data using external APIs, which include:


• `Android Jetpack View Binding`
  
• `Kotlin coroutines`

• `SQLite Database`

• `MPAndroidChart` – data visualization

• `Maps SDK for Android` - Access to Google Maps. 

• `Directions API` – Google API, request in the form of JSON provides component points between the beginning and end of the route, estimates the time of the route, determines the distance and returns postal addresses of specific points. 

• `Street View Static API` - Google API, request with an argument containing a specific location on the map returns its static view from Google Street View in the form of an image.

• `Glide` – a library that loads images from a link in imageView, additionally creates an image token for a given url, thus it does not require the Internet to reload / repeatedly the image. 

• `Volley` – Making requests/ queries to external APIs / https pages 

• `Google Firebase Firestorm` - External database for user authentication

• `GSON` – parsing JSON objects

--------------
## App update, preview of the new features! 

The improvement includes features such as:

• `Creating profiles` - Creating online users kept in a remote database

• `Google Firebase Firestorm` - External database for user authentication

• `Chart Page` - Page dedicated to logged in users, keeping them weekly total activity, and a daily training challenge

• `UI Improvements` - new animations for a more natural user experience (custom transition animations using viewpager2), improved bottom menu and home page style
  
  <table>
  <tr>
    <td> <img src="https://user-images.githubusercontent.com/77066408/187095683-a8ed6124-a2e7-41d5-b44f-2547320da52f.png"  alt="1"></td>
    <td> <img src="https://user-images.githubusercontent.com/77066408/187096165-d1028194-b19f-4e84-b213-0d2a8ae0b39a.png"  alt="2"></td>
    <td> <img src="https://user-images.githubusercontent.com/77066408/187094318-23499753-e44a-4792-93cc-06a6733b0ced.png"  alt="3"></td>
    
  </tr> 
</table>


----------------------
# Previous version 
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

-------------
## UI + Animation preview

The application implements a full range of animations using ObjectAnimator in SplashActivity, the possibilities of animation components of the application with animations written in xml files and using MotionLayout on the main page or ...

<table >
  <tr>
    <td> <b> Start Screen</br> + </br>Splash Screen </b> </td>
    <td> <img src="https://user-images.githubusercontent.com/77066408/175133342-0b2caaae-a5ed-4578-b12c-57469fd3f343.gif"  alt="1"></td>
    <td> <b> Track Details</br> + </br>Track History </b> </td>
    <td> <img src="https://user-images.githubusercontent.com/77066408/175134130-d9d9daff-34d6-4a13-b33b-490e591b3d49.gif"  alt="2"></td>
 
    
  </tr> 
</table>

additionally animations reacting to the change in values returned from the accelerometer sensor.

<table>
  <tr>
    <td> <img src="https://user-images.githubusercontent.com/77066408/175136396-60359aa3-b2c3-4654-a5ad-7f73e3a81e87.png"  alt="1"></td>
    <td> <img src="https://user-images.githubusercontent.com/77066408/175136557-b92b5c45-c6ba-4170-80b8-e58c6ae5e382.png"  alt="2"></td>
  </tr> 
</table>


---------------
## Nesting a fragment of a dynamic stopwatch in a fragment of detail

Presented fragment of the stopwatch implemented inside the fragment of details. In order to ensure the correct operation of the application when changing the orientation of the position, the <b>ViewModel</b> class was used, which is part of the Android Jetpack. Due to the longer lifecycle behavior, the class does not reconstruct itself when changing the orientation of the device. 

<table>
  <tr>
    <td> <img src="https://user-images.githubusercontent.com/77066408/175140036-d8976d98-d13b-4670-abb4-d48b58342c6d.png"  alt="1"></td>
    <td> <img src="https://user-images.githubusercontent.com/77066408/175140268-0c6f063d-b672-4cff-8d56-b434dd26cdea.png"  alt="2"></td>
  </tr> 
</table>

---------------
##  Preview screenshots of the Tablet version
![Screenshot_20220616-185102](https://user-images.githubusercontent.com/77066408/175141901-a8d51156-7566-438f-8c6c-d3ad52625af2.png)


-----------------------
## Additional information


The application does not require an Internet connection in order to use previously created routes. The returned request results have been thoughtfully saved in the internal database to ensure offline use (waypoints returned from the directions API and site views from the Static Street View API).

<i> For privacy and security reasons, the API key for Google's interfaces is stored in the local.properties file (excluded in the .gitignore file) and returns by reference via BuildConfig.&lt;name&gt;</i>



