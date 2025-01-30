import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Icon } from 'app/shared/component/Icon';
import { Text } from 'app/shared/component/Text';
import { UnauthenticatedContainer } from 'app/shared/component/UnauthenticatedContainer';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import { InputText } from 'primereact/inputtext';
import { Password } from 'primereact/password';
import React, { useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { isEmail } from 'react-jhipster';
import { Link, useNavigate } from 'react-router-dom';
import { handleRegister, reset } from './register.reducer';

export const RegisterPage = () => {
  const [password, setPassword] = useState('');
  const [passwordConfirmation, setPasswordConfirmation] = useState('');
  const [firstPasswordUpdated, setFirstPasswordUpdated] = useState<boolean>(false);
  const [secondPasswordUpdated, setSecondPasswordUpdated] = useState<boolean>(false);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

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
      window.showToast('success', 'Succès', `${successMessage}`);
      navigate('/login');
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
      <Card
        title={title}
        className="absolute w-30rem top-50 left-50 px-4 border-round-3xl shadow-8"
        style={{ transform: 'translate(-50%, -50%)' }}
      >
        <form onSubmit={handleValidSubmit}>
          <div className="flex flex-column gap-3">
            <div className="flex flex-column gap-2">
              <div className="p-inputgroup">
                <span className="p-inputgroup-addon">
                  <Icon icon="user" marginRight={false} colorSecondary />
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
                  <Icon icon="at" marginRight={false} colorSecondary />
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
                  <Icon icon="lock" marginRight={false} colorSecondary />
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
                  <Icon icon="lock" marginRight={false} colorSecondary />
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
            <Button type="submit" icon={<Icon icon="user-plus" />} label="Créer un compte" className="my-3" />
            <div className="flex justify-content-center">
              <Link to="/login">Déjà un compte ? Se connecter</Link>
            </div>
          </div>
        </form>
      </Card>
    </UnauthenticatedContainer>
  );

  // return (
  //   <div>
  //     <Row className="justify-content-center">
  //       <Col md="8">
  //         <h1 id="register-title" data-cy="registerTitle">
  //           Registration
  //         </h1>
  //       </Col>
  //     </Row>
  //     <Row className="justify-content-center">
  //       <Col md="8">
  //         <ValidatedForm id="register-form" onSubmit={handleValidSubmit}>
  //           <ValidatedField
  //             name="username"
  //             label="Username"
  //             placeholder="Your username"
  //             validate={{
  //               required: { value: true, message: 'Your username is required.' },
  //               pattern: {
  //                 value: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/,
  //                 message: 'Your username is invalid.',
  //               },
  //               minLength: { value: 1, message: 'Your username is required to be at least 1 character.' },
  //               maxLength: { value: 50, message: 'Your username cannot be longer than 50 characters.' },
  //             }}
  //             data-cy="username"
  //           />
  //           <ValidatedField
  //             name="email"
  //             label="Email"
  //             placeholder="Your email"
  //             type="email"
  //             validate={{
  //               required: { value: true, message: 'Your email is required.' },
  //               minLength: { value: 5, message: 'Your email is required to be at least 5 characters.' },
  //               maxLength: { value: 254, message: 'Your email cannot be longer than 50 characters.' },
  //               validate: v => isEmail(v) || 'Your email is invalid.',
  //             }}
  //             data-cy="email"
  //           />
  //           <ValidatedField
  //             name="firstPassword"
  //             label="New password"
  //             placeholder="New password"
  //             type="password"
  //             onChange={updatePassword}
  //             validate={{
  //               required: { value: true, message: 'Your password is required.' },
  //               minLength: { value: 4, message: 'Your password is required to be at least 4 characters.' },
  //               maxLength: { value: 50, message: 'Your password cannot be longer than 50 characters.' },
  //             }}
  //             data-cy="firstPassword"
  //           />
  //           <PasswordStrengthBar password={password} />
  //           <ValidatedField
  //             name="secondPassword"
  //             label="New password confirmation"
  //             placeholder="Confirm the new password"
  //             type="password"
  //             validate={{
  //               required: { value: true, message: 'Your confirmation password is required.' },
  //               minLength: { value: 4, message: 'Your confirmation password is required to be at least 4 characters.' },
  //               maxLength: { value: 50, message: 'Your confirmation password cannot be longer than 50 characters.' },
  //               validate: v => v === password || 'The password and its confirmation do not match!',
  //             }}
  //             data-cy="secondPassword"
  //           />
  //           <Button id="register-submit" color="primary" type="submit" data-cy="submit">
  //             Register
  //           </Button>
  //         </ValidatedForm>
  //         <p>&nbsp;</p>
  //         <Alert color="warning">
  //           <span>If you want to </span>
  //           <Link to="/login" className="alert-link">
  //             sign in
  //           </Link>
  //           <span>
  //             , you can try the default accounts:
  //             <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;) <br />- User (login=&quot;user&quot; and
  //             password=&quot;user&quot;).
  //           </span>
  //         </Alert>
  //       </Col>
  //     </Row>
  //   </div>
  // );
};

export default RegisterPage;
