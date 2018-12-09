import React, { Fragment, Component } from 'react';
import { MdWeb, MdAutorenew, MdTimer, MdHistory } from 'react-icons/lib/md';
import PropTypes from 'prop-types';
import {
    SfFloor,
    SfPipe,
    LED,
    Cooler,
    Pump
} from '../../components';
import './DeviceInfo.css';
import TextCenter from '../TextCenter/TextCenter';
import ReactTable, { ReactTableDefaults } from "react-table";
import 'react-table/react-table.css';
import moment from 'moment';
import Moment from 'react-moment';
import { Progress } from 'react-sweet-progress';
import '../RouterItem/Router.css'

class DeviceInfo extends Component {

    componentDidMount() {
        this.props.getDeviceLog();
        this.props.last();
    }

    _getResult(minEc, maxEc, minPh, maxPh, minTemp, maxTemp, ph, ec, wt, t, h, e) {
        let phRes = minPh <= ph && maxPh >= ph ? '좋음' : '보통';
        let ecRes = minEc <= ec && maxEc >= ec ? '좋음' : '보통';
        let tempRes = minTemp <= t && maxTemp >= t ? '좋음' : '보통';
        let wtRes = 18 <= wt && 22 >= wt ? '좋음' : '보통';
        let hRes = 35 <= h && 45 >= h ? '좋음' : '보통';
        let eRes = 450 <= e && 600 >= e ? '좋음' : '보통';
        if (!ph) phRes = ''
        if (!ec) ecRes = ''
        if (!t) tempRes = ''
        if (!wt) wtRes = ''
        if (!h) hRes = ''
        if (!e) eRes = ''
        return {
            phRes,
            ecRes,
            tempRes,
            wtRes,
            hRes,
            eRes
        }
    }

    render() {
        const { sfCode, farm, sendFunc, selectedSf, logset, startDate, plantResultDisabled
            , info, last, lastDataset } = this.props;
        const { ledCtrlMode, led21St, led22St, led31St, led32St, cooler1St, cooler2St, cooler3St, pumpSt
            , apCode, sfPortCnt } = selectedSf.toJS();
        let isHidden = '';
        let pumpText = pumpSt === 'T' ? '재배중' : '재배대기';
        let d;
        let ph;
        let ec;
        let wt;
        let t;
        let h;
        let e;
        if (lastDataset) {
            d = lastDataset.d
            ph = lastDataset.ph
            ec = lastDataset.ec
            wt = lastDataset.wt
            t = lastDataset.t
            h = lastDataset.h
            e = lastDataset.e
        }

        //state가 false라면 꺼져 있음, true라면 켜져있음
        let led21 = led21St === 'F' ? { code: 41, state: false } : { code: 51, state: true };
        let led22 = led22St === 'F' ? { code: 42, state: false } : { code: 52, state: true };
        let led31 = led31St === 'F' ? { code: 43, state: false } : { code: 53, state: true };
        let led32 = led32St === 'F' ? { code: 44, state: false } : { code: 54, state: true };
        let cooler1 = cooler1St === 'F' ? { code: 81, state: false } : { code: 91, state: true };
        let cooler2 = cooler2St === 'F' ? { code: 82, state: false } : { code: 92, state: true };
        let cooler3 = cooler3St === 'F' ? { code: 83, state: false } : { code: 93, state: true };
        let pumpA = ledCtrlMode ==='F' ? { code: 98, state: true }: { code: 98, state: false };
        let pumpB = ledCtrlMode ==='F' ? { code: 99, state: true }: { code: 99, state: false };

        var cooler_arr = [cooler1St === 'T', cooler2St === 'T', cooler3St === 'T'];
        var cnt = 0;
        for (var i = 0; i < 3; i++) {
            if (cooler_arr[i]) cnt++;
        }

        let cooler_100 = cnt === 3 ? 'selected' : ' ';
        let cooler_66 = cnt === 2 ? 'selected' : ' ';
        let cooler_33 = cnt === 1 ? 'selected' : ' ';
        let cooler_0 = cnt === 0 ? 'selected' : ' ';

        let floor2_100 = (led21St === 'T' && led22St === 'T') ? 'selected' : ' ';
        let floor2_50 = (floor2_100 === ' ' && (led21St === 'T' || led22St === 'T')) ? 'selected' : ' ';
        let floor2_0 = (led21St === 'F' && led22St === 'F') ? 'selected' : ' ';

        let floor3_100 = (led31St === 'T' && led32St === 'T') ? 'selected' : ' ';
        let floor3_50 = (floor3_100 === ' ' && (led31St === 'T' || led32St === 'T')) ? 'selected' : ' ';
        let floor3_0 = (led31St === 'F' && led32St === 'F') ? 'selected' : ' ';
        let auto = ledCtrlMode === 'T' ? 'selected' : ' ';
        let manual = ledCtrlMode === 'F' ? 'selected' : ' ';

        //state가 true라면 auto, false라면 manual
        let mode = ledCtrlMode === 'T' ? { code: 3, state: true } : { code: 2, state: false };

        //T라면 자동모드 
        let isDisabled = plantResultDisabled || ledCtrlMode === 'T' ? true : false;
        let btnDisabled = plantResultDisabled || ledCtrlMode === 'T' ? 'btn-disabled' : ' ';
        let modIsDisabled = plantResultDisabled ? 'btn-disabled' : ' ';
        let modBtnDisabled = plantResultDisabled ? true : false;

        let plantName = info && info.farmingDate ? info.plantName : '없음';
        let minEc = info && info.farmingDate ? info.minEc : 0;
        let maxEc = info && info.farmingDate ? info.maxEc : 0;
        let minPh = info && info.farmingDate ? info.minPh : 0;
        let maxPh = info && info.farmingDate ? info.maxPh : 0;
        let minTemp = info && info.farmingDate ? info.maxTemp : 0;
        let maxTemp = info && info.farmingDate ? info.minTemp : 0;

        let start = info && info.farmingDate && startDate ? startDate : '없음'

        console.log(lastDataset);

        let report = this._getResult(minEc, maxEc, minPh, maxPh, minTemp, maxTemp, ph, ec, wt, t, h, e);

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
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-lg-12 col-md-12">
                            <div>
                                {/* 타이틀 */}
                                <div className="dev-block-title">
                                    <MdWeb size={18} />
                                    <span className="dev-block-title">재배기 요약 정보</span>
                                </div>

                                {/* 재배기 요약 정보 블럭 */}
                                <div className="row">
                                    {/* 1번요소 */}
                                    <div className="col-lg-6 col-md-12" id="dev-info0">
                                        <div className="dev-block-lg">
                                            <div className="ml-3 mr-3">
                                                <div className="row">
                                                    <span className="col-4 router-label mt-1"
                                                        style={{ fontSize: '14px' }}>
                                                        최근 생육상태 레포트
                                                    </span>
                                                </div>
                                                <div className="row">
                                                    <table className="dev-info-table" summary="최근 데이터 기록">
                                                        <thead>
                                                            <tr>
                                                                <th></th>
                                                                <th>Ec</th>
                                                                <th>Ph</th>
                                                                <th>수온</th>
                                                                <th>광량</th>
                                                                <th>온도</th>
                                                                <th>습도</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr>
                                                                <td>최근</td>
                                                                <td>{ec}</td>
                                                                <td>{ph}</td>
                                                                <td>{wt}</td>
                                                                <td>{e}</td>
                                                                <td>{t}</td>
                                                                <td>{h}</td>
                                                            </tr>
                                                            <tr>
                                                                <td>적정</td>
                                                                <td>{(minEc + maxEc) / 2}</td>
                                                                <td>{(minPh + maxPh) / 2}</td>
                                                                <td>20°C</td>
                                                                <td>550</td>
                                                                <td>{(minTemp + maxTemp) / 2}°C</td>
                                                                <td>40%rh</td>
                                                            </tr>
                                                            <tr style={{ borderTop: '1px solid #e7e7e7' }}>
                                                                <td>결과</td>
                                                                <td>{report.ecRes}</td>
                                                                <td>{report.phRes}</td>
                                                                <td>{report.wtRes}</td>
                                                                <td>{report.eRes}</td>
                                                                <td>{report.tempRes}</td>
                                                                <td>{report.hRes}</td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <div className="row" style={{ marginTop: '13px' }}>
                                                    <div className="col-4 router-label"></div>
                                                    <div className="col-8 router-content">
                                                        <MdHistory size={16} className="mr-1" />
                                                        <Moment format="YYYY/MM/DD A HH:mm:ss">
                                                            {/* {new Date(regDate)} */}
                                                            {d}
                                                        </Moment>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    {/* 2번요소 */}
                                    <div className="col-lg-3 col-md-6" id="dev-info1">
                                        <div className="dev-block-md">
                                            <div className="ml-3 mr-3">
                                                <div className="row mb-1">
                                                    <div className="col-4 router-label">재배기 상태</div>
                                                    <div className="col-8 router-content">{pumpText}</div>
                                                </div>
                                                <div className="row mt-1 mb-1">
                                                    <div className="col-4 router-label">생육식물</div>
                                                    <div className="col-8 router-content">{plantName}</div>
                                                </div>
                                                <div className="row mt-1 mb-1">
                                                    <div className="col-4 router-label">사용 재배구 수</div>
                                                    <div className="col-8 router-content">
                                                        <Progress
                                                            percent={sfPortCnt / 32 * 100}
                                                            status="default"
                                                            theme={{
                                                                default: {
                                                                    symbol:
                                                                        <span>
                                                                            {sfPortCnt + '/32'}
                                                                        </span>,
                                                                    trailColor: '#cdf18d',
                                                                    color: '#96bd30'
                                                                }
                                                            }} />
                                                    </div>
                                                </div>
                                                <div className="row mt-1 mb-1">
                                                    <div className="col-4 router-label">재배 시작일</div>
                                                    <div className="col-8 router-content">
                                                        <Moment format="YYYY/MM/DD">
                                                            {startDate}
                                                        </Moment>
                                                    </div>
                                                </div>
                                                <div className="row mt-1 mb-1">
                                                    <div className="col-4 router-label">예상 완료일</div>
                                                    <div className="col-8 router-content">
                                                        <Moment format="YYYY/MM/DD">
                                                            {startDate + 2678400000}
                                                        </Moment>
                                                    </div>
                                                </div>
                                                <div className="row mt-1">
                                                    <div className="col-4 router-label">경과시간</div>
                                                    <div className="col-8 router-content">
                                                        <Moment fromNow>
                                                            {startDate}
                                                        </Moment>
                                                    </div>
                                                </div>
                                                <div className="row">
                                                    <div className="col-4 router-label"></div>
                                                    <div className="col-8 router-content">
                                                        <Progress
                                                            percent={3}
                                                            status="default"
                                                            theme={
                                                                {
                                                                    default: {
                                                                        symbol:
                                                                            <span>{3 + '%'}</span>,
                                                                        trailColor: '#cdf18d',
                                                                        color: '#96bd30'
                                                                    }
                                                                }
                                                            }
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    {/* 3번요소 */}
                                    <div className="col-lg-3 col-md-6" id="dev-info2">
                                        <div className="dev-block-md">
                                            <div className="ml-3 mr-3">
                                                <div className="row mb-1">
                                                    <div className="col-4 router-label">내부 아이피</div>
                                                    <div className="col-8 router-content">192.168.4.{sfCode}</div>
                                                </div>
                                                <div className="row mb-1">
                                                    <div className="col-4 router-label">등록 라우터</div>
                                                    <div className="col-8 router-content">pi3-ap#{apCode}</div>
                                                </div>
                                                <div className="row mb-1">
                                                    <div className="col-4 router-label">광량 공급방식</div>
                                                    <div className="col-8 router-content">인조광 의존형</div>
                                                </div>
                                                <div className="row mb-1">
                                                    <div className="col-4 router-label">양액 공급방식</div>
                                                    <div className="col-8 router-content">NFT</div>
                                                </div>
                                                <div className="row mb-1">
                                                    <div className="col-4 router-label">동작모드</div>
                                                    <div className="col-8 router-content">
                                                        {mode.state && '자동 모드'}
                                                        {!mode.state && '수동 모드'}
                                                    </div>
                                                </div>
                                                <div className="row mt-3">
                                                    <div className="col-4 router-label">
                                                    </div>
                                                    <div className="col-8 router-content">
                                                        <button className="sf-execute pt-1" onClick={() => sendFunc(mode.code)}
                                                            id={modIsDisabled} disabled={modBtnDisabled}
                                                            style={{
                                                                border: 0
                                                            }}>
                                                            <span >모드 전환</span>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="row mt-3">
                        <div className="col-lg-6 col-md-12" id="dev-info3">
                            <div className="dev-block-xxl">
                                <div className="dev-block-title">
                                    <MdWeb size={18} />
                                    <span className="ml-2">재배구 정보</span>
                                </div>
                                <div className="ml-3 mr-3 mt-1">
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
                                <div className="row">
                                    <div className="col-12 router-content" style={{ paddingRight: '30px' }}>
                                        <button className="led-ctrl-btn pt-1" onClick={() => { }}>
                                            <span >정보 수정</span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="col-lg-6 col-md-12">
                            <div className="row">
                                <div className="col-12" id="dev-info4">
                                    <div className="dev-block-xl">
                                        <div className="dev-block-title">
                                            <MdWeb size={18} />
                                            <span className="ml-2">광량 제어</span>
                                        </div>
                                        <div className="ml-3 mr-3 mt-2">
                                            <div className="row">
                                                <div className="col-3">
                                                    <LED obj={led21} sendFunc={sendFunc} index={17}
                                                        isDisabled={isDisabled}>
                                                        2F-A LED
                                                    </LED>
                                                </div>
                                                <div className="col-3">
                                                    <LED obj={led22} sendFunc={sendFunc} index={25}
                                                        isDisabled={isDisabled}>
                                                        2F-B LED
                                                    </LED>
                                                </div>
                                                <div className="col-3">
                                                    <LED obj={led31} sendFunc={sendFunc} index={1}
                                                        isDisabled={isDisabled}>
                                                        3F-A LED
                                                    </LED>
                                                </div>
                                                <div className="col-3">
                                                    <LED obj={led32} sendFunc={sendFunc} index={9}
                                                        isDisabled={isDisabled}>
                                                        3F-B LED
                                                    </LED>
                                                </div>
                                            </div>
                                            <div className="row mt-2"
                                                style={{
                                                    borderTop: '1px solid #e7e7e7',
                                                    borderBottom: '1px solid #e7e7e7',
                                                }}>
                                                <div className="col-6">
                                                    <div className="row">
                                                        <div className="col-3 router-label pt-1 pb-1">
                                                            2F광량
                                                    </div>
                                                        <div className="col-3 router-label pt-1 pb-1" style={{ borderLeft: '1px solid #e7e7e7' }}>
                                                            <span id={floor2_100} className="dev-percentage">100%</span>
                                                        </div>
                                                        <div className="col-3 router-label pt-1 pb-1">
                                                            <span id={floor2_50} className="dev-percentage">50%</span>
                                                        </div>
                                                        <div className="col-3 router-label pt-1 pb-1">
                                                            <span id={floor2_0} className="dev-percentage">0%</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="col-6" style={{ borderLeft: '1px solid #e7e7e7' }}>
                                                    <div className="row">
                                                        <div className="col-3 router-label pt-1 pb-1">
                                                            3F광량
                                                    </div>
                                                        <div className="col-3 router-label pt-1 pb-1" style={{ borderLeft: '1px solid #e7e7e7' }}>
                                                            <span id={floor3_100} className="dev-percentage">100%</span>
                                                        </div>
                                                        <div className="col-3 router-label pt-1 pb-1">
                                                            <span id={floor3_50} className="dev-percentage">50%</span>
                                                        </div>
                                                        <div className="col-3 router-label pt-1 pb-1">
                                                            <span id={floor3_0} className="dev-percentage">0%</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="row mt-3">
                                                <div className="col-12 router-content">
                                                    <button className="led-ctrl-btn pt-1" onClick={() => sendFunc(45)}
                                                        disabled={isDisabled} id={btnDisabled}>
                                                        <span >2F ON</span>
                                                    </button>
                                                    <button className="led-ctrl-btn pt-1" onClick={() => sendFunc(55)}
                                                        disabled={isDisabled} id={btnDisabled}>
                                                        <span >2F OFF</span>
                                                    </button>
                                                    <button className="led-ctrl-btn pt-1" onClick={() => sendFunc(46)}
                                                        disabled={isDisabled} id={btnDisabled}>
                                                        <span >3F ON</span>
                                                    </button>
                                                    <button className="led-ctrl-btn pt-1" onClick={() => sendFunc(56)}
                                                        disabled={isDisabled} id={btnDisabled}>
                                                        <span >3F OFF</span>
                                                    </button>
                                                    <button className="led-ctrl-btn pt-1" onClick={() => sendFunc(4)}
                                                        disabled={isDisabled} id={btnDisabled}>
                                                        <span >전체 ON</span>
                                                    </button>
                                                    <button className="led-ctrl-btn pt-1" onClick={() => sendFunc(5)}
                                                        disabled={isDisabled} id={btnDisabled}>
                                                        <span >전체 OFF</span>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div className="row mt-3">
                                <div className="col-12" id="dev-info4">
                                    <div className="dev-block-xl">
                                        <div className="dev-block-title">
                                            <MdWeb size={18} />
                                            <span className="ml-2">배양액 온도 제어</span>
                                        </div>
                                        <div className="ml-3 mr-3 mt-2">
                                            <div className="row">
                                                <div className="col-3">
                                                    <Cooler obj={cooler1} sendFunc={sendFunc} index={17}
                                                        isDisabled={isDisabled} text="left">
                                                        A cooler
                                                    </Cooler>
                                                </div>
                                                <div className="col-3">
                                                    <Cooler obj={cooler2} sendFunc={sendFunc} index={25}
                                                        isDisabled={isDisabled} text="center">
                                                        B cooler
                                                    </Cooler>
                                                </div>
                                                <div className="col-3">
                                                    <Cooler obj={cooler3} sendFunc={sendFunc} index={1}
                                                        isDisabled={isDisabled} text="right">
                                                        C cooler
                                                    </Cooler>
                                                </div>
                                                <div className="col-3" style={{
                                                    paddingLeft: 0
                                                }}>
                                                    <div className="row">
                                                        <div className="col-12 router-label"
                                                            style={{ fontSize: '12px' }}>
                                                            <span><strong>부가 정보</strong></span>
                                                        </div>
                                                    </div>
                                                    <div className="row mt-1">
                                                        <div className="col-6 router-label">동작 온도</div>
                                                        <div className="col-6 router-content">22°C</div>
                                                    </div>
                                                    <div className="row mt-1">
                                                        <div className="col-6 router-label">최적 온도</div>
                                                        <div className="col-6 router-content">20°C</div>
                                                    </div>
                                                    <div className="row mt-1">
                                                        <div className="col-6 router-label">현재 온도</div>
                                                        <div className="col-6 router-content">20°C</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="row mt-2"
                                                style={{
                                                    borderTop: '1px solid #e7e7e7',
                                                    borderBottom: '1px solid #e7e7e7',
                                                }}>
                                                <div className="col-9">
                                                    <div className="row">
                                                        <div className="col-2">
                                                            <div className="row">
                                                                <div className="col-12 router-label pt-1 pb-1">
                                                                    속도
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div className="col-10">
                                                            <div className="row">
                                                                <div className="col-3 router-label pt-1 pb-1" style={{ borderLeft: '1px solid #e7e7e7' }}>
                                                                    <span id={cooler_100} className="dev-percentage">100%</span>
                                                                </div>
                                                                <div className="col-3 router-label pt-1 pb-1">
                                                                    <span id={cooler_66} className="dev-percentage">66%</span>
                                                                </div>
                                                                <div className="col-3 router-label pt-1 pb-1">
                                                                    <span id={cooler_33} className="dev-percentage">33%</span>
                                                                </div>
                                                                <div className="col-3 router-label pt-1 pb-1" style={{ borderRight: '1px solid #e7e7e7' }}>
                                                                    <span id={cooler_0} className="dev-percentage">0%</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="col-3" style={{
                                                    paddingLeft: '5px'
                                                }}>
                                                    <div className="row">
                                                        <div className="col-6 router-label pt-1 pb-1">
                                                            <TextCenter>
                                                                <span id={auto} className="dev-percentage">자동</span>
                                                            </TextCenter>
                                                        </div>
                                                        <div className="col-6 router-label pt-1 pb-1">
                                                            <TextCenter>
                                                                <span id={manual} className="dev-percentage">수동</span>
                                                            </TextCenter>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="row mt-3">
                                                <div className="col-12 router-content">
                                                    <button className="led-ctrl-btn pt-1" onClick={() => sendFunc(8)}
                                                        disabled={isDisabled} id={btnDisabled}>
                                                        <span >전체 ON</span>
                                                    </button>
                                                    <button className="led-ctrl-btn pt-1" onClick={() => sendFunc(9)}
                                                        disabled={isDisabled} id={btnDisabled}>
                                                        <span >전체 OFF</span>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="row mt-3" style={{marginBottom:'120px'}}>
                        <div className="col-lg-6 col-md-12">
                        </div>
                        <div className="col-lg-6 col-md-12" id="dev-info4">
                            <div className="dev-block-xl" style={{height:'230px'}}>
                                <div className="dev-block-title">
                                    <MdWeb size={18} />
                                    <span className="ml-2">양액 공급기 제어</span>
                                </div>

                                <div className="ml-3 mr-3 mt-2">
                                    <div className="row">
                                        <div className="col-3">
                                            <Pump obj={pumpA} sendFunc={() => { }} index={17}
                                                isDisabled={isDisabled} text="left">
                                                양액 A
                                            </Pump>
                                        </div>
                                        <div className="col-3">
                                            <Pump obj={pumpB} sendFunc={() => { }} index={25}
                                                isDisabled={isDisabled} text="right">
                                                양액 B
                                            </Pump>
                                        </div>
                                        <div className="col-6">
                                            <div className="row">
                                                {/* <div className="col-12">
                                                    <div className="row">
                                                        <div className="col-12 router-label"
                                                            style={{ fontSize: '12px' }}>
                                                            <span><strong>부가 정보</strong></span>
                                                        </div>
                                                    </div>
                                                </div> */}
                                            </div>
                                            <div className="row">
                                                <div className="col-12" style={{
                                                    paddingLeft: 0, paddingRight: '30px'
                                                }}>
                                                    <table className="dev-info-table" summary="최근 데이터 기록">
                                                        <thead>
                                                            <tr>
                                                                <th></th>
                                                                <th>양액 A액</th>
                                                                <th>양액 B액</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr>
                                                                <td>공급량</td>
                                                                <td>40ml</td>
                                                                <td>40ml</td>
                                                            </tr>
                                                            <tr>
                                                                <td>질소</td>
                                                                <td>1.3%</td>
                                                                <td>2%</td>
                                                            </tr>
                                                            <tr>
                                                                <td>칼리</td>
                                                                <td>5%</td>
                                                                <td>3.5%</td>
                                                            </tr>
                                                            <tr>
                                                                <td>석회</td>
                                                                <td></td>
                                                                <td>2%</td>
                                                            </tr>
                                                            <tr>
                                                                <td>고토</td>
                                                                <td>0.7%</td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <td>붕소</td>
                                                                <td>0.05%</td>
                                                                <td></td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                    <div className="row mt-2" style={{
                                        borderTop: '1px solid #e7e7e7'
                                    }}>
                                        <div className="col-12 router-content mt-3">
                                            <button className="led-ctrl-btn pt-1" onClick={() => { }}
                                                disabled={isDisabled} id={btnDisabled}>
                                                <span >공급량 변경</span>
                                            </button>
                                            <button className="led-ctrl-btn pt-1" onClick={() => { }}
                                                disabled={isDisabled} id={btnDisabled}>
                                                <span >전체 공급</span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                {/* <div className="container-fluid">
                    <div className="row mb-5" id="nomargin">

                        <div className="col-lg-5 col-md-4 col-sm-6 col-xs-12 portfolio-item mb-2" id="nopadding">
                        <div className="Device-detail">
                            <div className="pl-2p pr-2p">
                                <div className="container-fluid">
                                    <div className="row">
                                        <div className="col-12 text-muted smaller size-9 dev-title" id="nopadding">
                                            <div className="mt-1 ml-3 mb-2">
                                                <MdWeb size={19} className="mr-1" />
                                                <span>
                                                    재배기 정보
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-12 DeviceInfo-box">
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
                    </div>
                    </div>
                </div> */}
            </Fragment>
        )
    }
}


export default DeviceInfo;