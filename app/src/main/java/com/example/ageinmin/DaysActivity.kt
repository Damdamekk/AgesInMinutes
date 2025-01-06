package com.example.ageinmin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DaysActivity : AppCompatActivity() {

    private var tvSelectedDateDays : TextView? = null
    private var tvAgeInDays : TextView? = null
    private var differenceInDays: Long = 0

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                // Wywołanie funkcji udostępniania
                shareResult("My age in days is $differenceInDays!")
                return true
            }

            //R.id.action_about -> {
            //Przekierowanie do nowej aktywności "O aplikacji"
            //val intent = Intent(this, AboutActivity::class.java)
            // startActivity(intent)
            //return true
            //}

            R.id.action_settings -> {
                // Przekierowanie do ustawień (np. permissions)
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_days)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnDatePicker: Button = findViewById(R.id.btnDatePickerDays)
        tvSelectedDateDays = findViewById(R.id.tvSelectedDateDays)
        tvAgeInDays = findViewById(R.id.tvAgeInDays)

        btnDatePicker.setOnClickListener {
            clickDatePickerDays()
        }

        val btnMinutesActivity: Button = findViewById(R.id.btnMinutesActivity)
        btnMinutesActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val btnShareResult: Button = findViewById(R.id.btnShareResult)
        btnShareResult.setOnClickListener {
            val resultMessage = "My age in days is $differenceInDays!"
            shareResult(resultMessage)
        }

    }

    fun shareResult(result: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, result)
        startActivity(Intent.createChooser(shareIntent, "Share your result via"))
    }

        fun clickDatePickerDays(){

            val myCalendar = Calendar.getInstance()
            val year =myCalendar.get(Calendar.YEAR)
            val month =myCalendar.get(Calendar.MONTH)
            val day =myCalendar.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, selectedYear, selectedMonth, selectedDayOfMonth ->

                    Toast.makeText(this,
                        "Year was $selectedYear, month was ${selectedMonth + 1}, " +
                                "day of month was $selectedDayOfMonth", Toast.LENGTH_LONG).show()

                    val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

                    tvSelectedDateDays?.text = selectedDate

                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                    val theDate = sdf.parse(selectedDate)

                    val selectedDateInMillis = theDate.time

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    val currentDateInMillis = currentDate.time

                    val differenceInMillis = currentDateInMillis - selectedDateInMillis

                    differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24)

                    tvAgeInDays?.text = differenceInDays.toString()

                },
                year,
                month,
                day
            )

            dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
            dpd.show()
    }
}