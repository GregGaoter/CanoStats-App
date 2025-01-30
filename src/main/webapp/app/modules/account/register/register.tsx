import { useAppDispatch, useAppSelector } from 'app/config/store';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import React, { useEffect, useState } from 'react';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { Link } from 'react-router-dom';
import { handleRegister, reset } from './register.reducer';
import { Card } from 'primereact/card';
import { Message } from 'primereact/message';
import { Icon } from 'app/shared/component/Icon';
import { Text } from 'app/shared/component/Text';
import { Controller, useForm, FieldErrors } from 'react-hook-form';
import { InputText } from 'primereact/inputtext';
import { Checkbox } from 'primereact/checkbox';
import { Button } from 'primereact/button';
import { UnauthenticatedContainer } from 'app/shared/component/UnauthenticatedContainer';

export const RegisterPage = () => {
  const [password, setPassword] = useState('');
  const dispatch = useAppDispatch();

  const {
    handleSubmit,
    control,
    formState: { errors, touchedFields },
  } = useForm({ defaultValues: { username: '', email: '', firstPassword: '', secondPassword: '' }, mode: 'onTouched' });

  useEffect(
    () => () => {
      dispatch(reset());
    },
    [],
  );

  const register = ({ username, email, firstPassword }) => {
    dispatch(handleRegister({ login: username, email, password: firstPassword, langKey: 'fr' }));
  };

  const handleValidSubmit = e => {
    handleSubmit(register)(e);
  };

  const updatePassword = event => setPassword(event.target.value);

  const successMessage = useAppSelector(state => state.register.successMessage);

  useEffect(() => {
    if (successMessage) {
      window.showToast('success', 'Succès', `${successMessage}`);
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
                    required: true,
                    pattern: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/,
                    min: 2,
                    max: 50,
                  }}
                  render={({ field }) => <InputText {...field} placeholder="Nom d'utilisateur" invalid={!!errors.username} />}
                />
              </div>
              {errors.username?.type === 'required' && (
                <Text color="text-red-300" className="text-sm">{`Votre nom d'utilisateur est requis.`}</Text>
              )}
              {errors.username?.type === 'pattern' && (
                <Text color="text-red-300" className="text-sm">{`Votre nom d'utilisateur est invalide.`}</Text>
              )}
              {errors.username?.type === 'min' && (
                <Text color="text-red-300" className="text-sm">{`Votre nom d'utilisateur doit comporter au moins 2 caractères.`}</Text>
              )}
              {errors.username?.type === 'max' && (
                <Text color="text-red-300" className="text-sm">{`Votre nom d'utilisateur ne peut pas dépasser 50 caractères.`}</Text>
              )}
            </div>
            <div className="p-inputgroup">
              <span className="p-inputgroup-addon">
                <Icon icon="at" marginRight={false} colorSecondary />
              </span>
              <Controller
                name="email"
                control={control}
                render={({ field }) => <InputText {...field} placeholder="Email" type="email" invalid={!!errors.email} />}
              />
            </div>
            <div className="p-inputgroup">
              <span className="p-inputgroup-addon">
                <Icon icon="lock" marginRight={false} colorSecondary />
              </span>
              <Controller
                name="firstPassword"
                control={control}
                render={({ field }) => (
                  <InputText
                    {...field}
                    placeholder="Mot de passe"
                    type="password"
                    invalid={!!errors.firstPassword}
                    onChange={updatePassword}
                  />
                )}
              />
            </div>
            <PasswordStrengthBar password={password} />
            <div className="p-inputgroup">
              <span className="p-inputgroup-addon">
                <Icon icon="lock" marginRight={false} colorSecondary />
              </span>
              <Controller
                name="secondPassword"
                control={control}
                render={({ field }) => (
                  <InputText {...field} placeholder="Confirmer le mot de passe" type="password" invalid={!!errors.secondPassword} />
                )}
              />
            </div>
            <Button type="submit" icon={<Icon icon="user-plus" />} label="Créer un compte" />
            <div className="flex justify-content-center mt-3">
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
