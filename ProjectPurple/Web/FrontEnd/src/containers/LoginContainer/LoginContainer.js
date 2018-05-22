import React, { Component } from 'react';
import { AuthWrapper, AuthError } from '../../components';
import { Link } from 'react-router-dom';
import {
  Button,
  Form,
  Grid,
  Header,
  Message,
  Segment,
  Divider,
} from 'semantic-ui-react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as authActions from '../../redux/modules/auth';
import * as userActions from '../../redux/modules/user';
import storage from '../../lib/storage';
//import createHistory from "history/createBrowserHistory";
import { withRouter } from 'react-router';

class LoginContainer extends Component {
  handleChange = e => {
    const { AuthActions } = this.props;
    const { name, value } = e.target;

    AuthActions.changeInput({
      name,
      value,
      form: 'login',
    });
  };

  componentWillUnmount() {
    const { AuthActions } = this.props;
    AuthActions.initializeForm('login');
  }

  setError = message => {
    const { AuthActions } = this.props;
    AuthActions.setError({
      form: 'login',
      message,
    });
    return false;
  };

  handleLocalLogin = async () => {
    const { form, AuthActions, UserActions, history } = this.props;
    const { email, password } = this.props.form.toJS();
    //const history = createHistory();

    try {
      console.log('email은 ' + email + ' pwd는 ' + password);
      await AuthActions.doSignin(email, password);
      const loggedInfo = this.props.result.toJS();

      UserActions.setLoggedInfo(loggedInfo);
      history.push('/home');
      storage.set('loggedInfo', loggedInfo);
    } catch (e) {
      this.setError('잘못된 계정정보입니다.' + email + password);
      console.log(e);
    }
  };

  render() {
    const { error } = this.props;
    const { email, password } = this.props.form.toJS(); // form 에서 email 과 password 값을 읽어옴
    const { handleChange } = this;

    return (
      <AuthWrapper>
        <Grid className="login__grid">
          <Grid.Column style={{ minWidth: 370 }}>
            <Form>
              <Segment>
                <Header as="h1" color="purple" textAlign="center">
                  로그인 Purple로 이동
                </Header>
                <Form.Input
                  fluid
                  name="email"
                  icon="user"
                  iconPosition="left"
                  placeholder="E-mail address"
                  value={email}
                  onChange={handleChange}
                />
                <Form.Input
                  fluid
                  name="password"
                  icon="lock"
                  iconPosition="left"
                  placeholder="Password"
                  type="password"
                  value={password}
                  onChange={handleChange}
                />
                <Button
                  color="purple"
                  fluid
                  size="large"
                  onClick={this.handleLocalLogin}>
                  로그인
                </Button>
              </Segment>
            </Form>
            <Message>
              Purple ID가 없으신가요? <Link to="/signup">지금 생성</Link>
              <Divider />
              <Link to="/find">Purple ID 또는 암호를 잊으셨습니까?</Link>
            </Message>
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
      form: state.auth.getIn(['login', 'form']),
      error: state.auth.getIn(['login', 'error']),
      result: state.auth.get('result'),
    }),
    dispatch => ({
      AuthActions: bindActionCreators(authActions, dispatch),
      UserActions: bindActionCreators(userActions, dispatch),
    })
  )(LoginContainer)
);
