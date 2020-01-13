package com.heickjack.sampletestapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.datafm.BaseActivity
import com.heickjack.sampletestapp.R
import com.heickjack.sampletestapp.databinding.ActivityPrimeNumberBinding
import java.util.stream.Collectors
import java.util.stream.IntStream


class PrimeNumberActivity : BaseActivity() {

    private lateinit var mBinding: ActivityPrimeNumberBinding

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, PrimeNumberActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_prime_number)

        mBinding.textPrimeNumbers.text = getPrimeList(10000)
        mBinding.buttonSubmit.setOnClickListener {
            val drawNumber = mBinding.inputDrawNumber.text.toString().toInt()
            if (drawNumber <= 10000){
                mBinding.textPrimeNumbersId.text =
                    "Inputs: (int)n = " + drawNumber + " Output: (string) " + answer(drawNumber)
            }else{
                Toast.makeText(this, getString(R.string.text_draw_number_exceeded), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPrimeList(n: Int): String {
        var textPrimeList = ""
        var nValue = n

        do {
            nValue += 100
            val primeList = IntStream.rangeClosed(2, n)
                .filter { x: Int -> hasNoFactors(x) }.boxed()
                .collect(Collectors.toList())

            for (item in primeList.iterator()) {
                textPrimeList += item.toString()
            }
        } while (textPrimeList.length < 10005)

        return textPrimeList
    }

    private fun hasNoFactors(number: Int): Boolean {
        return IntStream.range(2, number)
            .noneMatch { f: Int -> number % f == 0 }
    }

    private fun answer(n: Int): String {
        return mBinding.textPrimeNumbers.text.substring(n, n + 5)
    }
}