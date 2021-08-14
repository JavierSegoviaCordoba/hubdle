package internal

import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.getByName
import org.gradle.plugins.signing.SigningExtension

internal fun SigningExtension.signPublications() {
    if (isSnapshot.not() && project.isSignificant) {
        try {
            signInMemory()
        } catch (_: Throwable) {
            useGpgCmd()
        }
        sign(project.extensions.getByName<PublishingExtension>("publishing").publications)
    }
}

internal fun SigningExtension.signInMemory() {
    if (hasSigningKeyIdGradleProperty || hasSigningKeyIdEnvironmentVariable) {
        useInMemoryPgpKeys(signingKeyId, signingKey, signingKeyPassphrase)
    } else {
        useInMemoryPgpKeys(signingKey, signingKeyPassphrase)
    }
}

internal val SigningExtension.isSnapshot: Boolean
    get() = project.version.toString().endsWith("-SNAPSHOT")

internal val SigningExtension.signingKeyName: String?
    get() = getSigningVariable("signing.gnupg.keyName", "SIGNING_KEY_NAME")

internal val SigningExtension.hasSigningKeyNameGradleProperty: Boolean
    get() = project.properties["signing.gnupg.keyName"]?.toString().isNullOrBlank().not()

internal val SigningExtension.hasSigningKeyNameEnvironmentVariable: Boolean
    get() = System.getenv("SIGNING_KEY_NAME").isNullOrBlank().not()

internal val SigningExtension.signingKeyId: String?
    get() = getSigningVariable("signing.gnupg.keyId", "SIGNING_KEY_ID")

internal val SigningExtension.hasSigningKeyIdGradleProperty: Boolean
    get() = project.properties["signing.gnupg.keyId"]?.toString().isNullOrBlank().not()

internal val SigningExtension.hasSigningKeyIdEnvironmentVariable: Boolean
    get() = System.getenv("SIGNING_KEY_ID").isNullOrBlank().not()

internal val SigningExtension.signingKeyPassphrase: String?
    get() = getSigningVariable("signing.gnupg.passphrase", "SIGNING_KEY_PASSPHRASE")

internal val SigningExtension.hasSigningKeyPassphraseGradleProperty: Boolean
    get() = project.properties["signing.gnupg.passphrase"]?.toString().isNullOrBlank().not()

internal val SigningExtension.hasSigningKeyPassphraseEnvironmentVariable: Boolean
    get() = System.getenv("SIGNING_KEY_PASSPHRASE").isNullOrBlank().not()

internal val SigningExtension.signingKey: String?
    get() = getSigningVariable("signing.gnupg.key", "SIGNING_KEY")

internal val SigningExtension.hasSigningKeyGradleProperty: Boolean
    get() = project.properties["signing.gnupg.key"]?.toString().isNullOrBlank().not()

internal val SigningExtension.hasSigningKeyEnvironmentVariable: Boolean
    get() = System.getenv("SIGNING_KEY").isNullOrBlank().not()

private const val RESET = "\u001B[0m"
private const val RED = "\u001b[0;31m"

private fun SigningExtension.errorMessage(message: String) =
    project.logger.lifecycle("${RED}$message$RESET")

private fun SigningExtension.getSigningVariable(
    propertyName: String,
    environmentVariableName: String
): String? {
    val property: String? = project.properties[propertyName]?.toString()
    val environmentVariable: String? = System.getenv(environmentVariableName)

    when {
        property.isNullOrBlank() && environmentVariable.isNullOrBlank() -> {
            errorMessage(
                "$propertyName Gradle property and " +
                    "$environmentVariableName environment variable are missing",
            )
        }
        property.isNullOrBlank() && environmentVariable.isNullOrBlank().not() -> {
            errorMessage("$propertyName Gradle property is missing")
        }
        property.isNullOrBlank().not() && environmentVariable.isNullOrBlank() -> {
            errorMessage("$environmentVariableName environment variable is missing")
        }
    }

    return property ?: environmentVariable
}
