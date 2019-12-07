# Popular Movies

A new approach of Popular Movies project from [Udacity's Android Developer Nanodegree](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801) using [Kotlin](https://kotlinlang.org/) with [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html), State Machine design pattern and Test Driven Development.

This project has the same stage 1 features from original project where the data from movies is fetched from [TMDB API](https://developers.themoviedb.org/3/getting-started/introduction) and the movie posters are displayed in a grid layout and the remaining details are displayed in a separated view. Users can also change the sort order and app keeps fetching API while the main grid is scrolled down.

#### Architecture Strategies

Please check the post below on Medium with detailed explanation and motivations for this project:

[Android Clean Architecture — A “Unicorn” Approach](https://engineering.talkdesk.com/android-clean-architecture-an-unicorn-approach-a5076d1b409)

Highlights:
- Domain uses a State Machine pattern to request data from data layer and contextualize changes in the model.
- Domain also listen to changes in the data or use cases requests and emits updated model
- Domain only exposes use cases related methods to views (that is, there's no state machine related methods being exposed, so views can't control the state machine behavior of domain)
- Presenters listen to model changes and update views accordingly and also controls navigation
- Views are as dumb as it possible with minimum of logic to render data

#### Technical Strategies

Most part of code uses basic components from framework, so the use of external libraries was reduced to a minimum for better testability, increase of control and possibility to reuse the codebase for different platforms. So there's no use of popular libraries like Jetpack, Retrofit, Dagger, etc.

- App uses Single-Activity architecture
- State machine is initialized in the Application, so it isn't affected by views lifecycle
- For data, was used [OkHttp](https://square.github.io/okhttp/) and plain [JSONObject](https://developer.android.com/reference/org/json/JSONObject) to fetch and parse TMDB API responses.
- For image loading and caching was used [Picasso](https://square.github.io/picasso/)
- For threads control was used [RxJava](http://reactivex.io/) only to dispatch tasks to background or main thread (no special operators were used) where a [HandlerThread](https://developer.android.com/reference/android/os/HandlerThread) was used to enqueue tasks in the same background thread.
- All dependency injection is manual using a [Builder](https://en.wikipedia.org/wiki/Builder_pattern) pattern, so data layer and thread dispatcher can be replaced in the tests.

#### Testing Strategies

- **66 Unit tests** - Evaluate each class where dependencies are test doubles (it uses mostly [JUnit 4](https://junit.org/junit4/) and [Roboletric](http://robolectric.org/) for Android specific components)
- **9 Component Tests** - Evaluate integrated tests inside each view where only data layer and thread handler are test doubles (it uses Roboletric for loading and asserting inside views).
- **5 Integration tests** - Evaluate the proper requests to API and data returned (**OBS:** server is mocked with [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) and parser is a test double).
- **1 End to End test** - Evaluate the main use case of loading movies from real API, click in a movie and open its details (it uses [Espresso](https://developer.android.com/training/testing/espresso) tests).

## Copyright

Project developed by Márcio Souza de Oliveira.
