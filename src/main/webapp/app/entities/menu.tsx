import MenuItem from 'app/shared/layout/menus/menu-item';
import React from 'react';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="chart-line" to="/mouvements-stock">
        Mouvements de stock
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
