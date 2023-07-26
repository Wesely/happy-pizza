package tw.wesley.uiassignment.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import tw.wesley.uiassignment.data.local.AirData
import tw.wesley.uiassignment.databinding.ItemVerticalAirDataBinding

class VerticalAirDataAdapter(private val airDataList: List<AirData>) : RecyclerView.Adapter<VerticalAirDataAdapter.VerticalAirDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalAirDataViewHolder {
        val binding = ItemVerticalAirDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalAirDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerticalAirDataViewHolder, position: Int) {
        val data = airDataList[position]
        holder.binding.apply {
            tvSiteId.text = data.siteId
            tvSiteName.text = data.siteName
            tvStatus.text = data.status
            tvCounty.text = data.county
            tvPM25.text = data.pm25.toString()
        }
    }

    override fun getItemCount(): Int {
        Timber.d("getItemCount/airDataList.size=${airDataList.size}")
        return airDataList.size
    }

    class VerticalAirDataViewHolder(val binding: ItemVerticalAirDataBinding) : RecyclerView.ViewHolder(binding.root)
}
