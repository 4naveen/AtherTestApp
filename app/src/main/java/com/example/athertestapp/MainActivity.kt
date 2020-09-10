package com.example.athertestapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    var map = HashMap<String, Int>()
    var prevmap: HashMap<String, Int>? = null
    var grid: GridView? = null
    var score = 0
    var t: TextView? = null
    var nums = arrayOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "16"
    )
    var plantsList = Arrays.asList(*nums)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        t = findViewById(R.id.Score) as TextView
        grid = findViewById(R.id.grid_view) as GridView

        intializeGrid()
    }
    private fun intializeGrid() {
        val random1 = Random()
        val r1 = random1.nextInt(16 - 1 + 1) + 1
        val random2 = Random()
        var r2 = random2.nextInt(16 - 1 + 1) + 1
        if (r1 == r2) {
            if (r2 != 16) r2++ else r2--
        }
        Log.e("TAG", "$r1    $r2")
        // Initializing a new String Array
        val choice = intArrayOf(2, 4)
        val ind1 = Random().nextInt(choice.size)
        val ind2 = Random().nextInt(choice.size)

//        Log.i("TAG",choice[ind1]+"     "+choice[ind2]);
        for (i in nums.indices) {
            if (nums[i].toInt() == r1) {
                map[nums[i]] = choice[ind1]
            }
            if (nums[i].toInt() == r2) {
                map[nums[i]] = choice[ind2]
            }
            if (nums[i].toInt() != r2 && nums[i].toInt() != r1) {
                map[nums[i]] = 0
            }
        }
        //        Log.i("TAG",map.toString());
        // Populate a List from Array elements

        // Data bind GridView with ArrayAdapter (String Array elements)
        gridChanged(plantsList)
    }
    fun gridChanged(plantsList: List<String>) {
        grid!!.adapter = object : ArrayAdapter<String?>(this, android.R.layout.simple_list_item_1, plantsList) {
            override fun getView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {

                // Return the GridView current item as a View
                val view = super.getView(position, convertView, parent)

                // Convert the view as a TextView widget
                val tv = view as TextView

                //tv.setTextColor(Color.DKGRAY);

                // Set the layout parameters for TextView widget
                val lp = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )
                tv.layoutParams = lp
                tv.width = 100
                tv.height = 210

                // Display TextView text in center position
                tv.gravity = Gravity.CENTER
                tv.textSize = 20f

                // Set the TextView text (GridView item text)
                if (map[plantsList[position]] != 0) {
                    Log.i("TAG", position.toString() + "")
                    Log.i("TAG", plantsList[position].toString())
                    tv.text = map[plantsList[position]].toString() + ""
                } else {
                    tv.text = ""
                }
                tv.id = position

                // Set the TextView background color
                tv.setBackgroundColor(Color.parseColor("#b2dfdb"))

                // Return the TextView widget as GridView item
                return tv
            }
        }
    }
}