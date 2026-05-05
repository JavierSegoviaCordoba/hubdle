# DCL Annotations And Factory Patterns

Use this reference when a Hubdle DCL definition needs functions, factory objects, hidden services,
container element factories, defaults, or receiver-resolution constraints.

All examples use `org.gradle.declarative.dsl.model.annotations` annotations available in Gradle
`9.5.0` and `9.6.0-milestone-1`.

## Factory Functions

Use a plain function when the DSL needs a value expression that is assigned to a property or used
inside an assigned collection.

DSL:

```kotlin
hubdle {
    config {
        foo = foo("main", 42)

        foos = listOf(
            foo("main", 1),
            foo("test", 2)
        )
    }
}
```

Implementation:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HiddenInDefinition
import org.gradle.features.binding.Definition
import org.gradle.kotlin.dsl.newInstance

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    public val foo: Property<HubdleConfigFooDefinition>
    public val foos: ListProperty<HubdleConfigFooDefinition>

    @get:Inject
    @get:HiddenInDefinition
    public val objects: ObjectFactory

    public fun foo(name: String, number: Int): HubdleConfigFooDefinition =
        objects.newInstance<HubdleConfigFooDefinition>().also { foo ->
            foo.name.set(name)
            foo.number.set(number)
        }
}

public interface HubdleConfigFooDefinition {
    public val name: Property<String>
    public val number: Property<Int>
}
```

Rules:

- A pure factory call must be consumed: `foo = foo("main", 42)` is valid.
- A standalone pure factory call is invalid: `foo("main", 42)`.
- Use `ObjectFactory.newInstance<T>()` for managed return objects.
- Hide injected services with `@get:HiddenInDefinition`.

## `@Adding`

Use `@Adding` when the call itself is the DSL operation.

DSL:

```kotlin
hubdle {
    config {
        tag("ci")
        tag("release")
    }
}
```

Implementation:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.ListProperty
import org.gradle.declarative.dsl.model.annotations.Adding
import org.gradle.features.binding.Definition

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    public val tags: ListProperty<String>

    @Adding
    public fun tag(name: String) {
        tags.add(name)
    }
}
```

Rules:

- Parameters are supported: `tag("ci")`.
- Use `@Adding` for standalone calls.
- Keep the function model-oriented: mutate declarative state or add model elements.
- Do not apply plugins, register tasks, or mutate `Project` from a definition function.
- A `Unit` returning `@Adding` function works for standalone calls without a configuration block.
- If a block form is needed, add a fixture for the exact function shape before relying on it.

## `@Builder`

Use `@Builder` when the DSL should build a value through a chain of function calls. The simplest
shape is a single builder object that accumulates state and returns the final value at the end.

DSL:

```kotlin
hubdle {
    config {
        foo = foo("aa").bar("bb").baz(40)
    }
}
```

Implementation:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.Builder
import org.gradle.declarative.dsl.model.annotations.HiddenInDefinition
import org.gradle.features.binding.Definition
import org.gradle.kotlin.dsl.newInstance

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    public val foo: Property<HubdleFooDefinition>

    @get:Inject
    @get:HiddenInDefinition
    public val objects: ObjectFactory

    @Builder
    public fun foo(value: String): HubdleFooBuilder =
        objects.newInstance<HubdleFooBuilder>().also { builder ->
            builder.foo.set(value)
        }
}

public abstract class HubdleFooBuilder @Inject constructor(
    private val objects: ObjectFactory,
) {
    public abstract val foo: Property<String>
    public abstract val bar: Property<String>

    @Builder
    public fun bar(value: String): HubdleFooBuilder =
        apply {
            bar.set(value)
        }

    @Builder
    public fun baz(value: Int): HubdleFooDefinition =
        objects.newInstance<HubdleFooDefinition>().also { foo ->
            foo.foo.set(this.foo)
            foo.bar.set(bar)
            foo.baz.set(value)
        }
}

public interface HubdleFooDefinition {
    public val foo: Property<String>
    public val bar: Property<String>
    public val baz: Property<Int>
}
```

Rules:

- `@Builder` marks a function as a builder step.
- Builder steps may return the same builder object to continue the chain.
- Put `@Builder` on each function that participates in the chain.
- Keep the builder object small and declarative.
- The final function in the chain should return the value type being assigned.
- The whole chain is still a value expression; use it where the final returned value is consumed,
  such as `foo = foo("aa").bar("bb").baz(40)`.
- Do not call a builder chain as a standalone statement unless the final operation also has valid
  standalone DCL semantics.
- Add a `.gradle.dcl` fixture for every builder function because builder resolution participates in
  assignment analysis.

## `@ValueFactories`

`@ValueFactories` is for grouping factory functions under a namespace-like object. The functions are
still value factories: they return values that must be assigned or used inside an assigned value.

The difference from plain factory functions is where the factories live:

- Plain factory functions live directly on the current receiver: `jvm(21)`.
- `@ValueFactories` exposes a holder object and its factories are called through that holder:
  `versions.jvm(21)`.

Use it when several factories belong together or when their names would pollute/conflict with the
main DSL receiver. Do not use it just because a single factory exists.

Plain factory functions:

```kotlin
hubdle {
    config {
        jvm = jvm(21)
        platforms = listOf(
            jvm(17),
            android("35")
        )
    }
}
```

`@ValueFactories` grouped factories:

DSL:

```kotlin
hubdle {
    config {
        jvm = versions.jvm(21)
        platforms = listOf(
            versions.jvm(17),
            versions.android("35")
        )
    }
}
```

Implementation:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HiddenInDefinition
import org.gradle.declarative.dsl.model.annotations.ValueFactories
import org.gradle.features.binding.Definition
import org.gradle.kotlin.dsl.newInstance

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    public val jvm: Property<HubdlePlatformDefinition>
    public val platforms: ListProperty<HubdlePlatformDefinition>

    @get:Inject
    @get:HiddenInDefinition
    public val objects: ObjectFactory

    @get:ValueFactories
    public val versions: HubdleVersionFactories
        get() = objects.newInstance<HubdleVersionFactories>()
}

public abstract class HubdleVersionFactories @Inject constructor(
    private val objects: ObjectFactory,
) {
    public fun jvm(version: Int): HubdlePlatformDefinition =
        objects.newInstance<HubdlePlatformDefinition>().also { platform ->
            platform.kind.set("jvm")
            platform.version.set(version.toString())
        }

    public fun android(compileSdk: String): HubdlePlatformDefinition =
        objects.newInstance<HubdlePlatformDefinition>().also { platform ->
            platform.kind.set("android")
            platform.version.set(compileSdk)
        }
}

public interface HubdlePlatformDefinition {
    public val kind: Property<String>
    public val version: Property<String>
}
```

Rules:

- Use `@get:ValueFactories` on the property that returns the factory holder.
- The holder groups factory methods such as `versions.jvm(21)`.
- The holder is a factory namespace, not declarative state.
- Do not use the holder as a configuration block.
- Calls such as `versions.jvm(21)` are still value expressions; use them on the right side of an
  assignment or inside an assigned collection.
- Prefer plain factory functions when there are only one or two obvious factories on the current
  receiver.
- Prefer `@ValueFactories` when factory names need grouping, disambiguation, or a domain prefix.
- Implement the holder with managed objects or a concrete injected implementation that can create
  the returned model values.

## `@ElementFactoryName`

Use `@ElementFactoryName` on a named container element type to choose the DCL factory function name
for elements of that type.

DSL:

```kotlin
hubdle {
    config {
        reports {
            report("main") {
                title = "Main report"
            }
        }
    }
}
```

Implementation:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Nested
import org.gradle.declarative.dsl.model.annotations.ElementFactoryName
import org.gradle.features.binding.Definition

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    @get:Nested
    public val reports: NamedDomainObjectContainer<HubdleReportDefinition>
}

@ElementFactoryName("report")
public interface HubdleReportDefinition : Named {
    public val title: Property<String>
}
```

Rules:

- The annotation target is the element type, not the container property.
- The string value is the factory function name used inside the container block.
- Use it when the type name would produce a poor factory name or when the DSL should use a domain
  word like `report("main")`.

## `@HasDefaultValue`

Use `@HasDefaultValue` on a getter to tell the DCL schema whether a mutable property has a default.

DSL:

```kotlin
hubdle {
    config {
        requiredGroup = "com.acme"
    }
}
```

Implementation:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HasDefaultValue
import org.gradle.features.binding.Definition

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    @get:HasDefaultValue(false)
    public val requiredGroup: Property<String>

    @get:HasDefaultValue
    public val artifactName: Property<String>
}
```

Rules:

- `@HasDefaultValue` defaults to `true`.
- Use `@get:HasDefaultValue(false)` for a property the user must supply.
- The annotation describes schema/default metadata; it does not set the Gradle property value by
  itself.
- The apply action should still consume values with `orElse(...)` or validate required values.

## `@HiddenInDefinition`

Use `@HiddenInDefinition` to keep infrastructure out of the DCL schema.

DSL:

```kotlin
hubdle {
    config {
        foo = foo("main", 42)

        // objects = ...  // not part of the DSL
    }
}
```

Implementation:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HiddenInDefinition
import org.gradle.features.binding.Definition
import org.gradle.kotlin.dsl.newInstance

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    public val foo: Property<HubdleConfigFooDefinition>

    @get:Inject
    @get:HiddenInDefinition
    public val objects: ObjectFactory

    public fun foo(name: String, number: Int): HubdleConfigFooDefinition =
        objects.newInstance<HubdleConfigFooDefinition>().also { foo ->
            foo.name.set(name)
            foo.number.set(number)
        }
}

public interface HubdleConfigFooDefinition {
    public val name: Property<String>
    public val number: Property<Int>
}
```

Rules:

- Use `@get:HiddenInDefinition` for injected services and implementation helpers.
- Use type-level `@HiddenInDefinition` for infrastructure supertypes that should not contribute DSL
  members.
- Do not expose hidden types in public DCL-facing property/function signatures.

## `@VisibleInDefinition`

Use `@VisibleInDefinition` to expose a deliberate member from an otherwise hidden type or hidden
supertype.

DSL:

```kotlin
hubdle {
    config {
        displayName = "Documentation"

        // internalId = ... // hidden infrastructure is still not part of the DSL
    }
}
```

Implementation:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HiddenInDefinition
import org.gradle.declarative.dsl.model.annotations.VisibleInDefinition
import org.gradle.features.binding.Definition

@HiddenInDefinition
public interface HubdleInternalNaming {
    public val internalId: Property<String>

    @get:VisibleInDefinition
    public val displayName: Property<String>
}

public interface HubdleConfigDefinition :
    HubdleDefinition,
    HubdleInternalNaming,
    Definition<HubdleConfigBuildModel> {

    override val featureName: String
        get() = "config"
}
```

Rules:

- Use `@get:VisibleInDefinition` on the specific getter that should become part of the DCL schema.
- Use type-level `@VisibleInDefinition` only when all members of that type are intended to be
  visible.
- Do not put `@HiddenInDefinition` and `@VisibleInDefinition` on the same declaration.

## `@AccessFromCurrentReceiverOnly`

Use `@AccessFromCurrentReceiverOnly` when a property must be resolved only from the current receiver,
not from an outer receiver in a nested block.

DSL:

```kotlin
hubdle {
    config {
        id = "config-id"

        nested {
            id = "nested-id" // resolves only if nested has its own current-receiver id
        }
    }
}
```

Implementation:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Nested
import org.gradle.declarative.dsl.model.annotations.AccessFromCurrentReceiverOnly
import org.gradle.features.binding.Definition

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    @get:AccessFromCurrentReceiverOnly
    public val id: Property<String>

    @get:Nested
    public val nested: HubdleNestedDefinition
}

public interface HubdleNestedDefinition {
    @get:AccessFromCurrentReceiverOnly
    public val id: Property<String>
}
```

Rules:

- The annotation target is the getter.
- Use it to prevent accidental reads/writes through implicit outer receivers.
- Add a negative fixture if the behavior matters: nested access to an outer receiver should fail.

## General Rules

- Treat these annotations as DCL schema/evaluation metadata.
- Keep definitions declarative and side-effect free.
- Do not use deprecated DCL annotations.
- Add or update a `.gradle.dcl` functional fixture for every new annotation usage.
- Recheck behavior when upgrading Gradle because DCL APIs are still incubating.
