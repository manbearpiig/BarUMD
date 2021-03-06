package com.example.barze

import com.google.firebase.database.Exclude
import java.text.SimpleDateFormat
import java.util.*
import java.io.Serializable

class Bar : Serializable {
    var name: String? = null
    var address: String? = null
    var phone: String? = null
    var fee: Double? = null
    var open: String? = null // 4 digit representing time in 24 hour
    var close: String? = null
    var totalRates : Int = 0
    var totalScore : Int = 0
    constructor()

    constructor(name:String, address:String, phone:String, fee:Double, open:String, close:String){
        this.name = name
        this.address = address
        this.phone = phone
        this.fee = fee
        this.open = open
        this.close = close
    }

    @Exclude
    fun isBarOpen():Boolean{
        val dateFormatH = SimpleDateFormat("H")
        val dateFormatm = SimpleDateFormat("m")
        val currLocTime = Calendar.getInstance(TimeZone.getTimeZone("America/New_York")).time
        var timeH = dateFormatH.format(currLocTime)
        val timeMin = dateFormatm.format(currLocTime)
        if(timeMin.length < 2) {
            timeH += "0"
        }
        var currTime = (timeH + timeMin).toInt()
        var closeTime : Int = close!!.toInt()

        // if the bar closes after midnight
        if (close!! <= open!!){
            closeTime += 2400
        }

        // if current time is after midnight (and before 6am)
        if (currTime < 600){
            currTime += 2400
        }

        return closeTime > currTime && currTime > open!!.toInt()
    }

    @Exclude
    fun getRating():String{
        if (totalRates == 0){
            return "No Rating"
        }
        val rating:Double = totalScore.toDouble() / totalRates
        return "%.2f".format(rating)
    }

    @Exclude
    fun updateRating(score:Int){
        totalRates++
        totalScore += score
    }



}
