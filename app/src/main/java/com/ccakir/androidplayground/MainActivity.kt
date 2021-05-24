package com.ccakir.androidplayground

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ccakir.androidplayground.auth.IAuthManager
import com.ccakir.androidplayground.databinding.ActivityMainBinding
import com.ccakir.androidplayground.features.login.ui.LoginFragmentDirections
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val authManager: IAuthManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AndroidPlayground)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.loginFragment,
                R.id.repositoryListFragment
            )
        )

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNav.setupWithNavController(navController)

        addDestinationListener()

        lifecycleScope.launchWhenCreated {
            navigateToRepositoryListIfLoggedIn()
        }
    }

    private fun addDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

    private suspend fun navigateToRepositoryListIfLoggedIn() {
        if (!authManager.getUsername().isNullOrBlank()) {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToRepositoryListFragment())
        }
    }
}