plugins {
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.skycom.tmdbapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.skycom.tmdbapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        android.buildFeatures.buildConfig = true

        buildConfigField("String", "MOVIE_DB_BASE_URL", "\"https://api.themoviedb.org/3/\"")

        //This would be moved to separate not disclosed file, but is left to allow for easier testing by other parties.
        buildConfigField("String", "MOVIE_DB_API_TOKEN", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjYWI1OTc4NmZhYzRhMWFhNWFjYTBmODgwOGYxNDQ3YyIsIm5iZiI6MTY5NDYwOTA3My4zMDgsInN1YiI6IjY1MDFhZWIxZmZjOWRlMGVlM2M2NmIyMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.M2x_Mva94QQ_3ONY3RTdeO54ryjikoYQoSg7G9YMA4Y\"")

        buildConfigField("String", "MOVIE_IMAGE_BASE_URL", "\"https://image.tmdb.org/t/p/\"")
        buildConfigField("String", "MOVIE_POSTER_SIZE", "\"/w500\"")
        buildConfigField("String", "MOVIE_BACKDROP_SIZE", "\"/original\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.hilt.android)
    implementation(libs.coil.compose)
    implementation(libs.okhttp)
    implementation(libs.gson)
    implementation(libs.androidx.lifecycle.runtime.compose.android)

    kapt(libs.hilt.android.compiler)

    testImplementation(libs.androidx.paging.common)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}


kapt {
    correctErrorTypes = true
}