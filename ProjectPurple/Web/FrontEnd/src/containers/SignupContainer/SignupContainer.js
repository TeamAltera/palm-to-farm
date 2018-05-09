import React, { Component } from 'react';
import { AuthWrapper, AuthError } from '../../components';
import { Button, Form, Grid, Header, Segment } from 'semantic-ui-react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as authActions from '../../redux/modules/auth';
import { isEmail, isLength } from 'validator';
import * as userActions from '../../redux/modules/user';
import storage from '../../lib/storage';
import { withRouter } from 'react-router';

class SignupContainer extends Component {
  handleChange = e => {
    const { AuthActions } = this.props;
    const { name, value } = e.target;
    console.log(e);
    AuthActions.changeInput({
      name,
      value,
      form: 'signup',
    });

    // 검증작업 진행
    const validation = this.validate[name](value);
    if (name.indexOf('password') > -1 || !validation) return; // 비밀번호 검증이거나, 검증 실패하면 여기서 마침

    // TODO: 이메일, 아이디 중복 확인
  };

  handleLocalRegister = async () => {
    const { form, AuthActions, error, history } = this.props;
    const { email, username, password, passwordConfirm } = form.toJS();

    const { validate } = this;

    if (error) return; // 현재 에러가 있는 상태라면 진행하지 않음
    if (
      !validate['email'](email) ||
      !validate['username'](username) ||
      !validate['password'](password) ||
      !validate['passwordConfirm'](passwordConfirm)
    ) {
      // 하나라도 실패하면 진행하지 않음
      console.log('the form is invalied');
      return;
    }
    try {
      await AuthActions.localRegister({
        username,
        email,
        password,
      });
      const loggedInfo = this.props.result.toJS();
      console.log(loggedInfo);
      // TODO: 로그인 정보 저장 (로컬스토리지/스토어)
      history.push('/'); // 회원가입 성공시 홈페이지로 이동 -> 이후 url 변경해야함
    } catch (e) {
      //에러 처리하기
      if (e.response.status === 409) {
        const { key } = e.response.data;
        const message =
          key === 'email'
            ? '이미 존재하는 이메일입니다.'
            : '이미 존재하는 아이디입니다.';
        return this.setError(message);
      }
      this.setError('알 수 없는 에러가 발생했습니다.');
    }
  };

  setError = message => {
    const { AuthActions } = this.props;
    AuthActions.setError({
      form: 'register',
      message,
    });
  };

  validate = {
    email: value => {
      if (!isEmail(value)) {
        this.setError('잘못된 이메일 형식 입니다.');
        return false;
      }
      return true;
    },
    username: value => {
      if (!isLength(value, { min: 4, max: 15 })) {
        this.setError(
          '아이디는 4~15 글자의 알파벳 혹은 숫자로 이뤄져야 합니다.' //다시 적기
        );
        return false;
      }
      return true;
    },
    password: value => {
      if (!isLength(value, { min: 6 })) {
        this.setError('비밀번호를 6자 이상 입력하세요.');
        return false;
      }
      this.setError(null); // 이메일과 아이디는 에러 null 처리를 중복확인 부분에서 하게 됩니다
      return true;
    },
    passwordConfirm: value => {
      if (this.props.form.get('password') !== value) {
        this.setError('비밀번호확인이 일치하지 않습니다.');
        return false;
      }
      this.setError(null);
      return true;
    },
  };

  render() {
    const { error } = this.props;
    const {
      email,
      username,
      password,
      passwordConfirm,
    } = this.props.form.toJS();
    const { handleChange, handleLocalRegister } = this;

    return (
      <AuthWrapper>
        <Grid className="sign__grid">
          <Grid.Column style={{ minWidth: 450 }}>
            <Form>
              <Segment raised>
                <Header as="h1" color="purple" textAlign="center">
                  Purple ID 만들기
                </Header>
                <Form.Group widths="equal">
                  <Form.Input
                    fluid
                    name="username"
                    placeholder="성 이름"
                    value={username}
                    onChange={handleChange}
                  />
                </Form.Group>
                <Form.Field className="sign__grid__form__field">
                  <Form.Input
                    fluid
                    name="email"
                    icon="user"
                    iconPosition="left"
                    placeholder="name@example.com"
                    value={email}
                    onChange={handleChange}
                  />
                </Form.Field>
                <Form.Group widths="equal">
                  <Form.Input
                    fluid
                    name="password"
                    icon="lock"
                    iconPosition="left"
                    placeholder="비밀번호"
                    type="password"
                    value={password}
                    onChange={handleChange}
                  />
                  <Form.Input
                    fluid
                    name="passwordConfirm"
                    icon="lock"
                    iconPosition="left"
                    placeholder="비밀번호 확인"
                    type="password"
                    value={passwordConfirm}
                    onChange={handleChange}
                  />
                </Form.Group>
                <Button
                  color="purple"
                  fluid
                  size="large"
                  onClick={handleLocalRegister}>
                  시작하기
                </Button>
              </Segment>
            </Form>
          </Grid.Column>
        </Grid>
        {error && <AuthError>{error}</AuthError>}
      </AuthWrapper>
    );
  }
}

export default withRouter(
  connect(
    state => ({
      form: state.auth.getIn(['signup', 'form']),
      error: state.auth.getIn(['signup', 'error']),
      exists: state.auth.getIn(['signup', 'exists']),
      result: state.auth.get('result'),
    }),
    dispatch => ({
      AuthActions: bindActionCreators(authActions, dispatch),
      userActions: bindActionCreators(userActions, dispatch),
    })
  )(SignupContainer)
);
