group = "com.bmbteam"

patches {
    // TODO: Update this section with your project details.
    about {
        name = "BMB Team Patches"
        description = "Patches for apps I like"
        source = "git@github.com:DarkBatmanInc/morphe-bmbstore-update-check-patch.git"
        author = "BMB Team"
        contact = "N/A"
        website = "N/A"
        license = "GPLv3"
    }
}

// Separate configuration so gson is available at runtime for the
// generatePatchesList task but never bundled into the APK.
val patchListGeneratorClasspath = configurations.create("patchListGeneratorClasspath")

dependencies {
    compileOnly(libs.gson)
    patchListGeneratorClasspath(libs.gson)
}

tasks {
    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath + patchListGeneratorClasspath
        mainClass.set("util.PatchListGeneratorKt")
    }

    // Used by gradle-semantic-release-plugin.
    publish {
        dependsOn("generatePatchesList")
    }
}
