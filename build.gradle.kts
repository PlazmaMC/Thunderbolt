import io.papermc.paperweight.util.*
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    java
    `kotlin-dsl`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    id("io.papermc.paperweight.patcher") version "1.6.3"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") {
        content {
            onlyForConfigurations(configurations.paperclip.name)
        }
    }
}

val jdkVersion = property("jdkVersion").toString().toInt()
val projectName = property("projectName").toString()
val projectRepo = property("projectRepo").toString()
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
        repositories {
            maven {
                name = "githubPackage"
                url = uri("https://maven.pkg.github.com/$projectRepo") // Volt - Change this

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
            options.release = jdkVersion
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
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
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

            apiPatchDir = layout.projectDirectory.dir("patches/api")
            apiOutputDir = layout.projectDirectory.dir("$projectName-API")

            serverPatchDir = layout.projectDirectory.dir("patches/server")
            serverOutputDir = layout.projectDirectory.dir("$projectName-Server")
        }

        patchTasks.register("mojangApi") {
            isBareDirectory = true
            upstreamDirPath = "Plazma-MojangAPI"
            patchDir = layout.projectDirectory.dir("patches/mojang-api")
            outputDir = layout.projectDirectory.dir("$projectName-MojangAPI")
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
        apiCoordinates = "$group:$projectName-api"
        mojangApiCoordinates = "$group:$projectName-mojangapi"
        libraryRepositories.set(
            listOf(
                "https://repo.maven.apache.org/maven2/",
                "https://maven.pkg.github.com/$projectRepo",
                "https://papermc.io/repo/repository/maven-public/"
            )
        )
    }
    
    register("checkNeedsUpdate") {
        var latest: String = ""
        
        doFirst {
            val commit = layout.cache.resolve("commit.json")
            download.get().download("https://api.github.com/repos/PlazmaMC/PlazmaBukkit/commits/$upstreamRef", commit)
            latest = gson.fromJson<paper.libs.com.google.gson.JsonObject>(commit)["sha"].asString
        }
        
        doLast {
            println(latest != property("plazmaCommit"))
        }
    }

    register("updateUpstream") {
        val tempDir = layout.cacheDir("updateUpstream")
        val file = "gradle.properties"
        val builder = StringBuilder()

        doFirst {
            val commit = layout.cache.resolve("commit.json")
            download.get().download("https://api.github.com/repos/PlazmaMC/PlazmaBukkit/commits/$upstreamRef", commit)
            val latestCommit = gson.fromJson<paper.libs.com.google.gson.JsonObject>(commit)["sha"].asString

            val compare = layout.cache.resolve("compare.json")
            download.get().download("https://api.github.com/repos/PlazmaMC/PlazmaBukkit/compare/$upstreamCommitValue...$upstreamRef", compare)
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

            project.setProperty("plazmaCommit", latestCommit)
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
    
    clean {
        doLast {
            projectDir.resolve(".gradle/caches").deleteRecursively()
            listOf("$projectName-API", "$projectName-MojangAPI", "$projectName-Server", "paper-api-generator", "run").forEach {
                projectDir.resolve(it).deleteRecursively()
            }

            // remove dev environment files
            listOf("0001-fixup.patch", "compare.txt").forEach {
                projectDir.resolve(it).delete()
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
