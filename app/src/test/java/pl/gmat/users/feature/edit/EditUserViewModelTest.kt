package pl.gmat.users.feature.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.times
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
import pl.gmat.users.common.model.Address
import pl.gmat.users.common.model.User

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EditUserViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var stateObserverMock: Observer<EditUserState>

    @Mock
    private lateinit var effectObserverMock: Observer<EditUserEffect>

    @Mock
    private lateinit var repositoryMock: EditUserRepository

    private lateinit var viewModel: EditUserViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() = runBlockingTest {
        Dispatchers.setMain(testDispatcher)
        whenever(repositoryMock.loadAddresses()).thenReturn(emptyList())
        viewModel = EditUserViewModel(repositoryMock)
        viewModel.state.observeForever(stateObserverMock)
        viewModel.effect.observeForever(effectObserverMock)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on choose existing address clicked`() {
        viewModel.onChooseExistingAddressClicked()

        inOrder(stateObserverMock) {
            verify(stateObserverMock).onChanged(EditUserState())
            verify(stateObserverMock).onChanged(EditUserState(isAddNewAddressChecked = false))
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `on add new address clicked`() {
        viewModel.onAddNewAddressClicked()

        inOrder(stateObserverMock) {
            verify(stateObserverMock, times(2)).onChanged(EditUserState())
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `when is add new address checked on add clicked`() = runBlockingTest {
        viewModel.onAddClicked(EditUserForm())

        inOrder(stateObserverMock, repositoryMock, effectObserverMock) {
            verify(stateObserverMock).onChanged(EditUserState())
            verify(repositoryMock).addUser(User(), isNewAddress = true)
            verify(effectObserverMock).onChanged(EditUserEffect.Finish)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `when is add new address not checked on add clicked`() = runBlockingTest {
        val address = Address(id = 10, value = "test")
        val state = EditUserState(isAddNewAddressChecked = false, addresses = listOf(address))
        viewModel.state.value = state

        viewModel.onAddClicked(EditUserForm())

        inOrder(stateObserverMock, repositoryMock, effectObserverMock) {
            verify(stateObserverMock).onChanged(EditUserState())
            verify(stateObserverMock).onChanged(state)
            verify(repositoryMock).addUser(User(address = address), isNewAddress = false)
            verify(effectObserverMock).onChanged(EditUserEffect.Finish)
            verifyNoMoreInteractions()
        }
    }
}