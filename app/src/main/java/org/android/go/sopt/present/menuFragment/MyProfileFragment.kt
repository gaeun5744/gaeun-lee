package org.android.go.sopt.present.menuFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.android.go.sopt.databinding.FragmentMyProfileBinding
import org.android.go.sopt.present.loginPage.LoginActivity
import org.android.go.sopt.present.loginPage.MySharedPreferences
import org.android.go.sopt.present.viewModel.LoginViewModel
import org.android.go.sopt.remote.ServicePool
import org.android.go.sopt.remote.remoteData.model.MyProfileDto
import org.android.go.sopt.util.makeToastMessage
import retrofit2.Call
import retrofit2.Response

class MyProfileFragment : Fragment() {
    private var _binding: FragmentMyProfileBinding? = null
    private val binding: FragmentMyProfileBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }

    private val myProfileService = ServicePool.loginPageService
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView( // 뷰를 만든다 << 이때 초기화하면 좋음
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { // 이제 반환하는 View가 Null일 수 없기 때문에, ?를 지워주셔도 됩니다.
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 뷰가 만들어졌다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfile()
        clickLogOut()

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun getProfile() {

        viewModel.myProfile(MySharedPreferences.getUserId(requireContext()))

        viewModel.myProfile.observe(this){
            binding.name.text = "이름 : ${it.data.name}"
            binding.specialty.text = "특기 : ${it.data.skill}"
        }

    }

    private fun clickLogOut() {
        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Go SOPT").setMessage("로그아웃 하시겠나요?")
                .setPositiveButton("네") { _, _ ->
                    MySharedPreferences.clearUser(requireContext())
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()

                }.setNegativeButton("아니요", null).setCancelable(true)
            builder.show()

        }
    }
}