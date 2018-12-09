import React, { Component, Fragment } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as mainActions from '../../redux/modules/main';
import * as sensorActions from '../../redux/modules/sensor';
import * as plantActions from '../../redux/modules/plant';
import * as logActions from '../../redux/modules/log';
import DatePicker from 'react-datepicker';
import moment from 'moment';
import 'react-datepicker/dist/react-datepicker.css';
import Moment from 'react-moment';
import {
    SideBar,
    PageContent,
    Header,
    MainWrapper,
    PageBody,
    RouterItem,
    RouterAddButton,
    MainToolBar,
    RouterAddModal,
    FormError,
    SfItemContainer,
    SfItem,
    HeaderBlank,
    SfDeviceToolbar,
    TextCenter,
    DateToggle,
    BlankWrapper,
    StartFarmingModal,
    Chart,
    DeviceInfo,
    Copyright,
} from '../../components';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import ip from 'ip';
import BlockUi from 'react-block-ui';
import { Loader } from 'react-loaders';
import 'react-block-ui/style.css';
import 'loaders.css/loaders.min.css';
import './sfDeviceContainer.css';
import { DOMAIN } from '../../lib/spring_api/urlSetting';

var stompClient = null;
var socket = null;
var blinkTimer = null;
var updatePageTimer = null;

class SfDeviceContainer extends Component {
    toastId = null;
    goBackAction = true;

    constructor(props) {
        super(props);
        this.ec = React.createRef();
    }

    _goBack = () => {
        if (this.goBackAction) {
            this.goBackAction = false;
            this._disconnect();
            this.props.history.push('/main');
        }
    }

    _connect(draw) {
        const { selectedSf, selectedAp, dataset } = this.props;
        var apCode = selectedAp.apCode;
        var stamp = selectedSf.toJS().stamp;

        socket = new SockJS(
            DOMAIN + 'sensing_data'
        );

        socket.onclose = () => {
            stompClient.disconnect();
            console.log('disconnected')
        };

        stompClient = Stomp.over(socket);
        stompClient.connect('manager', 'manager', function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/messages' + apCode + stamp, draw);
        });
    }

    _drawChartsPoint = (message) => {
        const { tabSelected } = this.props;
        // add the point
        let msg = JSON.parse(message.body);

        //데이터 로거 선택 되었을 시에 수행
        if (tabSelected === 1) {
            let ec_ph = this.ec.getChart(); //float
            let wt = this.wt.getChart(); //float
            let th = this.th.getChart(); //float, float

            console.log(msg['d']);
            var date = msg['d'] * 1000;

            console.log(date);
            //console.log(parseDate(date));

            //ec
            var ec_d = parseFloat(msg['ec']);
            var ph_d = parseFloat(msg['ph']);
            var wl_d = parseInt(msg['wl']);
            var e_d = parseInt(msg['e']);
            var wt_d = parseFloat(msg['wt']);
            ec_ph.series[0].addPoint([date, ec_d], true, false);

            //ph
            ec_ph.series[1].addPoint([date, ph_d], true, false);

            //water temp
            wt.series[0].addPoint([date, wt_d], true, false);

            //temp, humi
            th.series[0].addPoint([date, parseFloat(msg['h'])], true, false);
            th.series[1].addPoint([date, parseFloat(msg['t'])], true, false);

            //change_chart add_on text
            this.ref_ec.innerHTML = ec_d + ' mS/cm';
            this.ref_ph.innerHTML = ph_d + ' ph';
            this.ref_wl.innerHTML = wl_d + '%';
            this.ref_e.innerHTML = e_d + 'cds';
            this.ref_wt.innerHTML = wt_d + '°C';
            this.dash_wl_ref.style.strokeDasharray = wl_d + ',100';
            this.dash_e_ref.style.strokeDasharray = e_d + ',1023';
            this._blink(this.ref_ec, this.ref_ph, this.ref_wt);
        }
        else {

        }
    }

    //텍스트 깜박이기
    _blink = (ecTxt, phTxt, csTxt) => {
        ecTxt.style.color = '#46ff46';
        phTxt.style.color = '#46ff46';
        csTxt.style.color = '#46ff46';
        blinkTimer = setTimeout(() => {
            ecTxt.style.color = '#212529';
            phTxt.style.color = '#212529';
            csTxt.style.color = '#212529';
        }, 1500);
    }

    _disconnect() {
        if (stompClient)
            stompClient.disconnect();
    }

    _changeModalsOpen = () => {
        const { SensorActions, modalsState } = this.props;
        SensorActions.changeModalsState(!modalsState);
    }

    _getDataset = async () => {
        const { SensorActions } = this.props;
        await SensorActions.getDataset();
    }

    _sfItemContainerControlClose = () => {
        const { MainActions } = this.props;
        MainActions.changeSfToggleState({
            sfToggleState: false,
            selectedAp: null,
        });
    }

    _changeDatePicker = (e) => {
        const { SensorActions, datePickerToggle } = this.props;
        e && e.preventDefault()
        SensorActions.changeDatePickerToggle(!datePickerToggle);
    }

    _headerUserButtonControl = () => {
        const { MainActions, popoverState } = this.props;
        MainActions.changePopoverState(!popoverState);
    }

    _handleLog = (date) => {
        const { SensorActions, LogActions, selectedSf, selectedAp } = this.props;
        const { stamp } = selectedSf.toJS();
        const { apCode } = selectedAp;
        if (date.format('YYYY-MM-DD') === moment().format('YYYY-MM-DD')) {
            console.log('conn')
            this._connect(this._drawChartsPoint);
        }
        else {
            this._disconnect();
        }
        SensorActions.changeStartDate(date);
        SensorActions.getDataset(apCode, stamp, date.format('YYYY-MM-DD'));
        LogActions.getDataset({
            apCode: apCode,
            stamp: stamp,
            date: date.format('YYYY-MM-DD')
        })
    }

    //수경재배 펌프 작동 설정
    _changeFarming = async (code) => {
        const {
            PlantActions,
            MainActions,
            selectedSf,
            selectedAp,
            selectedPlant,
            farmingCount,
            farming,
        } = this.props;
        const { innerIp, stamp } = selectedSf.toJS();
        const { apIp, apCode } = selectedAp;
        this._blocking(null);
        var prev = new Date();
        this._addLog(code, 'P');
        try {
            if (code === 10) {// 재배시작인 경우
                if (farmingCount === 0) {
                    //재배시작 코드이고, 포트에 작물이 하나도 없는경우 명령전송 불가
                    this._showAlert({
                        msg: '포트가 빈 상태로 재배 불가합니다.',
                        status: 'INTERNAL_SERVER_ERROR'
                    });
                    return;
                }
                this._changeModalsOpen();
            }
            await PlantActions.changeFarming({
                apCode: apCode,
                cmd: code,
                dest: innerIp,
                middle: apIp,
                stamp: stamp,
                usedIp: ip.address(),
                optData: {
                    sfPort: farmingCount,
                    plant: selectedPlant,
                    farm: farming,
                }
            }).then(
                (res) => {
                    console.log(res.data.data.deviceInfo);
                    MainActions.changeSelectedSf(res.data.data.deviceInfo);
                    this._getPortInfo();
                    this._changeFirstLog(res.data.status === 'OK' ? 'T' : 'F')
                    this._blocking(prev, res.data);
                    return res;
                }
            );
        } catch (e) {
            this._changeFirstLog('F')
            this._blocking(prev, {
                msg: '명령전송에 실패 하였습니다.',
                status: 'INTERNAL_SERVER_ERROR'
            });
            return;
        }
        if (code === 10) {// 재배시작인 경우
            this._connect(this._drawChartsPoint);
        }
        else {
            // this._clearLogSet();
            this._disconnect();
        }
    }

    // _clearLogSet = () => {
    //     const { LogActions } = this.props;
    //     LogActions.clearLog();
    // }

    //제어 명령 전송
    _sendCommand = async (code) => {
        const {
            LogActions,
            MainActions,
            selectedSf,
            selectedAp,
            farmingCount,
        } = this.props;
        const { innerIp, stamp } = selectedSf.toJS();
        const { apIp, apCode } = selectedAp;
        this._blocking(null);
        var prev = new Date();
        this._addLog(code, 'P');
        try {
            await LogActions.sendCommand({
                apCode: apCode,
                cmd: code,
                dest: innerIp,
                middle: apIp,
                stamp: stamp,
                usedIp: ip.address(),
                optData: null,
            }).then(
                (res) => {
                    MainActions.changeSelectedSf(res.data.data.deviceInfo);
                    this._blocking(prev, res.data);
                    return res;
                }
            );
        } catch (e) {
            this._blocking(prev, {
                msg: '명령전송에 실패 하였습니다.',
                status: 'INTERNAL_SERVER_ERROR'
            });
        }
    }

    //화면 블락
    _blocking = (date, data) => {
        const { SensorActions, blocking } = this.props;
        if (blocking)
            setTimeout(
                function () {
                    SensorActions.block(!blocking);
                    this._showAlert(data)
                }
                    .bind(this),
                2000
            );
        else
            SensorActions.block(!blocking);
    }

    //alert출력
    _showAlert = (data) => {
        let type = toast.TYPE.SUCCESS;
        if (data.msg) {
            if (data.status !== 'OK') {
                type = toast.TYPE.ERROR;
            }
            this.toastId = toast(
                data.msg, {
                    autoClose: 4000,
                    type: type,
                });
        }
    }

    //수경재배 정보(포트,포트 갯수) 설정, StartFarmingModal에서 사용
    _setFarming = (e) => {
        const { SensorActions } = this.props;
        SensorActions.changeInput({
            value: {
                row: e.target.value,
                col: e.target.name,
            },
            check: e.target.checked,
        });
        this._changeFarmingCount(e.target.checked, 1);
    }

    //수경재배 포트 갯수 설정, StartFarmingModal에서 사용
    _changeFarmingCount = (check, count) => {
        const { SensorActions, farmingCount } = this.props;
        let _farmingCount
        if (check) _farmingCount = farmingCount + count;
        else _farmingCount = farmingCount - count;
        SensorActions.changeFarmingCount({
            farmingCount: _farmingCount,
            isConfirm: _farmingCount !== 0
        });
    }

    //32개포트 전부 리셋(모두 식물 채움), StartFarmingModal에서 사용
    _setFarmingReset = () => {
        const { SensorActions, farming, farmingCount } = this.props;
        SensorActions.resetFarm(
            farming.map(
                (list) => list.map(
                    (item) => item.set('status', true)
                )
            )
        )
        this._changeFarmingCount(true, 32 - farmingCount);
    }

    //수경재배 파이프만 역으로 리셋, StartFarmingModal에서 사용
    _setFarmingResetPipe = (pipeCount, isFloor) => {
        const { SensorActions, farming } = this.props;
        let cnt = 0;
        SensorActions.resetPipe({
            pipe: farming.get(pipeCount).map(
                (item) => {
                    if (!item.get('status')) cnt++
                    return item.set('status', !item.get('status'))
                }
            ),
            value: pipeCount
        })
        if (!isFloor) {
            this._changeFarmingCount(true, cnt - (8 - cnt));
        }
        return cnt;
    }

    //수경재배 층만 역으로 리셋, StartFarmingModal에서 사용
    _setFarmingResetFloor = (up, dn) => {
        let cnt = 0;
        cnt += this._setFarmingResetPipe(up, true);
        cnt += this._setFarmingResetPipe(dn, true);
        this._changeFarmingCount(true, cnt - (16 - cnt));
    }

    //선택된 생육 식물 변경, StartFarmingModal에서 사용
    _plantChange = (e) => {
        const { SensorActions } = this.props;
        SensorActions.changePlant(e.target.value);
    }

    //생육 식물의 정보를 받아옴
    _getPlantInfo = async () => {
        const {
            PlantActions,
            selectedSf,
            selectedAp,
            selectedPlant,
            startDate
        } = this.props;
        const { stamp } = selectedSf.toJS();
        const { apCode } = selectedAp;
        await PlantActions.getDataset(apCode, stamp, selectedPlant)
            .then(
                res => {
                    //재배기가 현재 재배중이고 오늘 날짜라면 stomp connect
                    if (this.props.plantResult.toJS().farmingDate) {
                        if (moment().diff(startDate, 'days') === 0) {
                            this._connect(this._drawChartsPoint);
                        }
                        else {//오늘 날짜가 아니라면 stomp 연결중지
                            this._disconnect();
                        }
                    }
                    return res;
                }
            )
    }

    //현재 선택된 날짜의 센싱 데이터 전부 초기화
    _resetData = () => {
        const { SensorActions } = this.props;
        SensorActions.resetData();
    }

    //특정 날짜의 센싱 데이터 조회
    _getData = async (apCode, stamp, startDate) => {
        const { SensorActions } = this.props;
        await SensorActions.getDataset(
            apCode,
            stamp,
            startDate.format('YYYY-MM-DD'));
    }

    //최근 센싱 데이터 조회
    _getLastData = async (apCode, stamp, date) => {
        const { SensorActions } = this.props;
        await SensorActions.getLastDataset(
            apCode,
            stamp,
            date
        )
    }

    //사용자가 데이터 로거 페이지에 계속 머물 경우
    //데이터 로거 페이지 업데이트(자정 23:59분 이후에 발생하는 이벤트)
    _updatePage = () => {
        const {
            startDate,
            SensorActions
        } = this.props;

        //setTimeout은 memory leak방지를 위해 반드시 해제해줘야
        updatePageTimer = setTimeout(() => {
            SensorActions.resetData();
            SensorActions.changeStartDate(moment());
        }, startDate.endOf('day').valueOf() + 1000);
    }

    //수경 재배기의 포트 정보를 받아옴
    _getPortInfo = async () => {
        const { SensorActions, selectedSf, selectedAp, } = this.props;
        const { stamp } = selectedSf.toJS();
        const { apCode } = selectedAp;
        SensorActions.getPortInfo({
            apCode: apCode,
            stamp: stamp,
        })
    }

    //현재 수경재배기의 디바이스 로그 정보를 받아옴
    _getDeviceLog = async (date) => {
        const { LogActions, selectedSf, selectedAp } = this.props;
        const { sfCode } = selectedSf.toJS();
        const { apCode } = selectedAp;
        return LogActions.getDataset({
            apCode: apCode,
            sfCode: sfCode,
            date: date.format('YYYY-MM-DD')
        })
    }

    //형상정보 또는 데이터 로거 버튼 선택에 따른 페이지 변화
    _tabChange = (value) => {
        const { SensorActions } = this.props;
        SensorActions.changeTab(value);
    }

    //사이드바에서 선택되어진 요소 변경
    _sbChange = (value) => {
        const { MainActions } = this.props;
        MainActions.changeSelectedSb(value);
    }

    //리덕스 스토어에 로그 정보 추가
    _addLog = (code, state) => {
        const { LogActions } = this.props;
        LogActions.addLog({
            usedDate: moment(),
            actName: this._getCodeName(code),
            usedIp: ip.address(),
            result: state, //'P'라면 react-table의 수행결과 컬럼을 pending으로 설정
        });
    }

    //react-table의 제일 첫 번째 로그의 수행결과 수정
    _changeFirstLog = (state) => {
        const { LogActions } = this.props;
        LogActions.changeFirstLog(state);
    }

    //코드에 대응 되는 동작명 조회
    _getCodeName = (code) => {
        let name;
        switch (code) {
            case 2:
                name = '자동모드';
                break;
            case 3:
                name = '수동모드';
                break;
            case 4:
                name = 'LED켜기';
                break;
            case 5:
                name = 'LED끄기';
                break;
            case 8:
                name = '쿨러켜기';
                break;
            case 9:
                name = '쿨러끄기';
                break;
            case 10:
                name = '재배시작';
                break;
            case 11:
                name = '재배중지';
                break;
        }
        return name;
    }

    componentDidMount() {
        //this._sfItemContainerControlClose();
        const { farmingResult, startDate, history } = this.props;

        //if (farmingResult === null)
        //    this._getPortInfo();

        //저장된 토큰 값이 존재한다면
        if (localStorage.getItem("jwtToken")) {
            this._resetData(); //데이터 말소
            this._sbChange(1);
            this._tabChange(0);//선택된 탭 초기화
            this._getPortInfo();
            this._getPlantInfo();
            this._updatePage();
            console.log(farmingResult)
        }
        else {
            history.push('/');
        }
    }

    componentWillUnmount() {
        // 컴포넌트가 사라질 때 인스턴스 제거
        if (this.ec) {
            this.ec = null;
            this.wt = null;
            this.th = null;
            this.ref_ec = null;
            this.ref_ph = null;
            this.ref_wl = null;
            this.ref_e = null;
            this.ref_wt = null;
            this.dash_wl_ref = null;
            this.dash_e_ref = null;
        }
        //memory leak방지
        if (blinkTimer)
            clearTimeout(blinkTimer);
        if (updatePageTimer)
            clearTimeout(updatePageTimer);
    }

    //출력
    render() {
        const {
            sfToggleState,
            popoverState,
            dropdownState,
            result,
            deviceInfo,
            selectedAp,
            startDate,
            selectedSf,
            user,
            datePickerToggle,
            blocking,
            modalsState,
            farming,
            farmingCount,
            isConfirm,
            plantResult,
            dataset,
            tabSelected,
            sidebarSelected,
            farmingResult,
            logset,
            lastDataset
        } = this.props;
        console.log(plantResult);
        const { apCode } = selectedAp;
        const { sfCode, pumpSt, stamp } = selectedSf.toJS();
        let dt = moment();
        if (plantResult && plantResult.toJS().farmingDate) {
            dt = moment(new Date(plantResult.toJS().farmingDate))
        }
        return (
            <Fragment>
                <BlankWrapper blocking={blocking}>
                    <BlockUi tag="div" blocking={blocking} style={{ height: "100%", width: "100%", zIndex: 2000 }}
                        loader={<Loader active type='square-spin' color="#2ca8ff"
                            style={{ display: 'inline-block' }} />}
                    >
                    </BlockUi>
                </BlankWrapper>
                <MainWrapper>
                    < SideBar changeFunc={this._sbChange} selected={sidebarSelected} />
                    <HeaderBlank />
                    <Header
                        onClick={[this._headerUserButtonControl]}
                        popover={popoverState}
                        user={user}
                    // title="디바이스 정보"
                    />
                    <PageContent>
                        <SfDeviceToolbar backFunc={this._goBack}
                            sfCode={sfCode}
                            sendFunc={this._changeFarming}
                            startFunc={this._changeModalsOpen}
                            farmingState={plantResult && plantResult.toJS().farmingDate}
                            tabSelected={tabSelected}
                            tabChangeFunc={this._tabChange}
                        >

                            <DatePicker
                                onChange={this._handleLog}
                                selected={startDate}
                                customInput={<DateToggle startDate={startDate}
                                />}
                                dateFormat="YYYY-MM-DD"
                                minDate={dt}
                                maxDate={moment()}
                                withPortal
                                showWeekNumbers
                                disabled={plantResult && !plantResult.toJS().farmingDate}
                            >
                                <div style={{ color: 'green' }} id="nanum-gothic" className="mb-3 mt-0">
                                    <TextCenter>
                                        작물재배 데이터를 조회 할 날짜를 선택하세요.
                                        </TextCenter>
                                </div>
                            </DatePicker>
                        </SfDeviceToolbar>
                        {/* <div className="wrap"> */}
                        <PageBody disabled={true}>
                                <ToastContainer />
                                <StartFarmingModal
                                    isOpen={modalsState}
                                    modalFunc={this._changeModalsOpen}
                                    sendFunc={this._changeFarming}
                                    sfName={'sf-device#' + sfCode}
                                    onChange={this._setFarming}
                                    farm={farming}
                                    farmRes={farmingResult}
                                    resetFunc={this._setFarmingReset}
                                    resetPipeFunc={this._setFarmingResetPipe}
                                    resetFloorFunc={this._setFarmingResetFloor}
                                    farmingCount={farmingCount}
                                    isConfirm={isConfirm}
                                    plantChange={this._plantChange}
                                />
                                {/* 형상 정보 */}
                                {
                                    tabSelected === 0 &&
                                    <DeviceInfo sfCode={sfCode} 
                                        farm={farmingResult}
                                        sendFunc={this._sendCommand}
                                        logset={logset}
                                        selectedSf={selectedSf}
                                        startDate={dt}
                                        pumpSt={pumpSt}
                                        getDeviceLog={() => this._getDeviceLog(startDate)}
                                        plantResultDisabled={plantResult && !plantResult.toJS().farmingDate}
                                        startDate={startDate}
                                        info={plantResult && plantResult.toJS()}
                                        lastDataset={lastDataset}
                                        last={()=>this._getLastData(apCode,stamp,new Date())}
                                    />
                                }
                                {/* 데이터 로거 */}
                                {
                                    tabSelected === 1 && plantResult && plantResult.toJS().farmingDate &&
                                    <Chart dataset={dataset.toJS()}
                                        info={plantResult.toJS()}
                                        ec={ref => this.ec = ref}
                                        ph={ref => this.ph = ref}
                                        wt={ref => this.wt = ref}
                                        th={ref => this.th = ref}
                                        ref_ec={ref => this.ref_ec = ref}
                                        ref_ph={ref => this.ref_ph = ref}
                                        ref_wl={ref => this.ref_wl = ref}
                                        ref_wt={ref => this.ref_wt = ref}
                                        ref_e={ref => this.ref_e = ref}
                                        dash_wl_ref={ref => this.dash_wl_ref = ref}
                                        dash_e_ref={ref => this.dash_e_ref = ref}
                                        // start={startDate.startOf('day').valueOf()}
                                        // end={startDate.endOf('day').valueOf()}
                                        start={startDate.startOf('day').valueOf()}
                                        end={startDate.endOf('day').valueOf()}
                                        getData={() => this._getData(apCode, stamp, startDate)}
                                    />
                                }
                        </PageBody>
                        {/* </div> */}
                        <Copyright />
                    </PageContent>
                </MainWrapper >
            </Fragment >
        );
    }
}

export default withRouter(
    connect(
        state => ({
            dataset: state.sensor.get('dataset'),
            lastDataset: state.sensor.get('lastDataset'),
            startDate: state.sensor.get('startDate'),
            result: state.sensor.get('result'),
            datePickerToggle: state.sensor.get('datePickerToggle'),
            blocking: state.sensor.get('blocking'),
            modalsState: state.sensor.get('modalsState'),
            farming: state.sensor.get('farming'),
            farmingCount: state.sensor.getIn(['farmingInfo', 'farmingCount']),
            isConfirm: state.sensor.getIn(['farmingInfo', 'isConfirm']),
            selectedPlant: state.sensor.get('selectedPlant'),
            tabSelected: state.sensor.get('tabSelected'),
            farmingResult: state.sensor.get('farmingResult'),
            user: state.main.get('user'),
            selectedSf: state.main.get('selectedSf'),
            selectedAp: state.main.getIn(['sfToggle', 'selectedAp']),
            popoverState: state.main.get('popoverState'),
            sidebarSelected: state.main.get('sidebarSelected'),
            plantResult: state.plant.get('result'),
            logset: state.log.get('logset'),
        }),
        dispatch => ({
            MainActions: bindActionCreators(mainActions, dispatch),
            SensorActions: bindActionCreators(sensorActions, dispatch),
            PlantActions: bindActionCreators(plantActions, dispatch),
            LogActions: bindActionCreators(logActions, dispatch),
        })
    )(SfDeviceContainer)
);
