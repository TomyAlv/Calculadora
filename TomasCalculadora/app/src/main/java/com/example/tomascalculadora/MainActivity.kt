package com.example.tomascalculadora

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.example.tomascalculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    //Binding
    private lateinit var binding : ActivityMainBinding

    //Otros
    private var firstnumber = ""
    private var currentNumber = ""
    private var currentOperator = ""
    private var result = ""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //NoLimitScreen
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        //init Vistas
        binding.apply {
            // Botones
            binding.layoutMain.children.filterIsInstance<Button>().forEach { button ->
                // Listener Botones
                button.setOnClickListener {
                    // Boton Clicked
                    val buttonText = button.text.toString()    //get Text from the Button
                    when{
                        buttonText.matches(Regex("[0-9]"))->{    //if is 0 - 9 adds it to the result
                            if(currentOperator.isEmpty())
                            {
                                firstnumber+=buttonText
                                tvResult.text = firstnumber
                            }else
                            {
                                currentNumber+=buttonText
                                tvResult.text = currentNumber
                            }
                        }
                        buttonText.matches(Regex("[+\\-*/]"))->{        //if the button is a operator resets the actual number to 0
                            currentNumber = ""
                            if (tvResult.text.toString().isNotEmpty())
                            {
                                currentOperator = buttonText
                                tvResult.text = "0"
                            }
                        }
                        buttonText == "="->{            //if the button is equal it obtains the result
                            if (currentNumber.isNotEmpty()&& currentOperator.isNotEmpty())
                            {
                                tvFormula.text = "$firstnumber$currentOperator$currentNumber"            //gets the expression and shows it in the tvresult
                                result = evaluateExpression(firstnumber,currentNumber,currentOperator)
                                firstnumber = result
                                tvResult.text = result
                            }
                        }
                        buttonText == "."->{            //user wants to add a deciaml
                            if(currentOperator.isEmpty())
                            {
                                if (! firstnumber.contains("."))
                                {
                                    if(firstnumber.isEmpty())firstnumber+="0$buttonText"
                                    else firstnumber +=buttonText
                                    tvResult.text = firstnumber
                                }
                            }else
                            {
                                if (! currentNumber.contains("."))
                                {
                                    if(currentNumber.isEmpty()) currentNumber+="0$buttonText"
                                    else currentNumber +=buttonText
                                    tvResult.text = currentNumber
                                }
                            }
                        }
                        buttonText == "AC"->{            //if press AC it deletes all
                            currentNumber = ""
                            firstnumber = ""
                            currentOperator = ""
                            tvResult.text = "0"
                            tvFormula.text = ""
                        }
                    }
                }
            }


        }
    }

    // Funciones
    private fun evaluateExpression(firstNumber:String,secondNumber:String,operator:String):String
    {
        val num1  = firstNumber.toDouble()
        val num2  = secondNumber.toDouble()
        return when(operator)
        {
            "+"-> (num1+num2).toString()
            "-"-> (num1-num2).toString()
            "*"-> (num1*num2).toString()
            "/"-> (num1/num2).toString()
            else ->""
        }
    }
}
