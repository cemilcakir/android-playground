package com.ccakir.androidplayground.features.repository.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ccakir.androidplayground.databinding.FragmentRepositoryDetailsBinding


class RepositoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRepositoryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}