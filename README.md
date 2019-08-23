# Drupidroid

Android app skeleton with integration with Drupal 8 REST system. It works with a Drupal platform installed with REST modules enabled. The Android app uses the library Retrofit to manage the sessions and it was coded with Java.

## Overview of the project's code

```
.
├── app
│   └── src
│       └── main
│           ├── java
│           │   └── ch
│           │       └── drupalmountaincamp  <- Activity files
│           │           └── android
│           │               ├── model       <- POJO (Plain Old Java Object) classes used for the http calls
│           │               └── retrofit    <- Retrofit config files (API, session)
│           └── res                         
└── gradle                                  <- Gradle files (Open-source build automation system)
    └── wrapper
```

## Prerequisites

1. You need to have Android Studio installed. The following page will guide you through the installation.

   | Operating System | Tutorial                                            |
   | ---------------- | --------------------------------------------------- |
   | Mac/Linux/Windows| https://developer.android.com/studio  |

2. Install Drupal and create a test user.

3. Setup Drupal endpoints by enabling the following modules.
    * HAL
    * HTTP Basic Authentication
    * REST UI
    * RESTful Web Services
    * Serialization

4. Update the configuration of the REST UI module to enable the endpoints, in ```admin/config/services/rest```.
    * Content   ```methods: GET, POST, formats: hal_json, json, authentication: basic_auth```
    * User      ```methods: GET, POST, formats: json, authentication: basic_auth```

5. Once you have it installed, open the folder containing the project.

## Running the test.py

1. To make it work with your own API REST points, edit the following lines in the file ```app/src/main/java/ch/drupalmountaincamp/android/retrofit/UtilsApi```
```
public static final String BASE_URL_API = ""; // Add your own API REST Url.
```

2. Execute the app within Android Studio or create an APK file to install it in a device.

3. Run the project and test with the credentials of the user in Drupal.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
