plugins {
    id("build-jvm")
    alias(libs.plugins.openapi.generator)
}

sourceSets {
    main {
        java.srcDirs(layout.buildDirectory.dir("generate-resources/main/src/main/kotlin"))
    }
}

/**
 * Генерация моделей OpenAPI
 */
openApiGenerate {
    val openapiGroup = "${rootProject.group}.api"
    generatorName = "kotlin"
    packageName = openapiGroup
    apiPackage = "$openapiGroup.api"
    modelPackage = "$openapiGroup.models"
    invokerPackage = "$openapiGroup.invoker"
    inputSpec = rootProject.ext["spec"] as String

    //Генерим только модели
    globalProperties.apply {
        put("models", "")
        put("modelDocs", "false")
    }
    //Дополнительные параметры
    configOptions.set(
        mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "serializationLibrary" to "jackson",
            "collectionType" to "list"
        )
    )

    typeMappings.apply {
        put("double", "BigDecimal")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.jackson.kotlin)
    implementation(libs.jackson.datatype)
    testImplementation(kotlin("test-junit"))
}

tasks {
    compileKotlin {
        dependsOn(openApiGenerate)
    }
}