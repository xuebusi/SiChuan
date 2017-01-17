
option = {
	title : {
		text : '今日浏览量PV',
		x : 'center'
	},
	tooltip : {
		trigger : 'axis'
	},
	grid : {
		x : 40,
		x2 : 10,
		y : 30,
		y2 : 30,
		borderColor : '#ddd',
		borderWidth : 1,
		backgroundColor : '#fff'
	},
	toolbox : {
		show : false
	},
	calculable : true,
	xAxis : [ {
		type : 'category',
		boundaryGap : false,
		data : [ '00', '01', '02', '03', '04', '05', '06', '07', '08', '09',
				'10', '11', '12', '13', '14', '15', '16', '17', '18', '19',
				'20', '21', '22', '23', '24' ]
	} ],
	yAxis : [ {
		type : 'value'
	} ],
	axis : {
		axisLine : {
			color : 'red',
			width : 2,
			type : 'solid'
		}
	},
	series : [ {
		name : '今日',
		type : 'line',
		smooth : true,
		itemStyle : {
			normal : {
				areaStyle : {
					type : 'default'
				}
			}
		},
		data : [ 10, 12, 21, 54, 260, 830, 710, 600, 702, 721, 754, 860, 830,
				710, 400, 412, 421, 354, 360, 230, 110, 188, 197, 65, 60 ]
	} ]
};
var mainchart = document.getElementById('mainchart');
if (mainchart) {
	var myChart = echarts
			.init(document.getElementById('mainchart'), 'macarons');
	// 为echarts对象加载数据
	myChart.setOption(option);
}

browseroption = {
	tooltip : {
		trigger : 'item',
		formatter : "{a} <br/>{b} : {c} ({d}%)"
	},
	legend : {
		orient : 'vertical',
		x : 'left',
		data : [ 'IE 9+', 'Chrome', 'Firefox', 'Safari', 'IE 8+', '其他' ]
	},
	toolbox : {
		show : false
	},
	calculable : false,
	series : [

	{
		name : '终端类型',
		type : 'pie',
		radius : [ 60, 100 ],

		// for funnel
		x : '60%',
		width : '35%',
		funnelAlign : 'left',
		max : 1048,

		data : [ {
			value : 335,
			name : 'IE 9+'
		}, {
			value : 310,
			name : 'Chrome'
		}, {
			value : 234,
			name : 'Firefox'
		}, {
			value : 135,
			name : 'Safari'
		}, {
			value : 1048,
			name : 'IE 8+'
		}, {
			value : 251,
			name : '其他'
		} ]
	} ]
};
var browserdiv = document.getElementById('browserdiv');
if (browserdiv) {
	var browserChart = echarts.init(document.getElementById('browserdiv'),
			'macarons');
	// 为echarts对象加载数据
	browserChart.setOption(browseroption);
}

$(".changemainchart").click(
		function() {

			option = {
				title : {
					text : '今日UV',
					x : 'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				grid : {
					x : 40,
					x2 : 10,
					y : 30,
					y2 : 30,
					borderColor : '#ddd',
					borderWidth : 1,
					backgroundColor : '#fff'
				},
				toolbox : {
					show : false
				},
				calculable : true,
				xAxis : [ {
					type : 'category',
					boundaryGap : false,
					data : [ '00', '01', '02', '03', '04', '05', '06', '07',
							'08', '09', '10', '11', '12', '13', '14', '15',
							'16', '17', '18', '19', '20', '21', '22', '23',
							'24' ]
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				axis : {
					axisLine : {
						color : 'red',
						width : 2,
						type : 'solid'
					}
				},
				series : [ {
					name : 'today',
					type : 'line',
					smooth : true,
					itemStyle : {
						normal : {
							areaStyle : {
								type : 'default'
							}
						}
					},
					data : [ 30, 182, 434, 430, 382, 434, 791, 690, 630, 710,
							730, 682, 434, 791, 390, 430, 410, 591, 390, 330,
							410, 391, 390, 30, 26 ]
				} ]
			};
			var mainchart = document.getElementById('mainchart');
			if (mainchart) {
				var myChart = echarts.init(
						document.getElementById('mainchart'), 'macarons');
				// 为echarts对象加载数据
				myChart.setOption(option);
			}

		});

ageoption = {
	toolbox : {
		show : false
	},
	xAxis : [ {
		type : 'category',
		data : [ '14', '16', '18', '25', '30', '35', '40', '45', '50' ]
	} ],
	yAxis : [ {
		type : 'value'
	} ],
	grid : {
		x : 25,
		x2 : 5,
		y : 30,
		y2 : 25
	},
	series : [ {
		name : '蒸发量',
		type : 'bar',
		data : [ 2.0, 4.9, 17.0, 30.2, 25.6, 6.7, 5.6, 4.2, 3.63 ]
	} ]
};
var agechart = document.getElementById('agechart');
if (agechart) {
	var ageChart = echarts
			.init(document.getElementById('agechart'), 'macarons');
	// 为echarts对象加载数据
	ageChart.setOption(ageoption);
}

genderoption = {
	tooltip : {
		trigger : 'item',
		formatter : "{a} <br/>{b} : {c} ({d}%)"
	},
	legend : {
		orient : 'horizontal',
		x : 'center',
		y : 'bottom',
		data : [ '男', '女' ]
	},
	toolbox : {
		show : false
	},
	calculable : false,
	series : [

	{
		name : '访问来源',
		type : 'pie',
		radius : [ 50, 80 ],

		data : [ {
			value : 73,
			name : '男'
		}, {
			value : 27,
			name : '女'
		} ]
	} ]
};
var genderdiv = document.getElementById('genderdiv');
if (genderdiv) {
	var genderChart = echarts.init(document.getElementById('genderdiv'),
			'macarons');
	// 为echarts对象加载数据
	genderChart.setOption(genderoption);
}

educationoption = {
	toolbox : {
		show : false
	},
	calculable : true,
	xAxis : [ {
		type : 'value',
		boundaryGap : [ 0, 0.01 ]
	} ],
	grid : {
		x : 45,
		x2 : 10,
		y : 8,
		y2 : 25
	},
	yAxis : [ {
		type : 'category',
		data : [ '初中', '高中', '大学', '研究生', '博士', '其他' ]
	} ],
	series : [ {
		name : '2012年',
		type : 'bar',
		data : [ 0.12, 0.22, 0.42, 0.06, 0.03, 0.12 ]
	} ]
};
var educationdiv = document.getElementById('educationdiv');
if (educationdiv) {
	var educationChart = echarts.init(document.getElementById('educationdiv'),
			'macarons');
	// 为echarts对象加载数据
	educationChart.setOption(educationoption);
}

var labelTop = {
	normal : {
		label : {
			show : true,
			position : 'center',
			formatter : '{b}',
			textStyle : {
				baseline : 'bottom'
			}
		},
		labelLine : {
			show : false
		}
	}
};
var labelFromatter = {
	normal : {
		label : {
			formatter : function(params) {
				return 100 - params.value + '%'
			},
			textStyle : {
				baseline : 'top'
			}
		}
	},
}
var labelBottom = {
	normal : {
		color : '#ccc',
		label : {
			show : true,
			position : 'center'
		},
		labelLine : {
			show : false
		}
	},
	emphasis : {
		color : 'rgba(0,0,0,0)'
	}
};
var radius = [ 40, 55 ];
occupationoption = {
	legend : {
		x : 'center',
		y : 'center',
		data : [ 'IT', '证券行业', '房地产', '电力', '教育', '银行', '运输', '广告', '农业', '零售' ]
	},
	toolbox : {
		show : false
	},
	series : [ {
		type : 'pie',
		center : [ '10%', '30%' ],
		radius : radius,
		x : '0%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 46,
			itemStyle : labelBottom
		}, {
			name : 'IT',
			value : 54,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '30%', '30%' ],
		radius : radius,
		x : '20%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 56,
			itemStyle : labelBottom
		}, {
			name : '证券行业',
			value : 44,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '50%', '30%' ],
		radius : radius,
		x : '40%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 65,
			itemStyle : labelBottom
		}, {
			name : '房地产',
			value : 35,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '70%', '30%' ],
		radius : radius,
		x : '60%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 70,
			itemStyle : labelBottom
		}, {
			name : '电力',
			value : 30,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '90%', '30%' ],
		radius : radius,
		x : '80%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 73,
			itemStyle : labelBottom
		}, {
			name : '教育',
			value : 27,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '10%', '70%' ],
		radius : radius,
		y : '55%', // for funnel
		x : '0%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 78,
			itemStyle : labelBottom
		}, {
			name : '银行',
			value : 22,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '30%', '70%' ],
		radius : radius,
		y : '55%', // for funnel
		x : '20%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 78,
			itemStyle : labelBottom
		}, {
			name : '运输',
			value : 22,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '50%', '70%' ],
		radius : radius,
		y : '55%', // for funnel
		x : '40%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 78,
			itemStyle : labelBottom
		}, {
			name : '广告',
			value : 22,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '70%', '70%' ],
		radius : radius,
		y : '55%', // for funnel
		x : '60%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 83,
			itemStyle : labelBottom
		}, {
			name : '农业',
			value : 17,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '90%', '70%' ],
		radius : radius,
		y : '55%', // for funnel
		x : '80%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 89,
			itemStyle : labelBottom
		}, {
			name : '零售',
			value : 11,
			itemStyle : labelTop
		} ]
	} ]
};
var occupationdiv = document.getElementById('occupationdiv');
if (occupationdiv) {
	var occupationChart = echarts.init(
			document.getElementById('occupationdiv'), 'macarons');
	// 为echarts对象加载数据
	occupationChart.setOption(occupationoption);
}

realtimeoption = {
	tooltip : {
		trigger : 'axis'
	},
	toolbox : {
		show : false
	},
	grid : {
		x : 45,
		x2 : 20,
		y : 18,
		y2 : 45
	},
	xAxis : [ {
		type : 'category',
		boundaryGap : true,
		data : (function() {
			var res = [];
			var len = 60;
			while (len--) {
				res.push("11:23:" + len);
			}
			return res;
		})()
	} ],
	yAxis : [ {
		type : 'value',
		boundaryGap : [ 0.2, 0.2 ]
	} ],
	series : [

	{
		name : '浏览量PV',
		type : 'line',
		data : (function() {
			var res = [];
			var len = 100;
			while (len--) {
				res.push(Math.random() * 100 + 400);
			}
			return res;
		})()
	} ]
};
var realtimediv = document.getElementById('realtimechart');
if (realtimediv) {
	var realtimeChart = echarts.init(document.getElementById('realtimechart'),
			'macarons');
	// 为echarts对象加载数据
	realtimeChart.setOption(realtimeoption);
	// clearInterval(timeTicket);
	timeTicket = setInterval(function() {
		// 动态数据接口 addData
		realtimeChart.addData([ [ 0, // 系列索引
		Math.round(Math.random() * 100 + 400), // 新增数据
		true, // 新增数据是否从队列头部插入
		false // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
		] ]);
	}, 5000);
}

sourceoption = {
	tooltip : {
		trigger : 'item',
		formatter : "{a} <br/>{b} : {c} ({d}%)"
	},
	legend : {
		orient : 'vertical',
		x : 'left',
		data : [ '社交媒体', '搜索引擎', '直接访问', '其他方式' ]
	},
	toolbox : {
		show : false
	},
	calculable : false,
	series : [

	{
		name : '访问来源',
		type : 'pie',
		radius : [ 60, 100 ],

		// for funnel
		x : '60%',
		width : '35%',
		funnelAlign : 'left',
		max : 1048,

		data : [ {
			value : 235,
			name : '社交媒体'
		}, {
			value : 310,
			name : '搜索引擎'
		}, {
			value : 234,
			name : '直接访问'
		}, {
			value : 135,
			name : '其他方式'
		} ]
	} ]
};
var sourcediv = document.getElementById('sourcediv');
if (sourcediv) {
	var sourceChart = echarts.init(document.getElementById('sourcediv'),
			'macarons');
	// 为echarts对象加载数据
	sourceChart.setOption(sourceoption);
}

mapoption = {
	tooltip : {
		trigger : 'item'
	},
	legend : {
		orient : 'vertical',
		x : 'left',
		data : [ '浏览量PV' ]
	},
	dataRange : {
		min : 0,
		max : 2500,
		x : 'left',
		y : 'bottom',
		text : [ '高', '低' ], // 文本，默认为数值文本
		calculable : true
	},
	toolbox : {
		show : false
	},
	roamController : {
		show : true,
		x : 'right',
		mapTypeControl : {
			'china' : true
		}
	},
	series : [ {
		name : '浏览量PV',
		type : 'map',
		mapType : 'china',
		roam : false,
		itemStyle : {
			normal : {
				label : {
					show : true
				}
			},
			emphasis : {
				label : {
					show : true
				}
			}
		},
		data : [ {
			name : '北京',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '天津',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '上海',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '重庆',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '河北',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '河南',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '云南',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '辽宁',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '黑龙江',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '湖南',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '安徽',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '山东',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '新疆',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '江苏',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '浙江',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '江西',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '湖北',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '广西',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '甘肃',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '山西',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '内蒙古',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '陕西',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '吉林',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '福建',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '贵州',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '广东',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '青海',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '西藏',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '四川',
			value : Math.round(Math.random() * 10000)
		}, {
			name : '宁夏',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '海南',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '台湾',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '香港',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '澳门',
			value : Math.round(Math.random() * 1000)
		} ]
	} ]
};
var mapdiv = document.getElementById('mapdiv');
if (mapdiv) {
	var mapdiv = echarts.init(document.getElementById('mapdiv'), 'macarons');
	// 为echarts对象加载数据
	mapdiv.setOption(mapoption);
}

provinceoption = {
	tooltip : {
		trigger : 'item'
	},
	legend : {
		orient : 'vertical',
		x : 'left',
		data : [ '浏览量PV' ]
	},
	dataRange : {
		min : 0,
		max : 2500,
		x : 'left',
		y : 'bottom',
		text : [ '高', '低' ], // 文本，默认为数值文本
		calculable : true
	},
	toolbox : {
		show : false
	},
	roamController : {
		show : true,
		x : 'right',
		mapTypeControl : {
			'china' : true
		}
	},
	series : [ {
		name : '浏览量PV',
		type : 'map',
		mapType : '四川',
		roam : false,
		itemStyle : {
			normal : {
				label : {
					show : true
				}
			},
			emphasis : {
				label : {
					show : true
				}
			}
		},
		data : [ {
			name : '四川',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '天津',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '上海',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '成都市',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '内江市',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '内江市',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '内江市',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '内江市',
			value : Math.round(Math.random() * 1000)
		}, {
			name : '内江市',
			value : Math.round(Math.random() * 1000)
		}, ]
	} ]
};
var provincediv = document.getElementById('provincediv');
if (provincediv) {
	var provincediv = echarts.init(document.getElementById('provincediv'),
			'macarons');
	// 为echarts对象加载数据
	provincediv.setOption(provinceoption);
}

pagecontributionoption = {
	color : [ '#FBB367', '#80B1D2', '#FB8070', '#CC99FF', '#B0D961', '#99CCCC',
			'#BEBBD8', '#FFCC99', '#8DD3C8', '#FF9999', '#CCEAC4', '#BB81BC',
			'#FBCCEC', '#CCFF66', '#99CC66', '#66CC66', '#FF6666', '#FFED6F',
			'#ff7f50', '#87cefa', ],
	toolbox : {
		show : false,
		feature : {
			restore : {
				show : true
			},
			magicType : {
				show : true,
				type : [ 'force', 'chord' ]
			},
			saveAsImage : {
				show : true
			}
		}
	},
	tooltip : {
		trigger : 'item',
		formatter : function(params) {
			if (params.name && params.name.indexOf('-') != -1) {
				return params.name.replace('-', ' ' + params.seriesName + ' ')
			} else {
				return params.name ? params.name : params.data.id
			}
		}
	},
	legend : {
		data : [ '首页', '个人业务', '理财超市', '生活服务', '企业服务', '关于我们', '其他功能', '',
				'贡献值', '被贡献', ],
		orient : 'vertical',
		x : 'left'
	},
	series : [
			{
				"name" : "贡献值",
				"type" : "chord",
				"showScaleText" : false,
				"clockWise" : false,
				"data" : [ {
					"name" : "首页"
				}, {
					"name" : "个人业务"
				}, {
					"name" : "理财超市"
				}, {
					"name" : "生活服务"
				}, {
					"name" : "企业服务"
				}, {
					"name" : "关于我们"
				}, {
					"name" : "其他功能"
				} ],
				"matrix" : [ [ 0, 10, 20, 20, 10, 10, 20 ],
						[ 10, 0, 0, 0, 0, 10, 10 ], [ 20, 0, 0, 10, 0, 0, 0 ],
						[ 20, 0, 10, 0, 0, 10, 0 ], [ 10, 0, 0, 0, 0, 0, 0 ],
						[ 10, 10, 0, 10, 0, 0, 0 ], [ 20, 10, 0, 0, 0, 0, 0 ] ]
			},
			{
				"name" : "被贡献",
				"type" : "chord",
				"insertToSerie" : "贡献值",
				"data" : [ {
					"name" : "首页"
				}, {
					"name" : "个人业务"
				}, {
					"name" : "理财超市"
				}, {
					"name" : "生活服务"
				}, {
					"name" : "企业服务"
				}, {
					"name" : "关于我们"
				}, {
					"name" : "其他功能"
				} ],
				"matrix" : [ [ 0, 10, 10, 0, 10, 10, 10 ],
						[ 10, 0, 0, 0, 20, 10, 10 ], [ 10, 0, 0, 10, 0, 0, 0 ],
						[ 10, 0, 10, 0, 0, 10, 0 ], [ 10, 0, 0, 0, 0, 0, 0 ],
						[ 10, 10, 0, 10, 0, 0, 0 ], [ 10, 10, 0, 0, 0, 0, 0 ] ]
			} ]
};
var pagecontributiondiv = document.getElementById('pagecontributiondiv');
if (pagecontributiondiv) {
	var pagecontributiondiv = echarts.init(document
			.getElementById('pagecontributiondiv'), 'macarons');
	// 为echarts对象加载数据
	pagecontributiondiv.setOption(pagecontributionoption);
}

var labelTop = {
	normal : {
		label : {
			show : true,
			position : 'center',
			formatter : '{b}',
			textStyle : {
				baseline : 'bottom'
			}
		},
		labelLine : {
			show : false
		}
	}
};
var labelFromatter = {
	normal : {
		label : {
			formatter : function(params) {
				return 100 - params.value + '%'
			},
			textStyle : {
				baseline : 'top'
			}
		}
	},
}
var labelBottom = {
	normal : {
		color : '#ccc',
		label : {
			show : true,
			position : 'center'
		},
		labelLine : {
			show : false
		}
	},
	emphasis : {
		color : 'rgba(0,0,0,0)'
	}
};
var radius = [ 40, 55 ];
cunsumeoption = {
	legend : {
		x : 'center',
		y : 'center',
		data : [ '转账付款', '支付宝快捷支付', '银联在线支付', '无卡取款', 'ATM取款', '本行账户授权',
				'本行账户请求授权' ]
	},
	toolbox : {
		show : false
	},
	series : [ {
		type : 'pie',
		center : [ '10%', '30%' ],
		radius : radius,
		x : '0%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 46,
			itemStyle : labelBottom
		}, {
			name : '转账付款',
			value : 54,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '30%', '30%' ],
		radius : radius,
		x : '20%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 56,
			itemStyle : labelBottom
		}, {
			name : '支付宝快捷支付',
			value : 44,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '50%', '30%' ],
		radius : radius,
		x : '40%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 65,
			itemStyle : labelBottom
		}, {
			name : '银联在线支付',
			value : 35,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '70%', '30%' ],
		radius : radius,
		x : '60%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 70,
			itemStyle : labelBottom
		}, {
			name : '无卡取款',
			value : 30,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '90%', '30%' ],
		radius : radius,
		x : '80%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 73,
			itemStyle : labelBottom
		}, {
			name : 'ATM取款',
			value : 27,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '10%', '70%' ],
		radius : radius,
		y : '55%', // for funnel
		x : '0%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 78,
			itemStyle : labelBottom
		}, {
			name : '本行账户授权',
			value : 22,
			itemStyle : labelTop
		} ]
	}, {
		type : 'pie',
		center : [ '30%', '70%' ],
		radius : radius,
		y : '55%', // for funnel
		x : '20%', // for funnel
		itemStyle : labelFromatter,
		data : [ {
			name : 'other',
			value : 78,
			itemStyle : labelBottom
		}, {
			name : '本行账户请求授权',
			value : 22,
			itemStyle : labelTop
		} ]
	} ]
};
var cunsumediv = document.getElementById('cunsumediv');
if (cunsumediv) {
	var cunsumediv = echarts.init(document.getElementById('cunsumediv'),
			'macarons');
	// 为echarts对象加载数据
	cunsumediv.setOption(cunsumeoption);
}

focusoption = {
	tooltip : {
		trigger : 'axis'
	},
	toolbox : {
		show : false
	},
	calculable : true,
	polar : [ {
		indicator : [ {
			text : '消费',
			max : 100
		}, {
			text : '保险',
			max : 100
		}, {
			text : '理财',
			max : 100
		}, {
			text : '生活',
			max : 100
		}, {
			text : '支付',
			max : 100
		}, {
			text : '转账',
			max : 100
		} ],
		radius : 130
	} ],
	series : [ {
		name : '完全实况球员数据',
		type : 'radar',
		itemStyle : {
			normal : {
				areaStyle : {
					type : 'default'
				}
			}
		},
		data : [ {
			value : [ 55, 42, 88, 44, 76, 86 ],
			name : '舍普琴科'
		} ]
	} ]
};
var focusdiv = document.getElementById('focusdiv');
if (focusdiv) {
	var focusdiv = echarts
			.init(document.getElementById('focusdiv'), 'macarons');
	// 为echarts对象加载数据
	focusdiv.setOption(focusoption);
}

relationoption = {
	tooltip : {
		trigger : 'item',
		formatter : '{a} : {b}'
	},
	toolbox : {
		show : false
	},
	legend : {
		x : 'left',
		data : [ '收入', '支出' ]
	},
	series : [ {
		type : 'force',
		name : "人物关系",
		ribbonType : false,
		categories : [ {
			name : '人物'
		}, {
			name : '收入'
		}, {
			name : '支出'
		} ],
		itemStyle : {
			normal : {
				label : {
					show : true,
					textStyle : {
						color : '#333'
					}
				},
				nodeStyle : {
					brushType : 'both',
					borderColor : 'rgba(255,215,0,0.4)',
					borderWidth : 1
				},
				linkStyle : {
					type : 'curve'
				}
			},
			emphasis : {
				label : {
					show : false
				// textStyle: null // 默认使用全局文本样式，详见TEXTSTYLE
				},
				nodeStyle : {
				// r: 30
				},
				linkStyle : {}
			}
		},
		useWorker : false,
		minRadius : 15,
		maxRadius : 25,
		gravity : 1.1,
		scaling : 1.1,
		roam : 'move',
		nodes : [ {
			category : 0,
			name : '张三',
			value : 10,
			label : '张三\n（主要）'
		}, {
			category : 1,
			name : '李四',
			value : 2
		}, {
			category : 1,
			name : '王五',
			value : 3
		}, {
			category : 1,
			name : '李六',
			value : 3
		}, {
			category : 1,
			name : '刘八',
			value : 7
		}, {
			category : 2,
			name : '李一',
			value : 5
		}, {
			category : 2,
			name : '李二',
			value : 8
		}, {
			category : 2,
			name : '李山',
			value : 9
		}, {
			category : 2,
			name : '李五',
			value : 4
		}, {
			category : 2,
			name : '李八',
			value : 4
		}, {
			category : 2,
			name : '李九',
			value : 1
		}, ],
		links : [ {
			source : '李四',
			target : '张三',
			weight : 1,
			name : '女儿'
		}, {
			source : '王五',
			target : '张三',
			weight : 2,
			name : '父亲'
		}, {
			source : '李六',
			target : '张三',
			weight : 1,
			name : '母亲'
		}, {
			source : '刘八',
			target : '张三',
			weight : 2
		}, {
			source : '李一',
			target : '张三',
			weight : 3,
			name : '合伙人'
		}, {
			source : '李二',
			target : '张三',
			weight : 1
		}, {
			source : '李山',
			target : '张三',
			weight : 6,
			name : '竞争对手'
		}, {
			source : '李五',
			target : '张三',
			weight : 1,
			name : '爱将'
		}, {
			source : '李八',
			target : '张三',
			weight : 1
		}, {
			source : '李九',
			target : '张三',
			weight : 1
		}, {
			source : '李六',
			target : '王五',
			weight : 1
		}, {
			source : '李二',
			target : '王五',
			weight : 1
		}, {
			source : '李二',
			target : '李六',
			weight : 1
		}, {
			source : '李二',
			target : '刘八',
			weight : 1
		}, {
			source : '李二',
			target : '李一',
			weight : 1
		}, {
			source : '李山',
			target : '李二',
			weight : 6
		}, {
			source : '李山',
			target : '李六',
			weight : 1
		}, {
			source : '李八',
			target : '李二',
			weight : 1
		} ]
	} ]
};

var relationdiv = document.getElementById('relationdiv');
if (relationdiv) {
	var relationdiv = echarts.init(document.getElementById('relationdiv'),
			'macarons');
	// 为echarts对象加载数据
	relationdiv.setOption(relationoption);
}

function createRandomItemStyle() {
	return {
		normal : {
			color : 'rgb('
					+ [ Math.round(Math.random() * 160),
							Math.round(Math.random() * 160),
							Math.round(Math.random() * 160) ].join(',') + ')'
		}
	};
}

tagsoption = {
	tooltip : {
		show : true
	},
	series : [ {
		type : 'wordCloud',
		size : [ '80%', '80%' ],
		textRotation : [ 0, 45, 90, -45 ],
		textPadding : 0,
		autoSize : {
			enable : true,
			minSize : 14
		},
		data : [ {
			name : "购物达人",
			value : 10000,
			itemStyle : createRandomItemStyle()
		}, {
			name : "转账达人",
			value : 6181,
			itemStyle : createRandomItemStyle()
		}, {
			name : "爱车人士",
			value : 3000,
			itemStyle : createRandomItemStyle()
		}, {
			name : "阅读爱好者",
			value : 2000,
			itemStyle : createRandomItemStyle()
		}, {
			name : "上网达人",
			value : 1000,
			itemStyle : createRandomItemStyle()
		}, {
			name : "爱旅游",
			value : 600,
			itemStyle : createRandomItemStyle()
		}, {
			name : "微博控",
			value : 800,
			itemStyle : createRandomItemStyle()
		}, {
			name : "音乐迷",
			value : 400,
			itemStyle : createRandomItemStyle()
		}, {
			name : "买彩票",
			value : 200,
			itemStyle : createRandomItemStyle()
		}, {
			name : "军事迷",
			value : 100,
			itemStyle : createRandomItemStyle()
		}, {
			name : "财经高手",
			value : 3300,
			itemStyle : createRandomItemStyle()
		}

		]
	} ]
};
var tagsdiv = document.getElementById('tagsdiv');
if (tagsdiv) {
	var tagsdiv = echarts.init(document.getElementById('tagsdiv'), 'macarons');
	// 为echarts对象加载数据
	tagsdiv.setOption(tagsoption);
}

consumeoption = {
	tooltip : {
		trigger : 'axis'
	},
	grid : {
		x : 40,
		x2 : 20,
		y : 10,
		y2 : 30,
		borderColor : '#ddd',
		borderWidth : 1,
		backgroundColor : '#fff'
	},
	toolbox : {
		show : false
	},
	calculable : true,
	xAxis : [ {
		type : 'category',
		boundaryGap : false,
		data : [ '04', '05', '06' ]
	} ],
	yAxis : [ {
		type : 'value'
	} ],
	axis : {
		axisLine : {
			color : 'red',
			width : 2,
			type : 'solid'
		}
	},
	series : [ {
		name : '今日',
		type : 'line',
		smooth : true,
		itemStyle : {
			normal : {
				areaStyle : {
					type : 'default'
				}
			}
		},
		data : [ 1000, 1200, 221 ]
	} ]
};
var consumechart = document.getElementById('consumechart');
if (consumechart) {
	var consumechart = echarts.init(document.getElementById('consumechart'),
			'macarons');
	// 为echarts对象加载数据
	consumechart.setOption(consumeoption);
}
