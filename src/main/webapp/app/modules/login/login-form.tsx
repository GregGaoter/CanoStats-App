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

  const title = (
    <div className="flex justify-content-between align-items-end">
      <div className="p-card-title">CanoStats</div>
      <img src="content/images/logo-canopee.svg" height="100" />
    </div>
  );

  return (
    <Card
      title={title}
      className="absolute w-30rem top-50 left-50 px-4 border-round-3xl shadow-8"
      style={{ transform: 'translate(-50%, -50%)' }}
    >
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
        <Button label="Se connecter" className="mt-3" />
      </div>
    </Card>
  );
};

export default LoginForm;
