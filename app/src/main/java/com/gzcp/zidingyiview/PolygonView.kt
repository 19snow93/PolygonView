package com.gzcp.zidingyiview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.lang.Math.cos
import java.lang.Math.sin
import java.util.ArrayList
import android.R.attr.y



/**
 * Created by leo on 2017/12/1.
 */
class PolygonView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

    var path = Path()
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    //多边形线条
    var paintLine = Paint(Paint.ANTI_ALIAS_FLAG)
    //多边形属性字体
    var paintText = Paint(Paint.ANTI_ALIAS_FLAG)
    //多边形分数区域
    var paintScoreArea = Paint(Paint.ANTI_ALIAS_FLAG)
    //多边形分数点
    var paintScorePointColor = Paint(Paint.ANTI_ALIAS_FLAG)
    //多边形半径
    var radiusR = 300
    //多边形嵌套直径跨度
    var radiusRange = 50
    //多边形是几边形
    var count = 7
    //图形有多少层
    var num = 4
    //多边形的边是否有角边
    var isInSideLine = true
    //多边形分数点的宽度
    var paintScorePointColorWidth = 20f
    //多边形字体的大小
    var textSize = 24f
    //是否拥有多边形的分数点
    var haveScorePoint = true
    //多边形分数点的形状
    var scorePointShape = "round"
    //多边形分数区域的颜色
    var scoreAreaColor = 0
    //多边形的描边颜色
    var lineColor = Color.BLACK
    //多边形的字体颜色
    var textColor = Color.BLACK
    //多边形分数描边的颜色
    var scoreColor = Color.RED
    //存储多边形各个点
    var points = ArrayList<PointF>()
    //多边形的填充颜色
    var fillColors = ArrayList<Int>()
    //多边形的各个属性
    var attributes = ArrayList<String>()
    //多边形的各个分数
    var score = ArrayList<Float>()
    //多边形的builder  builder模式
    lateinit var builder:Builder

    constructor(context: Context?, attrs: AttributeSet?):this(context,attrs,0)

    constructor(context: Context):this(context,null){
        if(builder == null)
            builder = Builder(context)
        build(builder)
    }

    constructor(context: Context,builder:Builder):this(context){
        this.builder = builder
    }


   fun build(builder:Builder){
       this.builder = builder
       this.radiusR = builder.radius
       this.fillColors = builder.fillColors
       this.attributes = builder.attributes
       this.score = builder.score
       this.num = builder.num
       this.count = builder.count
       this.isInSideLine = builder.isInSideLine
       this.lineColor = builder.lineColor
       this.textColor = builder.textColor
       this.scoreColor = builder.scoreColor
       this.scorePointShape = builder.scorePointShape
       this.paintScorePointColorWidth = builder.paintScorePointColorWidth
       this.haveScorePoint = builder.haveScorePoint
       this.scoreAreaColor = builder.scoreAreaColor
       this.textSize = builder.textSize
       this.radiusRange = builder.radiusRange

       invalidate()
   }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var radius = radiusR

        //多边形的半径应该大于设定的半径区域
        radiusRange = if(radiusR > radiusRange) radiusRange else 0

        var width = width / 2
        var height = height / 2

        paint.setAntiAlias(true)
        paint.setStrokeWidth(5f)
        paint.setStyle(Paint.Style.FILL_AND_STROKE)

        //把画布平移到屏幕中心
        canvas?.translate(width.toFloat(),height.toFloat())
        //保存画布的状态
        canvas?.save()

        //多边形至少有一个
         if(num > 0) {
            for (j in 0..num - 1) {
                //获取当前需要画的多边形半径
                if (j > 0) {
                    radius = radius - radiusRange
                }
                //如果填充颜色的列表size等于嵌套多边形个数，则设置各个填充颜色
                if (fillColors.size == num) {
                    paint.color = fillColors.get(j)
                } else {
                    paint.color = Color.WHITE
                }
                //多边形多少个边
                for (i in 0..count - 1) {
                    //i等于0的时候，把点path移动到第一个点
                    if (i == 0) {
                        //（x，y）为（radius * cosA，radius * cosA）
                        path.moveTo((radius * cos(2 * Math.PI / count * i - Math.PI / 2)).toFloat(), (radius * sin(2 * Math.PI / count * i - Math.PI / 2)).toFloat())
                    } else {
                        path.lineTo((radius * cos(2 * Math.PI / count * i - Math.PI / 2)).toFloat(), (radius * sin(2 * Math.PI / count * i - Math.PI / 2)).toFloat())
                    }
                    //把相应的点保存到pointList中
                    points.add(PointF(radius * cos(2 * Math.PI / count * i - Math.PI / 2).toFloat(), radius * sin(2 * Math.PI / count * i - Math.PI / 2).toFloat()))
                }
                //画闭合图形
                path.close()
                canvas?.drawPath(path, paint)
                //描多边形的边颜色
                paintLine.color = lineColor
                paintLine.style = Paint.Style.STROKE
                paintLine.strokeWidth = 3f
                canvas?.drawPath(path, paintLine)
                path.reset();
            }
        }

        //是否有角边
        if(isInSideLine) {
            //画角边
            for (i in 0..count - 1) {
                canvas?.drawLine(0f, 0f, points.get(i).x, points.get(i).y, paintLine)
            }
        }

        canvas?.restore()

        canvas?.save()
        paint.reset()
        //设置字体的paint
        paintText.setTextSize(textSize);
        paintText.color = textColor
        paintText.style = Paint.Style.FILL_AND_STROKE
        //如果属性字体的size等于多边形的边数
        if(count == attributes.size) {
            for (i in 0..count - 1) {

                var x = points.get(i).x
                var y = points.get(i).y
                //按照四个象限调整字体的位置
                if (points.get(i).x < 0 && points.get(i).y < 0) {
                    x = x - 30
                    y = y - 20
                } else if (points.get(i).x < 0 && points.get(i).y > 0) {
                    x = x - 30
                    y = y + 30
                } else if (points.get(i).x > 0 && points.get(i).y < 0) {
                    x = x + 10
                    y = y - 10
                } else {
                    x = x + 10
                    y = y + 10
                }

                canvas?.drawText(attributes.get(i), x, y, paintText)
            }
        }

        canvas?.restore()

        canvas?.save()

        //开始设置多边形里面的战力分数形状
        paintScorePointColor.style = Paint.Style.STROKE
        paintScorePointColor.strokeWidth = paintScorePointColorWidth
        paintScorePointColor.color = scoreColor

        //设置战力多边形每个点的形状
        if (scorePointShape.equals("square")) {
            paintScorePointColor.setStrokeCap(Paint.Cap.SQUARE)
        } else {
            paintScorePointColor.setStrokeCap(Paint.Cap.ROUND)
        }

        //设置战力多边形描边的颜色
        paint.color = scoreColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth  = 3f

        //设置战力多边形区域的颜色
        paintScoreArea.setAntiAlias(true)
        paintScoreArea.setStyle(Paint.Style.FILL_AND_STROKE)
        paintScoreArea.color = scoreAreaColor

        //设置战力多边形的最大半径应该为原始半径长度
        radius = radiusR
        //如果战力分数列表size等于多边形边数则可以画战力多边形
        if(count == score.size) {
            for (i in 0..count - 1) {
                //画出战力多边形的路径，score[i]应为小数
                if (i == 0) {
                    path.moveTo((radius * cos(2 * Math.PI / count * i - Math.PI / 2) * score[i]).toFloat(), (radius * sin(2 * Math.PI / count * i - Math.PI / 2) * score[i]).toFloat())
                } else {
                    path.lineTo((radius * cos(2 * Math.PI / count * i - Math.PI / 2) * score[i]).toFloat(), (radius * sin(2 * Math.PI / count * i - Math.PI / 2) * score[i]).toFloat())
                }
            }
            //画出战力多边形的区域
            canvas?.drawPath(path,paintScoreArea)
            for (i in 0..count - 1) {
                //如果设置了多边形的描点，画出战力多边形的点
                if(haveScorePoint) {
                    canvas?.drawPoint((radius * cos(2 * Math.PI / count * i - Math.PI / 2) * score[i]).toFloat(), (radius * sin(2 * Math.PI / count * i - Math.PI / 2) * score[i]).toFloat(), paintScorePointColor)
                }
            }
            path.close()
            canvas?.drawPath(path,paint)
            path.reset();
        }
        canvas?.restore()

    }

    class Builder{

        var radius = 300
        var count = 7
        var num = 4
        var fillColors = ArrayList<Int>()
        var attributes = ArrayList<String>()
        var score = ArrayList<Float>()
        var context:Context
        var isInSideLine = true
        var lineColor = Color.BLACK
        var textColor = Color.BLACK
        var scoreColor = Color.RED
        var paintScorePointColorWidth = 20f
        var scorePointShape = "round"
        var haveScorePoint = true
        var scoreAreaColor = Color.TRANSPARENT
        var textSize = 24f
        var radiusRange = 50

        constructor(context: Context){
            this.context = context
        }

        fun radiusRange(radiusRange:Int):Builder{
            this.radiusRange = radiusRange
            return this
        }

        fun textSize(textSize:Float):Builder{
            this.textSize = textSize
            return this
        }

        fun haveScorePoint(haveScorePoint:Boolean):Builder{
            this.haveScorePoint = haveScorePoint
            return this
        }

        fun scoreAreaColor(scoreAreaColor:Int):Builder{
            this.scoreAreaColor = scoreAreaColor
            return this
        }

        fun scorePointShape(scorePointShape:String):Builder{
            this.scorePointShape = scorePointShape
            return this
        }

        fun isInSideLine(isInSideLine:Boolean):Builder{
            this.isInSideLine = isInSideLine
            return this
        }

        fun lineColor(lineColor:Int):Builder{
            this.lineColor = lineColor
            return this
        }

        fun textColor(textColor:Int):Builder{
            this.textColor = textColor
            return this
        }

        fun scoreColor(scoreColor:Int):Builder{
            this.scoreColor = scoreColor
            return this
        }

        fun radius(radius:Int):Builder{
            this.radius = radius
            return this
        }

        fun count(count:Int):Builder{
            this.count = count
            return this
        }

        fun num(num:Int):Builder{
            this.num = num
            return this
        }

        fun fillColors(fillColors:ArrayList<Int>):Builder{
            this.fillColors = fillColors
            return this
        }

        fun attributes(attributes:ArrayList<String>):Builder{
            this.attributes = attributes
            return this
        }

        fun score(score:ArrayList<Float>):Builder{
            this.score = score
            return this
        }

        fun build():PolygonView{
            return PolygonView(context,this)
        }

    }
}