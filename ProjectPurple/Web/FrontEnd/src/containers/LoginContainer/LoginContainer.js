import React, { Component } from "react";
import { LoginWrapper, LoginForm } from "../../components";
import { isEmail, isLength } from "validator";
import { Field, reduxForm } from "redux-form";

export default class LoginContainer extends Component {
  state = {};

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
        console.log("this is wrong email format");
        return false;
      }
      return true;
    },
    password: value => {
      if (!isLength(value, { min: 6 })) {
        console.log("password must be more than 6");
        return false;
      }
      return true;
    },
    passwordConfirm: value => {
      if (this.props.password !== value) {
        console.log("password and password confirm are not correct");
        return false;
      }
      return true;
    }
  };

  render() {
    return (
      <LoginWrapper>
        <LoginForm login={this.doLogin} />
      </LoginWrapper>
    );
  }
}
