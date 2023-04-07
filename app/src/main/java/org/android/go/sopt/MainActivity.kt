package org.android.go.sopt

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.android.go.sopt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var id:String
    private lateinit var password:String
    private lateinit var name:String
    private lateinit var speciality:String

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK){
                id = result.data?.getStringExtra("id") ?: ""
                password = result.data?.getStringExtra("password")?:""
                name= result.data?.getStringExtra("name")?:""
                speciality = result.data?.getStringExtra("speciality")?:""


            }
        }



        binding.loginButton.setOnClickListener{


            if(id== binding.id.text.toString()){



                val intent = Intent(this,IntroduceActivity::class.java)

                intent.putExtra("name",name)
                intent.putExtra("speciality",speciality)
                startActivity(intent)
            }

        }





        binding.signupButton.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            resultLauncher.launch(intent)
        }

        binding.root.setOnClickListener {
            hidKeyboard()
        }



    }

    private fun hidKeyboard() {
        val imm:InputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus?.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)

    }






}