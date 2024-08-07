plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.pizzaapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pizzaapplication"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.11.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.0")
    //  implementation("android.arch.lifecycle:extensions:1.1.1")
    implementation ("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.android.material:material:version")
    // animation
    implementation("com.airbnb.android:lottie:3.6.0")
    //   implementation("com.android.volley:volley:1.2.1")
    // authen gg
    implementation("com.google.android.gms:play-services-auth:20.5.0")
    implementation("androidx.credentials:credentials:1.2.2")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
// facebook authen
    implementation("com.facebook.android:facebook-android-sdk:[8,9)")

    implementation("com.github.momo-wallet:mobile-sdk:1.0.7")
    implementation("io.socket:socket.io-client:2.0.0")
    implementation ("com.auth0:java-jwt:4.0.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel:2.4.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("androidx.lifecycle:lifecycle-livedata:2.5.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation(libs.firebase.storage)

    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")


    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}