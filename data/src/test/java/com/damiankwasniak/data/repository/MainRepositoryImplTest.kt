package com.damiankwasniak.data.repository

import com.nhaarman.mockito_kotlin.verify
import com.damiankwasniak.data.api.MainApiService
import com.damiankwasniak.data.createSuccessResponse
import com.damiankwasniak.domain.api.model.PostApiModel
import com.damiankwasniak.domain.repository.MainRepository
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response
import rx.Observable
import rx.observers.TestSubscriber
import kotlin.test.assertEquals

class MainRepositoryImplTest {

    private lateinit var target: MainRepository

    @Mock
    lateinit var mainApiService: MainApiService

    private var testSubscriber: TestSubscriber<List<PostApiModel>> = TestSubscriber()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = MainRepositoryImpl(mainApiService)
    }

    @Test
    fun testPositiveCase() {
        //given
        val result = listOf(PostApiModel())
        `when`(mainApiService.getPosts()).thenReturn(Observable.just(createSuccessResponse(200,result)))

        //when
        target.getPosts().subscribe(testSubscriber)

        //then
        verify(mainApiService).getPosts()
        testSubscriber.assertNoErrors()
        assertEquals<List<PostApiModel>>(testSubscriber.onNextEvents[0], result)
        testSubscriber.assertCompleted()
    }


    @Test
    fun testNegativeCase() {
        //given
        val error = Exception()
        `when`(mainApiService.getPosts()).thenReturn(Observable.error(error))

        //when
        target.getPosts().subscribe(testSubscriber)

        //then
        verify(mainApiService).getPosts()
        testSubscriber.assertError(error)
        testSubscriber.assertNotCompleted()
    }


}