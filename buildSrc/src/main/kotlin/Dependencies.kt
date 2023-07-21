object Deps {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val materialDesign = "com.google.android.material:material:${Versions.material}"
    const val androidXCore = "androidx.core:core-ktx:${Versions.androidXCore}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModel}"
    const val activity = "androidx.activity:activity-ktx:${Versions.activity}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val junit = "junit:junit:${Versions.jUnit}"
    const val androidXTestExt = "androidx.test.ext:junit:${Versions.androidXTestExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterScalars =
        "com.squareup.retrofit2:converter-scalars:${Versions.converterScalars}"
    // Add other dependency references here
}
