
// Ph Config
const phConfig = (dataset, start, end) => {
    return {
        time:{
            useUTC:false,
        },
        chart: {
            zoomType: 'x',
            type: 'line',
        },
        title: {
            text: 'PH (2018.08.16.)'
        },
        subtitle: {
            text: 'PH센서로 부터 측정된 양액 농도'
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
            tickInterval: 3600 * 1000,
            min: start,
            max: end+1000,
        },
        yAxis: {
            title: {
                text: 'PH Values (ph)'
            },
            tickInterval: 0.5,
            tickWidth: 0.5,
            min: 0,
            max: 10,
            minPadding: 0.2,
            maxPadding: 0.2,
            maxZoom: 10,
            plotLines: [{
                label: {
                    // text: 'max',
                    x: 25
                },
                color: 'orange',
                width: 2,
                value: 5,
                dashStyle: 'longdashdot'
            }, {
                label: {
                    // text: 'max',
                    x: 25
                },
                color: 'orange',
                width: 2,
                value: 2,
                dashStyle: 'longdashdot'
            }],
        },
        legend: {
            enabled: false
        },
        //툴팁
        tooltip: {
        },

        rangeSelector: {
            selected: 1
        },

        plotOptions: {
            series: {
                zones: [{
                    value: 2,
                    color: 'red'
                }, {
                    value: 5,
                    color: 'green'
                }, {
                    color: 'red'
                }]
            },
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
            },
            marker: {
                lineWidth: 1
            }
        },
        colors: ['#6CF', '#39F', '#06C', '#036', '#000'],

        series: [{
            // type: 'spline',
            data: dataset.map(
                (arg) => [arg.d, arg.ec]
            ),
            // threshold: 5,
            // negativeColor: 'green',
            // color: 'red',
            tooltip: {
                valueDecimals: 2
            }
        }]
    }
}

export default phConfig;