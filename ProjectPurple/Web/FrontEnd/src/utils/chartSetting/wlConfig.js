
// water limig Config
const wlConfig = (dataset)=>{
    return {
        chart: {
            zoomType: 'x',
            type: 'spline',
        },
        title: {
            text: 'Electric Conductivity(2018.08.16.)'
        },
        subtitle: {
            text: 'EC센서로 부터 측정된 식물 양액 흡수'
        },
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: { // don't display the dummy year
                month: '%e. %b',
                year: '%b'
            },
            title: {
                text: 'Date'
            }
        },
        yAxis: {
            title: {
                text: 'Snow depth (m)'
            },
            min: 0
        },
        legend: {
            enabled: false
        },
        //툴팁
        tooltip: {
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

        series: [{
            // type: 'spline',
            data: dataset.map(
                (arg) => [arg.d, arg.ec]
            )
        }]
    }
}

export default wlConfig;