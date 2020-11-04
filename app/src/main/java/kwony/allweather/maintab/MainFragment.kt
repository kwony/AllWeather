package kwony.allweather.maintab

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import dagger.hilt.android.AndroidEntryPoint
import kwony.allweather.R
import kwony.allweather.common.BaseFragment
import kwony.allweather.common.TypeAdapterItem
import kwony.allweather.databinding.FragmentMainBinding
import kwony.allweather.editor.AccountEditorDialogFragment
import kwony.allweather.maintab.drawer.*
import kwony.allweather.utils.FragmentUtils
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {

    lateinit var binding: FragmentMainBinding

    private val mainViewModel: MainViewModel by activityViewModels()

    private val listener = object: MainAccountAdapterListener {
        override fun clickAccountAdd() {
            AccountEditorDialogFragment.newInstance(true).show(childFragmentManager, null)
        }

        override fun clickAccount(item: AccountListItem) {
            mainViewModel.changeSelectedAccountId(item.accountMeta.accountId)
        }
    }

    private val accountAdapter = MainAccountAdapter(listener)

    private var detailOpen = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        observe()
    }

    private fun initView(view: View) {
        binding = FragmentMainBinding.bind(view)

        FragmentUtils.replaceFragment(
            childFragmentManager,
            binding.frManageContent.id,
            "manageFragment",
            MainTabFragment()
        )

        RxView.clicks(binding.hamburger)
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .doOnNext {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
            .subscribe()

        binding.drawerLayout.addDrawerListener(object: DrawerLayout.SimpleDrawerListener(){
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                binding.content.translationX = slideOffset * binding.naviContent.width
            }
        })

        binding.naviContent.accountRv.apply{
            adapter = accountAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        binding.naviContent.editIv.setOnClickListener {
            val dialog = AccountEditorDialogFragment.newInstance(false, mainViewModel.currentAccountLiveData.value!!.accountId)

            dialog.show(childFragmentManager, null)
        }

        binding.titleBarArrow.setOnClickListener {
            if (detailOpen) {
                binding.accountDetail.visibility = View.GONE
                binding.titleBarArrow.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_down))
                detailOpen = false
            } else {
                binding.accountDetail.visibility = View.VISIBLE
                binding.titleBarArrow.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_up))
                detailOpen = true
            }
        }
    }

    private fun observe() {
        mainViewModel.currentAccountLiveData.observe(viewLifecycleOwner, Observer {
            binding.titleBarTitle.text = it.accountName
            binding.naviContent.setSelectedAccount(it)
            binding.accountDetail.setSelectedAccount(it)
        })

        mainViewModel.accountListLiveData.observe(viewLifecycleOwner, Observer { accountList ->
            val adapterAccountItem = accountList.map {
                AccountTypeAdapterItem(it)
            }

            val adapterItem = ArrayList<TypeAdapterItem<MainAccountAdapterViewType>>().apply {
                addAll(adapterAccountItem)
                if (adapterAccountItem.size < 5) {
                    add(AccountTypeAdapterAddItem(null))
                }
            }

            accountAdapter.submitItems(adapterItem)
        })
    }

    override fun handleBackPressed(): Boolean {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }

        return super.handleBackPressed()
    }
}