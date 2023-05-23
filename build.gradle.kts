plugins {
    kotlin("jvm") apply false
}

group = "io.github.aifiltration"
version = "0.0.1"

repositories {
    mavenCentral()
}

tasks.wrapper {
    gradleVersion = "8.1.1"
    distributionType = Wrapper.DistributionType.BIN
}
