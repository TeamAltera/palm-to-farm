import React from "react";
import { Button } from "semantic-ui-react";
import "./LoginButtons.css";

const LoginButtons = () => (
  <div className="LoginButtons">
    <Button
      content="Sign In"
      color="instagram"
      size="large"
      className="signin"
    />
    <Button
      content="Sign Up"
      color="instagram"
      size="large"
      className="signup"
    />
  </div>
);

export default LoginButtons;
