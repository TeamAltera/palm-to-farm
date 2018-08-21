import ReactHighcharts from 'react-highcharts';
import React, { Component } from 'react';
import {
    ecConfig,
    phConfig,
    wtConfig,
    wlConfig,
    thConfig,
} from '../../utils/chartSetting/index';

class Chart extends Component {
    //(t,h), e, wt, wl, ec, ph
    render() {
        const { dataset, ec, ph, th, wt, wl, start, end } = this.props;
        console.log(new Date(start));
        console.log(new Date(end));
        return (
            <div className="container-fluid">
                <div className="row">
                    <div className="col-lg-12 col-md-12 col-sm-12 portfolio-item mb-3">
                        <ReactHighcharts config={ecConfig(dataset)} ref={ec}></ReactHighcharts>
                    </div>
                </div>
                <div className="row">
                    <div className="col-lg-6 col-md-12 col-sm-12 portfolio-item mb-3">
                        <ReactHighcharts config={phConfig(dataset, start, end)} ref={ph}/>
                    </div>
                    <div className="col-lg-6 col-md-12 col-sm-12 portfolio-item mb-3">
                        <ReactHighcharts config={wtConfig(dataset)} ref={th}></ReactHighcharts>
                    </div>
                </div>
                <div className="row mb-5">
                    <div className="col-lg-6 col-md-12 col-sm-12 portfolio-item mb-3">
                        <ReactHighcharts config={wlConfig(dataset)} ref={wt}></ReactHighcharts>
                    </div>
                    <div className="col-lg-6 col-md-12 col-sm-12 portfolio-item mb-3">
                        <ReactHighcharts config={thConfig(dataset, start, end)} ref={wl}></ReactHighcharts>
                    </div>
                </div>
            </div>
        );
    }
}

export default Chart;