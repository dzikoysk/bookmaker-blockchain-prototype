dependencies {
    implementation(project(":bookmaker-blockchain"))
    implementation(project(":bookmaker-backend"))

    val javalin = "5.4.2"
    testImplementation("io.javalin:javalin-testtools:$javalin")
}