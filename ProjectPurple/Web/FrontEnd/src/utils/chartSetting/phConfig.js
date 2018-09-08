
// Ph Config
const phConfig = (dataset, start, end, min, max) => {
    return {
        time: {
            useUTC: false,
        },
        chart: {
            zoomType: 'x',
            type: 'spline',
        },
        title: {
            text: 'Electrical Conductivity'
        },
        // subtitle: {
        //     text: '전기전도도를 통한 양액의 염류 농도'
        // },
        rangeSelector: {
            // verticalAlign: 'top',
            // buttonPosition: {
            //     align: 'left',
            //     x: 0,
            //     y: 0
            // },
            // buttons: [{
            //     count: 120,
            //     type: 'minute',
            //     text: '30M'
            // }, {
            //     count: 240,
            //     type: 'minute',
            //     text: ' 1H'
            // },
            // {
            //     count: 240 * 3,
            //     type: 'minute',
            //     text: ' 3H'
            // },
            // {
            //     count: 240 * 12,
            //     type: 'minute',
            //     text: '12H'
            // }, {
            //     type: 'all',
            //     text: 'ALL'
            // }],
            // selected: 0,
            // inputEnabled: false,
            enabled: false
        },
        xAxis: {
            // maxRange: 5670,
            type: 'datetime',
            dateTimeLabelFormats: { // don't display the dummy year
                second: '%H:%M:%S',
                minute: '%H:%M',
                hour: '%H:%M',
            },
            labels: {
                // rotation: -45,
                // x: 5,
            },
            // gridLineWidth: 1,
            // maxRange: 3600 * 1000,
            // maxTickInterval: 30,
            // min: start,
            // max: end + 1000,
            minPadding: 0.02,
            maxPadding: 0.02,
            crosshair: {
                width: 1,
                color: 'black',
            },
            gridLineWidth: 1,
        },
        yAxis: {
            title: {
                text: 'EC Values (mS/cm)'
            },
            labels: {
                x: -3 
            },
            opposite: false,
            tickInterval: 1,
            // tickWidth: 1,
            tickmarkPlacement:'on',
            min: 0,
            max: 14,
            plotBands: [{
                color: 'rgba(0,0,0,.125)', // Color value
                from: min, // Start of the plot band
                to: max // End of the plot band
            }],
        },
        //툴팁
        legend: {
            enabled: false
        },
        tooltip: {
            crosshair: true,
            shared: true,
            useHTML: true,
            headerFormat: '<small>{point.key}</small><table>',
            pointFormat: '<tr><td>{series.name}: </td>' +
                '<td style="text-align: right"><b>{point.y} mS/cm</b></td></tr>',
            // '+<tr><td>maxEC: </td>'+
            // '<td style="text-align: right">'+this.point.y*1.1+' mS/cm</td></tr>'+
            // '<tr><td>minEC: </td>'+
            // '<td style="text-align: right">'+this.point.y*0.9+' mS/cm</td></tr>',
            footerFormat: '</table>',
            valueDecimals: 2,
            borderColor: 'gray'
        },
        // navigator: {
        //     series: {
        //         color: '#3bc0c3',
        //         lineWidth: 2
        //     }
        // },

        plotOptions: {
            series: {
                zones: [{
                    value: min,
                    color: '#FF0000',
                }, {
                    value: max,
                    color: '#0088FF',
                    
                }, {
                    color: '#FF0000',
                }]
            },
            // area: {
            //     marker: {
            //         radius: 2
            //     },
            //     lineWidth: 1,
            //     states: {
            //         hover: {
            //             lineWidth: 1
            //         }
            //     },
            //     threshold: null
            // },
            // marker: {
            //     lineWidth: 1
            // }
        },
        colors: ['#6CF', '#39F', '#06C', '#036', '#000'],
        exporting: {
            enabled: true
        },

        series: [{
            // type: 'spline',
            name: 'EC',
            data: dataset.map(
                (arg) => [arg.d, arg.ec]
            ),

        }],
        lang: {
            noData: "센싱 데이터가 없습니다."
        },
        noData: {
            style: {
                fontWeight: 'bold',
                fontSize: '15px',
                color: '#303030'
            }
        }
    }
}

export default phConfig;