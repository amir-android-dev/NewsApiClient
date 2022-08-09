package com.amir.newsapiclient.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewsAPIServiceTest {
    //1
    //we need to create object reference variables for NewsAPIService and MockWebServer.
    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    //2
    @Before
    fun setUp() {
        server = MockWebServer()
        // construct the service using Retrofit builder.
        service = Retrofit.Builder()
            .baseUrl(server.url("")) //here we have to pass url. But as we said we use fake url from moke
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    /*
    We created this JSON response file named newsresponse. But, MockWebServer can’t read this response

    from the JSON file directly. We need to create a file reader to read the contents of the JSON file and convert them into a String object.
     */
    //4
    private fun enqueueMockResponse(fileName: String) {
        //get resource file with json as a input stream
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        //get the data source from the stream and set it in memory buffer
        val source = inputStream.source().buffer()
        //create an instance of mockResponse
        val mockResponse = MockResponse()
        //set the body of mockResponse passing the string format of the source
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)

    }
//subject under test getTopHeadLines of NewsApiService
    //action is send request
    //result is received expected
    @Test
    fun getTopHeadLines_sendRequest_receivedExpected(){
    //Runblocking is the coroutine builder we use for testing. This runs a new coroutine and blocks the current thread until its completion.
        runBlocking {
            enqueueMockResponse("newsresponse.json")  //first of all, we need to enqueue the mock response passing the local json file name.
      val responseBody=  service.getTopHeadLines("us",1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=1038b0f7c65d46d2becf96ff73f79f8b")
        }

    }
    @Test
    fun getTopHeadLines_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody=  service.getTopHeadLines("us",1).body()
            val articlesList = responseBody!!.articles
            assertThat(articlesList.size).isEqualTo(20)
        }
    }
    @Test
    fun getTopHeadLines_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody=  service.getTopHeadLines("us",1).body()
            val articlesList = responseBody!!.articles
       val article = articlesList[3]
            assertThat(article.author).isEqualTo("Anthony Franco")
            assertThat(article.url).isEqualTo("https://www.mlbtraderumors.com/2022/08/matt-carpenter-suffers-foot-fracture.html")
            assertThat(article.publishedAt).isEqualTo("2022-08-09T04:24:37Z")
        }
    }

    //3
    @After
    fun tearDown() {
        server.shutdown()
    }
}