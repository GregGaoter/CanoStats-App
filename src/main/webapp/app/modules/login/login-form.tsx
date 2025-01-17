import React from 'react';
import { ValidatedField } from 'react-jhipster';
import { Link } from 'react-router-dom';
import { type FieldError, useForm } from 'react-hook-form';
import { Card } from 'primereact/card';
import { InputText } from 'primereact/inputtext';
import { Icon } from 'app/shared/component/Icon';
import { Password } from 'primereact/password';
import { Checkbox } from 'primereact/checkbox';
import { Button } from 'primereact/button';

export interface LoginFormProps {
  showModal: boolean;
  loginError: boolean;
  handleLogin: (username: string, password: string, rememberMe: boolean) => void;
  handleClose: () => void;
}

const LoginForm = (props: LoginFormProps) => {
  const login = ({ username, password, rememberMe }) => {
    props.handleLogin(username, password, rememberMe);
  };

  const {
    handleSubmit,
    register,
    formState: { errors, touchedFields },
  } = useForm({ mode: 'onTouched' });

  const { loginError, handleClose } = props;

  const handleLoginSubmit = e => {
    handleSubmit(login)(e);
  };

  const header = <img src="content/images/logo-canopee.svg" height="200" />;
  const background = <img src="content/images/login-background.png" />;

  return (
    <div
      className="w-full h-full"
      // className="flex align-items-center justify-content-center w-full h-full bg-contain bg-center bg-no-repeat"
      // style={{ backgroundImage: `url("content/images/login-background.png")` }}
    >
      <div className="absolute w-full h-full surface-ground"></div>
      {/* <div src="content/images/6213209.jpg" className="absolute w-full h-full bg-cover bg-center bg-no-repeat z-0 p-0" /> */}
      <Card title="CanoStats" className="absolute w-30rem top-50 left-50 p-4 z-1" style={{ transform: 'translate(-50%, -50%)' }}>
        <div className="flex flex-column gap-3">
          <div className="p-inputgroup">
            <span className="p-inputgroup-addon">
              <Icon icon="envelope" marginRight={false} />
            </span>
            <InputText placeholder="Adresse email" type="email" />
          </div>
          <div className="p-inputgroup">
            <span className="p-inputgroup-addon">
              <Icon icon="lock" marginRight={false} />
            </span>
            <InputText placeholder="Mot de passe" type="password" />
          </div>
          <div className="flex justify-content-around mt-2">
            <div className="flex align-items-center gap-2">
              <Checkbox checked={false} />
              <label>Se rappeler de moi</label>
            </div>
            <label>Reset password</label>
          </div>
          <Button label="Se connecter" className="mt-3" />
        </div>
      </Card>
    </div>
  );
};

export default LoginForm;
