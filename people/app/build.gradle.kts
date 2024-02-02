import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

//Room Database

    id("kotlin-kapt") //Add inside plugin
}

android {
    namespace = "com.example.everybody"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.everybody"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // below code used for hide api key

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        defaultConfig{
            buildConfigField("String","api_key",properties.getProperty("api_key"))
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        dataBinding =true
        viewBinding =true
        buildConfig =true
    }



}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //nagvigation dependencies

    val navVersion = "2.7.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")


    //room database
    val roomVersion = "2.6.1"

    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:$roomVersion")


//Co routine

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

//recycler view
    implementation("androidx.recyclerview:recyclerview:1.3.2")
// For control over item selection of both touch and mouse driven selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")


    //coil for loading images
    implementation("io.coil-kt:coil:2.5.0")

    //google play service for getting live location
    implementation( "com.google.android.gms:play-services-location:21.1.0")

    // Retrofit below here are for checking purpose
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //for circular image
    implementation ("de.hdodenhof:circleimageview:3.1.0")


    implementation ("com.google.code.gson:gson:2.10.1")
}