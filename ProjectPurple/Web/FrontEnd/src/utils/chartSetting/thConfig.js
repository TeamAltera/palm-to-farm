
// temp, humi Config
const thConfig = (dataset, start, end) => {
    return {
        time:{
            useUTC:false,
        },
        chart: {
            zoomType: 'x',
            type: 'spline',
        },
        title: {
            text: '',
            style: {
                display: 'none'
            }
        },
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: { // don't display the dummy year
                month: '%e. %b',
                year: '%b'
            },
            labels: {
                rotation: -45,
                // x: 5,
            },
            gridLineWidth: 1,
            minPadding: 0.02,
            maxPadding: 0.02,
            tickInterval: 3600 * 1000*2,
            min: start,
            max: end+1000,
        },
        yAxis: [{
            opposite: true,
            title: {
                text: 'Humidity Values (%rh)'
            },
            min: 0
        },
        {
            opposite: false,
            title: {
                text: 'Temperature Values (℃)'
            },
            min: 0
        }
        ],
        legend: {
            enabled: true
        },
        //툴팁
        tooltip: {
            crosshair: true,
            shared: true,
        },

        plotOptions: {
            area: {
                marker: {
                    radius: 2
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            }
        },
        colors: ['#6CF', '#39F', '#06C', '#036', '#000'],

        series: [
            //humidity
            {
                // type: 'spline',
                data: dataset.map(
                    (arg) => [arg.d, arg.h]
                ),
                yAxis: 1,
                id:0,
                color:'#6CF',
                name:'humidity'
            },
            //temperature
            {
                // type: 'spline',
                data: dataset.map(
                    (arg) => [arg.d, arg.t]
                ),
                id:1,
                color:'#39F',
                name:'temperature'
            }
        ]
    }
}

export default thConfig;