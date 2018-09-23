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
        if (!stompClient) {
            socket = new SockJS(
                'http://203.250.32.91:9001/smart_plant/device_data'
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
        const { deviceInfo, sfToggleState } = this.props;
        //MainActions.addItem({newSf:msg});
        let msg = JSON.parse(message.body);
        deviceInfo.toJS().some(
            (arg, index) => {
                //if (arg.apCode === msg.apCode) {
                //    console.log(index)
                //    console.log('find')
                    this._getDeviceAllInfo();

                    if (sfToggleState) {
                        this._sfItemContainerControlClose();
                    }
                    // MainActions.incrementSfCnt({
                    //     index: index,
                    //     count: arg.apSfCnt+1
                    // });
                    // MainActions.addItem({
                    //     index: index,
                    //     data: msg
                    // })
                    //this._getDeviceAllInfo();
                    return true;
                //}
            }
        );
        this._showAlert({
            msg: "pi3-ap#" + msg.apCode + "에 sf-device#"
                + msg.sfCode + "가 추가되었습니다.", status: 'OK'
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

    _changeSelectedSf = (item) => {
        const { MainActions } = this.props;
        MainActions.changeSelectedSf(item);
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
        this._sfItemContainerControlClose();
        this._sbChange(0);
        this._getDeviceAllInfo();
        this._getUserInfo();
    }

    componentWillUnmount() {
    }

    _renderRouterItems = (data) => {
        if (data) {
            return data.map(
                (ap) => {
                    return (
                        <RouterItem apName={ap.apSsid} userCode={ap.userCode}
                            ip={ap.apPublicIp} apCode={ap.apCode}
                            key={ap.apCode}
                            deleteFunc={() => this._deleteSingleRouter(ap.apCode)}
                            count={ap.apSfCnt} regDate={ap.apRegDate}
                            open={() =>
                                this._sfItemContainerControlOpen(
                                    {
                                        apCode: ap.apCode,
                                        apIp: ap.apPublicIp,
                                        apName: ap.apSsid,
                                        regDate: ap.apRegDate,
                                        count: ap.apSfCnt,
                                        plantDevices: ap.plantDevices,
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
        if (data) {
            return data.map(
                (sf) => {
                    return (
                        <SfItem key={sf.sfCode}
                            coolerSt={sf.coolerSt}
                            ledSt={sf.ledSt} pumpSt={sf.pumpSt}
                            ip={sf.innerIp} ctrlMode={sf.ledCtrlMode}
                            sfCode={sf.sfCode} regDate={sf.sfRegDate}
                            floor={sf.floorCnt} port={sf.sfPortCnt}
                            onClick={() => this._changeSelectedSf(sf)}
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

    _sbChange = (value) => {
        const { MainActions } = this.props;
        MainActions.changeSelectedSb(value);
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
            sidebarSelected,
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
                    <SideBar changeFunc={this._sbChange} selected={sidebarSelected} />
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
                            {selectedAp && this._renderSfItems(selectedAp.plantDevices)}
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
            sidebarSelected: state.main.get('sidebarSelected'),
        }),
        dispatch => ({
            MainActions: bindActionCreators(mainActions, dispatch),
            SensorActions: bindActionCreators(sensorActions, dispatch),
        })
    )(MainContainer)
);
