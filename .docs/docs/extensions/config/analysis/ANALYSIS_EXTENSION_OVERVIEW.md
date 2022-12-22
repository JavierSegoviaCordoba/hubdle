# Analysis extension Overview

- analysis
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - `ignoreFailures: Property<Boolean>`
    - `includes: SetProperty<String>` // `"**/*.kt", "**/*.kts"`
    - `excludes: SetProperty<String>` // `"**/resources/**", "**/build/**"`
    - `includes(vararg paths: String)`
    - `excludes(vararg paths: String)`
    - [reports](reports/REPORTS_EXTENSION_OVERVIEW.md)
    - `detekt(action: Action<DetektExtension>)`
