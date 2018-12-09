import React, { Fragment } from 'react';
import './ChartAddOn.css';
import SingleChart from './SingleChart'
import TextCenter from '../TextCenter/TextCenter';
import { MdWeb } from 'react-icons/lib/md';
// import {
//     spiderConfig
// } from '../../utils/chartSetting/index';
// import ReactHighcharts from 'react-highcharts';

const ChartAddOn = ({ maxEC, minEC, maxPH, minPH, lastEC, lastPH, lastCS, ref_ec, ref_ph, wl, elum,
    ref_wl, ref_e, ref_wt, dash_wl_ref, dash_e_ref, plant, minCS, maxCS }) => (
        <Fragment>

            <div className="col-lg-12 col-md-6 col-sm-12 portfolio-item mb-1" id="nopadding">
                {/* <div className="pl-2p pr-2p"> */}
                    <div className="DeviceInfo-box-small mb-1" id="nanum-gothic" style={{ height: '100%' }}>
                        <MdWeb size={18} className="ml-2 mr-1 text-muted" />
                        <span className="text-muted mt-1 smaller size-9">
                            {'Growth Condition'}
                        </span>
                        <hr className="line" />
                        <div className="Chart-addon">
                            <TextCenter>
                                <div className="mb-1">
                                    {plant}의 생육 데이터
                        </div>
                            </TextCenter>
                            <table style={{fontSize:'12px'}}>
                                <thead>
                                    <tr>
                                        <th>name</th>
                                        <th>data value</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>current ec</td>
                                        <td>
                                            <span ref={ref_ec}>{lastEC}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>current ph</td>
                                        <td>
                                            <span ref={ref_ph}>{lastPH}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>current cs temp</td>
                                        <td>
                                            <span ref={ref_wt}>{lastCS}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>appropriate ec</td>
                                        <td>{minEC}mS/cm~{maxEC}mS/cm</td>
                                    </tr>
                                    <tr>
                                        <td>appropriate ph</td>
                                        <td>{minPH}ph~{maxPH}ph</td>
                                    </tr>
                                    <tr>
                                        <td>appropriate cs</td>
                                        <td>{minCS}℃~{maxCS}℃</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                {/* </div> */}
            </div>
            
            <div className="col-lg-12 col-md-6 col-sm-12 portfolio-item mb-1" id="nopadding">
                {/* <div className="pl-2p pr-2p"> */}
                    <div className="DeviceInfo-box-small mb-1" id="nanum-gothic" style={{ height: '100%' }}>
                        <MdWeb size={18} className="ml-2 mr-1 text-muted" />
                        <span className="text-muted mt-1 smaller size-9">
                            {'Water Height & Elum'}
                        </span>
                        <hr className="line" />
                        <div className="Chart-addon">
                            <div className="per-wrapper">
                                <SingleChart arg={wl} arg_ref={ref_wl} dash_ref={dash_wl_ref}
                                    lim={100} unit="%"
                                />
                                <SingleChart arg={elum} arg_ref={ref_e} dash_ref={dash_e_ref}
                                    lim={1023} unit="cds"
                                />
                            </div>
                            <div className="container-fluid">
                                <div className="row">
                                    <div className="col-6" id="nopadding">
                                        <TextCenter>
                                            <span className="text-muted">water height</span>
                                        </TextCenter>
                                    </div>
                                    <div className="col-6" id="nopadding">
                                        <TextCenter>
                                            <span className="text-muted">current elum</span>
                                        </TextCenter>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                {/* </div> */}
            </div>
            {/* <div className="per-wrapper mt-2">
            <SingleChart arg={wl} arg_ref={ref_wl}/>
            <SingleChart arg={elum} arg_ref={ref_e}/>
        </div> */}
        </Fragment>
    );

export default ChartAddOn;