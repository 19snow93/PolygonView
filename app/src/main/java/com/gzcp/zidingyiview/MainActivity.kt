package com.gzcp.zidingyiview

import android.graphics.PointF
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var radius = 300
    var count = 7
    var num = 4
    var colors = ArrayList<Int>()
    var attributes = ArrayList<String>()
    var score = ArrayList<Float>()
    var attributesDong = ArrayList<String>()
    var scoreDong = ArrayList<Float>()
    lateinit var dongqiudiPolygonView:PolygonView
    lateinit var legendsPolygonView:PolygonView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dongqiudiPolygonView = findViewById(R.id.polygonView)
        legendsPolygonView = findViewById(R.id.polygonView1)

        scoreDong.add(3f / 10)
        scoreDong.add(6.5f / 10)
        scoreDong.add(8f / 10)
        scoreDong.add(4f / 10)
        scoreDong.add(2f / 10)
        scoreDong.add(9f / 10)

        attributesDong.add("射门")
        attributesDong.add("传球")
        attributesDong.add("盘带")
        attributesDong.add("防守")
        attributesDong.add("力量")
        attributesDong.add("速度")

        var builder:PolygonView.Builder = PolygonView.Builder(this)
        dongqiudiPolygonView.build(builder.radius(300).count(6).num(5).attributes(attributesDong)
                .score(scoreDong).lineColor(resources.getColor(R.color.gray))
                .scoreColor(resources.getColor(R.color.shallow_blue))
                .scoreAreaColor(resources.getColor(R.color.shallow_shallow_blue))
                .textSize(30f))


        colors.clear()
        colors.add(resources.getColor(R.color.outer_most))
        colors.add(resources.getColor(R.color.outer_secound))
        colors.add(resources.getColor(R.color.outer_third))
        colors.add(resources.getColor(R.color.inside))

        score.add(3f / 10)
        score.add(6.5f / 10)
        score.add(8f / 10)
        score.add(4f / 10)
        score.add(2f / 10)
        score.add(9f / 10)
        score.add(7f / 10)

        attributes.add("助攻")
        attributes.add("物理")
        attributes.add("魔法")
        attributes.add("防御")
        attributes.add("金钱")
        attributes.add("击杀")
        attributes.add("生存")


        var builder1:PolygonView.Builder = PolygonView.Builder(this)
        legendsPolygonView.build(builder1.radius(300).count(7).num(4).attributes(attributes)
                .score(score).fillColors(colors).lineColor(resources.getColor(R.color.gray)).haveScorePoint(false)
                .textSize(30f))
    }
}
