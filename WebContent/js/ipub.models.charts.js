/*
 * Author Vic Lee Vision 1.0.0.0 ipubs 是一个全局对象，封装所有的业务功能和模块
 * 
 */
var ipubs = ipubs || {};
(function(f) {
	// models 对象封系统常用的业务功能
	f.options = f.options || {
		appPath : '/jw'
	};
	f.models = f.models || {};
	f.models.chart = {
		/* 饼图 */
		drawPie : function(target, data, title) {

			var plot1 = $.jqplot(target, [data], {
						seriesDefaults : {
							// Make this a pie chart.
							// PieRenderer(设置饼状图的OPtion对象)
							// 饼状图通过seriesDefaults和series配置对象进行配置
							renderer : jQuery.jqplot.PieRenderer,

							rendererOptions : {
								diameter : undefined, // 设置饼的直径
								padding : 10, // 饼距离其分类名称框或者图表边框的距离，变相该表饼的直径
								sliceMargin : 0, // 饼的每个部分之间的距离
								fill : true, // 设置饼的每部分被填充的状态
								shadow : true, // 为饼的每个部分的边框设置阴影，以突出其立体效果
								shadowOffset : 3, // 设置阴影区域偏移出饼的每部分边框的距离
								shadowDepth : 3, // 设置阴影区域的深度
								shadowAlpha : 0.2, // 设置阴影区域的透明度
								background : '#fff', // 设置整个图表区域的背景色
								// Put data labels on the pie slices.
								// By default, labels show the percentage of the
								// slice.
								showDataLabels : true,
								onMouseOverSlice : function(ev, nv) {
									if (nv == null) {
										$('#chartTip').text('').hide();
									} else {
										$('#chartTip').text(nv[2][0] + ':'
												+ nv[2][1]).css('left',
												(ev.pageX + 20) + 'px').css(
												'top', (ev.pageY + 20) + 'px')
												.show();
									}
								}
							}
						},
						title : {
							text : title,
							show : true
						},
						grid : {
							drawGridLines : true, // wether to draw lines
													// across the grid or not.
							gridLineColor : '#0f0', // 设置整个图标区域网格背景线的颜色
							background : 'transparent', // 设置整个图表区域的背景色
							borderColor : '#f00', // 设置图表的(最外侧)边框的颜色
							borderWidth : 0, // 设置图表的（最外侧）边框宽度
							shadow : true, // 为整个图标（最外侧）边框设置阴影，以突出其立体效果
							shadowAngle : 45, // 设置阴影区域的角度，从x轴顺时针方向旋转
							shadowOffset : 2.5, // 设置阴影区域偏移出图片边框的距离
							shadowWidth : 5, // 设置阴影区域的宽度
							shadowDepth : 5, // 设置影音区域重叠阴影的数量
							shadowAlpha : 0.00, // 设置阴影区域的透明度
							renderer : $.jqplot.CanvasGridRenderer, // renderer
																	// to use to
																	// draw the
																	// grid.
							rendererOptions : {}
							// options to pass to the renderer. Note, the
							// default
							// CanvasGridRenderer takes no additional options.
						},
						legend : {
							show : false,
							location : 'e'
						}
					});
			return plot1;
		}
	};

})(ipubs);

$(document).ready(function() {
	var data = [['Heavy Industry', 12], ['Retail', 9], ['Light Industry', 14],
			['Out of home', 16], ['Commuting', 7], ['Orientation', 9]];
	var data2 = [['Heavy Industry', 43], ['Retail', 92],
			['Light Industry', 45], ['Orientation', 42]];
	var plot1 = ipubs.models.chart.drawPie("chart1", data, '终端在线率');
	var plot2 = ipubs.models.chart.drawPie("chart2", data, '终端同步率');
	var plot3 = ipubs.models.chart.drawPie("chart3", data2, '程序同步率');
	var plot4 = ipubs.models.chart.drawPie("chart4", data, '固件同步率');
	var plot5 = ipubs.models.chart.drawPie("chart5", data, '配置同步率');

	$('.bannerBg .chart .jqplot-title').css('top', '205px').css('width',
			'170px').css('left', '15px');

});
