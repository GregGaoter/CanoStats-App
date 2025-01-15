import React, { useContext, useEffect } from 'react';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { Alert, Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { handlePasswordResetInit, reset } from '../password-reset.reducer';
import { ToastContext } from 'app/shared/component/ToastContext';

export const PasswordResetInit = () => {
  const dispatch = useAppDispatch();
  const toast = useContext(ToastContext);

  useEffect(
    () => () => {
      dispatch(reset());
    },
    [],
  );

  const handleValidSubmit = ({ email }) => {
    dispatch(handlePasswordResetInit(email));
  };

  const successMessage = useAppSelector(state => state.passwordReset.successMessage);

  useEffect(() => {
    if (successMessage) {
      toast.current?.show({ severity: 'success', summary: 'Succès', detail: `${successMessage}` });
    }
  }, [successMessage]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1>Reset your password</h1>
          <Alert color="warning">
            <p>Enter the email address you used to register</p>
          </Alert>
          <ValidatedForm onSubmit={handleValidSubmit}>
            <ValidatedField
              name="email"
              label="Email"
              placeholder="Your email"
              type="email"
              validate={{
                required: { value: true, message: 'Your email is required.' },
                minLength: { value: 5, message: 'Your email is required to be at least 5 characters.' },
                maxLength: { value: 254, message: 'Your email cannot be longer than 50 characters.' },
                validate: v => isEmail(v) || 'Your email is invalid.',
              }}
              data-cy="emailResetPassword"
            />
            <Button color="primary" type="submit" data-cy="submit">
              Reset password
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default PasswordResetInit;
