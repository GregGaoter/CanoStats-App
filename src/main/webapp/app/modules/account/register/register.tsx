import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Icon } from 'app/shared/component/Icon';
import { Text } from 'app/shared/component/Text';
import { UnauthenticatedContainer } from 'app/shared/component/UnauthenticatedContainer';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import { InputText } from 'primereact/inputtext';
import { Messages } from 'primereact/messages';
import { Password } from 'primereact/password';
import React, { useEffect, useRef, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { isEmail } from 'react-jhipster';
import { Link } from 'react-router-dom';
import { handleRegister, reset } from './register.reducer';

export const RegisterPage = () => {
  const [password, setPassword] = useState('');
  const [passwordConfirmation, setPasswordConfirmation] = useState('');
  const [firstPasswordUpdated, setFirstPasswordUpdated] = useState<boolean>(false);
  const [secondPasswordUpdated, setSecondPasswordUpdated] = useState<boolean>(false);
  const [disableSubmit, setDisableSubmit] = useState<boolean>(false);
  const dispatch = useAppDispatch();
  const messages = useRef<Messages>(null);

  const {
    handleSubmit,
    control,
    formState: { errors, touchedFields },
  } = useForm({ defaultValues: { username: '', email: '' }, mode: 'onTouched' });

  useEffect(
    () => () => {
      dispatch(reset());
    },
    [],
  );

  const register = ({ username, email }) => {
    dispatch(handleRegister({ login: username, email, password, langKey: 'fr' }));
  };

  const handleValidSubmit = e => {
    handleSubmit(register)(e);
  };

  const updatePassword = event => {
    setPassword(event.target.value);
    setFirstPasswordUpdated(true);
  };

  const updatePasswordConfirmation = event => {
    setPasswordConfirmation(event.target.value);
    setSecondPasswordUpdated(true);
  };

  const successMessage = useAppSelector(state => state.register.successMessage);

  useEffect(() => {
    if (successMessage) {
      setDisableSubmit(true);
      messages.current.clear();
      messages.current.show({
        id: '1',
        sticky: true,
        severity: 'success',
        summary: 'Inscription enregistrée !',
        detail: 'Veuillez vérifier votre e-mail pour confirmation et activation de votre compte.',
        closable: false,
      });
    }
  }, [successMessage]);

  const title = (
    <div className="flex justify-content-center align-items-center gap-2">
      <img src="content/images/logo-canopee.svg" height="100" />
      <Text>CanoStats</Text>
    </div>
  );

  return (
    <UnauthenticatedContainer>
      <div className="flex flex-column align-items-center gap-2 absolute w-9 top-50 left-50" style={{ transform: 'translate(-50%, -50%)' }}>
        <Messages ref={messages} style={{ width: '52rem' }} />
        <Card title={title} className="px-4 border-round-3xl shadow-8 w-30rem">
          <form onSubmit={handleValidSubmit}>
            <div className="flex flex-column gap-3">
              <div className="flex flex-column gap-2">
                <div className="p-inputgroup">
                  <span className="p-inputgroup-addon">
                    <Icon icon="user" colorSecondary />
                  </span>
                  <Controller
                    name="username"
                    control={control}
                    rules={{
                      required: "Votre nom d'utilisateur est requis.",
                      pattern: {
                        value: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/,
                        message: "Votre nom d'utilisateur est invalide.",
                      },
                      minLength: {
                        value: 2,
                        message: "Votre nom d'utilisateur doit comporter au moins 2 caractères.",
                      },
                      maxLength: {
                        value: 50,
                        message: "Votre nom d'utilisateur ne peut pas dépasser 50 caractères.",
                      },
                    }}
                    render={({ field }) => <InputText {...field} placeholder="Nom d'utilisateur" invalid={!!errors.username} />}
                  />
                </div>
                {!!errors.username && (
                  <Text color="text-red-300" className="text-sm">
                    {errors.username.message}
                  </Text>
                )}
              </div>
              <div className="flex flex-column gap-2">
                <div className="p-inputgroup">
                  <span className="p-inputgroup-addon">
                    <Icon icon="at" colorSecondary />
                  </span>
                  <Controller
                    name="email"
                    control={control}
                    rules={{
                      required: 'Votre email est requis.',
                      minLength: {
                        value: 5,
                        message: 'Votre email doit comporter au moins 5 caractères.',
                      },
                      maxLength: {
                        value: 254,
                        message: 'Votre email ne peut pas dépasser 254 caractères.',
                      },
                      validate: v => isEmail(v) || 'Votre email est invalide.',
                    }}
                    render={({ field }) => <InputText {...field} placeholder="Email" type="email" invalid={!!errors.email} />}
                  />
                </div>
                {!!errors.email && (
                  <Text color="text-red-300" className="text-sm">
                    {errors.email.message}
                  </Text>
                )}
              </div>
              <div className="flex flex-column gap-2">
                <div className="p-inputgroup">
                  <span className="p-inputgroup-addon">
                    <Icon icon="lock" colorSecondary />
                  </span>
                  <Password
                    value={password}
                    onChange={updatePassword}
                    onBlur={() => setFirstPasswordUpdated(true)}
                    placeholder="Mot de passe"
                    invalid={(firstPasswordUpdated && !password) || (password && password.length < 4) || (password && password.length > 50)}
                    promptLabel="Choisir un mot de passe"
                    weakLabel="Faible"
                    mediumLabel="Moyen"
                    strongLabel="Fort"
                  />
                </div>
                {firstPasswordUpdated && !password && (
                  <Text color="text-red-300" className="text-sm">
                    Votre mot de passe est requis.
                  </Text>
                )}
                {password && password.length < 4 && (
                  <Text color="text-red-300" className="text-sm">
                    Votre mot de passe doit comporter au moins 4 caractères.
                  </Text>
                )}
                {password && password.length > 50 && (
                  <Text color="text-red-300" className="text-sm">
                    Votre mot de passe ne peut pas dépasser 50 caractères.
                  </Text>
                )}
              </div>
              <div className="flex flex-column gap-2">
                <div className="p-inputgroup">
                  <span className="p-inputgroup-addon">
                    <Icon icon="lock" colorSecondary />
                  </span>
                  <Password
                    value={passwordConfirmation}
                    onChange={updatePasswordConfirmation}
                    onBlur={() => setSecondPasswordUpdated(true)}
                    placeholder="Confirmation du mot de passe"
                    invalid={
                      (secondPasswordUpdated && !passwordConfirmation) ||
                      (passwordConfirmation && passwordConfirmation.length < 4) ||
                      (passwordConfirmation && passwordConfirmation.length > 50) ||
                      password !== passwordConfirmation
                    }
                    feedback={false}
                  />
                </div>
                {secondPasswordUpdated && !passwordConfirmation && (
                  <Text color="text-red-300" className="text-sm">
                    Votre confirmation mot de passe est requis.
                  </Text>
                )}
                {passwordConfirmation && passwordConfirmation.length < 4 && (
                  <Text color="text-red-300" className="text-sm">
                    Votre confirmation mot de passe doit comporter au moins 4 caractères.
                  </Text>
                )}
                {passwordConfirmation && passwordConfirmation.length > 50 && (
                  <Text color="text-red-300" className="text-sm">
                    Votre confirmation mot de passe ne peut pas dépasser 50 caractères.
                  </Text>
                )}
                {password !== passwordConfirmation && (
                  <Text color="text-red-300" className="text-sm">
                    Le mot de passe et sa confirmation ne correspondent pas !
                  </Text>
                )}
              </div>
              <Button type="submit" icon={<Icon icon="user-plus" />} label="Créer un compte" className="my-3" disabled={disableSubmit} />
              <div className="flex justify-content-center">
                <Link to="/login">Déjà un compte ? Se connecter</Link>
              </div>
            </div>
          </form>
        </Card>
      </div>
    </UnauthenticatedContainer>
  );
};

export default RegisterPage;
