ifeq ($(OS),Windows_NT)
    GRADLEW = gradlew
else
    GRADLEW = ./gradlew
endif

.PHONY: build
build:
	$(GRADLEW) assemble publishToMavenLocalTest
	$(GRADLEW) build
