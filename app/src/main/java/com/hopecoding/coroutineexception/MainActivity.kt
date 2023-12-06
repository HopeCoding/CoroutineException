package com.hopecoding.coroutineexception

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Handler koyduğumuzda uygulama çökmez. Handler koymazsak uygulama çöker.
        val handler = CoroutineExceptionHandler{coroutineContext, throwable ->
            println("exception:${throwable.localizedMessage}")
        }

        lifecycleScope.launch(handler) {
            supervisorScope {
                //Birden fazla launch olduğunda bu supervisorScope da yazmanız gerekiyor.
                //Uygulamanız çökmeyip diğer launchlara devam edicek

                launch {
                    throw Exception("Error")
                    // 1 tane hata olduğunda uygulama çökmese bile
                    // diğer işlemlerin launchların hiç biri çalışmaz.
                }
                launch {
                    delay(500L)
                    println("this is executed")
                }
            }
        }
        CoroutineScope(Dispatchers.Main + handler).launch {
            launch {
                throw Exception("error in a coroutine")
            }
        }

    }
}