package com.ccakir.androidplayground.features.repository.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ccakir.androidplayground.databinding.FragmentRepositoryDetailsBinding


class RepositoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRepositoryDetailsBinding

    private val args: RepositoryDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryDetailsBinding.inflate(layoutInflater, container, false)
        binding.txtRepositoryName.text = args.repository.name
        return binding.root
    }
}