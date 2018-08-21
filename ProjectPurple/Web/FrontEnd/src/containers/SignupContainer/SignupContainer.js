import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as authActions from '../../redux/modules/auth';
import { withRouter } from 'react-router';
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
    TextCenter,
    FormRow,
    PasswordProg,
    PageLink,
    SignupProg,
} from '../../components';
import scorePassword from '../../utils/scorePassword';
import emailValidator from '../../utils/emailValidator';
import { Collapse } from 'reactstrap';
import { PulseLoader } from 'react-spinners';

class SignupContainer extends Component {

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
            form: 'signup',
        });
        this._isPasswordSame(name, value);
        this._handlePasswordProg(name, value);
        this._isEmailExists(name, value);
    };

    //패스워드 progress bar제어
    _handlePasswordProg = (name, value) => {
        const { AuthActions } = this.props;
        if (name === 'password') {
            AuthActions.changePasswordScore(scorePassword(value));
        }
    }

    _isEmailExists = async (name, value) => {
        const { AuthActions } = this.props;
        if (name === 'email') {
            let result = emailValidator(value);
            if (result === 2) {
                await AuthActions.checkEmailExists(value).then(
                    res => {
                        let code = res.data.data;
                        if (code === 0) code = 2;
                        AuthActions.changeEmailExists(code);
                        return res;
                    }
                );
            }
            else
                AuthActions.changeEmailExists(result);
        }
    }

    _isPasswordSame = (name, value) => {
        const { AuthActions } = this.props;
        const { password, passwordConfirm } = this.props.form.toJS();
        let val;
        if (name === "password")
            val = passwordConfirm
        else if (name === "passwordConfirm")
            val = password;
        else return;

        if (val.trim() === "" && value.trim() === "")//둘다 공백
            AuthActions.changePasswordConfirm(0);
        else if (val.trim() === "" || value.trim() === "")//둘중 하나만 공백
            AuthActions.changePasswordConfirm(4);
        else if (val === value)//값이 일치
            AuthActions.changePasswordConfirm(5);
        else//값이 일치하지 않는 경우
            AuthActions.changePasswordConfirm(4);
    }

    _renderMsg(code) {
        let msg;
        let success = false;
        switch (code) {
            case 0://공백인 경우
                msg = null;
                break;
            case 1:
                msg = '이미 사용중인 이메일 입니다.';
                break;
            case 2://사용가능한 이메일인 경우
                msg = null;
                break;
            case 3:
                msg = '이메일 주소가 형식에 맞지 않습니다.';
                break;
            case 4:
                msg = '비밀번호가 일치하지 않습니다.';
                break;
            case 5://비밀번호가 일치하는 경우
                msg = null;
                break;
            case 6://이메일로 인증코드가 전송된 경우
                success = true;
                msg = '입력하신 이메일로 인증코드가 전송되었습니다. 제한시간 내에 인증코드를 입력해주세요.'
                break;
            case 7:// 인증코드가 일치하지 않는 경우
                msg = '인증코드가 일치하지 않습니다. 다시 입력해주세요.'
                break;
            default:
        }
        return (<FormError isSuccess={success}>{msg}</FormError>);
    }

    componentWillUnmount() {
        const { AuthActions } = this.props;
        AuthActions.initializeForm('signup');
    }

    _sendEmail = async () => {
        const { AuthActions } = this.props;
        const { email } = this.props.form.toJS();
        AuthActions.setSpinnerLoading(true);
        await AuthActions.sendAuthCode(email);
        AuthActions.setSpinnerLoading(false);
        AuthActions.setAuthCodeConfirm(6);
    }

    _removeInputValue = () => {
        const { AuthActions } = this.props;
        AuthActions.changeInput({
            name: 'authCode',
            value: '',
            form: 'signup'
        });
    }

    _handleSignup = async () => {
        const { AuthActions, history } = this.props;
        const { email, password, firstName, secondName, authCode } = this.props.form.toJS();

        AuthActions.setSpinnerLoading(true);
        try {
            await AuthActions.checkAuthCode(authCode);
            if(this.props.result.toJS().status==="OK"){
                await AuthActions.doSignup(email, password, firstName, secondName);
                if(this.props.result.toJS().status==="OK")
                    history.push('/');
                else //회원가입 실패
                    AuthActions.setSpinnerLoading(false);
            }
            else{
                this._removeInputValue();
                AuthActions.setSpinnerLoading(false);
                AuthActions.setAuthCodeConfirm(7);
            }
        }
        catch (e) {
            console.log(e);
        }
    }

    _renderFormError(error, loc) {
        if (error) {
            const err = error.toJS();
            if (err.location === loc)
                return (
                    <FormError>{err.msg}</FormError>
                )
        }
    }

    //출력
    render() {
        const {
            email,
            password,
            passwordConfirm,
            firstName,
            secondName,
            authCode,
        } = this.props.form.toJS();

        const {
            passwordScore,
            isExists,
            isPasswordConfirm,
            isAutenticated,
            error,
            spinnerLoading,
            isAuthCodeConfirm
        } = this.props;

        return (
            <Container>
                <Column option="mt-5">
                    <Form type="login">
                        <FormBody>
                            <SignupProg />
                            <FormGroup>
                                <FormRow>
                                    <Column option="-md-6">
                                        <FormInput
                                            id="firstName"
                                            type="text"
                                            placeholder="성"
                                            onChange={this._handleChange}
                                            value={firstName}
                                            name="firstName"
                                            disabled={spinnerLoading || isAutenticated}
                                        />
                                    </Column>
                                    <Column option="-md-6">
                                        <FormInput
                                            id="secondName"
                                            type="text"
                                            placeholder="이름"
                                            onChange={this._handleChange}
                                            value={secondName}
                                            name="secondName"
                                            disabled={spinnerLoading || isAutenticated}
                                        />
                                    </Column>
                                </FormRow>
                            </FormGroup>
                            <FormGroup>
                                <FormInput
                                    aria="emailHelp"
                                    id="email"
                                    type="email"
                                    placeholder="이메일"
                                    onChange={this._handleChange}
                                    value={email}
                                    name="email"
                                    disabled={spinnerLoading || isAutenticated}
                                />
                                {this._renderMsg(isExists)}
                            </FormGroup>
                            <FormGroup option="mb-2">
                                <FormInput
                                    id="password"
                                    type="password"
                                    placeholder="비밀번호"
                                    onChange={this._handleChange}
                                    value={password}
                                    name="password"
                                    disabled={spinnerLoading || isAutenticated}
                                />
                                <PasswordProg value={passwordScore} option="mt-2" />
                            </FormGroup>
                            <FormGroup>
                                <FormInput
                                    id="passwordCofirm"
                                    type="password"
                                    placeholder="비밀번호 확인"
                                    onChange={this._handleChange}
                                    value={passwordConfirm}
                                    name="passwordConfirm"
                                    disabled={spinnerLoading || isAutenticated}
                                />
                                {this._renderMsg(isPasswordConfirm)}
                            </FormGroup>
                            <TextCenter>
                                <PulseLoader
                                    color={'#123abc'}
                                    loading={spinnerLoading && !isAutenticated}
                                />
                            </TextCenter>
                            {this._renderFormError(error, 1)}
                            {!spinnerLoading && !isAutenticated &&
                                <Button onClick={this._sendEmail} option="primary"
                                    disabled={isExists === 2 && isPasswordConfirm === 5}>
                                    가입하기
                                </Button>}
                            <Collapse isOpen={isAutenticated} onEntered={this.onEntered}>
                                <FormGroup>
                                    <FormInput
                                        id="authCode"
                                        type="text"
                                        placeholder="인증코드 입력"
                                        onChange={this._handleChange}
                                        value={authCode}
                                        name="authCode"
                                        addon={true}
                                        addonText={"3:00"}
                                        disabled={false}
                                    />
                                    {this._renderMsg(isAuthCodeConfirm)}
                                </FormGroup>
                                <TextCenter>
                                    <PulseLoader
                                        color={'#123abc'}
                                        loading={spinnerLoading}
                                    />
                                </TextCenter>
                                {!spinnerLoading &&<Button onClick={this._handleSignup} 
                                    option="primary"
                                    disabled={authCode.trim() !== ""}>
                                    코드입력
                                </Button>}
                            </Collapse>
                            <TextCenter option="mt-3">
                                <PageLink
                                    preChildren="이미 가입하셨다면?"
                                    middleChildren="로그인"
                                    link="/"
                                />
                            </TextCenter>
                        </FormBody>
                    </Form>
                </Column>
            </Container>
        );
    }
}

export default withRouter(
    connect(
        state => ({
            passwordScore: state.auth.getIn(['signup', 'passwordScore']),
            form: state.auth.getIn(['signup', 'form']),
            error: state.auth.getIn(['signup', 'error']),
            spinnerLoading: state.auth.getIn(['signup', 'spinnerLoading']),
            isExists: state.auth.getIn(['signup', 'isExists']),
            isAutenticated: state.auth.getIn(['signup', 'isAutenticated']),
            isAuthCodeConfirm: state.auth.getIn(['signup', 'isAuthCodeConfirm']),
            isPasswordConfirm: state.auth.getIn(['signup', 'isPasswordConfirm']),
            result: state.auth.get('result'),
        }),
        dispatch => ({
            AuthActions: bindActionCreators(authActions, dispatch),
        })
    )(SignupContainer)
);
