rootProject.name = "sandbox-project"

dependencyResolutionManagement { repositories { mavenCentral() } }

include(":libraries:sub")

include(":libraries:sub-two")

include(":library")

include(":more:sub-dir:sub")

include(":more:sub")
