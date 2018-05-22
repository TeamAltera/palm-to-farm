import React, { Fragment } from 'react';
import { Button, Form, Grid, Header, Segment } from 'semantic-ui-react';
import './SignForm.css';

const SignForm = () => (
  <Fragment className="sign-form">
    <Grid className="sign__grid">
      <Grid.Column style={{ minWidth: 450 }}>
        <Form>
          <Segment raised>
            <Header as="h1" color="purple" textAlign="center">
              Purple ID 만들기
            </Header>
            <Form.Group widths="equal">
              <Form.Input fluid placeholder="성" />
              <Form.Input fluid placeholder="이름" />
            </Form.Group>
            <Form.Field className="sign__grid__form__field">
              <Form.Input
                fluid
                icon="user"
                iconPosition="left"
                placeholder="name@example.com"
              />
            </Form.Field>
            <Form.Group widths="equal">
              <Form.Input
                fluid
                icon="lock"
                iconPosition="left"
                placeholder="비밀번호"
                type="password"
              />
              <Form.Input
                fluid
                icon="lock"
                iconPosition="left"
                placeholder="비밀번호 확인"
                type="password"
              />
            </Form.Group>
            <Button color="purple" fluid size="large">
              시작하기
            </Button>
          </Segment>
        </Form>
      </Grid.Column>
    </Grid>
  </Fragment>
);

export default SignForm;
