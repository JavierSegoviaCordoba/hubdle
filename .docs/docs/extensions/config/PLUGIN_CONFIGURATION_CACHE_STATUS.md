# Estado de compatibilidad con Configuration Cache (plugins usados en Hubdle)

Fecha de análisis: 2026-05-07

Este informe cruza:

- Repos/issues de plugins usados por Hubdle (cerrados, abiertos y contexto de soporte).
- Issue de Gradle: https://github.com/gradle/gradle/issues/13490 (tracking oficial de plugins comunitarios).

> Nota: no fue posible consultar directamente `docs.gradle.org` desde este entorno (fallo DNS), así que se usó como fuente oficial alternativa el issue #13490 de Gradle, que mantiene la tabla de estado de plugins.

## Resumen por plugin (priorizados)

| Plugin ID | Estado observado | Evidencia principal | Conclusión práctica |
|---|---|---|---|
| `io.gitlab.arturbosch.detekt` | Soporte reportado | Gradle #13490 marca ✅ para Detekt; issue de soporte cerrado: https://github.com/detekt/detekt/issues/2811 | Compatible en general; mantener versión reciente. |
| `org.jetbrains.dokka` | Soporte reportado con fixes históricos | Gradle #13490 marca ✅ (`2.0.0`) y referencia https://github.com/Kotlin/dokka/issues/1217 (cerrado); hay issues posteriores puntuales (ej. warnings/duplicados). | Compatible base, con posibles regresiones puntuales según versión. |
| `com.squareup.sqldelight` / `app.cash.sqldelight` | Soporte reportado + historial de incidencias | Gradle #13490 marca ✅ para `com.squareup.sqldelight`; issues cerrados de compatibilidad: https://github.com/sqldelight/sqldelight/issues/1947, https://github.com/sqldelight/sqldelight/issues/2810, https://github.com/sqldelight/sqldelight/issues/2811, https://github.com/sqldelight/sqldelight/issues/2513, https://github.com/sqldelight/sqldelight/issues/2530, https://github.com/sqldelight/sqldelight/issues/6085 | Soporte existente, pero conviene vigilar cambios de versión. |
| `com.vanniktech.maven.publish` | Soporte reportado | Gradle #13490 marca ✅ (`0.34.0`) con issue ligado https://github.com/vanniktech/gradle-maven-publish-plugin/issues/259 (cerrado); adicionalmente https://github.com/vanniktech/gradle-maven-publish-plugin/issues/1220 (cerrado) | Compatible con mejoras continuas en config-cache. |
| `com.diffplug.spotless` | Soporte declarado, estado mixto en práctica | Gradle #13490 marca ✅ (`7.0.0`) con referencia https://github.com/diffplug/spotless/issues/2318 (cerrado). También hay cerrados: https://github.com/diffplug/spotless/issues/2585, https://github.com/diffplug/spotless/issues/2372; y abiertos recientes de config-cache. | Compatible en parte; revisar versión exacta y tareas usadas. |
| `org.sonarqube` | Compatibilidad parcial | Gradle #13490 marca 🙈 para `org.sonarqube`; en repo hay pruebas específicas de config-cache (`ConfigurationCacheIT` / `ConfigCache`). | Funciona pero puede desactivar cache en ciertos casos/tareas. |
| `com.adarshr.test-logger` (repo `radarsh/gradle-test-logger-plugin`) | Soporte no consolidado | Issue abierto de soporte: https://github.com/radarsh/gradle-test-logger-plugin/issues/327; issues cerrados relacionados: https://github.com/radarsh/gradle-test-logger-plugin/issues/339, https://github.com/radarsh/gradle-test-logger-plugin/issues/163, https://github.com/radarsh/gradle-test-logger-plugin/issues/195 | Riesgo alto de problemas según versión/uso. |
| `org.jetbrains.changelog` | Señales positivas de soporte | Issues cerrados: https://github.com/JetBrains/gradle-changelog-plugin/issues/15 y https://github.com/JetBrains/gradle-changelog-plugin/issues/141 | Compatible, con fixes ya aterrizados. |

## Sobre el issue de Gradle #13490

- Issue: https://github.com/gradle/gradle/issues/13490
- Propósito: seguimiento centralizado de adopción de Configuration Cache en plugins comunitarios.
- Estado del issue: cerrado (`completed`), pero su tabla es una referencia útil de soporte por plugin, versión y links a incidencias.

## Lectura recomendada para decisiones en Hubdle

1. Tomar #13490 como referencia base de soporte.
2. Confirmar con issues recientes del plugin (algunos están ✅ en tabla, pero con incidencias abiertas nuevas).
3. Priorizar upgrades en plugins con estado mixto (`🙈`, `⚠️`, o con muchos issues recientes de config-cache).

## Plugins del enum de Hubdle no cubiertos en profundidad en esta pasada

Ejemplos: `org.jetbrains.compose`, `org.jetbrains.kotlinx.kover`, `org.jetbrains.kotlin.plugin.serialization`, `org.jetbrains.kotlin.plugin.compose`, `com.gradle.plugin-publish`, entre otros.

Si hace falta, se puede ampliar este mismo informe con una segunda pasada plugin por plugin.
