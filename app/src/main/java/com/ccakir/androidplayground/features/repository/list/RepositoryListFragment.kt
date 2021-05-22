package com.ccakir.androidplayground.features.repository.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ccakir.androidplayground.databinding.FragmentRepositoryListBinding


class RepositoryListFragment : Fragment() {

    private lateinit var binding: FragmentRepositoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}