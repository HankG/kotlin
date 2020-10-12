/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.test.backend.handlers

import junit.framework.TestCase
import org.jetbrains.kotlin.backend.common.CodegenUtil.getMemberDeclarationsToGenerate
import org.jetbrains.kotlin.codegen.ClassFileFactory
import org.jetbrains.kotlin.codegen.GeneratedClassLoader
import org.jetbrains.kotlin.codegen.forTestCompile.ForTestCompileRuntime
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil.getFileClassInfoNoResolve
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.test.components.Assertions
import org.jetbrains.kotlin.test.model.ArtifactsResultsHandler
import org.jetbrains.kotlin.test.model.ResultingArtifact
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.utils.addToStdlib.runIf
import java.lang.reflect.Method
import java.net.URLClassLoader

class JvmBoxRunner(val assertions: Assertions) : ArtifactsResultsHandler<ResultingArtifact.Binary.Jvm>() {
    companion object {
        private val BOX_IN_SEPARATE_PROCESS_PORT = System.getProperty("kotlin.test.box.in.separate.process.port")
    }

    override fun processModule(module: TestModule, info: ResultingArtifact.Binary.Jvm) {
        val ktFiles = info.classFileFactory.inputFiles
        val classLoader = createClassLoader(info.classFileFactory)
        for (ktFile in ktFiles) {
            val className = ktFile.getFacadeFqName() ?: continue
            val clazz = classLoader.getGeneratedClass(className)
            val method = clazz.getBoxMethodOrNull() ?: continue
            callBoxMethodAndCheckResult(classLoader, clazz, method, unexpectedBehaviour = false)
            return
        }
        assertions.fail { "Can't find box methods" }
    }

    private fun callBoxMethodAndCheckResult(
        classLoader: URLClassLoader,
        clazz: Class<*>?,
        method: Method,
        unexpectedBehaviour: Boolean
    ) {
        val result = if (BOX_IN_SEPARATE_PROCESS_PORT != null) {
            TODO()
//            result = invokeBoxInSeparateProcess(classLoader, aClass)
        } else {
            val savedClassLoader = Thread.currentThread().contextClassLoader
            if (savedClassLoader !== classLoader) {
                // otherwise the test infrastructure used in the test may conflict with the one from the context classloader
                Thread.currentThread().contextClassLoader = classLoader
            }
            try {
                method.invoke(null) as String
            } finally {
                if (savedClassLoader !== classLoader) {
                    Thread.currentThread().contextClassLoader = savedClassLoader
                }
            }
        }
        if (unexpectedBehaviour) {
            TestCase.assertNotSame("OK", result)
        } else {
            assertions.assertEquals("OK", result)
        }
    }

    private fun createClassLoader(classFileFactory: ClassFileFactory): GeneratedClassLoader {
        val classLoader = ForTestCompileRuntime.runtimeJarClassLoader()
        // TODO: configure paths
        return GeneratedClassLoader(classFileFactory, classLoader)
    }

    private fun KtFile.getFacadeFqName(): String? {
        return runIf(getMemberDeclarationsToGenerate(this).isNotEmpty()) {
            getFileClassInfoNoResolve(this).facadeClassFqName.asString()
        }
    }

    private fun ClassLoader.getGeneratedClass(className: String): Class<*> {
        try {
            return loadClass(className)
        } catch (e: ClassNotFoundException) {
            assertions.fail { "No class file was generated for: $className" }
        }
    }

    private fun Class<*>.getBoxMethodOrNull(): Method? {
        return try {
            getMethod("box")
        } catch (e: NoSuchMethodException) {
            return null
        }
    }
}
