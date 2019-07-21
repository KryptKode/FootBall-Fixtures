package com.kryptkode.footballfixtures.app.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import java.io.File

abstract class BaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

}