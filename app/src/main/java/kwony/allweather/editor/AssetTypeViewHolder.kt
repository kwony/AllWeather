package kwony.allweather.editor

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.databinding.FragmentMainBinding
import kwony.allweather.databinding.ViewholderAssetTypeBinding

class AssetTypeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ViewholderAssetTypeBinding

    companion object {
        @JvmStatic
        fun newInstance(parent: ViewGroup): AssetTypeViewHolder {

        }
    }

}