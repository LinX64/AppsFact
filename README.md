# AppsFactory - Music management 

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

This project uses Clean Architecture with three different layers as recommended by [Google](https://developer.android.com/topic/architecture?gclid=Cj0KCQjwnP-ZBhDiARIsAH3FSRcqhwDHkL89guXx0hxFBQPoMx0rabJWKBWiMJi-Fc9hJf5i4vwx6JwaAi_iEALw_wcB&gclsrc=aw.ds#recommended-app-arch):

- Data
- Domain
- Presentation

### Solution

The project consists of 4 different `Fragment`s with a single `Activity`. It uses one base `Fragment` along with a base `ViewModel` to avoid the repetition of state handling and `onCreateView()` method for `Fragment`.

1. The mainScreen (`Fragment`) - loads saved albums from Database.
2. AlbumInfo `Fragment` which accepts `albumName` and `artistName` as arguments and then makes the call to the server to get the specific album detail.
3. Search Artist - where it searches for artists based on a name.
4. Top albums - when user clicks on an album, the apps navigates to top albums to show the top albums of that specific artist.

### Tests

**Automated test:** I wrote the tests for all of the mentioned screens which checks for most of the situation a user may face.

**Unit test:** It has a unit test for the `MainRepository` class, where all of the calls to server are being done in there. P.S: I already wrote the test for the database operations (CRUD).



