package com.damiankwasniak.domain.interactor

import com.damiankwasniak.domain.api.model.PostApiModel
import com.damiankwasniak.domain.mapper.PostApiModelToViewModelMapper
import com.damiankwasniak.domain.repository.MainRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import rx.Observable
import rx.observers.TestSubscriber
import kotlin.test.assertEquals

class AuthInteractorTest {

    private lateinit var target: AuthInteractor

    @Mock
    lateinit var mainRepository: MainRepository

    @Mock
    lateinit var mapper: PostApiModelToViewModelMapper

    private var mainTestSubscriber = TestSubscriber<List<PostViewModel>>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = AuthInteractor(mainRepository, mapper)
    }

    @Test
    fun testPositiveCase() {
        //given
        val result: List<PostApiModel> = listOf(PostApiModel())
        val mappedResult = PostViewModel()
        `when`(mainRepository.getPosts()).thenReturn(Observable.just(result))
        `when`(mapper.mapPostApiModelToViewModel(result[0])).thenReturn(mappedResult)

        //when
        target.getPosts().subscribe(mainTestSubscriber)

        //then
        mainTestSubscriber.assertNoErrors()
        assertEquals(mainTestSubscriber.onNextEvents[0][0], mappedResult)
        mainTestSubscriber.assertCompleted()

    }

    @Test
    fun testNegativeCase() {
        //given
        val error = Exception()
        `when`(mainRepository.getPosts()).thenReturn(Observable.error(error))

        //when
        target.getPosts().subscribe(mainTestSubscriber)

        //then
        mainTestSubscriber.assertError(error)
        mainTestSubscriber.assertNotCompleted()
    }

}