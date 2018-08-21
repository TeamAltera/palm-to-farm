import React, { Component, Fragment } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as mainActions from '../../redux/modules/main';
import * as sensorActions from '../../redux/modules/sensor';
import * as plantActions from '../../redux/modules/plant';
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
import ReactHighcharts from 'react-highcharts';

var stompClient = null;
var socket = null;

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
        var sfCode = selectedSf.toJS().sfCode;

        socket = new SockJS(
            'http://203.250.32.173:9001/smart_plant/sensing_data'
        );

        socket.onclose = () => {
            stompClient.disconnect();
            console.log('disconnected')
        };

        stompClient = Stomp.over(socket);
        stompClient.connect('manager', 'manager', function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/messages' + apCode + sfCode, draw);
        });
    }

    _drawChartsPoint = (message) => {
        // add the point
        let msg = JSON.parse(message.body);

        let ec = this.ec.getChart(); //float
        let ph = this.ph.getChart(); //float
        let wt = this.wt.getChart(); //float
        let wl = this.wl.getChart(); //int
        let th = this.th.getChart(); //float, float

        var date=msg['d']*1000;

        console.log(date);
        //console.log(parseDate(date));

        //ec
        ec.series[0].addPoint([date, parseFloat(msg['ec'])], true, false);

        //ph
        ph.series[0].addPoint([date, parseFloat(msg['ph'])], true, false);

        //water temp
        wt.series[0].addPoint([date, parseFloat(msg['wt'])], true, false);

        //water limit
        wl.series[0].addPoint([date, parseInt(msg['wl'])], true, false);

        //temp, humi
        th.series[0].addPoint([date, parseFloat(msg['h'])], true, false);
        //th.series[1].addPoint([date, parseFloat(msg['t'])], true, false);
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
        console.log(date);
        const { SensorActions } = this.props;
        SensorActions.changeStartDate(date);
    }

    _changeFarming = async (code) => {
        const {
            PlantActions,
            selectedSf,
            selectedAp,
            selectedPlant,
            farmingCount,
        } = this.props;
        const { sfIp, sfCode } = selectedSf.toJS();
        const { apIp, apCode } = selectedAp;
        this._blocking(null);
        var prev = new Date();
        try {
            await PlantActions.changeFarming({
                apCode: apCode,
                cmd: code,
                dest: sfIp,
                middle: apIp,
                sfCode: sfCode,
                usedIp: ip.address(),
                optData: {
                    sfPort: farmingCount,
                    plant: selectedPlant,
                }
            }).then(
                (res) => {
                    this._blocking(prev, res.data);
                    return res;
                }
            );
        } catch (e) {
            this._blocking(prev, {
                msg: '명령전송에 실패 하였습니다.',
                status: 'INTERNAL_SERVER_ERROR'
            });
            return;
        }
        if (code === 10) {// 재배시작인 경우
            this._changeModalsOpen();
            this._connect(this._drawChartsPoint);
        }
        else {
            this._disconnect();
        }
    }

    _sendCommand = async (code) => {
        const {
            SensorActions,
            selectedSf,
            selectedAp,
            farmingCount,
        } = this.props;
        const { sfIp, sfCode } = selectedSf.toJS();
        const { apIp, apCode } = selectedAp;
        this._blocking(null);
        var prev = new Date();
        if (farmingCount === 0) {
            //재배시작 코드이고, 포트에 작물이 하나도 없는경우 명령전송 불가
            return;
        }
        try {
            await SensorActions.sendCommand({
                apCode: apCode,
                cmd: code,
                dest: sfIp,
                middle: apIp,
                sfCode: sfCode,
                usedIp: ip.address(),
                optData: null,
            }).then(
                (res) => {
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

    _blocking = (date, data) => {
        const { SensorActions, blocking } = this.props;
        if (blocking && new Date() - date < 1000)
            setTimeout(
                function () {
                    SensorActions.block(!blocking);
                    this._showAlert(data)
                }
                    .bind(this),
                1000
            );
        else
            SensorActions.block(!blocking);
    }

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

    _setFarmingResetFloor = (up, dn) => {
        let cnt = 0;
        cnt += this._setFarmingResetPipe(up, true);
        cnt += this._setFarmingResetPipe(dn, true);
        this._changeFarmingCount(true, cnt - (16 - cnt));
    }

    _plantChange = (e) => {
        const { SensorActions } = this.props;
        SensorActions.changePlant(e.target.value);
    }

    _getPlantInfo = async () => {
        const {
            PlantActions,
            selectedSf,
            selectedAp,
            selectedPlant,
            startDate
        } = this.props;
        const { sfCode } = selectedSf.toJS();
        const { apCode } = selectedAp;
        console.log({
            apCode: apCode,
            sfCode: sfCode,
            plantCode: selectedPlant,
        })
        await PlantActions.getDataset(apCode, sfCode, selectedPlant)
            .then(
                res => {
                    //재배기가 현재 재배중이고 오늘 날짜라면 stomp connect
                    if (this.props.plantResult.toJS().farmingDate) {
                        this._getData(apCode, sfCode, startDate);
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

    _resetData = () => {
        const { SensorActions } = this.props;
        SensorActions.resetData();
    }

    _getData = async (apCode, sfCode, startDate) => {
        const { SensorActions } = this.props;
        await SensorActions.getDataset(
            apCode,
            sfCode,
            startDate.format('YYYY-MM-DD'));
    }

    componentDidMount() {
        //this._sfItemContainerControlClose();
        this._resetData(); //데이터 말소
        this._getPlantInfo();
    }

    componentWillUnmount() {
        // 컴포넌트가 사라질 때 인스턴스 제거
        if (this.ec) {
            this.ec = null;
            this.ph = null;
            this.wt = null;
            this.wl = null;
            this.th = null;
        }
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
        } = this.props;
        const { sfCode } = selectedSf.toJS();
        let dt = moment();
        if (plantResult.toJS().farmingDate) {
            dt = moment(new Date(plantResult.toJS().farmingDate))
        }
        console.log(startDate);
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
                    < SideBar />
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
                            farmingState={plantResult.toJS().farmingDate}
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
                                disabled={!plantResult.toJS().farmingDate}
                            >
                                <div style={{ color: 'green' }} id="nanum-gothic" className="mb-3 mt-0">
                                    <TextCenter>
                                        작물재배 데이터를 조회 할 날짜를 선택하세요.
                                </TextCenter>
                                </div>
                            </DatePicker>
                        </SfDeviceToolbar>
                        <PageBody>
                            <ToastContainer />
                            <StartFarmingModal
                                isOpen={modalsState}
                                modalFunc={this._changeModalsOpen}
                                sendFunc={this._changeFarming}
                                sfName={'sf-device#' + sfCode}
                                onChange={this._setFarming}
                                farm={farming}
                                resetFunc={this._setFarmingReset}
                                resetPipeFunc={this._setFarmingResetPipe}
                                resetFloorFunc={this._setFarmingResetFloor}
                                farmingCount={farmingCount}
                                isConfirm={isConfirm}
                                plantChange={this._plantChange}
                            />
                            {
                                plantResult.toJS().farmingDate && 
                                <Chart dataset={dataset.toJS()}
                                    ec={ref => this.ec = ref}
                                    ph={ref => this.ph = ref}
                                    wt={ref => this.wt = ref}
                                    wl={ref => this.wl = ref}
                                    th={ref => this.th = ref}
                                    // start={startDate.startOf('day').valueOf()}
                                    // end={startDate.endOf('day').valueOf()}
                                    start={startDate.startOf('day').valueOf()}
                                    end={startDate.endOf('day').valueOf()}
                                />
                            }
                            {/* {plantResult.toJS().farmingDate &&
                                <ReactHighcharts config={ecConfig} ref={ref => this.ec = ref} >
                                </ReactHighcharts>
                            } */}
                            {/* 콜백 함수로 ref추가 */}
                        </PageBody>
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
            startDate: state.sensor.get('startDate'),
            result: state.sensor.get('result'),
            datePickerToggle: state.sensor.get('datePickerToggle'),
            blocking: state.sensor.get('blocking'),
            modalsState: state.sensor.get('modalsState'),
            farming: state.sensor.get('farming'),
            farmingCount: state.sensor.getIn(['farmingInfo', 'farmingCount']),
            isConfirm: state.sensor.getIn(['farmingInfo', 'isConfirm']),
            selectedPlant: state.sensor.get('selectedPlant'),
            user: state.main.get('user'),
            selectedSf: state.main.get('selectedSf'),
            selectedAp: state.main.getIn(['sfToggle', 'selectedAp']),
            popoverState: state.main.get('popoverState'),
            plantResult: state.plant.get('result'),
            commandResult: state.plant.get('commandResult'),
        }),
        dispatch => ({
            MainActions: bindActionCreators(mainActions, dispatch),
            SensorActions: bindActionCreators(sensorActions, dispatch),
            PlantActions: bindActionCreators(plantActions, dispatch),
        })
    )(SfDeviceContainer)
);
