package com.example.musicapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.musicapp.API.ApiClient
import com.example.musicapp.API.ApiServices
import com.example.musicapp.MainApp.Companion.navController
import com.example.musicapp.dataFind.Find
import com.example.musicapp.dataSong.Song
import com.example.musicapp.databinding.FragmentFindBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class FindFragment : Fragment() {
    lateinit var binding: FragmentFindBinding
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var findController: NavController
    }
    private val api: ApiServices by lazy {
        ApiClient().getClient().create(ApiServices::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindBinding.inflate(layoutInflater)
        val view = binding.root
        val navHostFragment = childFragmentManager.findFragmentById(R.id.find_container) as NavHostFragment
        findController = navHostFragment.navController
        binding.closeBtn.visibility = View.GONE
        binding.closeBtn.setOnClickListener() {
            binding.findTxt.setText("")
            findController.navigate(R.id.recommend)
        }
        binding.findTxt.imeOptions = EditorInfo.IME_ACTION_SEARCH
        binding.findTxt.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                var bundle = Bundle()
                bundle.putString("query", binding.findTxt.text.toString())
                findController.navigate(R.id.result, bundle)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.findTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isNotEmpty() == true) {
                    binding.closeBtn.visibility = View.VISIBLE
                } else {
                    binding.closeBtn.visibility = View.GONE
                    findController.navigate(R.id.recommend)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.backBtn.setOnClickListener() {
            navController.navigate(R.id.discovery)
        }
        return view
    }
}