import { Text } from 'app/shared/component/Text';
import { Divider } from 'primereact/divider';
import React from 'react';

const Footer = () => (
  <div className="flex flex-column mb-2">
    <Divider />
    <div className="flex justify-content-center align-items-center gap-2">
      <img src="content/images/logo-gg.png" height="30"></img>
      <div className="text-sm">
        <Text>{`Développé par Grégory Gautier - ${new Date().getFullYear()}`}</Text>
      </div>
    </div>
  </div>
);

export default Footer;
