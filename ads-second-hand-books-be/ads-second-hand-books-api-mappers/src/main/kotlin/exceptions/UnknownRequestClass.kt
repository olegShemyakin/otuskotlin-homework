package org.akira.otuskotlin.ads.mappers.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz can not be mapped to AdContext") {
}