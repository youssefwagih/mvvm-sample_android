# Weather Photos Application
This is an application that let user capture photo from user and apply on it the current weather using the Weather API to get the weather data

# Technical notes
- Language used is java.
- Android Architecture Components are applied in this project.
- MVVM design pattern is used in this application.
- Weather API needs API key to be able to use it.
- No data binding applied but i knew about it also i knew about butterknife is better than using findviewbyId method and also databinding makes us get rid of these boilerplate code
- respository pattern will be used for handling the data layer to be responsible for the logic of getting data from remote APIs or from local database but our app has only remote APIs so no need to add the other logic.

# Project structure
- **UI**: it contains all the activities, fragments and viewmodels used in the app
- **Adapters**: it contains all the adapter used all over the app.
- **Network**: it contains the initialization or any helper class used in network
- **util**: it contains all the utilities the app needs it
- **base**: it contains all base classes used all over the app
- **Repository**: it contains all the data layer classes (models, sharedpreferences,  database and remote implementations) it should be responsible for handling data if it should come from remote or from database.

### Libraries: ###
- [Android architecture components](https://developer.android.com/topic/libraries/architecture/index.html)
- [Retrofit2](https://github.com/square/retrofit)
- [Picasso](https://github.com/square/picasso)
- [Dexter](https://github.com/Karumi/Dexter)