# RedditViewer
A reddit post viewer utilising MVVM, Jetpack Compose, Coroutines and pagination  

![screenshot-1689947915358](https://github.com/lightScout/RedditViewer/assets/40794963/5739b2ce-7844-43bd-b1f8-2327af205637)

https://github.com/lightScout/RedditViewer/assets/40794963/5999c40a-1061-4af9-ae3d-93ba65dbe8b5

* Kotlin: The project is written in Kotlin, besides shared preferences helper java class TinyDB.
* Android Jetpack: A variety of Android Jetpack libraries, like androidx.lifecycle for ViewModel and lifecycle-aware components, and androidx.activity:activity-compose for integrating Compose with activities.
* Compose: UI build using Jetpack Compose
* Hilt: Used for dependency injection.
* Retrofit: For network operations, Retrofit is being used, which is a type-safe HTTP client for Android and Java. It's easy to integrate and can return Kotlin Coroutines, making network operations seamless and straightforward.
* Glide: For image loading and caching, the Glide library is being. Its 'compose' artefact provides support for Jetpack Compose.
* Navigation Compose: Navigation Compose is being used to handle app screen navigation.
* OkHttp Logging Interceptor: Is being used for network logging during development.
* SharedPreferences: Due to the small amount of data being cached SharedPreferences is used.
