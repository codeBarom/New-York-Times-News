# NY Times - A NewYork Times most popular news app (developed in pure Kotlin)

A basic master/detail android application which shows most popular viewed news from NYTimes using its public api.

#### App Features
* App fetches NYTimes popular news and displays in RecyclerView.
* App built on master/detail flow pattern, so if launched on tab screens will be visible side by side.
* App shows shimmer effect while data is being fetched from server.
* 100% offline support. If data has been fetched once, then it will be served from db (Room).
* The cached data is only valid for 2 hours, after which data will be fetched again from server.
* Repository class where database serves as a single source of truth for data.
* Error cases handled and an error screen with retry button is shown in case of error.
* App includes UI and unit testing using Junit4 and Espresso.

#### App Architecture 
Based on MVVM architecture and repository pattern, where database serves as a single source of truth for data.
Refer media/mvvm_data_flow.png for diagram explanation.

#### App Specs
- Minimum SDK 19
- [Java8](https://java.com/en/download/faq/java8.xml)
- [Kotlin](https://kotlinlang.org/)
- MVVM Architecture
- Android Architecture Components (LiveData, Lifecycle, ViewModel, Room Persistence Library, ConstraintLayout)
- [Lifecycle-aware components](https://developer.android.com/topic/libraries/architecture/lifecycle)
- [Dagger 2](https://google.github.io/dagger/) for dependency injection.
- [Retrofit 2](https://square.github.io/retrofit/) for API integration.
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) for data observation.
- [ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel) for data container.
- [Room](https://developer.android.com/topic/libraries/architecture/room) for data persistence.
- [Gson](https://github.com/google/gson) for serialisation.
- [Okhhtp3](https://github.com/square/okhttp) for implementing interceptor, logging and mocking web server.
- [Mockito](https://site.mockito.org/) for implementing unit test cases.
- [Glide](https://github.com/bumptech/glide) for image loading.

#### App includes following main components:
* A local database with Room that servers as a single source of truth for data to display on UI. 
* A public nytimes api for fetching data from server (https://api.nytimes.com/svc/mostpopular/v2/viewed/7.json?api-key=lor6KDhACfXzTp6Ta9bHNTI5Jl6l68oD).
* A repository that works with the database and the api service, providing a unified data interface.
* A ViewModel that provides data specific for the UI.
* The UI, which shows a visual representation of the data in the ViewModel.
* UI testing using Espresso and mock server for activity and fragment.
* Unit Test cases for API service, Api Response, Database, Repository and ViewModel.

#### App Packages
* <b>data</b> package containing classes related to api, response and repository.
* <b>db</b> package containing classes for local room database and dao.
* <b>di</b> package containing classes for dependency injection (modules, components).
* <b>ui</b> package containing classes for activity, fragments and adapter.
* <b>viewmodel</b> package for viewmodel classes.

#### How to install the project and run app and test cases
* Simply clone or download the project from github link.
* Open project in Android Studio, wait for building and syncing sdk, install if anything missing and prompted.
* After successful building the project, click on the play button on top and wait for the application to install on simulator or device.
* To check the Junit test cases, right click on(test) folder and choose 'Run tests in nytimes with coverage'.
* To check UI test cases, right click on(androidTest) folder and choose 'Run tests in nytimes'.
* With coverage option will also show the coverage of the test cases in project.

##### You can write back to me if you have any suggestion which can improve the architecture and how we can increase the test cases coverage.# New-York-Times-News
# New-York-Times-News
# New-York-Times-News
