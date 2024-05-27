import io.papermc.paperweight.patcher.tasks.CheckoutRepo
import io.papermc.paperweight.util.*
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import paper.libs.com.google.gson.JsonObject

plugins {
    java
    `kotlin-dsl`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    id("io.papermc.paperweight.patcher") version "1.7.1"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") {
        content { onlyForConfigurations(configurations.paperclip.name) }
    }
}

val jdkVersion = property("jdkVersion").toString().toInt()
val projectName = property("brandName").toString()
val projectRepo = property("providerRepo").toString()
val upstreamRef = property("plazmaRef").toString()
val upstreamCommitValue = property("plazmaCommit").toString()

kotlin.jvmToolchain(jdkVersion)

dependencies {
    remapper("net.fabricmc:tiny-remapper:0.8.10:fat")
    decompiler("org.vineflower:vineflower:1.10.1")
    paperclip("io.papermc:paperclip:3.0.3")
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(jdkVersion))

    publishing {
        repositories.maven("https://maven.pkg.github.com/$projectRepo") {
            name = "githubPackage"

            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

subprojects {
    tasks {
        withType<JavaCompile>().configureEach {
            options.encoding = Charsets.UTF_8.name()
            options.release = jdkVersion
            options.compilerArgs.addAll(listOf(
                "--add-modules=jdk.incubator.vector",
                "-Xmaxwarns", "1"
            ))
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
    serverProject = project(":${projectName.lowercase()}-server")

    remapRepo = "https://papermc.io/repo/repository/maven-public/"
    decompileRepo = "https://papermc.io/repo/repository/maven-public/"

    useStandardUpstream("plazma") {
        url = github("PlazmaMC", "PlazmaBukkit")
        ref = providers.gradleProperty("plazmaCommit")

        withStandardPatcher {
            baseName("Plazma")

            apiPatchDir = projectDir.resolve("patches/api")
            apiOutputDir = projectDir.resolve("$projectName-API")

            serverPatchDir = projectDir.resolve("patches/server")
            serverOutputDir = projectDir.resolve("$projectName-Server")
        }

        patchTasks.register("generatedApi") {
            isBareDirectory = true
            upstreamDirPath = "paper-api-generator/generated"
            patchDir = projectDir.resolve("patches/generated-api")
            outputDir = projectDir.resolve("paper-api-generator/generated")
        }

        patchTasks.register("versionCatalogs") {
            isBareDirectory = true
            upstreamDirPath = "libs"
            patchDir = projectDir.resolve("patches/version-catalog")
            outputDir = projectDir.resolve("libs")
        }
    }
}

val github = "https://api.github.com/repos/PlazmaMC/PlazmaBukkit"

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
        apiCoordinates = "$group:$projectName-api"
        libraryRepositories.set(
            listOf(
                "https://repo.maven.apache.org/maven2/",
                "https://maven.pkg.github.com/$projectRepo",
                "https://papermc.io/repo/repository/maven-public/"
            )
        )
    }
    
    register("checkNeedsUpdate") {
        var latest = ""
        
        doFirst {
            val commit = layout.cache.resolve("commit.json")
            download.get().download(
                "$github/commits/$upstreamRef",
                commit
            )

            latest = gson.fromJson<JsonObject>(commit)["sha"].asString
        }
        
        doLast {
            println(latest != project.property("plazmaCommit"))
        }
    }

    register("updateUpstream") {
        dependsOn(clean)

        val tempDir = layout.cacheDir("alwaysUpToDate")
        val file = "gradle.properties"
        val builder = StringBuilder()

        doFirst {
            val commit = layout.cache.resolve("commit.json")
            download.get().download(
                "$github/commits/$upstreamRef",
                commit
            )

            val latestCommit = gson.fromJson<JsonObject>(commit)["sha"].asString

            val compare = layout.cache.resolve("compare.json")
            download.get().download(
                "$github/compare/$upstreamCommitValue...$upstreamRef",
                compare
            )

            gson.fromJson<JsonObject>(compare)["commits"].asJsonArray.forEach {
                builder.append("PlazmaMC/PlazmaBukkit")
                builder.append("@")
                builder.append(it.asJsonObject["sha"].asString.subSequence(0, 7))
                builder.append(": ")
                builder.append(it.asJsonObject["commit"].asJsonObject["message"].asString.split("\n")[0])
                builder.append("\n")
            }

            copy {
                from(file)
                into(tempDir)
                filter {
                    it.replace(
                        "plazmaCommit = .*".toRegex(),
                        "plazmaCommit = $latestCommit"
                    )
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
    }
    
    clean {
        doLast {
            listOf(
                ".gradle/caches",
                "$projectName-API",
                "$projectName-Server",
                "paper-api-generator",
                "run",

                // remove dev environment files
                "0001-fixup.patch",
                "compare.txt"
            ).forEach {
                projectDir.resolve(it).deleteRecursively()
            }
        }
    }
}

publishing {
    publications.create<MavenPublication>("devBundle") {
        artifact(tasks.generateDevelopmentBundle) {
            artifactId = "dev-bundle"
        }
    }
}
