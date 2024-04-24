# Comics

A simple application using https://developer.marvel.com APIs to display comic book title and cover image.

## Developer Keys

Add the following properties to either `~/.gradlew/gradle.properties` or `gradle.properties`.
In either case, replace `<PUBLIC_KEY>` and `<PRIVATE_KEY>` with the appropriate values.

```properties
marvelPublicKey=<PUBLIC_KEY>
marvelPrivateKey=<PRIVATE_KEY>
```

If running from CLI, specify the `ORG_GRADLE_PROJECT_` environment variables:

`ORG_GRADLE_PROJECT_marvelPublicKey=<PUBLIC_KEY> ORG_GRADLE_PROJECT_marvelPrivateKey=<PRIVATE_KEY> ./gradlew assembleDebug`

## Libraries

- Compose
- Hilt
- Material3
- Retrofit
- Moshi
- Glide
- JUnit
- MockK
- Espresso

## Run Unit Tests

```shell
./gradlew testDebugUnitTest
```

## Run UI Tests

```shell
./gradlew connectedAndroidTest
```
