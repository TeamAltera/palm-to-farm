import React, { Fragment } from 'react';
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
import './LoginForm.css';
import PropTypes from 'prop-types';

const LoginForm = ({ login }) => (
  <Fragment className="login-form">
    <Grid className="login__grid">
      <Grid.Column style={{ minWidth: 300 }}>
        <Form>
          <Segment raised>
            <Header as="h1" color="purple" textAlign="center">
              로그인 Purple로 이동
            </Header>
            <Form.Input
              fluid
              icon="user"
              iconPosition="left"
              placeholder="E-mail address"
            />
            <Form.Input
              fluid
              icon="lock"
              iconPosition="left"
              placeholder="Password"
              type="password"
            />
            <Button
              color="purple"
              fluid
              size="large"
              onClick={() => {
                login();
              }}>
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
  </Fragment>
);

LoginForm.proptypes = {
  login: PropTypes.func.isRequired,
};

export default LoginForm;
