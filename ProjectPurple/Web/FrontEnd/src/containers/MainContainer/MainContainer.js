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

    _headerUserButtonControl = () => {
        const { MainActions, popoverState } = this.props;
        MainActions.changePopoverState(!popoverState);
    }

    _changeModalsOpen = () => {
        const { MainActions, modalsState } = this.props;
        MainActions.changeModalsState(!modalsState);
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

    _searchRouter = async () => {
        const { MainActions } = this.props;
        const { a, b, c, d } = this.props.ip.toJS();
        console.log(a + '.' + b + '.' + c + '.' + d);
        await MainActions.searchRouter(a + '.' + b + '.' + c + '.' + d);
    }

    _addRouter = async () => {
        const { MainActions } = this.props;
        const { a, b, c, d } = this.props.ip.toJS();
        await MainActions.addRouter(a + '.' + b + '.' + c + '.' + d).then(
            (res) => {
                if (res.data.status === 'OK')
                    this._changeModalsOpen();
                this._showAlert(res.data);
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
                        />
                    );
                }
            );
        }
    }

    //출력
    render() {
        const {
            toggleState,
            popoverState,
            modalsState,
            dropdownState,
        } = this.props;
        const { data } = this.props.result.toJS();
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
                    <PageBody>
                        <ToastContainer />
                        {this._renderRouterItems(data)}
                        <RouterAddButton
                            onClick={this._changeModalsOpen} />
                        <RouterAddModal
                            modalFunc={this._changeModalsOpen}
                            isOpen={modalsState}
                            onChange={this._handleChange}
                            searchFunc={this._searchRouter}
                            addFunc={this._addRouter}
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
            toggleState: state.main.get('toggleState'),
            popoverState: state.main.get('popoverState'),
            //deviceInfo: state.main.get('deviceInfo'),
            dropdownState: state.main.get('dropdownState'),
            result: state.main.get('result'),
        }),
        dispatch => ({
            MainActions: bindActionCreators(mainActions, dispatch),
        })
    )(MainContainer)
);
