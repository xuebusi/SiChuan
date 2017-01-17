/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package test.io.transwarp.echarts.samples.scatter;

import io.transwarp.echarts.AxisPointer;
import io.transwarp.echarts.Tooltip;
import io.transwarp.echarts.axis.ValueAxis;
import io.transwarp.echarts.code.LineType;
import io.transwarp.echarts.code.PointerType;
import io.transwarp.echarts.code.Tool;
import io.transwarp.echarts.code.Trigger;
import io.transwarp.echarts.data.ScatterData;
import io.transwarp.echarts.series.Scatter;
import io.transwarp.echarts.style.LineStyle;
import test.io.transwarp.echarts.util.EnhancedOption;
import org.junit.Test;

/**
 * @author liuzh
 */
public class ScatterTest2 {

    @Test
    public void test() {
        //地址：http://echarts.baidu.com/doc/example/scatter2.html
        EnhancedOption option = new EnhancedOption();
        option.tooltip(new Tooltip()
                .trigger(Trigger.axis)
                .showDelay(0)
                .axisPointer(new AxisPointer().type(PointerType.cross)
                        .lineStyle(new LineStyle()
                                .type(LineType.dashed).width(1))));
        option.legend("scatter1", "scatter2");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataZoom, Tool.dataView, Tool.restore, Tool.saveAsImage);
        ValueAxis valueAxis = new ValueAxis().power(1).splitNumber(4).scale(true);
        option.xAxis(valueAxis);
        option.yAxis(valueAxis);
        //注：这里的结果是一种圆形一种方形，是因为默认不设置形状时，会循环形状数组
        option.series(
                new Scatter("scatter1").symbolSize("function (value){" +
                        "                return Math.round(value[2] / 5);" +
                        "            }").data(randomDataArray())
                , new Scatter("scatter2").symbolSize("function (value){" +
                        "                return Math.round(value[2] / 5);" +
                        "            }").data(randomDataArray()));
        option.exportToHtml("scatter2.html");
        option.view();
    }

    private ScatterData[] randomDataArray() {
        ScatterData[] scatters = new ScatterData[100];
        for (int i = 0; i < scatters.length; i++) {
            scatters[i] = new ScatterData(random(), random(), Math.abs(random()));
        }
        return scatters;
    }

    private int random() {
        int i = (int) Math.round(Math.random() * 100);
        return (i * (i % 2 == 0 ? 1 : -1));
    }
}
