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
    Footer,
    PageBody
} from '../../components';

class MainContainer extends Component {

    _sidebarControl = () => {
        const { MainActions, toggleState } = this.props;
        MainActions.changeToggleState(!toggleState);
    }

    _headerUserButtonControl = () => {
        const { MainActions, popoverState } = this.props;
        MainActions.changePopoverState(!popoverState);
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
        const { toggleState, popoverState } = this.props;

        return (
            <MainWrapper option={toggleState}>
                <SideBar />
                <PageContent>
                    <Header 
                        onClick={[this._sidebarControl, this._headerUserButtonControl]} 
                        direction={toggleState} popover={popoverState}
                    />
                    <PageBody/>
                    {/* <Footer /> */}
                </PageContent>
            </MainWrapper>

        );
    }
}

export default withRouter(
    connect(
        state => ({
            toggleState: state.main.get('toggleState'),
            popoverState: state.main.get('popoverState'),
        }),
        dispatch => ({
            MainActions: bindActionCreators(mainActions, dispatch),
        })
    )(MainContainer)
);
