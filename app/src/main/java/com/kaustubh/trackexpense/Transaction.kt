package com.kaustubh.trackexpense

data class Transaction(
    var amount:Double,
    val type:String,
    var from:String,
    var to:String,
    var forReason:String,
    var mode:String,
    var time:String,
    var date:String,
    val distinct:String
)