/*var dataset=null;
 function createChart(chartName) {
 Highcharts.setOptions({
 global: {
 useUTC: false
 }
 });

 Highcharts.chart('container', {

 title: {
 text: 'July temperatures'
 },

 xAxis: {
 type: 'datetime'
 },

 yAxis: {
 title: {
 text: null
 }
 },

 tooltip: {
 crosshairs: true,
 shared: true,
 valueSuffix: '°C'
 },

 legend: {
 },

 series: [{
 name: 'Temperature',
 data:[],
 zIndex: 1,
 marker: {
 fillColor: 'white',
 lineWidth: 2,
 lineColor: Highcharts.getOptions().colors[0]
 }
 }, {
 name: 'Range',
 data: ranges,
 type: 'arearange',
 lineWidth: 0,
 linkedTo: ':previous',
 color: Highcharts.getOptions().colors[0],
 fillOpacity: 0.3,
 zIndex: 0,
 marker: {
 enabled: false
 }
 }]
 });

 // Create the chart
 Highcharts.stockChart(chartName, {
 chart: {
 events: {
 load: function () {
 dataset=this.series[0];
 }
 }
 },

 rangeSelector: {
 buttons: [{
 count: 1,
 type: 'minute',
 text: '1M'
 }, {
 count: 5,
 type: 'minute',
 text: '5M'
 }, {
 type: 'all',
 text: 'All'
 }],
 inputEnabled: false,
 selected: 0
 },

 title: {
 text: 'Live random data'
 },

 exporting: {
 enabled: false
 },

 series: [{
 name: 'Random data',
 data:[]
 }]
 });
 }

 function dataAdd(data){
 dataset.addPoint([(new Date()).getTime(),data]);
 }
 */

var dataset;

function createChart(chartName) {
	$('#' + chartName).highcharts({
		chart : {
			type : 'line',
			events : {
				load : function() {
					dataset = this.series[0];
				}
			}
		},
		title : {
			text : false
		},
		xAxis : {
			type : 'datetime',
			minRange : 60 * 1000
		},
		yAxis : {
			title : {
				text : false
			}
		},
		legend : {
			enabled : false
		},
		plotOptions : {
			series : {
				threshold : 0,
				marker : {
					enabled : false
				}
			}
		},
		series : [ {
			name : 'Data',
			data : []
		} ]
	});
}

function dataAdd(data) {
	dataset.addPoint([ (new Date()).getTime(), data ]);
}