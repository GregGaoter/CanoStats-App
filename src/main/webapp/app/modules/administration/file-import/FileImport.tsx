import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { apiUrl as mouvementsStockApiUrl } from 'app/entities/mouvements-stock/mouvements-stock.reducer';
import { Icon } from 'app/shared/component/Icon';
import { LabelledControl } from 'app/shared/component/LabelledControl';
import axios, { AxiosResponse } from 'axios';
import dayjs, { Dayjs } from 'dayjs';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import { Dropdown } from 'primereact/dropdown';
import {
  FileUpload,
  FileUploadErrorEvent,
  FileUploadHeaderTemplateOptions,
  FileUploadOptions,
  FileUploadSelectEvent,
  FileUploadUploadEvent,
  ItemTemplateOptions,
} from 'primereact/fileupload';
import { InputText } from 'primereact/inputtext';
import { Messages } from 'primereact/messages';
import { Tag } from 'primereact/tag';
import { Tooltip } from 'primereact/tooltip';
import React, { useEffect, useRef, useState } from 'react';

enum EntityType {
  MOUVEMENTS_STOCK = 'Mouvements de stock',
  PRODUITS = 'Produits',
}

interface ImportEntity {
  entity: EntityType;
  remoteUrl: string;
}

export const FileImport = () => {
  const importEntities: ImportEntity[] = [
    { entity: EntityType.MOUVEMENTS_STOCK, remoteUrl: '/api/mouvements-stocks/import' },
    { entity: EntityType.PRODUITS, remoteUrl: '/api/produits/import' },
  ];

  const [totalSize, setTotalSize] = useState<number>(0);
  const [importEntity, setImportEntity] = useState<ImportEntity>(importEntities[0]);
  const [mouvementsStockMaxDate, setMouvementsStockMaxDate] = useState<Dayjs>(undefined);
  const fileUploadRef = useRef<FileUpload>(null);
  const messages = useRef<Messages>(null);

  const fetchMouvementsStockMaxDate = async () => {
    const maxDate: AxiosResponse<Dayjs, any> = await axios.get<Dayjs>(`${mouvementsStockApiUrl}/max-date`);
    setMouvementsStockMaxDate(maxDate.data);
  };

  useEffect(() => {
    fetchMouvementsStockMaxDate();
  }, []);

  const onTemplateSelect = (e: FileUploadSelectEvent) => {
    let _totalSize = totalSize;
    const files = e.files;
    for (let i = 0; i < files.length; i++) {
      _totalSize += files[i].size || 0;
    }
    setTotalSize(_totalSize);
  };

  const onTemplateUpload = (e: FileUploadUploadEvent) => {
    let _totalSize = 0;
    e.files.forEach(file => {
      _totalSize += file.size || 0;
    });
    setTotalSize(_totalSize);
    messages.current?.clear();
    messages.current?.show({
      id: '1',
      sticky: true,
      severity: 'success',
      summary: 'Succès',
      detail: e.xhr.responseText,
      closable: false,
    });
  };

  const onTemplateRemove = (file: File, callback: any) => {
    setTotalSize(totalSize - file.size);
    callback();
  };

  const onTemplateClear = () => {
    setTotalSize(0);
  };

  const handleError = (event: FileUploadErrorEvent) => {
    setTotalSize(0);
    messages.current?.clear();
    messages.current?.show({
      id: '1',
      sticky: true,
      severity: 'error',
      summary: 'Erreur',
      detail: event.xhr.responseText,
      closable: false,
    });
  };

  const headerTemplate = (options: FileUploadHeaderTemplateOptions) => {
    const { className, chooseButton, uploadButton, cancelButton } = options;
    return (
      <div className={className} style={{ backgroundColor: 'transparent', display: 'flex', alignItems: 'center' }}>
        {chooseButton}
        {uploadButton}
        {cancelButton}
      </div>
    );
  };

  const itemTemplate = (inFile: object, props: ItemTemplateOptions) => {
    const file = inFile as File;
    return (
      <div className="flex align-items-center justify-content-between text-color-secondary">
        <div className="flex align-items-center gap-3">
          <img src="content/images/fichier-json.png" height="40"></img>
          {file.name}
          <Tag value={props.formatSize} severity="info" />
        </div>
        <Button
          type="button"
          icon={<FontAwesomeIcon icon="xmark" />}
          className="p-button-outlined p-button-rounded p-button-danger ml-auto"
          onClick={() => onTemplateRemove(file, props.onRemove)}
        />
      </div>
    );
  };

  const emptyTemplate = () => {
    return (
      <div className="flex align-items-center flex-column">
        <FontAwesomeIcon
          icon="image"
          className="mt-3 p-5"
          style={{ fontSize: '5em', borderRadius: '50%', backgroundColor: 'var(--surface-b)', color: 'var(--surface-d)' }}
        />
        <span style={{ fontSize: '1.2em', color: 'var(--text-color-secondary)' }} className="my-5">
          Glissez et déposez le fichier JSON ici
        </span>
      </div>
    );
  };

  const chooseOptions: FileUploadOptions = {
    icon: <Icon icon="image" />,
    iconOnly: true,
    className: 'custom-choose-btn p-button-rounded p-button-outlined',
  };
  const uploadOptions = {
    icon: <Icon icon="cloud-arrow-up" />,
    iconOnly: true,
    className: 'custom-upload-btn p-button-success p-button-rounded p-button-outlined',
  };
  const cancelOptions = {
    icon: <Icon icon="xmark" />,
    iconOnly: true,
    className: 'custom-cancel-btn p-button-danger p-button-rounded p-button-outlined',
  };

  return (
    <Card title="Importer des données">
      <Tooltip target=".custom-choose-btn" content="Choisir" position="bottom" />
      <Tooltip target=".custom-upload-btn" content="Télécharger" position="bottom" />
      <Tooltip target=".custom-cancel-btn" content="Effacer" position="bottom" />
      <div className="flex flex-column gap-3">
        <div className="flex gap-3">
          <LabelledControl
            label="Type de données"
            control={
              <Dropdown value={importEntity} onChange={e => setImportEntity(e.value)} options={importEntities} optionLabel="entity" />
            }
          />
          {importEntity.entity === EntityType.MOUVEMENTS_STOCK && (
            <LabelledControl
              label="Dernier import"
              control={<InputText value={`${dayjs(mouvementsStockMaxDate).format('DD.MM.YYYY')}`} disabled />}
            />
          )}
        </div>
        <FileUpload
          ref={fileUploadRef}
          name="importFile"
          url={importEntity.remoteUrl}
          multiple={false}
          accept="application/json"
          maxFileSize={50000000}
          onUpload={onTemplateUpload}
          onSelect={onTemplateSelect}
          onError={handleError}
          onClear={onTemplateClear}
          headerTemplate={headerTemplate}
          itemTemplate={itemTemplate}
          emptyTemplate={emptyTemplate}
          chooseOptions={chooseOptions}
          uploadOptions={uploadOptions}
          cancelOptions={cancelOptions}
        />
        <Messages ref={messages} />
      </div>
    </Card>
  );
};
