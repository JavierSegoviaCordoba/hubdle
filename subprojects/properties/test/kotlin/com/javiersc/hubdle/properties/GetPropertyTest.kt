package com.javiersc.hubdle.properties

import com.javiersc.hubdle.properties.PropertyKey.POM
import com.javiersc.hubdle.properties.PropertyKey.Project
import com.javiersc.hubdle.properties.PropertyKey.Signing
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GetPropertyTest {

    @Test
    fun `Property to snake case`() {
        Project.group.toSnakeCase().shouldBe("PROJECT_GROUP")
        Project.name.toSnakeCase().shouldBe("PROJECT_NAME")
        Project.version.toSnakeCase().shouldBe("PROJECT_VERSION")

        POM.name.toSnakeCase().shouldBe("POM_NAME")
        POM.description.toSnakeCase().shouldBe("POM_DESCRIPTION")
        POM.url.toSnakeCase().shouldBe("POM_URL")
        POM.licenseName.toSnakeCase().shouldBe("POM_LICENSE_NAME")
        POM.licenseUrl.toSnakeCase().shouldBe("POM_LICENSE_URL")
        POM.developerId.toSnakeCase().shouldBe("POM_DEVELOPER_ID")
        POM.developerName.toSnakeCase().shouldBe("POM_DEVELOPER_NAME")
        POM.developerEmail.toSnakeCase().shouldBe("POM_DEVELOPER_EMAIL")
        POM.scmUrl.toSnakeCase().shouldBe("POM_SCM_URL")
        POM.scmConnection.toSnakeCase().shouldBe("POM_SCM_CONNECTION")
        POM.scmDeveloperConnection.toSnakeCase().shouldBe("POM_SCM_DEVELOPER_CONNECTION")

        Signing.gnupgKeyname.toSnakeCase().shouldBe("SIGNING_GNUPG_KEY_NAME")
        Signing.gnupgPassphrase.toSnakeCase().shouldBe("SIGNING_GNUPG_PASSPHRASE")
        Signing.keyId.toSnakeCase().shouldBe("SIGNING_KEY_ID")
    }
}
