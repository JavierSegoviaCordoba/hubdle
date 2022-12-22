# Format extension overview

- format
    - `isEnabled: Property<Boolean>` // true
    - `enabled(value: Boolean = true)`
    - `includes: SetProperty<String>`
    - `includes(vararg values: String)`
    - `excludes: SetProperty<String>`
    - `excludes(vararg values: String)`
    - `ktfmtVersion: Property<String>`
    - `spotless(action: Action<SpotlessExtension>)`
    - `spotlessPredeclare(action: Action<SpotlessPredeclare>)`
