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

The project consists of 4 different `Fragment`s with a single `Activity`. It uses one
base `Fragment` to avoid the repetition `onCreateView()` and `onViewCreated()` methods
for `Fragment`s.

1. The mainScreen (`Fragment`) - loads saved albums from Database.
2. AlbumInfo `Fragment` which accepts `id`, `albumName` and `artistName` as arguments and then makes the call to the server to get the specific album detail.
3. Search Artist - where it searches for artists based on a name.
4. Top albums - when user clicks on an album, the apps navigates to top albums to show the top albums of that specific artist.

### NetworkBoundResource

During my implementation and doing a bit research of which API and what best practice is perfect for
offline caching, I ended up
with [NetworkBoundResource](https://github.com/LinX64/AppsFactory/blob/master/app/src/main/java/com/example/appsfactory/util/NetworkBoundResource.kt)
which is an inline function where we can handle all the situations with Flow inside the Repository.
For instance, let's take a look at how we could implement the offline caching with this
amazing `NetworkBoundResource`:

```
override fun getAlbumInfo(
        id: Int,
        albumName: String,
        artistName: String
    ) = networkBoundResource(
        query = {
            albumInfoDao.getAlbumInfo(id)
        },
        fetch = {
            apiService.fetchAlbumInfo(albumName, artistName).album.toEntity()
        },
        saveFetchResult = { albumInfo ->
            appDb.withTransaction {
                albumInfoDao.deleteAll()
                albumInfoDao.insert(albumInfo)
            }
        }
    ).flowOn(ioDispatcher)
```

This basically works with high order functions, cross inlines and an inline function with different
stages for handling the offline caching, fetching the data from server, and etc.

**Navigation:**

<p align="center">
<img src="https://i.imgur.com/zS63MnP.png" height="420" />
</p>

**Screens and UI:**

<p align="center">

<img src="https://i.imgur.com/lwLmmND.png" height="420" />
<img src="https://i.imgur.com/uprWkdm.png" height="420" />
<img src="https://i.imgur.com/Lj3Wzw4.png" height="420" />
<img src="https://i.imgur.com/VuZxxGy.png" height="420" />

</p>

### Unit Tests

Unit tests are written for the `Repository`. For the `Repository` tests they are written
using `Mock` and `JUnit4`.

### Instrumentation Tests

Instrumentation tests are written for the `Activity` and `Fragment`. For the `Fragment` tests they
are written using `Espresso` and `JUnit4`.

### TODO

- [ ] Using NetworkBoundResource for the other `Repository` functions.

** The tests should be reviews after the changes I've made with States

- [ ] Add/Improve Unit tests.
- [ ] Add more tests for ViewModel
- [ ] Add more tests for UseCases
- [ ] Improvement for the UseCases
