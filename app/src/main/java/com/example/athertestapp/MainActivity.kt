package com.example.athertestapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    var map = HashMap<String, Int>()
    var grid: GridView? = null
    var score = 0
    var t: TextView? = null
    var position = arrayOf(
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
    var positionList = Arrays.asList(*position)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        t = findViewById(R.id.Score) as TextView
        t!!.text="0000"
        grid = findViewById(R.id.grid_view) as GridView

        intializeGrid()

        grid!!.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeTop() {

                for (i in 16 downTo 5) {
                    if (map[i.toString() + ""] == map[(i - 4).toString() + ""]) {
                        map[(i - 4).toString() + ""] =
                            map[i.toString() + ""]!! + map[(i - 4).toString() + ""]!!
                        map[i.toString() + ""] = 0
                        val score = t!!.text.toString()
                        var scorenumber = score.toInt()
                        scorenumber += map[(i - 4).toString() + ""]!!
                        t!!.text = scorenumber.toString() + ""
                    } else if (map[(i - 4).toString() + ""] == 0) {
                        map[(i - 4).toString() + ""] = map[i.toString() + ""]!!
                        map[i.toString() + ""] = 0
                    }
                }

                gridChanged(positionList)
                generateRandomNumber()
                checkGrid()
            }

            override fun onSwipeBottom() {

                for (i in 1..12) {
                    if (map[i.toString() + ""] == map[(i + 4).toString() + ""]) {
                        map[(i + 4).toString() + ""] =
                            map[i.toString() + ""]!! + map[(i + 4).toString() + ""]!!
                        map[i.toString() + ""] = 0
                        val score = t!!.text.toString()
                        var scorenumber = score.toInt()
                        scorenumber += map[(i + 4).toString() + ""]!!
                        t!!.text = scorenumber.toString() + ""
                    } else if (map[(i + 4).toString() + ""] == 0) {
                        map[(i + 4).toString() + ""] = map[i.toString() + ""]!!
                        map[i.toString() + ""] = 0
                    }
                }


                gridChanged(positionList)
                generateRandomNumber()
                checkGrid()
            }
            override fun onSwipeRight() {
                var k = 0
                for (i in 1..16) {
                    if (k == 0 && map[i.toString() + ""] == map[(i + 1).toString() + ""]) {
                        map[(i + 1).toString() + ""] =
                            map[i.toString() + ""]!! + map[(i + 1).toString() + ""]!!
                        map[i.toString() + ""] = 0
                        val score = t!!.text.toString()
                        var scorenumber = score.toInt()
                        scorenumber += map[(i + 1).toString() + ""]!!
                        t!!.text = scorenumber.toString() + ""
                    } else if ( k == 0 && map[(i + 1).toString() + ""] == 0) {
                        map[(i + 1).toString() + ""] = map[i.toString() + ""]!!
                        map[i.toString() + ""] = 0
                    }
                    if (i / 4 != (i + 1) / 4) {
                        k = 1
                    } else {
                        k = 0
                    }
                }
                gridChanged(positionList)
                generateRandomNumber()
                checkGrid()
            }
            override fun onSwipeLeft() {
              var k=0
                for (i in 16 downTo 2) {
                    if (i == 13 || i == 9 || i == 5) {
                        k = 1
                    } else {
                        k = 0
                    }
                    if (k == 0 && map[i.toString() + ""] == map[(i - 1).toString() + ""]) {
                        map[(i - 1).toString() + ""] =
                            map[i.toString() + ""]!! + map[(i - 1).toString() + ""]!!
                        map[i.toString() + ""] = 0
                        val score = t!!.text.toString()
                        var scorenumber = score.toInt()
                        scorenumber += map[(i - 1).toString() + ""]!!
                        t!!.text = scorenumber.toString() + ""
                    } else if ( k == 0 && map[(i - 1).toString() + ""] == 0) {
                        map[(i - 1).toString() + ""] = map[i.toString() + ""]!!
                        map[i.toString() + ""] = 0
                    }
                }

                gridChanged(positionList)
                generateRandomNumber()
                checkGrid()
            }

        })

    }
    private fun intializeGrid() {
        val random1 = Random()
        val r1 = random1.nextInt(16 ) + 1
        val random2 = Random()
        var r2 = random2.nextInt(16 ) + 1
        if (r1 == r2) {
            if (r2 != 16) r2++ else r2--
        }
        // Initializing a new String Array
        val choice = intArrayOf(2, 4)
        val ind1 = Random().nextInt(choice.size)
        val ind2 = Random().nextInt(choice.size)

        for (i in position.indices) {
            if (position[i].toInt() == r1) {
                map[position[i]] = choice[ind1]
            }
            if (position[i].toInt() == r2) {
                map[position[i]] = choice[ind2]
            }
            if (position[i].toInt() != r2 && position[i].toInt() != r1) {
                map[position[i]] = 0
            }
        }
        // Populate a List from Array elements

        // Data bind GridView with ArrayAdapter (String Array elements)
        gridChanged(positionList)
    }
    fun generateRandomNumber() {
        val ran = Random()
        var i = ran.nextInt(16) + 1
        var flag = 0
        for (key in map.keys) {
            if (map[key] != 0) {
                flag++
            }
        }
        if (flag != 16) { while (map[i.toString() + ""] != 0) {
                i = ran.nextInt(16) + 1
            }
            val choice = intArrayOf(2,4)
            val ind1 = ran.nextInt(choice.size)
            map[i.toString() + ""] = choice[ind1]
            gridChanged(positionList)
        }
    }
    fun gridChanged(positionList: List<String>) {
        grid!!.adapter = object : ArrayAdapter<String?>(this, android.R.layout.simple_list_item_1, positionList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

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
                if (map[positionList[position]] != 0) {
                    tv.text = map[positionList[position]].toString() + ""
                } else {
                    tv.text = ""
                }
                tv.id = position

                // Set the TextView background color
                tv.setBackgroundColor(Color.parseColor("#89ECDA"))

                // Return the TextView widget as GridView item
                return tv
            }
        }
    }
    fun checkGrid() {
        var flag = 0
        for (key in map.keys) {
            if (map[key] == 2048) {
                Toast.makeText(applicationContext,"CONGRATULATIONS! YOU WIN!",Toast.LENGTH_LONG).show()
                return
            }else{
                if (map[key]!=0){
                    flag++
                    if (flag==16){
                        Toast.makeText(applicationContext,"GAME OVER! YOU LOSE!",Toast.LENGTH_LONG).show()
                        return
                    }

                }
            }

        }

    }
}