# FootBall-Fixtures

An android app that consumes data from [ https://www.football-data.org]( https://www.football-data.org), completely written in [Kotlin][kotlin]
<br />
Fully compatible with [Android Q][android-q], supporting light and [dark theme][dark-theme] (see screenshots)

## API Key
Get your free api  [here](https://www.football-data.org/client/register) <br/>
Place it in `app/build.gradle`
```
android {
    defaultConfig {
       ...
        buildConfigField("String", "API_TOKEN", "\"PLACE_YOUR_API_KEY_HERE\"")
       ...
    }
    ...
}
```


## Libraries
* [AndroidX][androidx]
* [Android Architecture Components][arch]
* [Material Components][material]
* [Constraint Layout][constraint-layout]
* [Retrofit][retrofit] for REST api communication
* [Glide][glide] for image loading
* [Espresso][espresso] for UI tests
* [Mockk][mockk] for mocking in tests
* [Dagger2][dagger2] for dependency injection
* [RxJava2][rxjava2] for concurrency
* [Room][room] for database



[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[androidx]: https://developer.android.com/jetpack/androidx
[arch]: https://developer.android.com/arch
[espresso]: https://google.github.io/android-testing-support-library/docs/espresso/
[retrofit]: http://square.github.io/retrofit
[glide]: https://github.com/bumptech/glide
[mockk]: https://github.com/mockk/mockk
[dagger2]: https://github.com/google/dagger
[kotlin]: https://developer.android.com/kotlin
[material]: https://github.com/material-components/material-components-android/
[android-q]: https://developer.android.com/preview
[dark-theme]: https://developer.android.com/preview/features/darktheme
[constraint-layout]: https://developer.android.com/reference/android/support/constraint/ConstraintLayout
[rxjava2]: https://github.com/ReactiveX/RxJava
[room]: https://developer.android.com/topic/libraries/architecture/room

## Screenshots
### Dark
![](/screenshots/dark/01.png)
![](/screenshots/dark/02.png)
![](/screenshots/dark/03.png)
![](/screenshots/dark/04.png)
![](/screenshots/dark/05.png)
![](/screenshots/dark/06.png)

### Light
![](/screenshots/light/01.png)
![](/screenshots/light/02.png)
![](/screenshots/light/03.png)
![](/screenshots/light/04.png)
![](/screenshots/light/05.png)
![](/screenshots/light/06.png)