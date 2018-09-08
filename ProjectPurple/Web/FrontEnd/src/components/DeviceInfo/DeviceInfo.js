import React, { Fragment, Component } from 'react';
import { MdWeb, MdAutorenew, MdTimer } from 'react-icons/lib/md';
import PropTypes from 'prop-types';
import {
    SfFloor,
    SfPipe,
} from '../../components';
import './DeviceInfo.css';
import TextCenter from '../TextCenter/TextCenter';
import ReactTable, { ReactTableDefaults } from "react-table";
import 'react-table/react-table.css';
import moment from 'moment';

class DeviceInfo extends Component {

    componentDidMount(){
        this.props.getDeviceLog()
    }

    render() {
        const { sfCode, farm, sendFunc, selectedSf, logset, startDate } = this.props;
        const { ledCtrlMode, ledSt, coolerSt, pumpSt } = selectedSf.toJS();
        let isHidden = '';
        let pumpText = pumpSt === 'T' ? '켜짐' : '꺼짐';
        let led = ledSt === 'F' ? { code: 4, text: '꺼짐', btn: 'LED 켜기', state: ledCtrlMode === 'T' ? 'disabled' : ' selected' }
            : { code: 5, text: '켜짐', btn: 'LED 끄기', state: ledCtrlMode === 'T' ? '' : ' selected' };
        let cooler = coolerSt === 'F' ? { code: 8, text: '꺼짐', btn: '쿨러 켜기', state: ledCtrlMode === 'T' ? '' : ' selected' }
            : { code: 9, text: '켜짐', btn: '쿨러 끄기', state: ledCtrlMode === 'T' ? '' : ' selected' };
        let mode = ledCtrlMode === 'T' ? { code: 3, text: '자동', btn: '수동 전환' } : { code: 2, text: '수동', btn: '자동 전환' };

        const columns = [
            {
                Header: '동작 이름',
                columns: [
                    {
                        Header: "동작날짜",
                        accessor: "usedDate",
                        Cell: row => (
                            <span>{moment(row.value).format('h:mm:ss a')}</span>
                        )
                    },
                    {
                        Header: "동작 명",
                        accessor: "actName",
                    },]
            },
            {
                Header: '동작 정보',
                columns: [
                    {
                        Header: "수행IP주소",
                        accessor: "usedIp",
                    },
                    {
                        Header: "수행결과",
                        accessor: "result",
                        Cell: row => {
                            console.log(row.value)
                            return (
                                <span>
                                    <span style={{
                                        color: row.value === 'T' ? '#57d500'
                                            : row.value === 'F' ? '#ff2e00'
                                                : '#ffbf00',
                                        // transition: 'all .3s ease'
                                    }}>&#x25cf;</span> {
                                        row.value === 'T' ? 'SUCCESS'
                                            : row.value === 'F' ? `FAIL`
                                                : 'PENDING'
                                    }
                                </span>
                            )
                        }
                    }]
            }
        ];
        return (
            <Fragment>
                <div className={"container-fluid DeviceInfo-cont" + isHidden}>
                    {/* <div className={isHidden}> */}
                    <div className="row mb-5" id="nomargin">
                        <div className="col-lg-6 col-md-12 col-sm-12 col-xs-12 portfolio-item mb-2" id="nopadding">
                            <div className="pl-2p pr-2p">
                                <div className="container-fluid">
                                    <div className="row">
                                        <div className="col-12 text-muted smaller size-9 dev-title" id="nopadding">
                                            <div className="mt-1 ml-3">
                                                <MdWeb size={18} className="mr-1" />
                                                <span id="nanum-gothic" className="mb-1">
                                                    {"sf-device#" + sfCode + ' port state'}
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-12 DeviceInfo-box" id="nopadding">
                                            <div className="mt-2">
                                                {farm !== null &&
                                                    <Fragment>
                                                        <SfFloor floorIndex={3} option={false}>
                                                            <SfPipe portIndex={1} pipe={farm.get(0)} pipeIndex={0} option={false} />
                                                            <SfPipe portIndex={9} pipe={farm.get(1)} pipeIndex={1} option={false} />
                                                        </SfFloor>
                                                        <SfFloor floorIndex={2} option={false}>
                                                            <SfPipe portIndex={17} pipe={farm.get(2)} pipeIndex={2} option={false} />
                                                            <SfPipe portIndex={25} pipe={farm.get(3)} pipeIndex={3} option={false} />
                                                        </SfFloor>
                                                    </Fragment>
                                                }
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div className="col-lg-6 col-md-12 col-sm-12 col-xs-12 portfolio-item container-fluid" id="nopadding">
                            <div className="container-fluid">
                                <div className="row">
                                    <div className="col-lg-3 col-md-3 col-sm-3 col-xs-3 portfolio-item mb-1" id="nopadding">
                                        <div className="pl-2p pr-2p">
                                            <div className="DeviceInfo-box-small" id="nanum-gothic">
                                                <MdWeb size={18} className="ml-2 mr-1 text-muted" />
                                                <span className="text-muted mt-1 smaller size-9">
                                                    pump state
                                            </span>
                                                <hr className="line" />
                                                <TextCenter>
                                                    <span className="size-16">
                                                        <strong>{pumpText}</strong>
                                                    </span>
                                                </TextCenter>
                                                {/* <button className="DeviceInfo-btn mr-1 mt-1"
                                                onClick={() => sendFunc()}>
                                                <MdAutorenew />
                                                <span className="size-7 smaller">펌프 끄기</span>
                                            </button> */}

                                                <div className="text-muted smaller size-9 mt-1 mr-1"
                                                    style={{ textAlign: 'right', width: '100%' }}>
                                                    <span className="mr-1" style={{ border: '1px solid rgba(0,0,0,.125)', borderRadius: '4px', padding: '3px 5px 3px 5px' }}
                                                    ><MdTimer className={"mr-1"} />{moment().diff(startDate, 'days')}일 경과</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="col-lg-3 col-md-3 col-sm-3 col-xs-3 portfolio-item mb-1" id="nopadding">
                                        <div className="pl-2p pr-2p">
                                            <div className="DeviceInfo-box-small" id="nanum-gothic">
                                                <MdWeb size={18} className="ml-2 mr-1 text-muted" />
                                                <span className="text-muted mt-1 smaller size-9">
                                                    led state
                                    </span><hr className="line" />
                                                <TextCenter>
                                                    <span className="size-16">
                                                        <strong>{led.text}</strong>
                                                    </span>
                                                </TextCenter>
                                                <button className={"DeviceInfo-btn mr-1 mt-1" + led.state}
                                                    disabled={led.state !== ' selected'}
                                                    onClick={() => sendFunc(led.code)}>
                                                    <MdAutorenew />
                                                    <span className="size-7 smaller">{led.btn}</span>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="col-lg-3 col-md-3 col-sm-3 col-xs-3 portfolio-item mb-1" id="nopadding">
                                        <div className="pl-2p pr-2p">
                                            <div className="DeviceInfo-box-small" id="nanum-gothic">
                                                <MdWeb size={18} className="ml-2 mr-1 text-muted" />
                                                <span className="text-muted mt-1 smaller size-9">
                                                    cooler state
                                    </span><hr className="line" />
                                                <TextCenter>
                                                    <span className="size-16">
                                                        <strong>{cooler.text}</strong>
                                                    </span>
                                                </TextCenter>
                                                <button className={"DeviceInfo-btn mr-1 mt-1" + cooler.state}
                                                    disabled={cooler.state !== ' selected'}
                                                    onClick={() => sendFunc(cooler.code)}>
                                                    <MdAutorenew />
                                                    <span className="size-7 smaller">{cooler.btn}</span>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="col-lg-3 col-md-3 col-sm-3 col-xs-3 portfolio-item mb-1" id="nopadding">
                                        <div className="pl-2p pr-2p">
                                            <div className="DeviceInfo-box-small" id="nanum-gothic">
                                                <MdWeb size={18} className="ml-2 mr-1 text-muted" />
                                                <span className="text-muted mt-1 smaller size-9">
                                                    ctrl mode
                                    </span><hr className="line" />
                                                <TextCenter>
                                                    <span className="size-16">
                                                        <strong>{mode.text}</strong>
                                                    </span>
                                                </TextCenter>
                                                <button className="DeviceInfo-btn selected mr-1 mt-1"
                                                    onClick={() => sendFunc(mode.code)}>
                                                    <MdAutorenew />
                                                    <span className="size-7 smaller">{mode.btn}</span>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-lg-12 col-md-13 col-sm-12 portfolio-item" id="nopadding">
                                        <div className="pl-2p pr-2p">
                                            <div className="DeviceInfo-box-small" style={{ height: '100%' }}>
                                                <MdWeb size={18} className="ml-2 mr-1 text-muted" />
                                                <span className="text-muted mt-1 smaller size-9">
                                                    Device info
                                    </span>
                                                <hr className="line" style={{ marginBottom: 0 }} />
                                                <ReactTable
                                                    defaultPageSize={10}
                                                    // minRows={8}
                                                    data={logset.toJS()}
                                                    columns={columns}
                                                    showPageSizeOptions={false}
                                                    noDataText="수행기록이 존재하지 않습니다."
                                                    style={{
                                                        fontStyle: 'nanum-gothic'
                                                    }}
                                                    className="smaller size-9 -striped -highlight"
                                                    ofText='/'
                                                // manual

                                                />
                                                {/* <br />
                                    <div style={{ textAlign: "center" }}>
                                        <em>Tip: Hold shift when sorting to multi-sort!</em>
                                    </div>; */}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                {/* <div className="col-lg-6 col-md-6 col-sm-12 portfolio-item" id="padding-ex-left">
                                <div className="DeviceInfo-box-small">

                                </div>
                            </div> */}
                            </div>
                        </div>
                        {/* <div className="col-lg-6 col-md-12 col-sm-12 portfolio-item" id="padding-ex-left">
                        <div className="container-fluid">
                            <div className="row">
                                <div className="col-12 chart-title" id="nopadding">
                                    <MdWeb size={20} className="ml-3 mt-2" />
                                    <div className="chart-title-text">
                                        <span id="nanum-gothic">{"Temperature & Humidiity"}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-12">

                            </div>
                        </div>
                    </div> */}
                    </div>
                </div>
            </Fragment>
        )
    }
}

export default DeviceInfo;