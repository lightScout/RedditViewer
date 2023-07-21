package com.lightscout.redditviewer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutinesTestExtension(
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : BeforeTestExecutionCallback, AfterTestExecutionCallback, ParameterResolver {

    override fun beforeTestExecution(context: ExtensionContext?) {
        Dispatchers.setMain(dispatcher)
    }


    override fun afterTestExecution(context: ExtensionContext?) {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    override fun supportsParameter(
        parameterContext: ParameterContext?,
        extensionContext: ExtensionContext?
    ): Boolean {
        return parameterContext?.parameter?.type == TestCoroutineDispatcher::class.java
    }

    override fun resolveParameter(
        parameterContext: ParameterContext?,
        extensionContext: ExtensionContext?
    ): Any {
        return dispatcher
    }
}