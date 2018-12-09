import ReactHighcharts from 'react-highcharts';
import ReactHighstock from 'react-highcharts/ReactHighstock';
import React, { Component } from 'react';
import {
    ecConfig,
    wtConfig,
    thConfig,
} from '../../utils/chartSetting/index';
import './Chart.css'
import ChartAddOn from './ChartAddOn';
import { MdWeb } from 'react-icons/lib/md';

class Chart extends Component {

    componentDidMount(){
        this.props.getData();
    }
    
    //(t,h), e, wt, wl, ec, ph
    render() {
        const {
            dataset,
            ec,
            ph,
            th,
            wt,
            start,
            end,
            info,
            ref_ec,
            ref_ph,
            ref_wl,
            ref_e,
            ref_wt,
            dash_wl_ref,
            dash_e_ref,
        } = this.props;
        const {
            minEc,
            maxEc,
            minPh,
            maxPh,
            minTemp,
            maxTemp,
            plantName,
        } = info;
        let wl_d = 0, e_d = 0, wt_d = 0 + ' ℃', ec_d = 0 + ' mS/cm', ph_d = 0 + ' ph', len;
        if ((len = dataset.length) !== 0) {
            ec_d = dataset[len - 1].ec + ' mS/cm';
            ph_d = dataset[len - 1].ph + ' ph';
            wl_d = dataset[len - 1].wl;
            wt_d = dataset[len - 1].wt + ' ℃';
            e_d = dataset[len - 1].e;
        }
        return (
            <div className={"container-fluid chart-cont"} style={{marginBottom:'120px'}}>
                <div className="row" id="nomargin">
                    {/* <div className="container-fluid">
                        <div className="row">
                            <div className="col-12 chart-title" id="nopadding">
                                <MdWeb size={20} className="ml-3 mt-2" />
                                <div className="chart-title-text">
                                    <span id="nanum-gothic">{"Electrical Conductivity & PH"}</span>
                                </div>
                            </div>
                        </div> */}

                    <div className="col-lg-9 col-md-12 col-sm-12 portfolio-item mb-1" id="nopadding">
                        <div className="pl-2p pr-2p">
                            <div className="DeviceInfo-box-small" id="nanum-gothic" style={{ height: '100%' }}>
                                <MdWeb size={18} className="ml-2 mr-1 text-muted" />
                                <span className="text-muted mt-1 smaller size-9">
                                    {'Electrical conductivity & PH'}
                                </span>
                                <hr className="line" />
                                <ReactHighstock config={ecConfig(dataset, start, end, minEc, maxEc, minPh, maxPh)} ref={ec}></ReactHighstock>
                            </div>
                        </div>
                    </div>
                    <div className="col-lg-3 col-md-12 hidden-sm-down" id="nopadding">
                    <div className="pl-2p pr-2p">
                        <div className="container-fluid" id="nopadding">
                            {/* <div className="row" id="nomargin"> */}
                                    <ChartAddOn
                                        maxEC={maxEc}
                                        minEC={minEc}
                                        maxPH={maxPh}
                                        minPH={minPh}
                                        maxCS={maxTemp}
                                        minCS={minTemp}
                                        ref_ec={ref_ec}
                                        ref_ph={ref_ph}
                                        ref_wl={ref_wl}
                                        ref_e={ref_e}
                                        ref_wt={ref_wt}
                                        wl={wl_d}
                                        elum={e_d}
                                        lastCS={wt_d}
                                        lastPH={ph_d}
                                        lastEC={ec_d}
                                        dash_wl_ref={dash_wl_ref}
                                        dash_e_ref={dash_e_ref}
                                        plant={plantName}
                                    />
                                </div>
                            {/* </div> */}
                        </div>
                    </div>
                </div>
                {/* <div className="row mb-2">
                    <div className="col-lg-10 col-md-12 col-sm-12 portfolio-item" id="nopadding">
                        <ReactHighstock config={phConfig(dataset, start, end, minPh, maxPh)} ref={ph}/>
                    </div>
                    <div className="col-lg-2 hidden-sm-down" id="nopadding">
                        <ChartAddOn max={maxPh} min={minPh} unit="ph"/>
                    </div>
                </div> */}
                <div className="row mb-5" id="nomargin">
                    <div className="col-lg-6 col-md-12 col-sm-12 portfolio-item mb-2" id="nopadding">
                        <div className="pl-2p pr-2p">
                            <div className="DeviceInfo-box-small" id="nanum-gothic" style={{ height: '100%' }}>
                                <MdWeb size={18} className="ml-2 mr-1 text-muted" />
                                <span className="text-muted mt-1 smaller size-9">
                                    {'Temperature of Culture Solution'}
                                </span>
                                <hr className="line" />
                                <ReactHighcharts config={wtConfig(dataset, start, end)} ref={wt}></ReactHighcharts>
                            </div>
                        </div>
                    </div>
                    <div className="col-lg-6 col-md-12 col-sm-12 portfolio-item" id="nopadding">
                        <div className="pl-2p pr-2p">
                            <div className="DeviceInfo-box-small" id="nanum-gothic" style={{ height: '100%' }}>
                                <MdWeb size={18} className="ml-2 mr-1 text-muted" />
                                <span className="text-muted mt-1 smaller size-9">
                                    {'Temperature & Humidity'}
                                </span>
                                <hr className="line" />
                                <ReactHighcharts config={thConfig(dataset, start, end)} ref={th}></ReactHighcharts>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Chart;
