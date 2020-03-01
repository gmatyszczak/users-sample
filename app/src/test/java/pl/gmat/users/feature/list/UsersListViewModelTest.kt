package pl.gmat.users.feature.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class UsersListViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var effectObserverMock: Observer<UsersListEffect>

    @Mock
    private lateinit var stateObserverMock: Observer<UsersListState>

    @Mock
    private lateinit var usersListRepositoryMock: UsersListRepository

    private lateinit var viewModel: UsersListViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() = runBlockingTest {
        Dispatchers.setMain(testDispatcher)
        whenever(usersListRepositoryMock.loadUsers()).thenReturn(flowOf(listOf(testUser)))
        viewModel = UsersListViewModel(usersListRepositoryMock)
        viewModel.effect.observeForever(effectObserverMock)
        viewModel.state.observeForever(stateObserverMock)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on add clicked`() {
        viewModel.onAddClicked()

        inOrder(stateObserverMock, effectObserverMock) {
            verify(stateObserverMock).onChanged(UsersListState(users = listOf(testUser)))
            verify(effectObserverMock).onChanged(UsersListEffect.ShowAddUser)
            verifyNoMoreInteractions()
        }
    }
}