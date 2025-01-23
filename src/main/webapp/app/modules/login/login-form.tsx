import { Checkbox } from 'app/shared/component/Checkbox';
import { Icon } from 'app/shared/component/Icon';
import { Text } from 'app/shared/component/Text';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import { InputText } from 'primereact/inputtext';
import { Message } from 'primereact/message';
import React from 'react';
import { Controller, useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';

export interface LoginFormProps {
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
    control,
    formState: { errors, touchedFields },
  } = useForm({ defaultValues: { username: '', password: '', rememberMe: false }, mode: 'onTouched' });

  const { loginError } = props;

  const handleLoginSubmit = e => {
    handleSubmit(login)(e);
  };

  const title = (
    <div className="flex justify-content-between align-items-end">
      <Text>CanoStats</Text>
      <img src="content/images/logo-canopee.svg" height="100" />
    </div>
  );

  const loginErrorMessage = (
    <div>
      <strong>Échec de la connexion !</strong> Veuillez vérifier vos identifiants et réessayer.
    </div>
  );

  return (
    <Card
      title={title}
      className="absolute w-30rem top-50 left-50 px-4 border-round-3xl shadow-8"
      style={{ transform: 'translate(-50%, -50%)' }}
    >
      <form onSubmit={handleLoginSubmit}>
        <div className="flex flex-column gap-3">
          {loginError && <Message severity="error" content={loginErrorMessage} />}
          <div className="p-inputgroup">
            <span className="p-inputgroup-addon">
              <Icon icon="user" marginRight={false} colorSecondary />
            </span>
            <Controller
              name="username"
              control={control}
              render={({ field }) => <InputText {...field} placeholder="Nom d'utilisateur" />}
            />
          </div>
          <div className="p-inputgroup">
            <span className="p-inputgroup-addon">
              <Icon icon="lock" marginRight={false} colorSecondary />
            </span>
            <Controller
              name="password"
              control={control}
              render={({ field }) => <InputText {...field} placeholder="Mot de passe" type="password" />}
            />
          </div>
          <div className="flex justify-content-between align-items-center mt-3">
            <Controller
              name="rememberMe"
              control={control}
              render={({ field }) => (
                <Checkbox label="Se souvenir de moi" checked={field.value} onChange={checked => field.onChange(checked)} />
              )}
            />
            <Link to="/account/reset/request">Mot de passe oublié ?</Link>
          </div>
          <div className="flex justify-content-center mb-3">
            <Link to="/account/register">Créer un compte</Link>
          </div>
          <Button type="submit" icon={<Icon icon="right-to-bracket" />} label="Se connecter" />
        </div>
      </form>
    </Card>
  );
};

export default LoginForm;
