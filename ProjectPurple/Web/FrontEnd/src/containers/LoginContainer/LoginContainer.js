import React, { Component } from "react";
import { AuthWrapper, AuthError } from "../../components";
import { Link } from "react-router-dom";
import {
  Button,
  Form,
  Grid,
  Header,
  Message,
  Segment,
  Divider
} from "semantic-ui-react";
import { isEmail, isLength } from "validator";
import { Field, reduxForm } from "redux-form";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import * as authActions from "../../redux/modules/auth";
import storage from "../../lib/storage";

class LoginContainer extends Component {
  handleChange = e => {
    const { AuthActions } = this.props;
    const { name, value } = e.target;

    AuthActions.changeInput({
      name,
      value,
      form: "login"
    });
  };

  setError = message => {
    const { AuthActions } = this.props;
    AuthActions.setError({
      form: "login",
      message
    });
    return false;
  };

  handleLocalLogin = async () => {
    const { form, AuthActions, UserActions, history } = this.props;
    const { email, password } = form.toJS();

    try {
      await AuthActions.doSignin({ email, password });
      const loggedInfo = this.props.result.toJS();

      UserActions.setLoggedInfo(loggedInfo);
      history.push("/");
      storage.set("loggedInfo", loggedInfo);
    } catch (e) {
      console.log("a");
      this.setError("잘못된 계정정보입니다.");
    }
  };

  doLogin = value => {
    this.validate.email("asdf@ad.ad");
    this.validate.password("1234");

    const secret = "MySecretKey1$1$234";
    console.log(this.hashed("1234", secret));
  };

  hashed = (password, secret) => {
    const crypto = require("crypto");
    return crypto
      .createHmac("sha256", secret)
      .update(password)
      .digest("hex");
  };

  validate = {
    email: value => {
      if (!isEmail(value)) {
        this.setError("잘못된 이메일 형식 입니다.");
        console.log("this is wrong email format");
        return false;
      }
      return true;
    },
    password: value => {
      if (!isLength(value, { min: 6 })) {
        this.setError("비밀번호를 6자 이상 입력하세요.");
        console.log("password must be more than 6");
        return false;
      }
      return true;
    }
  };

  render() {
    const { error } = this.props;
    const { email, password } = this.props.form.toJS(); // form 에서 email 과 password 값을 읽어옴
    const { handleChange } = this;

    return (
      <AuthWrapper>
        <Grid className="login__grid">
          <Grid.Column style={{ minWidth: 300 }}>
            <Form>
              <Segment raised>
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
                  //onClick={handleLocalLogin}
                >
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

export default connect(
  state => ({
    form: state.auth.getIn(["login", "form"]),
    error: state.auth.getIn(["login", "error"])
  }),
  dispatch => ({
    AuthActions: bindActionCreators(authActions, dispatch)
  })
)(LoginContainer);
