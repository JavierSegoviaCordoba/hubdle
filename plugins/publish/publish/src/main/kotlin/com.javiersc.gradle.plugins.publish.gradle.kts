import com.javiersc.gradle.plugins.publish.internal.configurePublish
import com.javiersc.gradle.plugins.publish.internal.configurePublishOnlySignificant

plugins {
    `maven-publish`
    signing
}

configurePublish()

configurePublishOnlySignificant()
