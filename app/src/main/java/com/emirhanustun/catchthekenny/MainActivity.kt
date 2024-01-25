package com.emirhanustun.catchthekenny

import android.content.DialogInterface
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.emirhanustun.catchthekenny.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var score = 0;
    var imageArray = ArrayList<ImageView>()
    var runnable = Runnable {}
    var handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //ImageArray
        imageArray.add(binding.imageView)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)
        imageArray.add(binding.imageView10)
        imageArray.add(binding.imageView11)
        imageArray.add(binding.imageView12)

        hideImages()

        //CountDownTimer

        object : CountDownTimer(15500,50) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timetxt.text = "Time: ${millisUntilFinished/1000}"
            }

            override fun onFinish() {
                binding.timetxt.text = "Time: 0"
                handler.removeCallbacks(runnable)

                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }

                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over" + "Score: $score")
                alert.setMessage("Restart The Game?")
                alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    //restart
                    val intentFromMain = intent
                    finish()
                    startActivity(intentFromMain)
                })

                alert.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                  Toast.makeText(this@MainActivity, "Game Over!", Toast.LENGTH_LONG).show()
                })
                alert.show()
            }

        }.start()
    }

    fun hideImages(){

        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }
                val random = Random()
                val randomIndex = random.nextInt(12)
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable, 500)
            }

        }

        handler.post(runnable)
    }

    fun increaseScore(view: View){
        score = score + 1
        binding.scoretxt.text = "Score: ${score}"
    }
}