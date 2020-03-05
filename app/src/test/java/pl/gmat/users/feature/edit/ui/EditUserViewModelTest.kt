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
import pl.gmat.users.feature.edit.data.EditUserRepository
import pl.gmat.users.feature.edit.mapper.EditUserFormMapper
import pl.gmat.users.feature.edit.model.EditUserForm
import pl.gmat.users.feature.edit.model.EditUserMode
import pl.gmat.users.testAddress
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
    private val addresses = listOf(testAddress)

    @Before
    fun setup() = runBlockingTest {
        Dispatchers.setMain(testDispatcher)
        whenever(repositoryMock.loadAddresses()).thenReturn(emptyList())
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
        whenever(repositoryMock.loadAddresses()).thenReturn(addresses)
        whenever(mapperMock.toEditUserForm(testUser, addresses)).thenReturn(EditUserForm())
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
                submitButtonTextResId = updateMode.submitButtonResId,
                addresses = addresses,
                isAddNewAddressChecked = false
            )
        )
        verify(effectObserverMock).onChanged(EditUserEffect.InitializeForm(EditUserForm()))
        verifyNoMoreInteractions(stateObserverMock, effectObserverMock)
    }

    @Test
    fun `on choose existing address clicked`() {
        viewModel.onChooseExistingAddressClicked()

        inOrder(stateObserverMock) {
            verify(stateObserverMock).onChanged(
                EditUserState(
                    submitButtonTextResId = EditUserMode.Add.submitButtonResId
                )
            )
            verify(stateObserverMock).onChanged(
                EditUserState(
                    isAddNewAddressChecked = false,
                    submitButtonTextResId = EditUserMode.Add.submitButtonResId
                )
            )
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `on add new address clicked`() {
        viewModel.onAddNewAddressClicked()

        inOrder(stateObserverMock) {
            verify(stateObserverMock, times(2))
                .onChanged(
                    EditUserState(
                        submitButtonTextResId = EditUserMode.Add.submitButtonResId
                    )
                )
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `when is add mode on submit clicked`() = runBlockingTest {
        whenever(mapperMock.toUser(EditUserForm(), true, emptyList(), null))
            .thenReturn(testUser)
        viewModel.onSubmitClicked(EditUserForm())

        inOrder(stateObserverMock, repositoryMock, effectObserverMock) {
            verify(stateObserverMock).onChanged(
                EditUserState(
                    submitButtonTextResId = EditUserMode.Add.submitButtonResId
                )
            )
            verify(repositoryMock).insertOrUpdateUser(testUser, isNewAddress = true)
            verify(effectObserverMock).onChanged(EditUserEffect.Finish)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `when is update mode on submit clicked`() = runBlockingTest {
        val repositoryMock = mock<EditUserRepository>()
        val mapperMock = mock<EditUserFormMapper>()
        whenever(repositoryMock.loadAddresses()).thenReturn(addresses)
        whenever(mapperMock.toEditUserForm(testUser, addresses)).thenReturn(EditUserForm())
        val viewModel = EditUserViewModel(
            repositoryMock,
            updateMode,
            mapperMock
        )
        val stateObserverMock = mock<Observer<EditUserState>>()
        val effectObserverMock = mock<Observer<EditUserEffect>>()
        viewModel.state.observeForever(stateObserverMock)
        viewModel.effect.observeForever(effectObserverMock)

        whenever(mapperMock.toUser(EditUserForm(), false, addresses, testUser.id))
            .thenReturn(testUser)
        val state = EditUserState(
            isAddNewAddressChecked = false,
            addresses = addresses,
            submitButtonTextResId = updateMode.submitButtonResId
        )
        viewModel.state.value = state

        viewModel.onSubmitClicked(EditUserForm())

        inOrder(stateObserverMock, repositoryMock, effectObserverMock) {
            verify(stateObserverMock).onChanged(
                EditUserState(
                    isAddNewAddressChecked = false,
                    submitButtonTextResId = updateMode.submitButtonResId,
                    addresses = addresses
                )
            )
            verify(stateObserverMock).onChanged(state)
            verify(repositoryMock).insertOrUpdateUser(testUser, isNewAddress = false)
            verify(effectObserverMock).onChanged(EditUserEffect.Finish)
            verifyNoMoreInteractions()
        }
    }
}