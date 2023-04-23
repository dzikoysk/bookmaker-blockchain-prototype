dependencies {
    implementation(project(":bookmaker-blockchain"))

    val javalin = "5.4.2"
    implementation("io.javalin:javalin:$javalin")
    testImplementation("io.javalin:javalin-testtools:$javalin")

    val jackson = "2.14.2"
    implementation("com.fasterxml.jackson.core:jackson-databind:$jackson")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jackson")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jackson")

    implementation("org.apache.groovy:groovy:4.0.7")
    implementation("org.panda-lang:panda-utilities:0.5.3-alpha")
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("ch.qos.logback:logback-classic:1.4.4")
}