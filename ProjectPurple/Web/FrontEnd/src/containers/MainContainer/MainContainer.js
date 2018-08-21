import React, { Component, Fragment } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as mainActions from '../../redux/modules/main';
import * as sensorActions from '../../redux/modules/sensor';
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
    BlankWrapper,
} from '../../components';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import BlockUi from 'react-block-ui';
import { Loader } from 'react-loaders';
import 'react-block-ui/style.css';
import 'loaders.css/loaders.min.css';
import '../SfDeviceContainer/sfDeviceContainer.css'
import setAuthorizationToken from '../../utils/setAuthorizationToken';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

var stompClient = null;
var socket = null;

class MainContainer extends Component {
    toastId = null;

    _connect(addSfAuto, userCode) {
        if (!socket) {
            socket = new SockJS(
                'http://203.250.32.173:9001/smart_plant/device_data'
            );

            socket.onclose = () => {
                stompClient.disconnect();
                console.log('disconnected')
            };

            stompClient = Stomp.over(socket);
            stompClient.connect('manager', 'manager', function (frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/us' + userCode, addSfAuto);
            });
        }
    }

    _addSfAuto = (message) => {
        const { deviceInfo, MainActions } = this.props;
        let msg = JSON.parse(message.body);
        deviceInfo.toJS().data.deviceInfo.plantDevices.push(msg);
        //MainActions.addItem({newSf:msg});
        deviceInfo.toJS().data.deviceInfo.raspAPDevices.map(
            (arg) => {
                if (arg.AP_CODE === msg.AP_CODE)
                    arg.AP_SF_CNT += 1;
            }
        );
        this._showAlert({
            msg: "pi3-ap#" + msg.AP_CODE + "에 sf-device#"
                + msg.SF_CODE + "가 추가되었습니다.", status: 'OK'
        });

    }

    _handleSignout = async () => {
        // const { UserActions } = this.props;
        // try {
        //     await UserActions.logout();
        // } catch (e) {
        //     console.log(e);
        // }

        localStorage.removeItem('jwtToken');
        setAuthorizationToken(null);
        window.location.href = '/'; // 홈페이지로 새로고침
    }


    _sfItemContainerControlOpen = (data) => {
        const { MainActions } = this.props;
        MainActions.changeSfToggleState({
            sfToggleState: true,
            selectedAp: data,
        });
    }

    _sfItemContainerControlClose = () => {
        const { MainActions, selectedAp } = this.props;
        MainActions.changeSfToggleState({
            sfToggleState: false,
            selectedAp: selectedAp,
        });
    }

    _headerUserButtonControl = () => {
        const { MainActions, popoverState } = this.props;
        MainActions.changePopoverState(!popoverState);
    }

    _changeModalsOpen = () => {
        const { MainActions, modalsState } = this.props;
        MainActions.changeModalsState(!modalsState);
        if (!modalsState) MainActions.resInit();
        else MainActions.initializeForm();
    }

    _changeDropdown = () => {
        const { MainActions, dropdownState } = this.props;
        MainActions.changeDropdownState(!dropdownState);
    }

    // componentWillUnmount() {
    //     const { AuthActions, isAutenticated, history } = this.props;
    //     //if (isAutenticated) {
    //     //    history.push('/main');  
    //     //}
    //     AuthActions.initializeForm('signin');
    // }

    _getDeviceAllInfo = async () => {
        const { MainActions } = this.props;
        await MainActions.getDeviceAllInfo();
    }

    _getUserInfo = async () => {
        const { MainActions } = this.props;
        await MainActions.getUserInfo().then((res) => {
            this._connect(this._addSfAuto, res.data.data.userCode);
            return res;
        });
    }

    _deleteAllRouter = async () => {
        const { MainActions } = this.props;
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


    _deleteSingleRouter = async (apCode) => {
        const { MainActions } = this.props;
        await MainActions.deleteSingleRouter(apCode).then(
            (res) => {
                this._showAlert(res.data);
                return res;
            }
        );
    }

    //라우터 조회
    _searchRouter = async () => {
        const { MainActions, isConfirm } = this.props;
        const { a, b, c, d } = this.props.ip.toJS();
        MainActions.resInit();
        await MainActions.searchRouter(a + '.' + b + '.' + c + '.' + d).then(
            res => {
                MainActions.changeConfirm(res.data.status === 'OK'
                    && res.data.data.code === 200);
                return res;
            }
        );
    }

    //라우터 추가
    _addRouter = async () => {
        const { MainActions } = this.props;
        const { a, b, c, d } = this.props.ip.toJS();
        MainActions.resInit();
        await MainActions.addRouter(a + '.' + b + '.' + c + '.' + d).then(
            (res) => {
                if (res.data.status === 'OK') {
                    this._changeModalsOpen();
                    this._showAlert(res.data);
                }
                return res;
            }
        );
    }

    _showAlert = (data) => {
        let type = toast.TYPE.SUCCESS
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

    _changeSelectedSf = (sfCode, count, sfIp) => {
        const { MainActions } = this.props;
        MainActions.changeSelectedSf({
            sfCode: sfCode,
            count: count,
            sfIp: sfIp,
        });
    }

    //Input 컴포넌트의 value가 변화할 때 수행
    _handleChange = e => {
        const { MainActions } = this.props;
        const { name, value } = e.target;

        MainActions.changeInput({
            name,
            value,
        });
    }

    componentDidMount() {
        this._getDeviceAllInfo();
        this._getUserInfo();
    }

    _renderRouterItems = (data) => {
        if (data && data.data && data.data.deviceInfo) {
            return data.data.deviceInfo.raspAPDevices.map(
                (ap) => {
                    return (
                        <RouterItem apName={ap.AP_SSID} userCode={ap.USER_CODE}
                            ip={ap.AP_PUBLIC_IP} key={ap.AP_CODE} apCode={ap.AP_CODE}
                            deleteFunc={() => this._deleteSingleRouter(ap.AP_CODE)}
                            count={ap.AP_SF_CNT} regDate={ap.AP_REG_DATE}
                            open={() =>
                                this._sfItemContainerControlOpen(
                                    {
                                        apCode: ap.AP_CODE,
                                        apIp: ap.AP_PUBLIC_IP,
                                        apName: ap.AP_SSID,
                                        regDate: ap.AP_REG_DATE,
                                        count: ap.AP_SF_CNT,
                                    })
                            }
                            close={this._sfItemContainerControlClose}
                        />
                    );
                }
            );
        }
    }

    _renderSfItems = (data) => {
        const { apCode, count } = this.props.selectedAp;
        if (data && data.data && data.data.deviceInfo) {
            return data.data.deviceInfo.plantDevices.filter(
                (item, index, array) => {
                    return apCode === item.AP_CODE
                }
            ).map(
                (sf) => {
                    return (
                        <SfItem key={sf.SF_CODE} coolerSt={sf.COOLER_ST}
                            ledSt={sf.LED_ST} pumpSt={sf.PUMP_ST}
                            ip={sf.INNER_IP} ctrlMode={sf.LED_CTRL_MODE}
                            sfCode={sf.SF_CODE} regDate={sf.SF_REG_DATE}
                            floor={sf.FLOOR_CNT} port={sf.SF_PORT_CNT}
                            onClick={() => this._changeSelectedSf(sf.SF_CODE, 0, sf.INNER_IP)}
                        />
                    );
                }
            );
        }
    }

    //modal에서 에러메시지 출력
    _renderFormError = (res) => {
        if (res) {
            const { status, msg, data } = res.toJS();
            console.log(res.toJS());
            return (
                <FormError isSuccess={status === 'OK' && data.code === 200}>
                    <div className="mb-2 ml-2">
                        {msg}
                    </div>
                </FormError>
            )
        }
    }

    //출력
    render() {
        const {
            sfToggleState,
            popoverState,
            modalsState,
            dropdownState,
            result,
            deviceInfo,
            isConfirm,
            selectedAp,
            user,
            blocking,
        } = this.props;

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
                    <SideBar />
                    <HeaderBlank />
                    <Header
                        onClick={[this._headerUserButtonControl]}
                        popover={popoverState}
                        user={user}
                    />
                    <ToastContainer />
                    <PageContent>
                        <MainToolBar
                            isOpen={dropdownState}
                            toggle={this._changeDropdown}
                            addFunc={this._changeModalsOpen}
                            deleteFunc={this._deleteAllRouter}
                        />
                        <SfItemContainer option={sfToggleState} data={selectedAp}
                            close={this._sfItemContainerControlClose}>
                            {selectedAp && this._renderSfItems(deviceInfo.toJS())}
                        </SfItemContainer>
                        <PageBody>
                            {this._renderRouterItems(deviceInfo.toJS())}
                            <RouterAddButton
                                onClick={this._changeModalsOpen} />
                            <RouterAddModal
                                modalFunc={this._changeModalsOpen}
                                isOpen={modalsState}
                                onChange={this._handleChange}
                                searchFunc={this._searchRouter}
                                addFunc={this._addRouter}
                                errorRender={() => this._renderFormError(result)}
                                isConfirm={isConfirm}
                            />

                        </PageBody>
                        {/* <Footer /> */}
                    </PageContent>
                </MainWrapper>
            </Fragment>
        );
    }
}

export default withRouter(
    connect(
        state => ({
            ip: state.main.get('ip'),
            modalsState: state.main.get('modalsState'),
            popoverState: state.main.get('popoverState'),
            deviceInfo: state.main.get('deviceInfo'),
            dropdownState: state.main.get('dropdownState'),
            result: state.main.get('result'),
            msg: state.main.get('msg'),
            isConfirm: state.main.get('isConfirm'),
            selectedAp: state.main.getIn(['sfToggle', 'selectedAp']),
            sfToggleState: state.main.getIn(['sfToggle', 'sfToggleState']),
            blocking: state.sensor.get('blocking'),
            user: state.main.get('user'),
        }),
        dispatch => ({
            MainActions: bindActionCreators(mainActions, dispatch),
            SensorActions: bindActionCreators(sensorActions, dispatch),
        })
    )(MainContainer)
);
