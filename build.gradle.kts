import io.papermc.paperweight.util.*
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    id("io.papermc.paperweight.patcher") version "1.5.11"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") {
        content {
            onlyForConfigurations(configurations.paperclip.name)
        }
    }
}

dependencies {
    remapper("net.fabricmc:tiny-remapper:0.8.10:fat")
    decompiler("net.minecraftforge:forgeflower:2.0.627.2")
    paperclip("io.papermc:paperclip:3.0.3")
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

    publishing {
        repositories {
            maven {
                name = "githubPackage"
                url = uri("https://maven.pkg.github.com/PlazmaMC/Volt") // Volt - Change this

                credentials {
                    username = System.getenv("GITHUB_USERNAME")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }

            publications.register<MavenPublication>("gpr") {
                from(components["java"])
            }
        }
    }
}

subprojects {
    tasks {
        withType<JavaCompile>().configureEach {
            options.compilerArgs.addAll(listOf("--add-modules=jdk.incubator.vector", "-Xmaxwarns", "1"))
            options.encoding = Charsets.UTF_8.name()
            options.release = 17
        }
    
        withType<Javadoc> {
            options.encoding = Charsets.UTF_8.name()
        }
    
        withType<ProcessResources> {
            filteringCharset = Charsets.UTF_8.name()
        }
    
        withType<Test> {
            testLogging {
                showStackTraces = true
                exceptionFormat = TestExceptionFormat.FULL
                events(TestLogEvent.STANDARD_OUT)
            }
        }
    }

    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://jitpack.io")
    }
}

paperweight {
    serverProject = project(":volt-server") // Volt - Change this

    remapRepo = "https://papermc.io/repo/repository/maven-public/"
    decompileRepo = "https://papermc.io/repo/repository/maven-public/"

    useStandardUpstream("plazma") {
        url = github("PlazmaMC", "PlazmaBukkit")
        ref = providers.gradleProperty("plazmaCommit")

        withStandardPatcher {
            baseName("Plazma")

            apiPatchDir = layout.projectDirectory.dir("patches/api")
            apiOutputDir = layout.projectDirectory.dir("Volt-API") // Volt - Change this

            serverPatchDir = layout.projectDirectory.dir("patches/server")
            serverOutputDir = layout.projectDirectory.dir("Volt-Server") // Volt - Change this
        }

        patchTasks.register("generatedApi") {
            isBareDirectory = true
            upstreamDirPath = "paper-api-generator/generated"
            patchDir = layout.projectDirectory.dir("patches/generated-api")
            outputDir = layout.projectDirectory.dir("paper-api-generator/generated")
        }

        patchTasks.register("versionCatalogs") {
            isBareDirectory = true
            upstreamDirPath = "libs"
            patchDir = layout.projectDirectory.dir("patches/version-catalog")
            outputDir = layout.projectDirectory.dir("libs")
        }
    }
}

tasks {
    applyPatches {
        dependsOn("applyVersionCatalogsPatches")
        dependsOn("applyGeneratedApiPatches")
    }

    rebuildPatches {
        dependsOn("rebuildVersionCatalogsPatches")
        dependsOn("rebuildGeneratedApiPatches")
    }

    generateDevelopmentBundle {
        apiCoordinates = "org.plazmamc.plazma:volt-api"
        mojangApiCoordinates = "io.papermc.paper:paper-mojangapi"
        libraryRepositories.set(
            listOf(
                "https://repo.maven.apache.org/maven2/",
                "https://maven.pkg.github.com/PlazmaMC/Plazma", // Volt - Change this
                "https://papermc.io/repo/repository/maven-public/"
            )
        )
    }

    register("updateUpstream") {
        val tempDir = layout.cacheDir("updateUpstream")
        val file = "gradle.properties"
        val builder = StringBuilder()

        doFirst {
            val commit = layout.cache.resolve("commit.json")
            download.get().download("https://api.github.com/repos/PlazmaMC/PlazmaBukkit/commits/master", commit)
            val latestCommit = gson.fromJson<paper.libs.com.google.gson.JsonObject>(commit)["sha"].asString

            val compare = layout.cache.resolve("compare.json")
            download.get().download("https://api.github.com/repos/PlazmaMC/PlazmaBukkit/compare/${property("plazmaCommit")}...dev/1.20.4", compare)
            gson.fromJson<paper.libs.com.google.gson.JsonObject>(compare)["commits"].asJsonArray.forEach {
                builder.append("PlazmaMC/PlazmaBukkit@${it.asJsonObject["sha"].asString.subSequence(0, 7)}: ${it.asJsonObject["commit"].asJsonObject["message"].asString.split("\n")[0]}\n")
            }

            copy {
                from(file)
                into(tempDir)
                filter {
                    it.replace("plazmaCommit = .*".toRegex(), "plazmaCommit = $latestCommit")
                }
            }
        }

        doLast {
            copy {
                from(tempDir.file("gradle.properties"))
                into(project.file(file).parent)
            }
            project.file("compare.txt").writeText(builder.toString())
        }

        finalizedBy(applyPatches)
    }
}

publishing {
    publications.create<MavenPublication>("devBundle") {
        artifact(tasks.generateDevelopmentBundle) {
            artifactId = "dev-bundle"
        }
    }
}
