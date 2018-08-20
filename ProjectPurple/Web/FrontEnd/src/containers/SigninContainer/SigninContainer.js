import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as authActions from '../../redux/modules/auth';
import { withRouter } from 'react-router';
import jwt from 'jsonwebtoken';
import {
    Container,
    Column,
    FormHeader,
    FormBody,
    FormInput,
    Button,
    FormError,
    Form,
    FormGroup,
    Copyright,
    TextCenter,
    PageLink,
} from '../../components';
import setHashing from '../../utils/setHashing';
import SigninValidator from '../../utils/SigninValidator';
import { PulseLoader } from 'react-spinners';

class SigninContainer extends Component {

    //Input 컴포넌트의 value가 변화할 때 수행
    _handleChange = e => {
        const { AuthActions } = this.props;
        const { name, value } = e.target;

        //name: Input 컴포넌트의 name
        //value: 수정할 string 값
        //form: state의 루트 요소
        AuthActions.changeInput({
            name,
            value,
            form: 'signin',
        });
    }

    //input value 초기화
    _removeInputValue = () => {
        const { AuthActions } = this.props;
        AuthActions.changeInput({
            name: 'email',
            value: '',
            form: 'signin'
        });
        AuthActions.changeInput({
            name: 'password',
            value: '',
            form: 'signin'
        });
    }

    _setErrorMessage = (msg, location) => {
        const { AuthActions } = this.props;
        AuthActions.setError({
            form: 'signin',
            error: {
                msg: msg,
                location: location
            }
        });
    }

    _setUserInfo = () => {
        const { AuthActions } = this.props;
        let token = localStorage.getItem('jwtToken');
        let res = false;
        if (token) {
            //토큰으로 부터 사용자 정보 설정
            AuthActions.setCurrentUser({
                user: jwt.decode()
            });
            //사용자 인증이 되었음을 설정
            AuthActions.setAutenticated({
                form: 'signin',
                autenticated: true
            });
            res = true;
        }
        return res;
    }

    //Signin 동작 수행, 해싱 수행해서 API서버에게 전달해야, validation또한 수행해야
    _handleSignin = async () => {
        const { AuthActions, history } = this.props;
        const { email, password } = this.props.form.toJS();

        let error = SigninValidator(email, password);
        
        //AuthActions.setSpinnerLoading(true);
        if (!error) {
            //비밀번호 해싱 수행(단방향 해시)
            let pwd = setHashing(password);
            console.log('email은 ' + email + ' pwd는 ' + password);
            console.log(pwd);

            try {
                await AuthActions.doSignin(email, password);

                if (this._setUserInfo())//signin 성공시 main으로 이동
                    history.push('/main');
                else {//실패시 input value초기화, error메시지 출력
                    this._removeInputValue();
                    let result = this.props.result.toJS();
                    let msg = result.msg;
                    if (result.data.blockCount != null) {
                        msg += " (로그인 오류 허용 횟수 5회 중 " + result.data.blockCount + "회 오류)";
                    }
                    this._setErrorMessage(msg, 2);
                    console.log(this.props.error.toJS());
                }
            }
            catch (e) {
                console.log(e);
                this._setErrorMessage(this.props.result.toJS().msg, 2);
            }
        }
        else {
            this._setErrorMessage(error.msg, error.location);
        }
        //AuthActions.setSpinnerLoading(false);
    };

    _renderFormError(error, loc) {
        if (error) {
            const err = error.toJS();
            if (err.location === loc)
                return (
                    <FormError>{err.msg}</FormError>
                )
        }
    }

    componentWillUnmount() {
        const { AuthActions, isAutenticated, history } = this.props;
        //if (isAutenticated) {
        //    history.push('/main');
        //}
        AuthActions.initializeForm('signin');
    }

    //출력
    render() {
        const { error, spinnerLoading } = this.props;
        // form 에서 email 과 password 값을 읽어온다
        const { email, password } = this.props.form.toJS();
        return (
            <Container>
                <Column option="mt-5">
                    <Form type="login">
                        <FormHeader>
                            로그인
                        </FormHeader>
                        <FormBody>
                            <FormGroup>
                                <FormInput
                                    aria="emailHelp"
                                    id="email"
                                    text="Email address"
                                    type="email"
                                    placeholder="이메일"
                                    onChange={this._handleChange}
                                    value={email}
                                    name="email"
                                    disabled={false}
                                />
                            </FormGroup>
                            {this._renderFormError(error, 1)}
                            <FormGroup>
                                <FormInput
                                    id="password"
                                    text="Password"
                                    type="password"
                                    placeholder="비밀번호"
                                    onChange={this._handleChange}
                                    value={password}
                                    name="password"
                                    disabled={false}
                                />
                            </FormGroup>
                            {this._renderFormError(error, 2)}
                            <TextCenter>
                                <PulseLoader
                                    color={'#123abc'}
                                    loading={spinnerLoading}
                                />
                            </TextCenter>
                            {
                                !spinnerLoading &&
                                <Button onClick={this._handleSignin} option="primary" disabled={true}>
                                    로그인
                                </Button>
                            }
                            <TextCenter option="mt-3">
                                <PageLink
                                    preChildren="계정이 없다면 "
                                    middleChildren="회원가입"
                                    postChildren="해주세요."
                                    link="/signup"
                                    option="mt-1"
                                />
                                <PageLink
                                    preChildren="비밀번호를 잊어버렸다면 "
                                    middleChildren="여기"
                                    postChildren="를 클릭해주세요."
                                    link="/"
                                    option="mt-1"
                                />
                            </TextCenter>
                        </FormBody>
                    </Form>
                    <Copyright />
                </Column>
            </Container>
        );
    }
}

export default withRouter(
    connect(
        state => ({
            form: state.auth.getIn(['signin', 'form']),
            error: state.auth.getIn(['signin', 'error']),
            user: state.auth.get('user'),
            result: state.auth.get('result'),
            isAutenticated: state.auth.getIn('signin', 'isAutenticated'),
            spinnerLoading: state.auth.getIn(['signin', 'spinnerLoading']),
        }),
        dispatch => ({
            AuthActions: bindActionCreators(authActions, dispatch),
        })
    )(SigninContainer)
);
