package tw.wesley.uiassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tw.wesley.uiassignment.data.local.AirData
import tw.wesley.uiassignment.repo.AirDataRepository
import tw.wesley.uiassignment.viewmodel.AirQualityViewModel

@ExperimentalCoroutinesApi
class AirQualityViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: AirQualityViewModel
    private val airDataRepository = mockk<AirDataRepository>()

    private val horizontalAirDataObserver: Observer<List<AirData>> = mockk(relaxed = true)
    private val verticalAirDataObserver: Observer<List<AirData>> = mockk(relaxed = true)

    @Before
    fun setup() {
        val mockData = List(60) { index ->
            AirData(
                siteId = index.toString(),
                siteName = "siteName",
                county = "county",
                pm25 = index,
                status = "良好"
            )
        }
        every { airDataRepository.getListAirDataFlow() } returns flowOf(mockData)
        coEvery { airDataRepository.fetchAndStoreAirQualityData() } just Runs

        // 使用fake的Repo來創立ViewModel
        viewModel = AirQualityViewModel(airDataRepository)
        // 在這個VM上觀察在意的 LiveData
        viewModel.horizontalAirLiveData.observeForever(horizontalAirDataObserver)
        viewModel.verticalAirLiveData.observeForever(verticalAirDataObserver)
    }

    /**
     * Everytime we called viewModel.fetchAirData()
     * Make sure the horizontalAirLiveData and verticalAirLiveData are updated
     */
    @Test
    fun fetchAirData_ShouldUpdateLiveData() = runTest {
        viewModel.fetchAirData()

        // 確認呼叫 fetchAirData 的時候會呼叫到 etListAirDataFlow()
        verify { airDataRepository.getListAirDataFlow() }

        // 確認 LiveData 有被更新 exactly 1 次 (不要呼叫一次就重複更新多次 也不要呼叫了卻沒更新)
        verify(exactly = 1) { horizontalAirDataObserver.onChanged(any()) }
        verify(exactly = 1) { verticalAirDataObserver.onChanged(any()) }
    }
}
