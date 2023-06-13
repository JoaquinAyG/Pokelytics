package study.project.pokelytics.activities

import android.content.Context
import android.content.Intent
import study.project.pokelytics.R
import study.project.pokelytics.databinding.ActivityPolicyEngBinding

class PolicyActivity : ActivityBase<ActivityPolicyEngBinding>() {


    override fun getResourceLayout(): Int = R.layout.activity_policy_eng

    override fun initializeView() {

    }
    override fun bindViewModel() {
        binding.apply {
            lifecycleOwner = this@PolicyActivity
        }
    }
    override fun subscribe() {

    }

    companion object {
        fun getIntent(context: Context) = Intent(context, PolicyActivity::class.java)
    }
}