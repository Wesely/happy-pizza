package tw.wesley.uiassignment.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import tw.wesley.uiassignment.R
import tw.wesley.uiassignment.databinding.ActivityMainBinding
import tw.wesley.uiassignment.viewmodel.AirQualityViewModel

@AndroidEntryPoint // For HiltViewModel is used in our AirQualityViewModel
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var searchView: SearchView

    // Use this delegate to auto create/destroy on correct lifecycle
    private val viewModel: AirQualityViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged") // it's fully changed so we have to use this. Considering to use paging schema to ease the effort
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        // 初始化 RecyclerView 和 Adapters
        val horizontalAdapter = HorizontalAirDataAdapter(emptyList())
        val verticalAdapter = VerticalAirDataAdapter(emptyList()) { data ->
            // Example: 沙鹿的空氣不良好
            Toast.makeText(baseContext, getString(R.string.home_item_toast, data.siteName), Toast.LENGTH_SHORT).show()
        }

        with(binding) {
            setSupportActionBar(toolbar)
            setContentView(root)

            horizontalRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = horizontalAdapter
            }
            verticalRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = verticalAdapter
            }
        }

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setTitle(R.string.home_title)

        // Ask to start getting the data
        lifecycleScope.launch {
            viewModel.fetchAirData()
        }

        lifecycleScope.launch {
            // collectLatest() would cancel previous task when a new task has come
            viewModel.uiState.collectLatest { uiState ->
                if (uiState.isSearching) {
                    // 搜尋功能啟用中
                    binding.horizontalRecyclerView.isVisible = false
                    if (uiState.searchingKeyword.isBlank()) {
                        // 還沒輸入任何字: 蓋掉主畫面顯示提示
                        Timber.d("還沒輸入任何字")
                        binding.centerSearchHint.isVisible = true
                        binding.centerSearchHint.setText(R.string.search_hint)
                        verticalAdapter.updateData(emptyList())
                    } else {
                        // 有輸入文字
                        // 找不到的時候，顯示蓋板提示。反之則隱藏蓋板提示
                        Timber.d("有輸入文字")
                        binding.centerSearchHint.isVisible = uiState.searchResultAirDataList.isEmpty()
                        binding.centerSearchHint.text = getString(R.string.search_not_found, uiState.searchingKeyword)
                        verticalAdapter.updateData(uiState.searchResultAirDataList)
                    }
                    verticalAdapter.notifyDataSetChanged()
                } else {
                    // 瀏覽功能啟用中 (非搜尋中)
                    binding.horizontalRecyclerView.isVisible = true
                    binding.centerSearchHint.isVisible = false
                    verticalAdapter.updateData(uiState.verticalAirDataList)
                    horizontalAdapter.updateData(uiState.horizontalAirDataList)
                    verticalAdapter.notifyDataSetChanged()
                    horizontalAdapter.notifyDataSetChanged()
                }
                Timber.d("collect/searchresult/${uiState.searchResultAirDataList.map { it.siteName }}")
            }
        }

    }

    // https://developer.android.com/develop/ui/views/search/training/setup
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val menuItem = menu.findItem(R.id.menu_item_search)
        searchView = (menuItem.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = getString(R.string.search_hint)
            isIconified = true
            isIconifiedByDefault = false
            onActionViewExpanded()

            // inside onCreateOptionsMenu, if viewModel is dirty, it means it's restoring.
            if (viewModel.uiState.value.isSearching) {
                menuItem.expandActionView()
                setQuery(viewModel.uiState.value.searchingKeyword, false)
            }


            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            // Show/Hide softKeyboard based on the focus state.
            setOnQueryTextFocusChangeListener { v, hasFocus ->
                Timber.d("setOnQueryTextFocusChangeListener/$hasFocus")
                viewModel.activateSearching(hasFocus)
                if (hasFocus) {
                    // flag = InputMethodManager.IMPLICIT won't work
                    inputMethodManager.showSoftInput(v, 0)
                } else {
                    // flag = InputMethodManager.IMPLICIT won't work
                    inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Timber.d("onQueryTextSubmit/$query")
                    viewModel.queryAirData(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Timber.d("onQueryTextChange/$newText")
                    viewModel.queryAirData(newText)
                    return true
                }
            })

            setOnCloseListener {
                viewModel.activateSearching(false)
                true
            }

        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_item_search -> {
            // User chose the "Search" item
            viewModel.activateSearching(true)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}