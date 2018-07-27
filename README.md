# CloverNews

This is an example Android project that fetches top headlines from News API based on selected country and/or category.

## Language 
Kotlin


## Arhitecture
a MVVM variation; module consists of these components:
View(activity or fragment) - reacts on user input and updates view
UseCase - business logic  
ViewModel - connects view with business logic
ViewState- view observes this state class that viewmodel changes
ViewStatus- enum used to indicate view state
ViewModelFactory- implements ViewModelProvider.Factory interface, used to provide viewmodel

## Networking
Retrofit in combination with Rx, gson and OkHttp

## Libraries:
RxJava2- used for networking and for communication between layers
RxBinding2- used to observe UI elements as observable
Android Architecture libraries- used for ViewModel and Live Data
Retrofit, gson- used for networking
Picasso- used for fetching images for articles
AndroidTagView- used for country and category tags
Android Country Flags- icons for country flags
Dagger- used for dependency injection
AVLoadingIndicatorView- used for custom loader

## API 
News API: https://newsapi.org/v2/
You have to sign up and get your API key for this app to work.

## Running the project:
After cloning the project from git, before building and running the app, you should add a new Kotlin file in package clover.studio.clovernews.commons constants named ApiKey that includes following code:

```Kotlin
const val API_KEY = "Enter_your_API_key_here"
```

