import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as mainActions from '../../redux/modules/main';
import {
    AuthWrapper,
    AuthHeader,
    AuthBody,
    Input,
    Button,
    SideBar,
    PageContent,
    Header,
    MainWrapper,
} from '../../components';

class MainContainer extends Component {

    _sidebarControl=()=>{
        const { MainActions, toggleState } = this.props;
        MainActions.changeToggleState(!toggleState);
    }

    // componentWillUnmount() {
    //     const { AuthActions, isAutenticated, history } = this.props;
    //     //if (isAutenticated) {
    //     //    history.push('/main');
    //     //}
    //     AuthActions.initializeForm('signin');
    // }

    //출력
    render() {
        const { toggleState } = this.props;

        return (
            <MainWrapper option={toggleState}>
                <SideBar />
                <PageContent>
                    <Header onClick={this._sidebarControl}/>
                </PageContent>
            </MainWrapper>

        );
    }
}

export default withRouter(
    connect(
        state => ({
            toggleState: state.main.get('toggleState'),
        }),
        dispatch => ({
            MainActions: bindActionCreators(mainActions, dispatch),
        })
    )(MainContainer)
);
