package com.codebaron.nytimes.data

import com.codebaron.nytimes.data.network.Resource
import com.codebaron.nytimes.data.network.Status
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun exception() {
        val apiResponse = Resource.error<Any>("test error")
        Assert.assertEquals("test error", apiResponse.errorMessage)
        Assert.assertEquals(Status.ERROR, apiResponse.status)
    }

    @Test
    fun success() {
        val resource = Resource.success("test success")
        Assert.assertEquals("test success", resource.data)
        Assert.assertEquals(Status.SUCCESS, resource.status)
    }
}