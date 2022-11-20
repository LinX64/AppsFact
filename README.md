# AppsFact - Music management

### Tech stacks

- Kotlin
- MVVM
- Clean Architecture
- Use-cases
- Hilt (Dependency Injection)
- Room (Persistence library)
- Retrofit
- Navigation
- Automated tests (Espresso)
- Unit Tests (Junit4)

### Layers (Clean Architecture)

This project uses Clean Architecture with three different layers as recommended
by [Google](https://developer.android.com/topic/architecture?gclid=Cj0KCQjwnP-ZBhDiARIsAH3FSRcqhwDHkL89guXx0hxFBQPoMx0rabJWKBWiMJi-Fc9hJf5i4vwx6JwaAi_iEALw_wcB&gclsrc=aw.ds#recommended-app-arch):

- Data (data layer: exposing application data)
- Domain (containing use-cases and business logic)
- UI (displaying data to user)

### Solution

The project consists of 4 different `Fragment`s with a single `Activity`. It uses one
base `Fragment` to avoid the repetition `onCreateView()` and `onViewCreated()` methods for `Fragment`s, as well as a `BaseAdapter` for Adapters.

1. The mainScreen (`Fragment`) - loads saved albums from Database.
2. AlbumInfo `Fragment` which accepts `id`, `albumName` and `artistName` as arguments and then makes
   the call to the server to get the specific album detail.
3. Search Artist - where it searches for artists based on a name.
4. Top albums - when user clicks on an album, the apps navigates to top albums to show the top
   albums of that specific artist.

**Navigation:**

<p align="center">
<img src="https://i.imgur.com/zS63MnP.png" height="420" />
</p>

**Screens and UI:**

<p align="center">

<img src="https://i.imgur.com/C9wYaaK.png" height="420" />
<img src="https://i.imgur.com/e6Iqk6o.png" height="420" />
<img src="https://i.imgur.com/1x9Pm3m.png" height="420" />
<img src="https://i.imgur.com/ruVU2VB.png" height="420" />

</p>

### Unit Tests

Unit tests are written for the `Repository`. For the `Repository` tests they are written
using `Mock` and `JUnit4`.

### Instrumentation Tests

Instrumentation tests are written for the `Activity` and `Fragment`. For the `Fragment` tests they
are written using `Espresso` and `JUnit4`.

### TODO

- Add more tests for the `ViewModel` and `Repository`.
- Add/Improve Unit tests.
- Handle errors within the albums.
