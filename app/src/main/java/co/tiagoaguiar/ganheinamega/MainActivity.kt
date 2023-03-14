package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import co.tiagoaguiar.ganheinamega.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var preference:SharedPreferences




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonGenerateNumber.setOnClickListener(this)
        supportActionBar?.hide()
        preference= getSharedPreferences("key", Context.MODE_PRIVATE)
        val result = preference.getString("result", null)
        if (result != null){
            binding.textResult.text = "Última posta: $result"
        }else{
            binding.textResult.text = null
        }


    }

    override fun onClick(view: View) {
        if(view.id == R.id.button_generate_number){
            val text = binding.editNumber.text.toString()
            val textResult = binding.textResult
           numberGenerate(text, textResult)
        }
    }

    private fun numberGenerate(text:String, textResult: TextView){
        if (isValidateInput(text)){
            val intText = text.toInt()
            if (intText in 6..15){
                val random= Random()
                val numbers = mutableSetOf<Int>()
                while (true){
                    var number = random.nextInt(59) // 0 até 59
                    numbers.add(number+1)
                    if (numbers.size == intText ){
                        break
                    }
                }
                textResult.text = numbers.joinToString(" - ")
                val editor = preference.edit()
                editor.putString("result", textResult.text.toString())
                editor.apply()

            }else{
                Toast.makeText(this,
                    R.string.entry_out_scope,
                    Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this, R.string.input_empty, Toast.LENGTH_LONG).show()
        }
    }
    private fun isValidateInput(text: String): Boolean{
        return text!=""
    }
}