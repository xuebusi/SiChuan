package io.transwarp.scrcu.base.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.transwarp.echarts.DataRange;
import io.transwarp.echarts.Grid;
import io.transwarp.echarts.Label;
import io.transwarp.echarts.LabelLine;
import io.transwarp.echarts.Legend;
import io.transwarp.echarts.Polar;
import io.transwarp.echarts.Toolbox;
import io.transwarp.echarts.axis.Axis;
import io.transwarp.echarts.axis.AxisLabel;
import io.transwarp.echarts.axis.CategoryAxis;
import io.transwarp.echarts.axis.SplitArea;
import io.transwarp.echarts.axis.ValueAxis;
import io.transwarp.echarts.code.AxisType;
import io.transwarp.echarts.code.BrushType;
import io.transwarp.echarts.code.LineType;
import io.transwarp.echarts.code.Orient;
import io.transwarp.echarts.code.Position;
import io.transwarp.echarts.code.Sort;
import io.transwarp.echarts.code.Symbol;
import io.transwarp.echarts.code.Tool;
import io.transwarp.echarts.code.Trigger;
import io.transwarp.echarts.code.X;
import io.transwarp.echarts.data.Data;
import io.transwarp.echarts.data.TreeData;
import io.transwarp.echarts.data.WordCloudData;
import io.transwarp.echarts.json.GsonUtil;
import io.transwarp.echarts.series.Bar;
import io.transwarp.echarts.series.Force;
import io.transwarp.echarts.series.Funnel;
import io.transwarp.echarts.series.Line;
import io.transwarp.echarts.series.Map;
import io.transwarp.echarts.series.Pie;
import io.transwarp.echarts.series.Radar;
import io.transwarp.echarts.series.Tree;
import io.transwarp.echarts.series.WordCloud;
import io.transwarp.echarts.series.force.Link;
import io.transwarp.echarts.series.force.Node;
import io.transwarp.echarts.style.ItemStyle;
import io.transwarp.echarts.style.LineStyle;
import io.transwarp.echarts.style.LinkStyle;
import io.transwarp.echarts.style.NodeStyle;
import io.transwarp.echarts.style.TextStyle;
import io.transwarp.echarts.style.itemstyle.Normal;
import test.io.transwarp.echarts.util.EnhancedOption;

public class ChartUtils {

    public static String genPie(String name, List<Object> dataList) {
        EnhancedOption option = new EnhancedOption();
        //设置标题的显示位置
        option.title().text(name).x("center").y("bottom");
        //设置触发类型
        option.tooltip().trigger(Trigger.item).formatter("{b}<br/>{a}: {c} ({d}%)");
        option.toolbox().show(false);
        option.calculable(false);
        //option.toolbox().show(true).feature(Tool.magicType);
        Pie p1 = new Pie(name);
        //设置饼图的内半径、外半径
        p1.radius(20, 40).setData(dataList);
        option.series(p1);
        return GsonUtil.format(option);

    }

    public static String genPortraitPie(String name, java.util.Map<String, Integer> map) {
        List<Object> list = new ArrayList<>();
        for (String key : map.keySet()) {
            Data data = new Data(key, map.get(key));
            list.add(data);
        }
        EnhancedOption option = new EnhancedOption();
        option.title().text(name).x("left").y("top");
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
        option.toolbox().show(false);
        option.calculable(false);
        Pie p1 = new Pie(name);
        p1.radius(20, 50).setData(list);
        option.series(p1);
        return GsonUtil.format(option);

    }

    public static String genMutilPie(String name, List<Object>... dataList) {
        EnhancedOption option = new EnhancedOption();
        option.title().text(name).x("center").y("bottom");
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
        option.toolbox().show(false);
        option.calculable(false);
        for (int i = 0; i < dataList.length; i++) {
            Pie p1 = new Pie(name);
            p1.radius("30%").center(30 * (i + 1) + "%", "50%").setData(dataList[i]);
            option.series(p1);
        }
        return GsonUtil.format(option);

    }

    public static String genMultiLineChart(List<Object> xAxisList, Object[] nameList, List<Object>... dataList) {
        EnhancedOption option = new EnhancedOption();
        option.tooltip().trigger(Trigger.axis);
        Legend legend = new Legend();
        //将名称设置为底部显示
        legend.y("bottom").data(nameList);
        option.legend(legend);
        option.toolbox().show(true);
        option.calculable(false);
        //设置类目起始和结束两端空白策略，true：留空，false：顶头
        CategoryAxis axis = new CategoryAxis().boundaryGap(false);
        //给x轴添加数据
        axis.setData(xAxisList);
        option.xAxis(axis);
        option.yAxis(new ValueAxis());
        Grid grid = new Grid();
        //设置x，y轴的位置
        grid.x("60").x2("30").y("10").y2("60");
        option.grid(grid);
        for (int i = 0; i < dataList.length; i++) {
            List<Object> list = dataList[i];
            Line line = new Line().smooth(true).name(String.valueOf(nameList[i]));
            line.setData(list);
            option.series(line);
        }

        return GsonUtil.format(option);

    }

    /**
     * 活跃用户分析生成折线图
     *
     * @param name
     * @param xAxisList
     * @param dataList
     * @return
     */
    public static String genLineChart(String name, List<Object> xAxisList, List<Object> dataList) {
        EnhancedOption option = new EnhancedOption();
        option.tooltip().trigger(Trigger.axis);
        Legend legend = new Legend();
        legend.y("bottom").data(name);
        option.legend(legend);
        option.toolbox().show(true);
        option.calculable(true);
        CategoryAxis axis = new CategoryAxis().boundaryGap(false);
        axis.setData(xAxisList);
        option.xAxis(axis);
        option.yAxis(new ValueAxis());
        Line line = new Line().smooth(true).name(name);
        line.setData(dataList);
        Grid grid = new Grid();
        //设置折线图位置样式布局
        grid.x("60").x2("30").y("20").y2("60");
        option.grid(grid);
        option.series(line);
        return GsonUtil.format(option);

    }

    public static String genMapChart(String name, List<Object> dataList) {
        Map map = new Map("Map");
        for (int i = 0; i < dataList.size(); i++) {
            Data ts = (Data) dataList.get(i);
            Data data = new Data(ts.getName());
            data.value(Math.round(Math.random() * 1000) + i);
            map.data(data);
        }
        EnhancedOption option = new EnhancedOption();
        option.setBackgroundColor("#E0EAEC");
        Grid grid = new Grid();
        grid.x("40").x2("10").y("30").y2("30");
        grid.borderWidth(0);
        option.setGrid(grid);
        option.grid(grid);
        Legend legend = new Legend();
        legend.x("right").data(name);
        option.legend(legend);
        DataRange dr = new DataRange();
        dr.calculable(true);
        dr.min(0);
        dr.max(1000);
        dr.x("left");
        dr.y("bottom");
        dr.text("高, 低");
        option.dataRange(dr);
        Toolbox tb = new Toolbox();
        tb.show(false);
        option.toolbox(tb);
        map.itemStyle().normal().label().show(true);
        map.itemStyle().emphasis().label().show(true);
        map.setMapType("四川");
        map.setName(name);
        option.series(map);
        return GsonUtil.format(option);

    }

    public static void main(String[] args) {
        // List<Object> xAxisListMoney = new ArrayList<Object>();
        // xAxisListMoney.add("历史1");
        // xAxisListMoney.add("历史2");
        // xAxisListMoney.add("历史3");
        // xAxisListMoney.add("历史4");
        // xAxisListMoney.add("历史5");
        // List<Object> xAxisListMoney1 = new ArrayList<Object>();
        // xAxisListMoney1.add("物理1");
        // xAxisListMoney1.add("物理2");
        // xAxisListMoney1.add("物理3");
        // xAxisListMoney1.add("物理4");
        // xAxisListMoney1.add("物理5");
        // getForce("张三",xAxisListMoney,xAxisListMoney1);
        List<String> dataList = new ArrayList<String>();
        dataList.add("李四");
        dataList.add("王五");
        genTree("张三", dataList);

    }

    public static String getForce(String name, List<Object> dataList, List<Object> dataList1) {
        EnhancedOption option = new EnhancedOption();
        option.tooltip().trigger(Trigger.item).formatter("{a} : {b}");
        option.legend("收入", "支出").legend().x(X.left);
        // 数据
        Force force = new Force("交易关系");
        force.categories("类型", "收入", "支出");
        force.itemStyle().normal().label(new Label().show(true).textStyle(new TextStyle().color("#333"))).nodeStyle()
                .brushType(BrushType.both).borderWidth(1);

        force.itemStyle().emphasis().linkStyle(new LinkStyle()).nodeStyle(new NodeStyle()).label().show(true);
        force.useWorker(false).minRadius(15).maxRadius(25).gravity(1.1).scaling(1.1).linkSymbol(Symbol.arrow);
        // 主要人物
        force.nodes(new Node(0, name, 12));
        // 分之人物
        for (Object object : dataList) {
            Node node = new Node();
            node.setCategory(1);
            node.setName(object.toString());
            node.setValue(13);
            force.nodes(node);
        }

        for (Object object : dataList1) {
            Node node = new Node();
            node.setCategory(2);
            node.setName(object.toString());
            node.setValue(14);
            force.nodes(node);
        }

        for (Object object : dataList1) {
            Link link = new Link();
            link.setSource(name);
            link.setTarget(object);
            link.setWeight(3);
            force.links(link);
        }

        for (Object object : dataList) {
            Link link = new Link();
            link.setSource(object);
            link.setTarget(name);
            link.setWeight(1);
            force.links(link);
        }

        option.series(force);
        return GsonUtil.format(option);
    }

    public static String genRadar(String name, java.util.Map<String, Integer> data) {
        EnhancedOption option = new EnhancedOption();
        option.title().text(name);
        option.tooltip().trigger(Trigger.axis);
        option.toolbox().show(false).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        Polar polar = new Polar();
        int count = 0;
        for (int i : data.values()) {
            count = count / 4 * 3;
            if (i > count) {
                count = i;
            }
            count = count * 4 / 3;
        }

        if (data != null) {
            for (String s : data.keySet()) {
                polar.indicator(new Data().text(s).max(count));
            }
            option.polar(polar.radius(60));
        }
        Radar radar = new Radar();
        radar.itemStyle().normal().areaStyle().typeDefault();
        radar.data(new Data().name(name).setValue(data.values()));
        option.series(radar);
        return GsonUtil.format(option);
    }

    public static String genRadar(String name, List<String> key, List<Integer> value) {
        EnhancedOption option = new EnhancedOption();
        option.title().text(name);
        option.tooltip().trigger(Trigger.axis);
        option.toolbox().show(false).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        Polar polar = new Polar();
        int count = 0;
        for (int i : value)

        {
            count = count / 4 * 3;
            if (i > count) {
                count = i;
            }
            count = count * 4 / 3;
        }

        if (key != null) {
            for (String s : key) {
                polar.indicator(new Data().text(s).max(count));
            }
            option.polar(polar.radius(60));
        }
        Radar radar = new Radar();
        radar.itemStyle().normal().areaStyle().typeDefault();
        radar.data(new Data().name(name).setValue(value));
        option.series(radar);
        return GsonUtil.format(option);
    }

    public static String genTrends(List<Object> dataList) {
        EnhancedOption option = new EnhancedOption();
        option.tooltip().show(true);
        WordCloud wordCloud = new WordCloud();
        wordCloud.size("80%", "80%");
        wordCloud.textRotation(0, 45, 90, -45);
        wordCloud.textPadding(0);
        wordCloud.autoSize().enable(true).minSize(14);
        if (dataList != null) {
            for (int i = 0; i < dataList.size(); i++) {
                String teamp = dataList.get(i).toString();
                WordCloudData data = new WordCloudData();
                data.setName(teamp);
                data.setValue(10000);
                data.itemStyle(new ItemStyle().normal(createRandomItemStyle()));
                wordCloud.data(data);
            }
        }
        option.series(wordCloud);
        return GsonUtil.format(option);

    }

    private static Normal createRandomItemStyle() {
        Normal normal = new Normal();
        normal.color("rgb(" + Math.round(Math.random() * 160) + "," + Math.round(Math.random() * 160) + ","
                + Math.round(Math.random() * 160) + ")");
        return normal;
    }

    public static String genTree(String name, List<String> dataList) {
        EnhancedOption option = new EnhancedOption();
        option.title().text("家庭关系");
        option.tooltip().trigger(Trigger.item).formatter("{b}: {c}");
        option.toolbox().show(false);
        option.calculable(false);

        Tree tree = new Tree("树图");
        tree.symbol(Symbol.rectangle);
        tree.orient(Orient.vertical).nodePadding(1).rootLocation().x(X.center).y(50);
        tree.itemStyle().normal().label(new Label().position(Position.inside).formatter("{b}")).lineStyle().width(3)
                .color("#545CA6").type(LineType.broken);
        tree.itemStyle().emphasis().label().show(true);
        List<Integer> size = new ArrayList<Integer>();
        size.add(60);
        size.add(30);
        TreeData root = new TreeData(name, 4).symbolSize(size);

        List<TreeData> children1 = new LinkedList<TreeData>();
        if (dataList != null) {
            for (String str : dataList) {
                children1.add(new TreeData(str, 4).symbolSize(size));

            }
        }

		/*
         * List<TreeData> children3 = new LinkedList<TreeData>();
		 * children3.add(new TreeData("子节点3", 3).symbolSize(size));
		 * children3.add(new TreeData("子节点4", 2).symbolSize(size));
		 * children3.add(new TreeData("子节点5", 1).symbolSize(size));
		 * sub1.setChildren(children3);
		 * 
		 * List<TreeData> children2 = new LinkedList<TreeData>();
		 * children2.add(new TreeData("子节点3", 3).symbolSize(size));
		 * children2.add(new TreeData("子节点4", 2).symbolSize(size));
		 * children2.add(new TreeData("子节点5", 1).symbolSize(size));
		 * sub2.setChildren(children2);
		 */

        root.setChildren(children1);

        tree.data(root);

        option.series(tree);
        return GsonUtil.format(option);
    }

    public static String genPercentBar(String title, String name, List<Object> key, List<Object> values) {
        EnhancedOption option = new EnhancedOption();
        Grid grid = new Grid();
        grid.x("40").x2("10").y("10").y2("60");
        option.grid(grid);
        option.title().x("left").text(title).subtext("").show(false);
        option.tooltip().trigger(Trigger.axis);
        Legend legend = new Legend();
        legend.y("bottom").data(name);
        option.legend(legend);
        option.toolbox().show(false);
        option.calculable(true);
        CategoryAxis axis = new CategoryAxis();
        axis.setData(key);
        option.xAxis(axis);
        option.yAxis(new ValueAxis());

        Bar bar = new Bar(name);
        bar.itemStyle(new ItemStyle()
                .normal(new Normal().label(new Label().show(true).position(Position.inside).formatter("{c}%"))));
        bar.setData(values);
        option.series(bar);
        // option.exportToHtml("bar1.html");
        // option.view();
        return GsonUtil.format(option);
    }

	/*
	 * public static void main(String[] args) { String str = genTree(); }
	 */

    public static String genBar(String title, String name, List<Object> key, List<Object> values) {
        EnhancedOption option = new EnhancedOption();
        Grid grid = new Grid();
        grid.x("60").x2("10").y("10").y2("60");
        option.grid(grid);
        option.title().x("left").text(title).subtext("").show(false);
        option.tooltip().trigger(Trigger.axis);
        Legend legend = new Legend();
        legend.y("bottom").data(name);
        option.legend(legend);
        option.toolbox().show(false);
        option.calculable(true);
        CategoryAxis axis = new CategoryAxis();
        axis.setData(key);
        option.xAxis(axis);
        option.yAxis(new ValueAxis());

        Bar bar = new Bar(name);
        bar.setData(values);
        option.series(bar);
        return GsonUtil.format(option);
    }

    public static String genFunnel(String title, List<Object> key, List<Integer> values) {
        // 地址：http://echarts.baidu.com/doc/example/funnel.html
        EnhancedOption option = new EnhancedOption();
        option.title().text(title);
        // Grid grid = new Grid();
        // grid.x("40").x2("10").y("10").y2("60");
        // option.grid(grid);
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}%");
        option.toolbox().show(false);
        option.calculable(true);

        Funnel funnel = new Funnel(title);
        funnel.x("10%").y(60).width("80%").min(0).max(100).minSize("0%").maxSize("100%").sort(Sort.descending).gap(10);
        funnel.itemStyle().normal().borderColor("#fff").borderWidth(1)
                .label(new Label().show(true).position(Position.inside)).labelLine(new LabelLine().show(false)
                .length(10).lineStyle(new LineStyle().width(1).type(LineType.solid)));
        funnel.itemStyle().emphasis().borderColor("red").borderWidth(5)
                .label(new Label().show(true).formatter("{b}:{c}%").textStyle(new TextStyle().fontSize(20)))
                .labelLine(new LabelLine().show(true));
        for (int i = 0; i < key.size(); i++) {
            Data data = new Data();
            data.value(values.get(i));
            data.name(key.get(i).toString());
            funnel.data(data);
        }

        // funnel.data(new Data().value(60).name("访问"),
        // new Data().value(40).name("咨询"),
        // new Data().value(20).name("订单"),
        // new Data().value(80).name("点击"),
        // new Data().value(100).name("展现")
        // );

        option.series(funnel);
        // option.exportToHtml("funnel.html");
        // option.view();
        return GsonUtil.format(option);
    }

    public static String genBar9(String title, List<Object> yAxisList, List<Object> dataList) {
        EnhancedOption option = new EnhancedOption();
        Grid grid = new Grid();
        grid.x("40").x2("10").y("10").y2("60");
        option.grid(grid);
        option.title().text(title);
        option.legend(new Legend().show(false));
        option.toolbox().show(false);
        Axis axis = new ValueAxis();
        axis.type(AxisType.value);
        axis.position("top");
        axis.splitArea(new SplitArea().show(false));
        axis.axisLabel(new AxisLabel().show(false));
        option.xAxis(axis);

        Axis yaxis = new CategoryAxis();
        yaxis.type(AxisType.category);
        yaxis.splitArea(new SplitArea().show(false));
        yaxis.setData(yAxisList);
        option.yAxis(yaxis);

        Bar bar = new Bar("标签比例");
        bar.stack("标签比例");
        bar.itemStyle(new ItemStyle()
                .normal(new Normal().label(new Label().show(true).position(Position.insideLeft).formatter("{c}%"))));
        bar.setData(dataList);
        option.series(bar);
        // option.exportToHtml("bar9.html");
        // option.view();
        return GsonUtil.format(option);
    }

}
