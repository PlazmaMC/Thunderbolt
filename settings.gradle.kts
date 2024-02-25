import java.util.Locale

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

// Volt start - Remove this
if (!file(".git").exists()) {
    val errorText = """
        
        =====================[ ERROR ]=====================
         The Volt project directory is not a properly cloned Git repository.
         
         In order to build Volt from source you must clone
         the repository using Git, not download a code zip from GitHub.
         
         See https://github.com/PaperMC/Paper/blob/master/CONTRIBUTING.md
         for further information on building and modifying Volt.
        ===================================================
    """.trimIndent()
    error(errorText)
}
// Volt end - Remove this

if (file("libs").exists()) {
    dependencyResolutionManagement {
        versionCatalogs {
            create("api") {
                from(files("libs/api.versions.toml"))
            }
            create("server") {
                from(files("libs/server.versions.toml"))
            }
            create("common") {
                from(files("libs/common.versions.toml"))
            }
        }
    }
}

rootProject.name = "volt" // Volt - Change this
for (name in listOf("Volt-API", "Volt-Server", "paper-api-generator")) { // Volt - Change this
    val projName = name.lowercase(Locale.ENGLISH)
    include(projName)
    findProject(":$projName")!!.projectDir = file(name)
}
