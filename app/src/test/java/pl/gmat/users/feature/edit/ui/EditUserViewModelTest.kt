package pl.gmat.users.feature.edit.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
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
import pl.gmat.users.feature.edit.data.EditUserRepository
import pl.gmat.users.feature.edit.mapper.EditUserFormMapper
import pl.gmat.users.feature.edit.model.EditUserForm
import pl.gmat.users.feature.edit.model.EditUserMode
import pl.gmat.users.testUser

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

    @Mock
    private lateinit var mapperMock: EditUserFormMapper

    private lateinit var viewModel: EditUserViewModel

    private val testDispatcher = TestCoroutineDispatcher()
    private val updateMode = EditUserMode.Update(testUser)

    @Before
    fun setup() = runBlockingTest {
        Dispatchers.setMain(testDispatcher)
        viewModel = EditUserViewModel(
            repositoryMock,
            EditUserMode.Add,
            mapperMock
        )
        viewModel.state.observeForever(stateObserverMock)
        viewModel.effect.observeForever(effectObserverMock)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when mode update on init`() = runBlockingTest {
        whenever(mapperMock.toEditUserForm(testUser)).thenReturn(EditUserForm())
        val viewModel = EditUserViewModel(
            repositoryMock,
            updateMode,
            mapperMock
        )
        val stateObserverMock = mock<Observer<EditUserState>>()
        val effectObserverMock = mock<Observer<EditUserEffect>>()
        viewModel.state.observeForever(stateObserverMock)
        viewModel.effect.observeForever(effectObserverMock)

        verify(stateObserverMock).onChanged(
            EditUserState(
                submitButtonTextResId = updateMode.submitButtonResId
            )
        )
        verify(effectObserverMock).onChanged(EditUserEffect.InitializeForm(EditUserForm()))
        verifyNoMoreInteractions(stateObserverMock, effectObserverMock)
    }

    @Test
    fun `when is add mode on submit clicked`() = runBlockingTest {
        whenever(mapperMock.toUser(EditUserForm(), null))
            .thenReturn(testUser)
        viewModel.onSubmitClicked(EditUserForm())

        inOrder(stateObserverMock, repositoryMock, effectObserverMock) {
            verify(stateObserverMock).onChanged(
                EditUserState(
                    submitButtonTextResId = EditUserMode.Add.submitButtonResId
                )
            )
            verify(repositoryMock).insertOrUpdateUser(testUser)
            verify(effectObserverMock).onChanged(EditUserEffect.Finish)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `when is update mode on submit clicked`() = runBlockingTest {
        val repositoryMock = mock<EditUserRepository>()
        val mapperMock = mock<EditUserFormMapper>()
        whenever(mapperMock.toEditUserForm(testUser)).thenReturn(EditUserForm())
        val viewModel = EditUserViewModel(
            repositoryMock,
            updateMode,
            mapperMock
        )
        val stateObserverMock = mock<Observer<EditUserState>>()
        val effectObserverMock = mock<Observer<EditUserEffect>>()
        viewModel.state.observeForever(stateObserverMock)
        viewModel.effect.observeForever(effectObserverMock)

        whenever(mapperMock.toUser(EditUserForm(), testUser.id))
            .thenReturn(testUser)
        val state = EditUserState(
            submitButtonTextResId = updateMode.submitButtonResId
        )
        viewModel.state.value = state

        viewModel.onSubmitClicked(EditUserForm())

        inOrder(stateObserverMock, repositoryMock, effectObserverMock) {
            verify(stateObserverMock).onChanged(
                EditUserState(
                    submitButtonTextResId = updateMode.submitButtonResId
                )
            )
            verify(stateObserverMock).onChanged(state)
            verify(repositoryMock).insertOrUpdateUser(testUser)
            verify(effectObserverMock).onChanged(EditUserEffect.Finish)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `on add new address clicked`() {
        viewModel.onAddNewAddressClicked()

        inOrder(stateObserverMock, effectObserverMock) {
            verify(stateObserverMock).onChanged(
                EditUserState(
                    submitButtonTextResId = EditUserMode.Add.submitButtonResId
                )
            )
            verify(stateObserverMock).onChanged(
                EditUserState(
                    submitButtonTextResId = EditUserMode.Add.submitButtonResId,
                    addresses = listOf(Address(id = -1))
                )
            )
            verifyNoMoreInteractions()
        }
    }
}