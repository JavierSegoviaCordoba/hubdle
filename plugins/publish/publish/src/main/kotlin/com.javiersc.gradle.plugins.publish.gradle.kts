import com.javiersc.gradle.plugins.publish.internal.configurePublishOnlySignificant
import internal.configurePublishing

plugins {
    `maven-publish`
    signing
}

configurePublishing()

configurePublishOnlySignificant()
