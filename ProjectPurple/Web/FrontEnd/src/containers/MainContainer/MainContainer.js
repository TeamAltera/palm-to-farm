import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as mainActions from '../../redux/modules/main';
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
} from '../../components';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './MainContainer.css';

class MainContainer extends Component {
    toastId = null;

    _sidebarControl = () => {
        const { MainActions, toggleState } = this.props;
        MainActions.changeToggleState(!toggleState);
    }

    _sfItemContainerControlOpen = (apCode, apName, regDate) => {
        const { MainActions } = this.props;
        const data={
            apCode: apCode,
            apName: apName,
            regDate: regDate,
        };
        MainActions.changeSelectedAp(data)
        MainActions.changeSfToggleState(true);
    }

    _sfItemContainerControlClose = () => {
        const { MainActions } = this.props;
        MainActions.changeSfToggleState(false);
    }

    _headerUserButtonControl = () => {
        const { MainActions, popoverState } = this.props;
        MainActions.changePopoverState(!popoverState);
    }

    _changeModalsOpen = () => {
        const { MainActions, modalsState } = this.props;
        MainActions.changeModalsState(!modalsState);
        if(!modalsState) MainActions.resInit();
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

    _deleteAllRouter = async () => {
        const { MainActions } = this.props;
    }

    _deleteSingleRouter = async (apCode) => {
        const { MainActions } = this.props;
        await MainActions.deleteSingleRouter(apCode).then(
            (res) => {
                this._showAlert(res.data)
                return res;
            }
        );
    }

    //라우터 조회
    _searchRouter = async () => {
        const { MainActions, isConfirm } = this.props;
        const { a, b, c, d } = this.props.ip.toJS();
        console.log(a + '.' + b + '.' + c + '.' + d);
        MainActions.resInit();
        await MainActions.searchRouter(a + '.' + b + '.' + c + '.' + d).then(
            res=>{
                MainActions.changeConfirm(res.data.status==='OK');
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
                if (res.data.status === 'OK'){
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
                            open={()=>this._sfItemContainerControlOpen(ap.AP_CODE, ap.AP_SSID, ap.AP_REG_DATE)}
                            close={this._sfItemContainerControlClose}
                        />
                    );
                }
            );
        }
    }

    //modal에서 에러메시지 출력
    _renderFormError=(res)=>{
        if (res) {
            const {status, msg} = res.toJS();
            return (
                <FormError isSuccess={status==='OK'}>{msg}</FormError>
            )
        }
    }

    //출력
    render() {
        const {
            sfToggleState,
            toggleState,
            popoverState,
            modalsState,
            dropdownState,
            result,
            deviceInfo,
            isConfirm,
            selectedAp,
        } = this.props;
        return (
            <MainWrapper option={toggleState}>
                <SideBar />
                <PageContent>
                    <Header
                        onClick={[this._sidebarControl, this._headerUserButtonControl]}
                        direction={toggleState} popover={popoverState}
                        title="디바이스 정보"
                    />
                    <MainToolBar
                        isOpen={dropdownState}
                        toggle={this._changeDropdown}
                        addFunc={this._changeModalsOpen}
                        deleteFunc={this._deleteAllRouter}
                    />
                    <SfItemContainer option={sfToggleState} data={selectedAp} 
                    close={this._sfItemContainerControlClose}/>
                    <PageBody>
                        <ToastContainer />
                        {this._renderRouterItems(deviceInfo.toJS())}
                        <RouterAddButton
                            onClick={this._changeModalsOpen} />
                        <RouterAddModal
                            modalFunc={this._changeModalsOpen}
                            isOpen={modalsState}
                            onChange={this._handleChange}
                            searchFunc={this._searchRouter}
                            addFunc={this._addRouter}
                            errorRender={()=>this._renderFormError(result)}
                            isConfirm={isConfirm}
                        />
                    </PageBody>
                    {/* <Footer /> */}
                </PageContent>
            </MainWrapper>

        );
    }
}

export default withRouter(
    connect(
        state => ({
            ip: state.main.get('ip'),
            modalsState: state.main.get('modalsState'),
            sfToggleState: state.main.get('sfToggleState'),
            toggleState: state.main.get('toggleState'),
            popoverState: state.main.get('popoverState'),
            deviceInfo: state.main.get('deviceInfo'),
            dropdownState: state.main.get('dropdownState'),
            result: state.main.get('result'),
            msg: state.main.get('msg'),
            isConfirm: state.main.get('isConfirm'),
            selectedAp: state.main.get('selectedAp'),
        }),
        dispatch => ({
            MainActions: bindActionCreators(mainActions, dispatch),
        })
    )(MainContainer)
);
