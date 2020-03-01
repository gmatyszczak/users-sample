package pl.gmat.users.feature.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.gmat.users.testUser

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserDetailsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var stateObserverMock: Observer<UserDetailsState>

    @Mock
    private lateinit var effectObserverMock: Observer<UserDetailsEffect>

    @Mock
    private lateinit var repositoryMock: UserDetailsRepository

    private lateinit var viewModel: UserDetailsViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() = runBlockingTest {
        Dispatchers.setMain(testDispatcher)
        whenever(repositoryMock.loadUser()).thenReturn(testUser)
        viewModel = UserDetailsViewModel(repositoryMock)
        viewModel.state.observeForever(stateObserverMock)
        viewModel.effect.observeForever(effectObserverMock)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on init`() {
        verify(stateObserverMock).onChanged(UserDetailsState(user = testUser))
    }

    @Test
    fun `on delete clicked`() = runBlockingTest {
        viewModel.onDeleteClicked()

        inOrder(stateObserverMock, repositoryMock, effectObserverMock) {
            verify(stateObserverMock).onChanged(UserDetailsState(user = testUser))
            verify(repositoryMock).deleteUser()
            verify(effectObserverMock).onChanged(UserDetailsEffect.Finish)
            verifyNoMoreInteractions()
        }
    }
}