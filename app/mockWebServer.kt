lateinit var mockWebServer: MockWebServer

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    @Throws(IOException::class)
    fun mockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    @Throws(IOException::class)
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Throws(IOException::class)
    fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, Collections.emptyMap())
    }

    @Throws(IOException::class)
    fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = BaseApiTest::class.java.classLoader?.getResourceAsStream(
            String.format(
                "api-response/%s",
                fileName
            )
        )!!
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }

    fun createService(clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(clazz)
    }