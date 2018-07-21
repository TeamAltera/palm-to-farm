import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { AuthWrapper, AuthHeader, AuthBody, Input, Button } from '../../components';

class MainContainer extends Component {

    //출력
    render() {

        const {user} = this.props;

        return (
            <div>
                {console.log(user)}
            </div>
        );
    }
}

export default withRouter(
    connect(
        state => ({
            user: state.auth.get('user'),
        }),
        dispatch => ({
        })
    )(MainContainer)
);
